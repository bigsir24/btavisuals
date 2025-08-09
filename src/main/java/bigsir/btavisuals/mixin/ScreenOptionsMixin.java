package bigsir.btavisuals.mixin;

import bigsir.btavisuals.BTAVisuals;
import net.minecraft.client.gui.options.ScreenOptions;
import net.minecraft.client.gui.options.data.OptionsPage;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ScreenOptions.class, remap = false)
public abstract class ScreenOptionsMixin {
	@Shadow
	private boolean doOptionsScroll;

	@Shadow
	private OptionsPage selectedPage;

	@Inject(method = "render", at = @At("HEAD"))
	public void test(int mx, int my, float renderPartialTicks, CallbackInfo ci) {
		if (this.selectedPage == BTAVisuals.modPage && (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))) {
			this.doOptionsScroll = false;
		}
	}

	@Inject(method = "render", at = @At("TAIL"))
	public void test2(int mx, int my, float renderPartialTicks, CallbackInfo ci) {
		this.doOptionsScroll = true;
	}
}
