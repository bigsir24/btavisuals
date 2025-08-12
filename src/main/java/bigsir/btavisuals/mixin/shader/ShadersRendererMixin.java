package bigsir.btavisuals.mixin.shader;

import bigsir.btavisuals.BTAVisuals;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Framebuffer;
import net.minecraft.client.render.shader.Shader;
import net.minecraft.client.render.shader.Shaders;
import net.minecraft.client.render.shader.ShadersRenderer;
import net.minecraft.client.render.texture.Texture;
import org.lwjgl.opengl.ARBMultitexture;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ShadersRenderer.class, remap = false)
public abstract class ShadersRendererMixin {

	@Shadow
	@Final
	protected Texture worldFramebufferTex;

	@Shadow
	@Final
	protected Texture worldFramebufferDepth;

	@Shadow
	public Minecraft mc;

	@Redirect(method = "endRenderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/shader/Shader;isEnabled()Z", ordinal = 1))
	public boolean redirectShader(Shader instance) {
		return !BTAVisuals.enableToneMap.value;
	}

	@Redirect(method = "endRenderWorld", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL20;glUseProgram(I)V", ordinal = 0))
	public void noBind(int program, @Local(name = "partialTicks") float partialTicks) {
		BTAVisuals.shader.bind();
		ARBMultitexture.glActiveTextureARB(33984);
		this.worldFramebufferTex.bind();
		BTAVisuals.shader.uniformInt("colortex0", 0);
		ARBMultitexture.glActiveTextureARB(33985);
		this.worldFramebufferDepth.bind();
		BTAVisuals.shader.uniformInt("depthtex0", 1);
		ARBMultitexture.glActiveTextureARB(33986);
		GL11.glBindTexture(GL11.GL_TEXTURE_1D, BTAVisuals.texMatrix);
		BTAVisuals.shader.uniformInt("texMatrix", 2);
		ARBMultitexture.glActiveTextureARB(33984);
		mc.ppm.enabled = true;
		Shaders.setUniforms(mc, BTAVisuals.shader, partialTicks);
		mc.ppm.enabled = false;

		BTAVisuals.shader.uniformBool("tonemap", BTAVisuals.enableToneMap.value);
		BTAVisuals.shader.uniformBool("dither", BTAVisuals.enableDither.value);
		GL20.glUniform3fv(BTAVisuals.shader.getUniform("step"), BTAVisuals.stepCache);

		BTAVisuals.bayerCache.position(0);
		GL20.glUniform1fv(GL20.glGetUniformLocation(((ShaderAccessor)BTAVisuals.shader).getProgram(), "bayer"), BTAVisuals.bayerCache);

		GL20.glUniform1i(BTAVisuals.shader.getUniform("bayerSize"), BTAVisuals.bayerSizeCache);
		GL20.glUniform1f(BTAVisuals.shader.getUniform("bayerMax"), BTAVisuals.bayerMaxCache);
		GL20.glUniform1f(BTAVisuals.shader.getUniform("bayerBrightness"), BTAVisuals.bayerBrightnessCache);
	}
}
