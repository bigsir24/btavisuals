package bigsir.btavisuals.mixin.selection;

import bigsir.btavisuals.BTAVisuals;
import bigsir.btavisuals.ducks.ISelect;
import net.minecraft.client.Minecraft;
import net.minecraft.core.util.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Minecraft.class, remap = false)
public abstract class SelectionMinecraftMixin implements ISelect {
	@Shadow
	public HitResult objectMouseOver;
	@Shadow
	private int ticksRan;
	@Unique
	private int selectFlash = 0;
	private boolean up = true;

	private static final int FREQ = 15;
	@Unique private float value;


	@Inject(method = "runTick", at = @At(value = "HEAD"))
	public void test(CallbackInfo ci){
		float min = BTAVisuals.selectorBlinkMin.value / 300.0F;
		float max = BTAVisuals.selectorBlinkMax.value / 300.0F;
		float amplitude = (max - min) / 2.0F;
		value = (float) ((Math.sin(++selectFlash * Math.PI / (30 - BTAVisuals.selectorBlinkFrequency.value)) + 1) * amplitude + min); // PI / 15 = 0.2094395102

		/*int frequency = 30 - BTAVisuals.selectorBlinkFrequency.value;

		if(up && selectFlash < frequency){
			selectFlash++;
		}else if(!up && selectFlash > 0){
			selectFlash--;
		}

		if(selectFlash >= frequency || selectFlash <= 0){
			up = !up;
		}*/

	}

	@Override
	public float getSelectFlash() {
		return value;
	}
}
