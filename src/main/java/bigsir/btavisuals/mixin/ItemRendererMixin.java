package bigsir.btavisuals.mixin;

import bigsir.btavisuals.BTAVisuals;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.ItemRenderer;
import net.minecraft.core.entity.player.Player;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ItemRenderer.class, remap = false)
public abstract class ItemRendererMixin {
	@Shadow
	@Final
	private Minecraft mc;
	@Unique
	private float lastFireTick;

	@Inject(method = "renderFireInFirstPerson", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glTranslatef(FFF)V"))
	public void offsetFireRender(float partialTick, CallbackInfo ci){
		if (BTAVisuals.fireOverlay.value == 1){
			Player player = this.mc.thePlayer;
			float progress =  1 - (lastFireTick + (player.remainingFireTicks - lastFireTick) * partialTick) / player.maxFireTicks;
			lastFireTick = player.remainingFireTicks;

			float offset = -BTAVisuals.fireOverlayOffset.value / 100.0F;
			float maxOffset = Math.min(0, -1.45F - offset);
			GL11.glTranslatef(0, offset + maxOffset * progress, 0);
		}else {
			GL11.glTranslatef(0, -(BTAVisuals.fireOverlayOffset.value / 100.0F), 0);
		}

	}
}
