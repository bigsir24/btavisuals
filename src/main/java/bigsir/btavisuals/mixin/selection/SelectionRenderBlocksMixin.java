package bigsir.btavisuals.mixin.selection;

import bigsir.btavisuals.ducks.IBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.RenderBlocks;
import net.minecraft.core.block.Block;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RenderBlocks.class, remap = false)
public abstract class SelectionRenderBlocksMixin implements IBlocks {

	@Shadow
	@Final
	private Minecraft mc;

	@Shadow
	public int lightmapCoordTopLeft;
	@Shadow
	public int lightmapCoordBottomLeft;
	@Shadow
	public int lightmapCoordBottomRight;
	@Shadow
	public int lightmapCoordTopRight;
	@Shadow
	public float colorRedTopLeft;
	@Shadow
	public float colorRedTopRight;
	@Shadow
	public float colorRedBottomLeft;
	@Shadow
	public float colorRedBottomRight;
	@Shadow
	public float colorGreenTopLeft;
	@Shadow
	public float colorGreenTopRight;
	@Shadow
	public float colorGreenBottomLeft;
	@Shadow
	public float colorGreenBottomRight;
	@Shadow
	public float colorBlueTopLeft;
	@Shadow
	public float colorBlueTopRight;
	@Shadow
	public float colorBlueBottomLeft;
	@Shadow
	public float colorBlueBottomRight;
	@Unique
	boolean select = false;
	@Unique
	private float selectBrightness = 1.0f;

	@Override
	public void setSelect() {
		select = true;
	}

	@Override
	public void resetSelect() {
		select = false;
	}

	@Override
	public void setSelectBrightness(float selectBrightness) {
		this.selectBrightness = Math.min(selectBrightness, 1.0f);
	}

	/*

	@Redirect(method = {"renderTopFace", "renderNorthFace", "renderWestFace", "renderEastFace", "renderSouthFace", "renderBottomFace"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;setColorOpaque_F(FFF)V"))
	public void redir(Tessellator instance, float r, float g, float b){
		if(!select) instance.setColorOpaque_F(r,g,b);
	}*/

	@Inject(method = "setupLighting", at = @At("TAIL"), cancellable = true)
	public void canc(Block block, int x, int y, int z, float r, float g, float b, int side, int meta, int dirX, int dirY, int dirZ, float depth, int topX, int topY, int topZ, float topP, float botP, int lefX, int lefY, int lefZ, float lefP, float rigP, CallbackInfo ci){
		if(select) {
			this.colorRedTopLeft = this.colorRedTopRight = this.colorRedBottomLeft = this.colorRedBottomRight = r * selectBrightness;
			this.colorGreenTopLeft = this.colorGreenTopRight = this.colorGreenBottomLeft = this.colorGreenBottomRight = g * selectBrightness;
			this.colorBlueTopLeft = this.colorBlueTopRight = this.colorBlueBottomLeft = this.colorBlueBottomRight = b * selectBrightness;
		}
	}

}
