package bigsir.btavisuals.mixin;

import net.minecraft.client.gui.ButtonElement;
import net.minecraft.client.gui.SwitchElement;
import net.minecraft.client.gui.options.components.BooleanOptionComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BooleanOptionComponent.class, remap = false)
public abstract class BooleanOptionComponentMixin {

	@Shadow
	@Final
	private SwitchElement button;

	@Inject(method = "buttonClicked", at = @At("HEAD"), cancellable = true)
	public void disable(int mouseButton, int x, int y, int width, int height, int relativeMouseX, int relativeMouseY, CallbackInfo ci){
		if (!button.enabled) ci.cancel();
	}
}
