package bigsir.btavisuals.mixin;

import bigsir.btavisuals.BTAVisuals;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(value = ItemModelStandard.class, remap = false)
public abstract class ItemModelStandardMixin {
	@Inject(method = "renderAsItemEntity", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glRotatef(FFFF)V", shift = At.Shift.AFTER))
	public void rotateZ(Tessellator tessellator, Entity entity, Random random, ItemStack itemstack, int renderCount, float yaw, float brightness, float partialTick, CallbackInfo ci){
		if (BTAVisuals.billboardItems.value) {
			GL11.glTranslatef(0,0.1f, 0);
			GL11.glRotatef(-EntityRenderDispatcher.instance.viewLerpPitch, 1, 0, 0);
			GL11.glTranslatef(0,-0.1f, 0);
		}
	}

	@Inject(method = "renderFlat", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;setNormal(FFF)V", shift = At.Shift.AFTER))
	public void d(Tessellator tessellator, IconCoordinate index, CallbackInfo ci) {
		if (BTAVisuals.billboardItems.value) {
			GL11.glDisable(GL11.GL_LIGHTING);
		}
	}

	@Inject(method = "renderFlat", at = @At(value = "TAIL"))
	public void d1(Tessellator tessellator, IconCoordinate index, CallbackInfo ci) {
		if (BTAVisuals.billboardItems.value) {
			GL11.glEnable(GL11.GL_LIGHTING);
		}
	}

}
