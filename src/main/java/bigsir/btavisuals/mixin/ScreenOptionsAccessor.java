package bigsir.btavisuals.mixin;

import net.minecraft.client.gui.options.ScreenOptions;
import net.minecraft.client.gui.options.data.OptionsPage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ScreenOptions.class, remap = false)
public interface ScreenOptionsAccessor {
	@Accessor
	OptionsPage getSelectedPage();
}
