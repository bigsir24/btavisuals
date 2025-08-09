package bigsir.btavisuals.mixin;

import net.minecraft.client.render.entity.EntityRendererItem;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.EntityItem;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityRendererItem.class, remap = false)
public abstract class EntityRendererItemMixin {
	@Inject(method = "render(Lnet/minecraft/client/render/tessellator/Tessellator;Lnet/minecraft/core/entity/EntityItem;DDDFF)V", at = @At(value = "HEAD"), cancellable = true)
	public void flash(Tessellator tessellator, EntityItem entity, double x, double y, double z, float yaw, float partialTick, CallbackInfo ci) {
		if (entity.age >= 5900 && entity.age % 8 < 4) ci.cancel();
	}
}
