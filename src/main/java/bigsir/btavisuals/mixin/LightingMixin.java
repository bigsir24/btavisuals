package bigsir.btavisuals.mixin;

import bigsir.btavisuals.BTAVisuals;
import net.minecraft.client.render.Lighting;
import net.minecraft.core.util.phys.Vec3;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Lighting.class, remap = false)
public abstract class LightingMixin {

	@Unique
	private static boolean invLight = false;

	@Inject(method = "enableInventoryLight", at = @At("HEAD"))
	private static void invLight(CallbackInfo ci){
		invLight = true;
	}

	@Inject(method = "enableInventoryLight", at = @At("TAIL"))
	private static void invLightEnd(CallbackInfo ci){
		invLight = false;
	}

	@Redirect(method = "enableLight(FF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/util/phys/Vec3;normalize()Lnet/minecraft/core/util/phys/Vec3;", ordinal = 0))
	private static Vec3 fixLight(Vec3 instance){
		double[] light = BTAVisuals.invLights[BTAVisuals.sideLightDirection.value];
		if(!invLight){
			instance.x = light[0];
			instance.z = -light[1];
		}
		return instance.normalize();
	}
	@Redirect(method = "enableLight(FF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/util/phys/Vec3;normalize()Lnet/minecraft/core/util/phys/Vec3;", ordinal = 1))
	private static Vec3 fixLight2(Vec3 instance){
		double[] light = BTAVisuals.invLights[BTAVisuals.sideLightDirection.value];
		if(!invLight){
			instance.x = -light[0];
			instance.z = light[1];
		}
		return instance.normalize();
	}
}
