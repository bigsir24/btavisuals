package bigsir.btavisuals.mixin;

import bigsir.btavisuals.BTAVisuals;
import net.minecraft.client.render.block.model.BlockModelLeaves;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicLeavesBase;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.WorldSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BlockModelLeaves.class, remap = false)
public abstract class BlockModelLeavesMixin {

	@Redirect(method = "getBlockTextureFromSideAndMetadata", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/block/model/BlockModelLeaves;fancyGraphics:Z"))
	public boolean redirectOption() {
		return BTAVisuals.leavesMode.value >= 1;
	}

	@Inject(method = "shouldSideBeRendered", at = @At("HEAD"), cancellable = true)
	public void shouldRender(WorldSource blockAccess, AABB bounds, int x, int y, int z, int side, CallbackInfoReturnable<Boolean> cir) {
		if (BTAVisuals.leavesMode.value >= 2 && blockAccess.getBlockLogic(x, y, z, BlockLogicLeavesBase.class) != null)
			cir.setReturnValue(false);
	}
}
