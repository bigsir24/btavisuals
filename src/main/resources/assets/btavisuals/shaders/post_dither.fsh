#version 120

uniform sampler2D colortex0;
uniform sampler2D depthtex0;

// Color correction params
uniform float brightness;
uniform float contrast;
uniform float exposure;
uniform float saturation;
uniform float rMod;
uniform float gMod;
uniform float bMod;

uniform float fxaa;
uniform float heatHaze;
uniform int bloom;

uniform int dimension;
uniform int shaderEffect;

uniform float width;
uniform float height;
uniform float frameTimeCounter;

uniform bool tonemap;
uniform bool dither;
uniform float bayer[256];
uniform vec3 step;
uniform int bayerSize;
uniform float bayerMax;
uniform float bayerBrightness;

varying vec2 texcoord;

#define PI 3.14159

// FXAA defines
#define FXAA_REDUCE_MIN   (1.0/ 128.0)
#define FXAA_REDUCE_MUL   (1.0 / 8.0)
float FXAA_SPAN_MAX = 8.0f * fxaa;

vec4 applyFXAA(vec2 fragCoord, sampler2D tex, vec2 uViewportSize) {
    vec4 color;
    vec2 inverseVP = vec2(1.0 / uViewportSize.x, 1.0 / uViewportSize.y);
    vec3 rgbNW = texture2D(tex, (fragCoord + vec2(-1.0, -1.0)) * inverseVP).xyz;
    vec3 rgbNE = texture2D(tex, (fragCoord + vec2(1.0, -1.0)) * inverseVP).xyz;
    vec3 rgbSW = texture2D(tex, (fragCoord + vec2(-1.0, 1.0)) * inverseVP).xyz;
    vec3 rgbSE = texture2D(tex, (fragCoord + vec2(1.0, 1.0)) * inverseVP).xyz;
    vec3 rgbM  = texture2D(tex, fragCoord  * inverseVP).xyz;
    vec3 luma = vec3(0.299, 0.587, 0.114);
    float lumaNW = dot(rgbNW, luma);
    float lumaNE = dot(rgbNE, luma);
    float lumaSW = dot(rgbSW, luma);
    float lumaSE = dot(rgbSE, luma);
    float lumaM  = dot(rgbM,  luma);
    float lumaMin = min(lumaM, min(min(lumaNW, lumaNE), min(lumaSW, lumaSE)));
    float lumaMax = max(lumaM, max(max(lumaNW, lumaNE), max(lumaSW, lumaSE)));

    vec2 dir;
    dir.x = -((lumaNW + lumaNE) - (lumaSW + lumaSE));
    dir.y =  ((lumaNW + lumaSW) - (lumaNE + lumaSE));

    float dirReduce = max((lumaNW + lumaNE + lumaSW + lumaSE) *
    (0.25 * FXAA_REDUCE_MUL), FXAA_REDUCE_MIN);

    float rcpDirMin = 1.0 / (min(abs(dir.x), abs(dir.y)) + dirReduce);
    dir = min(vec2(FXAA_SPAN_MAX, FXAA_SPAN_MAX),
    max(vec2(-FXAA_SPAN_MAX, -FXAA_SPAN_MAX),
    dir * rcpDirMin)) * inverseVP;

    vec3 rgbA = 0.5 * (
    texture2D(tex, fragCoord * inverseVP + dir * (1.0 / 3.0 - 0.5)).xyz +
    texture2D(tex, fragCoord * inverseVP + dir * (2.0 / 3.0 - 0.5)).xyz);
    vec3 rgbB = rgbA * 0.5 + 0.25 * (
    texture2D(tex, fragCoord * inverseVP + dir * -0.5).xyz +
    texture2D(tex, fragCoord * inverseVP + dir * 0.5).xyz);

    float lumaB = dot(rgbB, luma);
    if ((lumaB < lumaMin) || (lumaB > lumaMax))
    color = vec4(rgbA, 1.0);
    else
    color = vec4(rgbB, 1.0);
    return color;
}

vec3 adjustBrightness(vec3 color, float value) {
    return color + value;
}

vec3 adjustContrast(vec3 color, float value) {
    return 0.5 + (1.0 + value) * (color - 0.5);
}

