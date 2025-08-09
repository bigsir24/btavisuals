package bigsir.btavisuals.mixin;

import net.minecraft.client.gui.SwitchElement;
import net.minecraft.client.gui.options.components.BooleanOptionComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = BooleanOptionComponent.class, remap = false)
public interface BooleanOptionAccessor {
	@Accessor
	SwitchElement getButton();
}
