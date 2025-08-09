package bigsir.btavisuals.models;

import bigsir.btavisuals.BTAVisuals;
import net.minecraft.client.render.block.model.BlockModelLayer;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicLayerLeaves;
import net.minecraft.core.util.helper.Side;

public class BlockModelLeafPile extends BlockModelLayer<BlockLogicLayerLeaves> {

	public static final IconCoordinate fancy = TextureRegistry.getTexture("minecraft:block/leaves/oak_fancy");
	public static final IconCoordinate fast = TextureRegistry.getTexture("minecraft:block/leaves/oak");

	public BlockModelLeafPile(Block<BlockLogicLayerLeaves> block) {
		super(block);
	}

	@Override
	public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int data) {
		if (BTAVisuals.leafPileGraphics.value && BTAVisuals.leavesMode.value == 0) {
			return fast;
		}

		return fancy;
	}
}
