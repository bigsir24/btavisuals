package bigsir.btavisuals.mixin.selection;

import bigsir.btavisuals.BTAVisuals;
import bigsir.btavisuals.GLManager;
import bigsir.btavisuals.ducks.IBlocks;
import bigsir.btavisuals.ducks.ISelect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.RenderBlocks;
import net.minecraft.client.render.RenderGlobal;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModel;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.camera.ICamera;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.client.world.WorldClient;
import net.minecraft.core.block.*;
import net.minecraft.core.enums.LightLayer;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.phys.HitResult;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RenderGlobal.class, remap = false)
public abstract class SelectionRenderGlobalMixin {
	@Shadow
	private RenderBlocks globalRenderBlocks;
	@Shadow
	private WorldClient worldObj;
	@Shadow
	@Final
	private Minecraft mc;
	@Unique
	private float lastFloat = 0.0f;

	@Inject(method = "drawSelectionBox", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glEnable(I)V", ordinal = 0), cancellable = true)
	public void cacheSelect(ICamera camera, HitResult hitResult, float partialTick, CallbackInfo ci){
		int selectorType = BTAVisuals.selectorType.value;
		if (selectorType == 0) return;

		if(hitResult.hitType == HitResult.HitType.TILE){
			Tessellator tessellator = Tessellator.instance;
			/*GL11.glEnable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, (MathHelper.sin((float)System.currentTimeMillis() / 100.0F) * 0.2F + 0.4F) * 0.5F);
			GL11.glBlendFunc(GL11.GL_DST_COLOR, GL11.GL_SRC_COLOR);*/
			TextureRegistry.blockAtlas.bind();
			//GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
			GL11.glPushMatrix();
			Block<?> block = worldObj.getBlock(hitResult.x, hitResult.y, hitResult.z);

			//This is used to prevent Z-fighting when rendering the two blocks

			GLManager.glDisable(GL11.GL_ALPHA_TEST);
			//GL11.glDisable(GL11.GL_ALPHA_TEST);

			if (!BTAVisuals.selectorDepthTest.value) GLManager.glDisable(GL11.GL_DEPTH_TEST); // Should already be enabled be previous calls
			GL11.glPolygonOffset(-0.5F, -0.5F);

			GLManager.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
			//GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);

			double x = camera.getX(partialTick);
			double y = camera.getY(partialTick);
			double z = camera.getZ(partialTick);
			if (block == null) {
				block = Blocks.STONE;
			}

			GL11.glEnable(GL11.GL_ALPHA_TEST);
			tessellator.startDrawingQuads();
			tessellator.setTranslation(-x, -y, -z);

			if (selectorType == 1) {
				tessellator.lockColor(); //Disable if using bright rendering
			}else {
				RenderBlocks.enableDirectionalLight = false; //False if bright rendering
				((IBlocks)this.globalRenderBlocks).setSelect(); //Bright rendering
			}


			BlockModel.setRenderBlocks(this.globalRenderBlocks);

			boolean cache = this.globalRenderBlocks.enableAO;
			this.globalRenderBlocks.enableAO = false;
			GL11.glEnable(GL11.GL_BLEND);
			//GL11.glEnable(GL11.GL_ALPHA_TEST);

			int alphaFunc = GL11.glGetInteger(GL11.GL_ALPHA_TEST_FUNC);
			float alphaRef = GL11.glGetFloat(GL11.GL_ALPHA_TEST_REF);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0f);

			if (selectorType == 1) {
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			}else {
				GL11.glBlendFunc(GL11.GL_DST_COLOR, GL11.GL_ONE_MINUS_DST_COLOR); //Bright rendering
			}
			//GL11.glColor4f(1.0f,1.0f,1.0f,0.4f);

			//GL11.glColor4f(1.0f,1.0f,1.0f,0.4f);

			//GL11.glLineWidth(1.0f);
			//GL11.glDisable(GL11.GL_TEXTURE_2D);

			GL11.glDepthMask(false);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_ADD);

			float flash = ((ISelect) this.mc).getSelectFlash();

			int hx = hitResult.x + hitResult.side.getOffsetX();
			int hy = hitResult.y + hitResult.side.getOffsetY();
			int hz = hitResult.z + hitResult.side.getOffsetZ();

			float brightness = worldObj.getLightBrightness(hx,hy,hz);

			if (selectorType == 2) ((IBlocks)this.globalRenderBlocks).setSelectBrightness(brightness+0.4f); //Bright rendering

			int light = worldObj.getBlockLightValue(hx,hy,hz);
			int slight = worldObj.getSavedLightValue(LightLayer.Sky, hx,hy,hz);

			//brightness = Math.min(light + slight, 15) / 15f; //Not sure if this did anything useful?

			//System.out.println(brightness);

			float alpha = 0.05f;
			if(block == Blocks.ICE) {
				alpha += 0.15f;
			}else if(block == Blocks.BLOCK_NETHER_COAL || block == Blocks.OBSIDIAN){
				if(brightness < 0.5f) {
					brightness += 0.3f;
				}
			}
			GL11.glColor4f(brightness,brightness,brightness, alpha + lerp(this.lastFloat, flash, partialTick));

			this.lastFloat = flash;

			//Used for MCPE-like bright selection
			if (selectorType == 2) {
				int color = BlockColorDispatcher.getInstance().getDispatch(block).getWorldColor(worldObj, hitResult.x, hitResult.y, hitResult.z);
				float r = ((color & 0xff0000) >> 16) / 255.0f;
				float g = ((color & 0x00ff00) >> 8) / 255.0f;
				float b = (color & 0x0000ff) / 255.0f;
				tessellator.setColorOpaque_F(r, g, b);
			}
			//GL11.glColor4f(1.0f,1.0f,1.0f,1.0f); //Bright rendering

			BlockModelDispatcher.getInstance().getDispatch(block).render(tessellator, hitResult.x, hitResult.y, hitResult.z);

			BlockLogic logic = block.getLogic();
			if(logic instanceof BlockLogicDoor){
				if(((BlockLogicDoor) logic).isTop){
					Block<?> blockOther = worldObj.getBlock(hitResult.x, hitResult.y-1, hitResult.z);
					BlockModelDispatcher.getInstance().getDispatch(blockOther).render(tessellator, hitResult.x, hitResult.y-1, hitResult.z);
				}else{
					Block<?> blockOther = worldObj.getBlock(hitResult.x, hitResult.y+1, hitResult.z);
					BlockModelDispatcher.getInstance().getDispatch(blockOther).render(tessellator, hitResult.x, hitResult.y+1, hitResult.z);
				}
			}else if(logic instanceof BlockLogicChest){
				int meta = worldObj.getBlockMetadata(hitResult.x, hitResult.y, hitResult.z);
				BlockLogicChest.Type type = BlockLogicChest.getTypeFromMeta(meta);
				if(type != BlockLogicChest.Type.SINGLE){
					int index = BlockLogicChest.getDirectionFromMeta(meta).getHorizontalIndex();
					if(type == BlockLogicChest.Type.RIGHT){
						Direction dir = Direction.horizontalDirections[wrapInt(index+1, 4)];
						int u = dir.getOffsetX();
						int w = dir.getOffsetZ();
						Block<?> blockOther = worldObj.getBlock(hitResult.x + u, hitResult.y, hitResult.z + w);
						BlockModelDispatcher.getInstance().getDispatch(blockOther).render(tessellator, hitResult.x + u, hitResult.y, hitResult.z + w);
					}else if(type == BlockLogicChest.Type.LEFT){
						Direction dir = Direction.horizontalDirections[wrapInt(index-1, 4)];
						int u = dir.getOffsetX();
						int w = dir.getOffsetZ();
						Block<?> blockOther = worldObj.getBlock(hitResult.x + u, hitResult.y, hitResult.z + w);
						BlockModelDispatcher.getInstance().getDispatch(blockOther).render(tessellator, hitResult.x + u, hitResult.y, hitResult.z + w);
					}
				}
			}

			tessellator.draw();

			this.globalRenderBlocks.enableAO = cache;
			if (selectorType == 2) {
				RenderBlocks.enableDirectionalLight = true; //Bright rendering
				((IBlocks) this.globalRenderBlocks).resetSelect(); //Bright rendering
			}

			GL11.glAlphaFunc(alphaFunc, alphaRef); //Resetting to previous values

			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);

			//GL11.glEnable(GL11.GL_TEXTURE_2D);
			//GL11.glDisable(GL11.GL_BLEND);
			//GL11.glDisable(GL11.GL_ALPHA_TEST);
			//GL11.glDepthMask(true);
			tessellator.setTranslation(0.0, 0.0, 0.0);

			//Resetting offset
			//GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glPolygonOffset(0.0F, 0.0F);
			//GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
			//GL11.glEnable(GL11.GL_ALPHA_TEST);

			GL11.glDepthMask(true);
			GL11.glPopMatrix();

			//GL11.glDisable(GL11.GL_BLEND);
			//GL11.glDisable(GL11.GL_ALPHA_TEST);

			GLManager.restore();

			//GL11.glEnable(GL11.GL_DEPTH_TEST);
			ci.cancel();
		}
	}

	public int wrapInt(int num, int length){
		if(num < 0){
			int tempNum = num % length;
			return tempNum < 0 ? tempNum + length : 0;
		}
		return num % length;
	}

	public float lerp(float old, float curr, float partial){
		return old + (old-curr)*partial;
	}
}