vec3 adjustExposure(vec3 color, float value) {
    return (1.0 + value) * color;
}

vec3 adjustSaturation(vec3 color, float value) {
    const vec3 luminosityFactor = vec3(0.2126, 0.7152, 0.0722);
    vec3 grayscale = vec3(dot(color, luminosityFactor));

    return mix(grayscale, color, 1.0 + value);
}

vec3 adjustColor(vec3 color, float rValue, float gValue, float bValue) {
    return vec3(color.r * rValue, color.g * gValue, color.b * bValue);
}

void Bloom(inout vec3 color) {
    vec3 blur = vec3(0.0f);

    int quality = 7 * bloom;
    float range = 10 * (3 - bloom);

    float qh = quality / 2.0f - 0.5f;
    float allStrengths = 0.0f;
    for(int i=0; i < quality; i++) {
        for(int j=0; j < quality; j++) {
            vec2 offset = vec2((i - qh) * range / width, (j - qh) * range / height);

            float dist = 1.33f - distance(vec2(qh), vec2(i, j)) / qh;

            float strength = dist;

            blur += pow(texture2D(colortex0, texcoord.xy + offset).rgb, vec3(2.2f)) * strength;
            allStrengths += strength;
        }
    }

    blur /= allStrengths;

    color.rgb *= 1.0f - (bloom * 0.1f);
    color.rgb += blur.rgb * 0.15f * bloom;
}

void HeatHaze(inout vec2 coord, float amount) {
    float depth = texture2D(depthtex0, coord.xy).r;
    float strength = pow(depth, 500);
    strength = (strength - 0.16) * 2.0f;
    strength = clamp(strength, 0.0f, 1.0f) * amount;

    vec2 wave = vec2(0.0f, 0.0f);

    float waveSize = 50;
    wave.x += sin(coord.y * 1.3 * waveSize + frameTimeCounter * 10);
    wave.x += sin(coord.y * 5.0 * waveSize + frameTimeCounter * 14);
    wave.y += sin(coord.x * 1.0 * waveSize + frameTimeCounter * 5) * 2;

    coord += wave * strength * 0.0001f;
}


vec3 colorEffects(vec3 tex) {
    if (shaderEffect == 1) {
        return vec3(tex.r, 0, 0);
    }
    return tex;
}


void main() {
    vec3 color = vec3(0.0);
    vec2 coord = texcoord;

    if (heatHaze > 0) {
        HeatHaze(coord, heatHaze);
    }

    if (fxaa > 0.01) {
        color = applyFXAA(vec2(coord.x * width, coord.y * height), colortex0, vec2(width, height)).rgb;
    } else {
        color = texture2D(colortex0, coord.xy).rgb;
    }

    if (bloom > 0) {
        Bloom(color);
    }

    // Perform color correction
    color = adjustBrightness(color, brightness);
    color = adjustContrast(color, contrast);
    color = adjustExposure(color, exposure);
    color = adjustSaturation(color, saturation);
    color = adjustColor(color, rMod, gMod, bMod);
    color = colorEffects(color);

    if (dither) {
        //float bayer[16] = float[16](0.0, 0.5, 0.125, 0.625, 0.75, 0.25, 0.875, 0.375, 0.1875, 0.6875, 0.0625, 0.5625, 0.9375, 0.4375, 0.8125, 0.3125);
        int w = (int(mod(gl_FragCoord.x, bayerSize)));
        int h = (int(mod(gl_FragCoord.y, bayerSize)));
        float bayerVal = bayerBrightness * (bayer[w + h*bayerSize] - bayerMax * 0.5);
        color.r = clamp(color.r + bayerVal, 0, 1);
        color.g = clamp(color.g + bayerVal, 0, 1);
        color.b = clamp(color.b + bayerVal, 0, 1);
    }

    if (tonemap) {
        vec3 diff = vec3(mod(color.r, step.r), mod(color.g, step.g), mod(color.b, step.b));
        color.r = color.r - diff.r + (diff.r > step.r * 0.5 ? step.r : 0);
        color.g = color.g - diff.g + (diff.g > step.g * 0.5 ? step.g : 0);
        color.b = color.b - diff.b + (diff.b > step.b * 0.5 ? step.b : 0);
    }

    gl_FragColor = vec4(color, 1.0);
}
