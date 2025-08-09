package bigsir.btavisuals;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.options.components.BooleanOptionComponent;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.components.ToggleableOptionComponent;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.OptionBoolean;
import net.minecraft.client.option.OptionRange;
import net.minecraft.core.item.Items;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.util.helper.DamageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import turniplabs.halplibe.util.OptionsInitEntrypoint;

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
	public static final String[] leavesModeString = new String[]{"options.btavisuals.fast", "options.btavisuals.fancy", "options.btavisuals.transparent", "options.btavisuals.two_sided"};
	public static final String[] snowTypeString = new String[]{"options.btavisuals.default", "options.btavisuals.light", "options.btavisuals.heavy"};
	public static final String[] animalLabelsString = new String[]{"options.btavisuals.visible", "options.btavisuals.icons", "options.btavisuals.hidden"};
	public static final String[] sideLightString = new String[]{"options.btavisuals.default", "options.btavisuals.flipped", "options.btavisuals.none"};
	public static final String[] fireOverlayString = new String[]{"options.btavisuals.static", "options.btavisuals.dynamic"};
	public static final String[] selectorTypeString = new String[]{"options.btavisuals.default", "options.btavisuals.blinking", "options.btavisuals.bright"};
	public static Minecraft mc;

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
	}

	private static String tk(String str) {
		return MOD_ID + "." + str;
	}

	public static float sideLight(int sideId) {
		if (sideId < 0 || sideId > 5) return 1;
		return sideLights[clamp(sideLightDirection.value, sideLightDirection.lowest, sideLightDirection.highest)][sideId];
	}

	public static boolean AO() {
		return mc.gameSettings.ambientOcclusion.value;
	}
}
