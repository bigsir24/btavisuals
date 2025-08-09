package bigsir.btavisuals.mixin;

import net.minecraft.core.world.World;
import net.minecraft.core.world.type.WorldType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = WorldType.class, remap = false)
public interface WorldTypeAccessor {
	@Invoker
	int invokeGetDayLengthTicks(World world);
}
