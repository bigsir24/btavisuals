package bigsir.btavisuals.mixin;

import bigsir.btavisuals.BTAVisuals;
import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.entity.MobRendererSpider;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.client.render.model.ModelSpider;
import net.minecraft.core.entity.monster.MobSpider;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MobRendererSpider.class, remap = false)
public abstract class MobRendererSpiderMixin {
	@Inject(method = "setSpiderEyeBrightness", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glColor4f(FFFF)V", shift = At.Shift.AFTER))
	public void fixShadow(MobSpider spider, int renderPass, float partialTick, CallbackInfoReturnable<Boolean> cir){
		GL11.glEnable(GL11.GL_ALPHA_TEST);
	}
}
