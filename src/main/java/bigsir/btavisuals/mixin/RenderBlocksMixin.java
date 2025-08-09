package bigsir.btavisuals.mixin;

import bigsir.btavisuals.BTAVisuals;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.render.RenderBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = RenderBlocks.class, remap = false)
public abstract class RenderBlocksMixin {
	@WrapOperation(method = "setupLighting", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/RenderBlocks;SIDE_LIGHT_MULTIPLIER:[F"))
	private static float[] dirFix(Operation<float[]> original){
		return BTAVisuals.sideLights[BTAVisuals.clamp(BTAVisuals.sideLightDirection.value, BTAVisuals.sideLightDirection.lowest, BTAVisuals.sideLightDirection.highest)];
	}
}
