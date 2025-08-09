package bigsir.btavisuals.mixin;

import bigsir.btavisuals.BTAVisuals;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = World.class, remap = false)
public abstract class WorldMixin {
	@ModifyConstant(method = "randomDisplayUpdates", constant = @Constant(intValue = 1000))
	private int modifyDisplayUpdate(int constant) {
		return (int) (1000 * (BTAVisuals.animationTicks.value / 100.0F));
	}
}
