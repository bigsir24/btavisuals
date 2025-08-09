package bigsir.btavisuals.mixin;

import bigsir.btavisuals.models.BlockModelLeafPile;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.BlockModelLayer;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicLayerLeaves;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = BlockModelDispatcher.class, remap = false)
public abstract class BlockModelDispatcherMixin {
	@Redirect(method = "<init>", at = @At(value = "NEW", target = "(Lnet/minecraft/core/block/Block;)Lnet/minecraft/client/render/block/model/BlockModelLayer;", ordinal = 1))
	public BlockModelLayer<?> changeLeafPileModel(Block<BlockLogicLayerLeaves> block) {
		return new BlockModelLeafPile(block);
	}
}
