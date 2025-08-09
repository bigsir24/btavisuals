package bigsir.btavisuals.mixin;

import bigsir.btavisuals.BTAVisuals;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenPhotoMode;
import net.minecraft.client.option.OptionBoolean;
import net.minecraft.client.render.FogManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.camera.ICamera;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.util.debug.Debug;
import net.minecraft.core.world.World;
import net.minecraft.core.world.weather.Weather;
import net.minecraft.core.world.weather.WeatherSnow;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = WorldRenderer.class, remap = false)
public abstract class WorldRendererMixin {
	@Shadow
	private float farPlaneDistance;

	@Shadow
	public FogManager fogManager;

	@Shadow
	public Minecraft mc;

	@Redirect(method = "renderRainSnow", at = @At(value = "INVOKE" ,target = "Lnet/minecraft/core/world/weather/Weather;getPrecipitationTexture(Lnet/minecraft/core/world/World;)Ljava/lang/String;"))
	public String overrideSnowTexture(Weather instance, World world) {
		if (!(instance instanceof WeatherSnow) || BTAVisuals.snowType.value == 0) return instance.getPrecipitationTexture(world);
		return BTAVisuals.snowType.value == 1 ? "/assets/minecraft/textures/environment/snow_light.png" : "/assets/minecraft/textures/environment/snow.png";
	}

	@Inject(method = "setupCameraTransform", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glMatrixMode(I)V", ordinal = 0))
	public void f(float partialTicks, CallbackInfo ci) {
		this.farPlaneDistance = BTAVisuals.farPlaneDistance.value + 1;
	}

	@Redirect(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/FogManager;setupFog(IFF)V"))
	public void f(FogManager instance, int mode, float dist, float partialTicks) {
		instance.setupFog(mode, BTAVisuals.fogDistance.value + 1, partialTicks);
	}

	@Inject(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/FogManager;setupFog(IFF)V", ordinal = 5, shift = At.Shift.AFTER))
	public void f2(float partialTicks, long updateRenderersUntil, CallbackInfo ci) {
		//this.fogManager.setupFog(0, this.farPlaneDistance, partialTicks);
	}

	@Inject(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/FogManager;setupFog(IFF)V", ordinal = 0, shift = At.Shift.AFTER))
	public void f3(float partialTicks, long updateRenderersUntil, CallbackInfo ci) {
		this.fogManager.setupFog(-1, this.farPlaneDistance, partialTicks);
	}

	@Inject(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/util/debug/Debug;change(Ljava/lang/String;)V", ordinal = 11))
	public void render(float partialTicks, long updateRenderersUntil, CallbackInfo ci) {
		if (this.mc.gameSettings.clouds.value) {
			GL11.glPushMatrix();
			Debug.change("clouds");
			this.fogManager.setupFog(0, this.farPlaneDistance, partialTicks);
			GL11.glEnable(GL11.GL_FOG);
			GL11.glFogf(GL11.GL_FOG_START, 4 * 16);
			GL11.glFogf(GL11.GL_FOG_END, 8 * (BTAVisuals.cloudFogDistance.value + 1) * 16);
			this.mc.renderGlobal.renderClouds(partialTicks);
			GL11.glDisable(GL11.GL_FOG);
			GL11.glPopMatrix();
		}
	}

	@Redirect(method = "renderWorld", at = @At(value = "FIELD", target = "Lnet/minecraft/client/option/OptionBoolean;value:Ljava/lang/Object;", ordinal = 9))
	private Object modValue(OptionBoolean instance) {
		return false;
	}
}
