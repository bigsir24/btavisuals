package bigsir.btavisuals.mixin;

import bigsir.btavisuals.BTAVisuals;
import net.minecraft.client.render.block.model.BlockModelGrass;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = BlockModelGrass.class, remap = false)
public abstract class BlockModelGrassMixin {
	@Redirect(method = {"render", "renderBlockOnInventory"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/RenderBlocks;fancyGrass:Z"))
	public boolean redirectOption() {
		return BTAVisuals.grassMode.value == 1;
	}
}
