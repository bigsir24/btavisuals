package bigsir.btavisuals.mixin.shader;

import bigsir.btavisuals.BTAVisuals;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.minecraft.client.Minecraft;
import net.minecraft.client.option.enums.RenderScale;
import net.minecraft.client.render.Lighting;
import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.camera.ICamera;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.shader.ShadersRenderer;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.enums.LightLayer;
import net.minecraft.core.util.helper.MathHelper;
import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix;
import org.lwjgl.util.vector.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.FloatBuffer;
import java.util.Arrays;

@Mixin(value = MobRenderer.class, remap = false)
public abstract class MobRendererMixin<T extends Mob> extends EntityRenderer<T> {
	@Shadow
	@Final
	private Minecraft mc;

	@Shadow
	public abstract void loadEntityTexture(T entity);

	@Shadow
	protected abstract int getOverlayColor(T entity, float brightness, float partialTick);

	@Redirect(method = "render(Lnet/minecraft/client/render/tessellator/Tessellator;Lnet/minecraft/core/entity/Mob;DDDFF)V", at = @At(value = "FIELD", target = "Lnet/minecraft/core/entity/Mob;hurtTime:I", ordinal = 1))
	public int cancel1(Mob instance) {
		return instance.hurtTime;
	}

	@Redirect(method = "render(Lnet/minecraft/client/render/tessellator/Tessellator;Lnet/minecraft/core/entity/Mob;DDDFF)V", at = @At(value = "FIELD", target = "Lnet/minecraft/core/entity/Mob;deathTime:I", ordinal = 1))
	public int cancel2(Mob instance) {
		return instance.deathTime;
	}

	@Inject(method = "render(Lnet/minecraft/client/render/tessellator/Tessellator;Lnet/minecraft/core/entity/Mob;DDDFF)V", at = @At("HEAD"))
	public void start(Tessellator tessellator, T entity, double x, double y, double z, float yaw, float partialTick, CallbackInfo ci, @Share("actTex")LocalIntRef actTex, @Share("bound")LocalIntRef bound) {
		if (true) return; //TODO unfinished
		this.loadEntityTexture(entity);

		int activeTex = GL20.glGetInteger(GL20.GL_ACTIVE_TEXTURE);
		bound.set(GL20.glGetInteger(GL20.GL_TEXTURE_BINDING_2D));

		BTAVisuals.shader.bind();
		float brightness = entity.getBrightness(partialTick);

		//System.out.println(GL11.glGetInteger(GL11.GL_FOG_MODE));

		//System.out.println(this.renderDispatcher.textureManager.loadTexture(entity.getEntityTexture()).id());
		//System.out.println(GL20.glGetInteger(GL20.GL_ACTIVE_TEXTURE));

		if (LightmapHelper.isLightmapEnabled()) {
			GL20.glUniform1i(BTAVisuals.shader.getUniform("lightmapTex"), 1);
			//GL20.glActiveTexture(GL13.GL_TEXTURE0 + 2);
			int lmt = ((LightmapHelperAccessor)this.mc.worldRenderer.lightmapHelper).getLightmapTexture();
			GL20.glBindTexture(GL11.GL_TEXTURE_2D, lmt);
			GL13.glTexParameteri(3553, 10241, 9729);
			GL13.glTexParameteri(3553, 10240, 9729);
			GL13.glTexParameteri(3553, 10242, 10496);
			GL13.glTexParameteri(3553, 10243, 10496);
			brightness = 1.0F;
		}

		GL20.glUniform3f(BTAVisuals.shader.getUniform("entityBrightness"), brightness, brightness, brightness);

		Minecraft mc = Minecraft.getMinecraft();
		FloatBuffer projInverse = ((ShadersRendererAccessor)mc.renderer).getMatrixBuffer();
		projInverse.position(0).limit(16);
		GL20.glUniformMatrix4fv(BTAVisuals.shader.getUniform("projectionInverse"), false, projInverse);
		float scale = (float) this.mc.gameSettings.renderScale.value.scale;
		GL20.glUniform2f(BTAVisuals.shader.getUniform("screen"), mc.gameWindow.getWidthPixels() * scale, mc.gameWindow.getHeightPixels() * scale);

		GL20.glUniform1i(BTAVisuals.shader.getUniform("fogMode"), GL11.glGetInteger(GL11.GL_FOG_MODE));
		GL20.glUniform1f(BTAVisuals.shader.getUniform("depthFar"), BTAVisuals.farPlaneDistance.value + 1);

		GL20.glActiveTexture(activeTex);

		//GL11.glGetFloatv(GL11.GL_CURRENT_COLOR, lights);
		//GL20.glUniform4f(BTAVisuals.shader.getUniform("entityBrightness"), lights[0], lights[1], lights[2], lights[3]);
		if (entity.hurtTime > 0 || entity.deathTime > 0) {
			GL20.glUniform4f(BTAVisuals.shader.getUniform("overlayColor"), brightness, 0, 0, 0.4F);
		}else {
			GL20.glUniform4f(BTAVisuals.shader.getUniform("overlayColor"), 1, 1, 1, 0);
		}
		int argb = this.getOverlayColor(entity, brightness, partialTick);
		float r = ((argb >>> 16) & 0xFF) / 255.0F;
		float g = ((argb >>> 8) & 0xFF) / 255.0F;
		float b = ((argb) & 0xFF) / 255.0F;
		float a = ((argb >>> 24) & 0xFF) / 255.0F;
		GL20.glUniform4f(BTAVisuals.shader.getUniform("additionalOverlay"), r, g, b, a);
	}

	@Inject(method = "render(Lnet/minecraft/client/render/tessellator/Tessellator;Lnet/minecraft/core/entity/Mob;DDDFF)V", at = @At("TAIL"))
	public void end(Tessellator tessellator, T entity, double x, double y, double z, float yaw, float partialTick, CallbackInfo ci, @Share("actTex")LocalIntRef actTex, @Share("bound")LocalIntRef bound) {
		//GL20.glUseProgram(0);
	}
}
