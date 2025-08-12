package bigsir.btavisuals.mixin.shader;

import net.minecraft.client.gui.container.ScreenInventoryCreative;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ScreenInventoryCreative.class, remap = false)
public abstract class ScreenInventoryCreativeMixin {
	@Redirect(method = "drawGuiContainerBackgroundLayer", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glRotatef(FFFF)V", ordinal = 1))
	public void no1(float r, float g, float b, float a) {
		GL11.glRotatef(r,g,b,a);
	}
	@Redirect(method = "drawGuiContainerBackgroundLayer", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glRotatef(FFFF)V", ordinal = 2))
	public void no2(float r, float g, float b, float a) {
		GL11.glRotatef(r,g,b,a);
	}
}
