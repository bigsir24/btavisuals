#version 120

varying vec2 texcoord;
varying vec2 lightcoord;
varying vec3 lighting;
varying float diffuse1;
varying float diffuse2;
varying vec4 normalV;

varying vec3 light;

void main(){
    gl_Position = ftransform();

    vec4 normalVec = vec4(normalize(gl_NormalScale * gl_NormalMatrix * gl_Normal), 0);
    float directional1 = max(0.0, dot(normalVec, gl_LightSource[0].position));
    float directional2 = max(0.0, dot(normalVec, gl_LightSource[1].position));
    light = (gl_LightModel.ambient + gl_LightSource[0].diffuse * (directional1 + directional2)).xyz;

    texcoord = gl_MultiTexCoord0.xy;
    lightcoord = gl_MultiTexCoord1.xy;
}
