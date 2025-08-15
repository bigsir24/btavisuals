package bigsir.btavisuals;

import bigsir.btavisuals.mixin.shader.ShaderAccessor;
import bigsir.btavisuals.shader.Bayer;
import bigsir.btavisuals.shader.ShaderProviderJar;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.TooltipElement;
import net.minecraft.client.gui.options.components.BooleanOptionComponent;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.components.ToggleableOptionComponent;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.client.input.InputDevice;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.OptionBoolean;
import net.minecraft.client.option.OptionRange;
import net.minecraft.client.render.shader.Shader;
import net.minecraft.core.item.Items;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.util.helper.DamageType;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.ARBMultitexture;
import org.lwjgl.opengl.ARBTextureFloat;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import turniplabs.halplibe.util.OptionsInitEntrypoint;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BTAVisuals implements ModInitializer, ClientStartEntrypoint, OptionsInitEntrypoint {
    public static final String MOD_ID = "btavisuals";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final float[] SIDE_LIGHT_FIXED = new float[]{0.5F, 1.0F, 0.6F, 0.6F, 0.8F, 0.8F};
	public static final float[] SIDE_LIGHT_DEFAULT = new float[]{0.5F, 1.0F, 0.8F, 0.8F, 0.6F, 0.6F};
	public static final float[] NO_SHADE = new float[]{1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F};
	public static final float[][] sideLights = new float[][]{SIDE_LIGHT_DEFAULT, SIDE_LIGHT_FIXED, NO_SHADE};
	public static final double[][] invLights = new double[][]{
		{0.2, 0.7},
		{0.7, 0.2},
		{1, 1}
	};
	public static OptionsPage modPage;
	public static OptionBoolean billboardItems;
	public static OptionBoolean smoothWater;
	public static OptionRange sideLightDirection;
	public static OptionRange farPlaneDistance;
	public static OptionRange fogDistance;
	public static OptionRange cloudDistance;
	public static OptionRange cloudFogDistance;
	public static OptionBoolean tintedClouds;
	public static OptionBoolean dualPassOnFast;
	public static OptionRange leavesMode;
	public static OptionRange cloudMode;
	public static OptionRange grassMode;
	public static OptionRange rainOpacity;
	public static OptionRange snowOpacity;
	public static OptionRange snowType;
	public static OptionRange rainParticleAmount;
	public static OptionBoolean showOwnNameplate;
	public static OptionBoolean smoothInvModel;
	public static OptionBoolean allSteve;
	public static OptionRange hideNameplates;
	public static OptionRange colorMode;
	public static OptionBoolean fixHeldBlockShading;
	public static OptionRange animalLabels;
	public static OptionRange colorModeLabels;
	public static OptionBoolean seasonalSunPath;
	public static OptionRange fireOverlay;
	public static OptionRange fireOverlayOffset;
	public static OptionRange animationTicks;
	public static OptionBoolean leafPileGraphics;
	public static OptionRange selectorType;
	public static OptionRange selectorBlinkFrequency;
	public static OptionRange selectorBlinkMin;
	public static OptionRange selectorBlinkMax;
	public static OptionBoolean selectorDepthTest;
	public static OptionBoolean blinkingItems;
	public static OptionBoolean enableToneMap;
	public static OptionBoolean enableDither;
	public static OptionRange redBits;
	public static OptionRange greenBits;
	public static OptionRange blueBits;
	public static OptionRange bayerMatrix;
	public static OptionRange bayerBrightness;
	public static OptionRange toneMapFalloff;
	public static OptionRange toneMapFalloffStart;
	public static OptionRange toneMapFalloffEnd;
	public static OptionRange ditherFalloff;
	public static OptionRange ditherFalloffStart;
	public static OptionRange ditherFalloffEnd;
	public static OptionRange falloffType;
	public static final String[] leavesModeString = new String[]{"options.btavisuals.fast", "options.btavisuals.fancy", "options.btavisuals.transparent", "options.btavisuals.two_sided"};
	public static final String[] snowTypeString = new String[]{"options.btavisuals.default", "options.btavisuals.light", "options.btavisuals.heavy"};
	public static final String[] animalLabelsString = new String[]{"options.btavisuals.visible", "options.btavisuals.icons", "options.btavisuals.hidden"};
	public static final String[] sideLightString = new String[]{"options.btavisuals.default", "options.btavisuals.flipped", "options.btavisuals.none"};
	public static final String[] fireOverlayString = new String[]{"options.btavisuals.static", "options.btavisuals.dynamic"};
	public static final String[] selectorTypeString = new String[]{"options.btavisuals.default", "options.btavisuals.blinking", "options.btavisuals.bright"};
	public static final String[] falloffString = new String[]{"options.btavisuals.disabled", "options.btavisuals.normal", "options.btavisuals.inverted"};
	public static final String[] falloffTypeString = new String[]{"options.btavisuals.spherical", "options.btavisuals.linear"};
	public static Minecraft mc;
	public static Shader shader;
	public static KeyBinding recompile;
	public static KeyBinding test;
	//public static FloatBuffer bayerCache = BufferUtils.createFloatBuffer(4096);
	public static int bayerSizeCache;
	public static float bayerBrightnessCache;
	public static float bayerMaxCache;
	public static float[] stepCache = new float[3];
	public static int texMatrix = -1;
	public static float bayerTexMiddle = 0;
	public static float bayerTexOffset = 0;
	/*public static int progress;
	public static int oprogress;*/

	public static void setupToneMap() {
		float nRed = (float) Math.pow(2, redBits.value);
		float nGreen = (float) Math.pow(2, greenBits.value);
		float nBlue = (float) Math.pow(2, blueBits.value);
		stepCache[0] = nRed <= 1 ? 0 : 1.0F / (nRed-1.0F);
		stepCache[1] = nGreen <= 1 ? 0 : 1.0F / (nGreen-1.0F);
		stepCache[2] = nBlue <= 1 ? 0 : 1.0F / (nBlue-1.0F);
	}

	public static void setupBayer() {
		float[] bayerArray = Bayer.gen1DBayerF(bayerMatrix.value);
		/*bayerCache.position(0).limit(bayerArray.length);
		bayerCache.put(bayerArray);
		bayerCache.position(0).limit(bayerArray.length);*/
		bayerMaxCache = (float) Bayer.max;
		bayerSizeCache = (int) Math.pow(2, bayerMatrix.value+1);
		bayerTexOffset = 1.0F / (bayerSizeCache*bayerSizeCache);
		bayerTexMiddle = bayerTexOffset / 2.0F;

		if (texMatrix != -1) GL11.glDeleteTextures(texMatrix);
		texMatrix = GL11.glGenTextures();
		GL11.glEnable(GL11.GL_TEXTURE_1D);
		GL11.glBindTexture(GL11.GL_TEXTURE_1D, texMatrix);
		GL11.glTexParameteri(GL11.GL_TEXTURE_1D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_1D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_1D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_1D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexImage1D(GL11.GL_TEXTURE_1D, 0, ARBTextureFloat.GL_RGB32F_ARB, bayerArray.length, 0, GL11.GL_RED, GL11.GL_FLOAT, bayerArray);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

    @Override
    public void onInitialize() {
        LOGGER.info("BTAVisuals initialized.");
    }

	@Override
	public void beforeClientStart() {
	}

	@Override
	public void afterClientStart() {
		mc = Minecraft.getMinecraft();
		shader = new Shader().compile(new ShaderProviderJar(MOD_ID), "post_dither");
		recompile = new KeyBinding("options.compile").setDefault(InputDevice.keyboard, Keyboard.KEY_O);
		test = new KeyBinding("options.test").setDefault(InputDevice.keyboard, Keyboard.KEY_P);

		OptionsPage page = modPage = new OptionsPage(tk("options"), Items.PAINTBRUSH.getDefaultStack());
		OptionsPages.register(page);

		//Disabled until properly implemented
		BooleanOptionComponent tempDisabled = new BooleanOptionComponent(smoothWater);
		//BTAVisuals.<BooleanOptionAccessor>cast(tempDisabled).getButton().enabled = false;

		page.withComponent(new OptionsCategory(tk("options.graphics"))
			.withComponent(new ToggleableOptionComponent<>(leavesMode))
			.withComponent(new ToggleableOptionComponent<>(cloudMode))
			.withComponent(new ToggleableOptionComponent<>(grassMode))
		);

		page.withComponent(new OptionsCategory(tk("options.display"))
			.withComponent(new BooleanOptionComponent(billboardItems))
			.withComponent(new BooleanOptionComponent(blinkingItems))
			.withComponent(new ToggleableOptionComponent<>(farPlaneDistance))
			.withComponent(new ToggleableOptionComponent<>(fogDistance))
		);

		page.withComponent(new OptionsCategory(tk("options.world"))
			.withComponent(tempDisabled)
			.withComponent(new BooleanOptionComponent(leafPileGraphics))
			.withComponent(new ToggleableOptionComponent<>(sideLightDirection))
			.withComponent(new ToggleableOptionComponent<>(animationTicks))
			.withComponent(new BooleanOptionComponent(dualPassOnFast))
		);

		page.withComponent(new OptionsCategory(tk("options.selector"))
			.withComponent(new ToggleableOptionComponent<>(selectorType))
			.withComponent(new ToggleableOptionComponent<>(selectorBlinkFrequency))
			.withComponent(new ToggleableOptionComponent<>(selectorBlinkMin))
			.withComponent(new ToggleableOptionComponent<>(selectorBlinkMax))
			.withComponent(new ToggleableOptionComponent<>(selectorDepthTest))
		);

		page.withComponent(new OptionsCategory(tk("options.clouds"))
			.withComponent(new ToggleableOptionComponent<>(cloudDistance))
			.withComponent(new ToggleableOptionComponent<>(cloudFogDistance))
			.withComponent(new BooleanOptionComponent(tintedClouds))
		);

		page.withComponent(new OptionsCategory(tk("options.weather"))
			.withComponent(new ToggleableOptionComponent<>(rainOpacity))
			.withComponent(new ToggleableOptionComponent<>(snowOpacity))
			.withComponent(new ToggleableOptionComponent<>(snowType))
			.withComponent(new ToggleableOptionComponent<>(rainParticleAmount))
		);

		page.withComponent(new OptionsCategory(tk("options.overlay"))
			.withComponent(new ToggleableOptionComponent<>(fireOverlay))
			.withComponent(new ToggleableOptionComponent<>(fireOverlayOffset))
		);

		page.withComponent(new OptionsCategory(tk("options.players"))
			.withComponent(new BooleanOptionComponent(smoothInvModel))
			.withComponent(new BooleanOptionComponent(fixHeldBlockShading))
			.withComponent(new BooleanOptionComponent(allSteve))
			.withComponent(new BooleanOptionComponent(showOwnNameplate))
			.withComponent(new ToggleableOptionComponent<>(hideNameplates))
			.withComponent(new ToggleableOptionComponent<>(colorMode))
		);

		page.withComponent(new OptionsCategory(tk("options.labels"))
			.withComponent(new ToggleableOptionComponent<>(animalLabels))
			.withComponent(new ToggleableOptionComponent<>(colorModeLabels))
		);

		page.withComponent(new OptionsCategory(tk("options.misc"))
			.withComponent(new BooleanOptionComponent(seasonalSunPath))
		);

		page.withComponent(new OptionsCategory(tk("options.tone_map"))
			.withComponent(new BooleanOptionComponent(enableToneMap))
			.withComponent(new ToggleableOptionComponent<>(redBits))
			.withComponent(new ToggleableOptionComponent<>(greenBits))
			.withComponent(new ToggleableOptionComponent<>(blueBits))
			.withComponent(new BooleanOptionComponent(enableDither))
			.withComponent(new ToggleableOptionComponent<>(bayerBrightness))
			.withComponent(new ToggleableOptionComponent<>(bayerMatrix))
		);

		page.withComponent(new OptionsCategory(tk("options.falloff"))
			.withComponent(new ToggleableOptionComponent<>(falloffType))
			.withComponent(new ToggleableOptionComponent<>(toneMapFalloff))
			.withComponent(new ToggleableOptionComponent<>(toneMapFalloffStart))
			.withComponent(new ToggleableOptionComponent<>(toneMapFalloffEnd))
			.withComponent(new ToggleableOptionComponent<>(ditherFalloff))
			.withComponent(new ToggleableOptionComponent<>(ditherFalloffStart))
			.withComponent(new ToggleableOptionComponent<>(ditherFalloffEnd))
		);

		/////////////// Needs options.txt to be loaded ///////////////
		setupBayer();
		setupToneMap();
		bayerBrightnessCache = bayerBrightness.value / 100.0F;
		/////////////// Needs options.txt to be loaded ///////////////
	}

	public static String translateRange(String[] array, OptionRange option) {
		return I18n.getInstance().translateKey(array[clamp(option.value, option.lowest, option.highest)]);
	}

	public static int clamp(int value, int min, int max) {
		return Math.max(Math.min(value, max), min);
	}

	@Override
	public void initOptions(GameSettings settings) {

		billboardItems = new OptionBoolean(settings, tk("billboard_items"), false);
		smoothWater = new OptionBoolean(settings, tk("smooth_water"), false);
		sideLightDirection = new OptionRange(settings, tk("side_light_direction"), 0, 3);
		fixHeldBlockShading = new OptionBoolean(settings, tk("fix_carried_block_shading"), true);

		farPlaneDistance = new OptionRange(settings, tk("far_plane_distance"), 12 * 16, 128 * 16);
		fogDistance = new OptionRange(settings, tk("fog_distance"), 10 * 16, 128 * 16);

		cloudDistance = new OptionRange(settings, tk("cloud_distance"), 1, 8);
		tintedClouds = new OptionBoolean(settings, tk("tinted_clouds"), false);
		dualPassOnFast = new OptionBoolean(settings, tk("dual_pass_on_fast"), false);
		cloudFogDistance = new OptionRange(settings, tk("cloud_fog_distance"), 1, 8);

		leavesMode = new OptionRange(settings, tk("leaves_mode"), 1, 4);
		cloudMode = new OptionRange(settings, tk("cloud_mode"), 1, 2);
		grassMode = new OptionRange(settings, tk("grass_mode"), 1, 2);

		rainOpacity = new OptionRange(settings, tk("rain_opacity"), 100, 101);
		snowOpacity = new OptionRange(settings, tk("snow_opacity"), 100, 101);
		snowType = new OptionRange(settings, tk("snow_type"), 0, 3);
		rainParticleAmount = new OptionRange(settings, tk("rain_particle_limit"), 100, 101);

		showOwnNameplate = new OptionBoolean(settings, tk("show_own_nameplate"), false);
		smoothInvModel = new OptionBoolean(settings, tk("smooth_inv_model"), false);
		allSteve = new OptionBoolean(settings, tk("all_steve"), false);
		hideNameplates = new OptionRange(settings, tk("hide_nameplates"), 0, 3);
		colorMode = new OptionRange(settings, tk("color_mode"), 0, 17);

		animalLabels = new OptionRange(settings, tk("animal_labels"), 0, 3);
		colorModeLabels = new OptionRange(settings, tk("color_mode"), 0, 17);

		seasonalSunPath = new OptionBoolean(settings, tk("seasonal_sun_path"), false);

		fireOverlay = new OptionRange(settings, tk("fire_overlay"), 0, 2);
		fireOverlayOffset 	= new OptionRange(settings, tk("fire_overlay_offset"), 0, 151);
		animationTicks = new OptionRange(settings, tk("animation_ticks"), 100, 101);

		leafPileGraphics = new OptionBoolean(settings, tk("leaf_pile_graphics"), true);

		selectorType = new OptionRange(settings, tk("selector_type"), 0, 3);
		selectorBlinkFrequency = new OptionRange(settings, tk("selector_blink_frequency"), 15, 31);
		selectorBlinkMin = new OptionRange(settings, tk("selector_blink_min"), 0, 61);
		selectorBlinkMax = new OptionRange(settings, tk("selector_blink_max"), 15, 61);
		selectorDepthTest = new OptionBoolean(settings, tk("selector_depth_test"), true);

		enableToneMap = new OptionBoolean(settings, tk("enable_tone_map"), false);
		enableDither = new OptionBoolean(settings, tk("enable_dither"), false);
		redBits = new OptionRange(settings, tk("red_bits"), 3, 9);
		greenBits = new OptionRange(settings, tk("green_bits"), 2, 9);
		blueBits = new OptionRange(settings, tk("blue_bits"), 3, 9);
		bayerMatrix = new OptionRange(settings, tk("bayer_matrix"), 3, 4);
		bayerBrightness = new OptionRange(settings, tk("bayer_brightness"), 25, 101);

		toneMapFalloff = new OptionRange(settings, tk("tone_map_falloff"), 0, 3);
		toneMapFalloffStart = new OptionRange(settings, tk("tone_map_falloff_start"), 0, 512);
		toneMapFalloffEnd = new OptionRange(settings, tk("tone_map_falloff_end"), 32, 512);

		ditherFalloff = new OptionRange(settings, tk("dither_falloff"), 0, 3);
		ditherFalloffStart = new OptionRange(settings, tk("dither_falloff_start"), 0, 512);
		ditherFalloffEnd = new OptionRange(settings, tk("dither_falloff_end"), 32, 512);

		falloffType = new OptionRange(settings, tk("falloff_type"), 0, 2);

		blinkingItems = new OptionBoolean(settings, tk("blinking_items"), false);
	}

	private static String tk(String str) {
		return MOD_ID + "." + str;
	}

	@SuppressWarnings("OptionalGetWithoutIsPresent")
	public static void devBuildResources() {
		ModContainer mod = FabricLoader.getInstance().getModContainer(MOD_ID).get();
		Path path = mod.findPath("assets").get();
		if ("jar".equals(path.toUri().getScheme())) return; // Return if jar

		Path shadersBuildFolder = path.resolve(MOD_ID + "/shaders");
		Path projectResources = path.getRoot().resolve(path.subpath(0, path.getNameCount() - 4)).resolve("src/main/resources/assets/" + MOD_ID + "/shaders");

		try (Stream<Path> pathStream = Files.list(projectResources)){
			for (Path shaderFile : pathStream.collect(Collectors.toList())) {
				Files.copy(shaderFile, shadersBuildFolder.resolve(shaderFile.getFileName()), StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException ignore) {}
	}

	public static void compileShaders() {
		if (shader != null) GL20.glDeleteProgram(((ShaderAccessor)shader).getProgram());
		shader = new Shader().compile(new ShaderProviderJar(MOD_ID), "post_dither");
	}

	public static float sideLight(int sideId) {
		if (sideId < 0 || sideId > 5) return 1;
		return sideLights[clamp(sideLightDirection.value, sideLightDirection.lowest, sideLightDirection.highest)][sideId];
	}

	public static boolean AO() {
		return mc.gameSettings.ambientOcclusion.value;
	}
}
