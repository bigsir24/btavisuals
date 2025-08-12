package bigsir.btavisuals.mixin.shader;

import bigsir.btavisuals.BTAVisuals;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.InputDevice;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Minecraft.class, remap = false)
public abstract class MinecraftMixin {
	@Inject(method = "checkBoundInputs", at = @At(value = "HEAD"), cancellable = true)
	public void keyBindingCheck(InputDevice currentInputDevice, CallbackInfoReturnable<Boolean> cir){

		/*if(BTAVisuals.recompile.isPressEvent(currentInputDevice)) {
			BTAVisuals.devBuildResources();
			BTAVisuals.compileShaders();
			cir.setReturnValue(true);
		}*/

	}
}
