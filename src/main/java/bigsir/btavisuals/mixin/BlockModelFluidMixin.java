package bigsir.btavisuals.mixin;

import bigsir.btavisuals.BTAVisuals;
import bigsir.btavisuals.util.Corner;
import bigsir.btavisuals.util.Lmh;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import net.minecraft.core.block.Blocks;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.RenderBlocks;
import net.minecraft.client.render.block.model.BlockModel;
import net.minecraft.client.render.block.model.BlockModelFluid;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.enums.LightLayer;
import net.minecraft.core.util.helper.Side;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = BlockModelFluid.class, remap = false)
public abstract class BlockModelFluidMixin<T extends BlockLogic> extends BlockModelStandard<T> {
	@Shadow
	public abstract boolean render(Tessellator tessellator, int x, int y, int z);

	public BlockModelFluidMixin(Block<T> block) {
		super(block);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 4))
	public void r1(Tessellator instance, double x, double y, double z, double u, double v,
				   @Local(name = "x") int xx,
				   @Local(name = "y") int yy,
				   @Local(name = "z") int zz,
				   @Local(name = "r") float r,
				   @Local(name = "g") float g,
				   @Local(name = "b") float b) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.TOP, Corner.MIN_MIN);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
		} else {
			r *= this.getBlockBrightness(renderBlocks.blockAccess, xx, yy, zz);
			g *= this.getBlockBrightness(renderBlocks.blockAccess, xx, yy, zz);
			b *= this.getBlockBrightness(renderBlocks.blockAccess, xx, yy, zz);
		}

		instance.setColorOpaque_F(r,g,b);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 5))
	public void r2(Tessellator instance, double x, double y, double z, double u, double v,
				   @Local(name = "x") int xx,
				   @Local(name = "y") int yy,
				   @Local(name = "z") int zz,
				   @Local(name = "r") float r,
				   @Local(name = "g") float g,
				   @Local(name = "b") float b) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.TOP, Corner.MIN_MAX);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
		} else {
			r *= this.getBlockBrightness(renderBlocks.blockAccess, xx, yy, zz);
			g *= this.getBlockBrightness(renderBlocks.blockAccess, xx, yy, zz);
			b *= this.getBlockBrightness(renderBlocks.blockAccess, xx, yy, zz);
		}

		instance.setColorOpaque_F(r,g,b);
		instance.addVertexWithUV(x, y, z, u, v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 6))
	public void r3(Tessellator instance, double x, double y, double z, double u, double v,
				   @Local(name = "x") int xx,
				   @Local(name = "y") int yy,
				   @Local(name = "z") int zz,
				   @Local(name = "r") float r,
				   @Local(name = "g") float g,
				   @Local(name = "b") float b) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.TOP, Corner.MAX_MAX);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
		} else {
			r *= this.getBlockBrightness(renderBlocks.blockAccess, xx, yy, zz);
			g *= this.getBlockBrightness(renderBlocks.blockAccess, xx, yy, zz);
			b *= this.getBlockBrightness(renderBlocks.blockAccess, xx, yy, zz);
		}

		instance.setColorOpaque_F(r,g,b);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 7))
	public void r(Tessellator instance, double x, double y, double z, double u, double v,
				   @Local(name = "x") int xx,
				   @Local(name = "y") int yy,
				   @Local(name = "z") int zz,
				   @Local(name = "r") float r,
				   @Local(name = "g") float g,
				   @Local(name = "b") float b) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.TOP, Corner.MAX_MIN);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
		} else {
			r *= this.getBlockBrightness(renderBlocks.blockAccess, xx, yy, zz);
			g *= this.getBlockBrightness(renderBlocks.blockAccess, xx, yy, zz);
			b *= this.getBlockBrightness(renderBlocks.blockAccess, xx, yy, zz);
		}

		instance.setColorOpaque_F(r,g,b);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 0))
	public void rf1(Tessellator instance, double x, double y, double z, double u, double v,
				   @Local(name = "x") int xx,
				   @Local(name = "y") int yy,
				   @Local(name = "z") int zz,
				   @Local(name = "r") float r,
				   @Local(name = "g") float g,
				   @Local(name = "b") float b) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.TOP, Corner.MIN_MIN);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
		} else {
			r *= this.getBlockBrightness(renderBlocks.blockAccess, xx, yy, zz);
			g *= this.getBlockBrightness(renderBlocks.blockAccess, xx, yy, zz);
			b *= this.getBlockBrightness(renderBlocks.blockAccess, xx, yy, zz);
		}

		instance.setColorOpaque_F(r,g,b);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 1))
	public void rf2(Tessellator instance, double x, double y, double z, double u, double v,
				   @Local(name = "x") int xx,
				   @Local(name = "y") int yy,
				   @Local(name = "z") int zz,
				   @Local(name = "r") float r,
				   @Local(name = "g") float g,
				   @Local(name = "b") float b) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.TOP, Corner.MIN_MAX);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
		} else {
			r *= this.getBlockBrightness(renderBlocks.blockAccess, xx, yy, zz);
			g *= this.getBlockBrightness(renderBlocks.blockAccess, xx, yy, zz);
			b *= this.getBlockBrightness(renderBlocks.blockAccess, xx, yy, zz);
		}

		instance.setColorOpaque_F(r,g,b);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 2))
	public void rf3(Tessellator instance, double x, double y, double z, double u, double v,
				   @Local(name = "x") int xx,
				   @Local(name = "y") int yy,
				   @Local(name = "z") int zz,
				   @Local(name = "r") float r,
				   @Local(name = "g") float g,
				   @Local(name = "b") float b) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.TOP, Corner.MAX_MAX);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
		} else {
			r *= this.getBlockBrightness(renderBlocks.blockAccess, xx, yy, zz);
			g *= this.getBlockBrightness(renderBlocks.blockAccess, xx, yy, zz);
			b *= this.getBlockBrightness(renderBlocks.blockAccess, xx, yy, zz);
		}

		instance.setColorOpaque_F(r,g,b);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 3))
	public void rf(Tessellator instance, double x, double y, double z, double u, double v,
				   @Local(name = "x") int xx,
				   @Local(name = "y") int yy,
				   @Local(name = "z") int zz,
				   @Local(name = "r") float r,
				   @Local(name = "g") float g,
				   @Local(name = "b") float b) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.TOP, Corner.MAX_MIN);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
		} else {
			r *= this.getBlockBrightness(renderBlocks.blockAccess, xx, yy, zz);
			g *= this.getBlockBrightness(renderBlocks.blockAccess, xx, yy, zz);
			b *= this.getBlockBrightness(renderBlocks.blockAccess, xx, yy, zz);
		}

		instance.setColorOpaque_F(r,g,b);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 8))
	public void rf1s(Tessellator instance, double x, double y, double z, double u, double v,
				   @Local(name = "x") int xx,
				   @Local(name = "y") int yy,
				   @Local(name = "z") int zz,
				   @Local(name = "r") float r,
				   @Local(name = "g") float g,
				   @Local(name = "b") float b, @Local(name="side") int side) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		Side s = Side.getSideById(side + 2);
		Corner corner;

		if (side == 0) {
			corner = Corner.MIN_MAX;
		} else if (side == 1) {
			corner = Corner.MAX_MAX;
		} else if (side == 2) {
			corner = Corner.MAX_MAX;
		} else {
			corner = Corner.MIN_MAX;
		}

		int ll = getLightAvg(xx, yy, zz, s, corner);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
		} else {
			r *= this.getBlockBrightness(renderBlocks.blockAccess, xx + s.getOffsetX(), yy, zz + s.getOffsetZ());
			g *= this.getBlockBrightness(renderBlocks.blockAccess, xx + s.getOffsetX(), yy, zz + s.getOffsetZ());
			b *= this.getBlockBrightness(renderBlocks.blockAccess, xx + s.getOffsetX(), yy, zz + s.getOffsetZ());
		}

		float sl = BTAVisuals.sideLight(side + 2);

		instance.setColorOpaque_F(r * sl,g * sl,b * sl);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 9))
	public void rf2s(Tessellator instance, double x, double y, double z, double u, double v,
				   @Local(name = "x") int xx,
				   @Local(name = "y") int yy,
				   @Local(name = "z") int zz,
				   @Local(name = "r") float r,
				   @Local(name = "g") float g,
				   @Local(name = "b") float b, @Local(name="side") int side) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		Side s = Side.getSideById(side + 2);
		Corner corner;

		if (side == 0) {
			corner = Corner.MAX_MAX;
		} else if (side == 1) {
			corner = Corner.MIN_MAX;
		} else if (side == 2) {
			corner = Corner.MIN_MAX;
		} else {
			corner = Corner.MAX_MAX;
		}

		int ll = getLightAvg(xx, yy, zz, s, corner);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
		} else {
			r *= this.getBlockBrightness(renderBlocks.blockAccess, xx + s.getOffsetX(), yy, zz + s.getOffsetZ());
			g *= this.getBlockBrightness(renderBlocks.blockAccess, xx + s.getOffsetX(), yy, zz + s.getOffsetZ());
			b *= this.getBlockBrightness(renderBlocks.blockAccess, xx + s.getOffsetX(), yy, zz + s.getOffsetZ());
		}

		float sl = BTAVisuals.sideLight(side + 2);

		instance.setColorOpaque_F(r * sl,g * sl,b * sl);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 10))
	public void rf3s(Tessellator instance, double x, double y, double z, double u, double v,
				   @Local(name = "x") int xx,
				   @Local(name = "y") int yy,
				   @Local(name = "z") int zz,
				   @Local(name = "r") float r,
				   @Local(name = "g") float g,
				   @Local(name = "b") float b, @Local(name="side") int side) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		Side s = Side.getSideById(side + 2);
		Corner corner;

		if (side == 0) {
			corner = Corner.MAX_MIN;
		} else if (side == 1) {
			corner = Corner.MIN_MIN;
		} else if (side == 2) {
			corner = Corner.MIN_MIN;
		} else {
			corner = Corner.MAX_MIN;
		}

		int ll = getLightAvg(xx, yy, zz, s, corner);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
		} else {
			r *= this.getBlockBrightness(renderBlocks.blockAccess, xx + s.getOffsetX(), yy, zz + s.getOffsetZ());
			g *= this.getBlockBrightness(renderBlocks.blockAccess, xx + s.getOffsetX(), yy, zz + s.getOffsetZ());
			b *= this.getBlockBrightness(renderBlocks.blockAccess, xx + s.getOffsetX(), yy, zz + s.getOffsetZ());
		}

		float sl = BTAVisuals.sideLight(side + 2);

		instance.setColorOpaque_F(r * sl,g * sl,b * sl);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 11))
	public void rfs(Tessellator instance, double x, double y, double z, double u, double v,
				   @Local(name = "x") int xx,
				   @Local(name = "y") int yy,
				   @Local(name = "z") int zz,
				   @Local(name = "r") float r,
				   @Local(name = "g") float g,
				   @Local(name = "b") float b, @Local(name="side") int side) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		Side s = Side.getSideById(side + 2);
		Corner corner;

		if (side == 0) {
			corner = Corner.MIN_MIN;
		} else if (side == 1) {
			corner = Corner.MAX_MIN;
		} else if (side == 2) {
			corner = Corner.MAX_MIN;
		} else {
			corner = Corner.MIN_MIN;
		}

		int ll = getLightAvg(xx, yy, zz, s, corner);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
		} else {
			r *= this.getBlockBrightness(renderBlocks.blockAccess, xx + s.getOffsetX(), yy, zz + s.getOffsetZ());
			g *= this.getBlockBrightness(renderBlocks.blockAccess, xx + s.getOffsetX(), yy, zz + s.getOffsetZ());
			b *= this.getBlockBrightness(renderBlocks.blockAccess, xx + s.getOffsetX(), yy, zz + s.getOffsetZ());
		}

		float sl = BTAVisuals.sideLight(side + 2);

		instance.setColorOpaque_F(r * sl,g * sl,b * sl);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Inject(method = "render", at = @At(value = "INVOKE" ,target = "Lnet/minecraft/client/render/tessellator/Tessellator;setColorOpaque_F(FFF)V", ordinal = 3))
	public void fixLight(Tessellator tessellator, int x, int y, int z, CallbackInfoReturnable<Boolean> cir, @Local(name="side") int side, @Local(name="brightness") LocalFloatRef brightness) {
		brightness.set(BTAVisuals.sideLight(side + 2));
	}

	@Inject(method = "render", at = @At(value = "INVOKE" ,target = "Lnet/minecraft/client/render/tessellator/Tessellator;setColorOpaque_F(FFF)V", ordinal = 3, shift = At.Shift.AFTER))
	public void fixLight2(Tessellator tessellator, int x, int y, int z, CallbackInfoReturnable<Boolean> cir, @Local(name="side") int side, @Local(name="brightness") float brightness, @Local(name="r") float r, @Local(name="g") float g, @Local(name="b") float b) {
		tessellator.setColorOpaque_F(r * brightness,g * brightness,b * brightness);
	}

	@Unique
	private int getLight(int x, int y, int z) {
		RenderBlocks rb = BlockModel.renderBlocks;
		return rb.blockAccess.getLightmapCoord(x, y, z, rb.blockAccess.getSavedLightValue(LightLayer.Block, x, y, z)) | (isSolid(x,y,z) ? 1 : 0);
	}

	@Unique
	private int getLightAvg(int x, int y, int z, Side side, Corner corner) {
		int sx = side.getOffsetX();
		int sy = side.getOffsetY();
		int sz = side.getOffsetZ();

		Side c1 = corner.getCorner1(side);
		Side c2 = corner.getCorner2(side);

		int lc = getLight(x + sx, y + sy, z + sz);
		int lx = getLight(x + c1.getOffsetX() + sx, y + sy + c1.getOffsetY(), z + sz + c1.getOffsetZ());
		int lz = getLight(x + sx + c2.getOffsetX(), y + sy + c2.getOffsetY(), z + c2.getOffsetZ() + sz);
		int lxz = getLight(x + c1.getOffsetX() + c2.getOffsetX() + sx, y + sy + c1.getOffsetY() + c2.getOffsetY(), z + c1.getOffsetZ() + c2.getOffsetZ() + sz);

		int lcl = getLight(x, y, z);
		int lxl = getLight(x + c1.getOffsetX(), y + c1.getOffsetY(), z + c1.getOffsetZ());
		int lzl = getLight(x + c2.getOffsetX(), y + c2.getOffsetY(), z + c2.getOffsetZ());
		int lxzl = getLight(x + c1.getOffsetX() + c2.getOffsetX(), y + c1.getOffsetY() + c2.getOffsetY(), z + c1.getOffsetZ() + c2.getOffsetZ());

		boolean bcl = isSolidCoord(lcl);
		boolean bxl = isSolidCoord(lxl);
		boolean bzl = isSolidCoord(lzl);
		boolean bxzl = isSolidCoord(lxzl);

		boolean bc = isSolidCoord(lc);
		boolean bx = isSolidCoord(lx);
		boolean bz = isSolidCoord(lz);
		boolean bxz = isSolidCoord(lxz);

		//I need this because I'm dumb :I
		lcl = lcl & ~0b1111;
		lxl = lxl & ~0b1111;
		lzl = lzl & ~0b1111;
		lxzl = lxzl & ~0b1111;

		lc = lc & ~0b1111;
		lx = lx & ~0b1111;
		lz = lz & ~0b1111;
		lxz = lxz & ~0b1111;

		//set(lxzl, lxl, lzl, lcl);
		//int avgc = mixNonZero4(0);
		int avgc = 0;

		if(!bxzl && !bzl && !bxl && !bcl) {
			avgc = Lmh.avg(lcl, lxl, lzl, lxzl);
		}else if(bxzl && !bzl && !bxl && !bcl) {
			avgc = Lmh.avg(lcl, lxl, lzl);
		}else if(bxzl && bzl && !bxl && !bcl) {
			avgc = Lmh.avg(lcl, lxl);
		}else if(bxzl && !bzl && bxl && !bcl) {
			avgc = Lmh.avg(lcl, lzl);
		}else if (!bxzl && bzl && !bxl && !bcl){
			avgc = Lmh.avg(lxl, lcl, lxzl);
		}else if (!bxzl && !bzl && bxl && !bcl){
			avgc = Lmh.avg(lzl, lcl, lxzl);
		}else {
			avgc = lcl;
		}


		//set(lxz, lx, lz, lc);
		//int avgs = mixNonZero4(0);
		int avgs = 0;


		if(!bxz && !bz && !bx && !bc) {
			avgs = Lmh.avg(lx, lxz, lz, lc);
		}else if(bxz && !bz && !bx && !bc) {
			avgs = Lmh.avg(lc, lx, lz);
		}else if(bxz && bz && !bx && !bc) {
			avgs = Lmh.avg(lc, lx);
		}else if(bxz && !bz && bx && !bc) {
			avgs = Lmh.avg(lc, lz);
		}else if (!bxz && bz && !bx && !bc){
			avgs = Lmh.avg(lx, lxz, lc);
		}else if (!bxz && !bz && bx && !bc){
			avgs = Lmh.avg(lz, lxz, lc);
		}else {
			avgs = lc;
		}

		if(lc == 0) {
			set(lx,lxz,lz);
			int bits = (bxl ? 4 : 0) | (bxzl ? 2 : 0) | (bzl ? 1 : 0);
			int mix = mixNonZero(bits);
			return mix == 0 ? avgc : mix > avgc ? mix : Lmh.avg(mix, avgc);
		}

		if(avgs == 0 && avgc != 0) {
			return avgc;
		}else if(avgc == 0 && avgs != 0) {
			return avgs;
		}else {
			return avgs > avgc ? avgs : Lmh.avg(avgs, avgc);
		}

		/*
		if(lc == 0) {
			if (lc == 0 && lx != 0 && lz != 0 && lxz != 0) {
				return LightmapHelper.avg(LightmapHelper.avg(lx, lxz), lz);
			}else if (lc == 0 && lx != 0 && lz != 0 && lxz == 0) {
				return LightmapHelper.avg(lx, lz);
			}else if (lc == 0 && lx != 0 && lz == 0 && lxz != 0) {
				return LightmapHelper.avg(lxz, lx);
			}else if (lc == 0 && lx == 0 && lz != 0 && lxz != 0) {
				return LightmapHelper.avg(lxz, lz);
			}else {
				return lcl;
			}
		}else {
			if(lcl == 0 || lxl == 0 || lzl == 0 || lxzl == 0){
				return LightmapHelper.avg(lc, lxz, lx, lz);
			}else{
				return LightmapHelper.avg(LightmapHelper.avg(lcl, lxzl, lxl, lzl), LightmapHelper.avg(lc, lxz, lx, lz));
			}
		}
		 */


		/*
		int avgs = lxz == 0 ? lc == 0 ? LightmapHelper.avg(lz, lx) : LightmapHelper.avg(LightmapHelper.avg(lc, lx), lz) : lc == 0 ? lx == 0 ? lxz : LightmapHelper.avg(LightmapHelper.avg(lz, lx), lxz) : LightmapHelper.avg(lc,lx,lz,lxz);
		int avgc = lxzl == 0 ? LightmapHelper.avg(LightmapHelper.avg(lcl, lxl), lzl) : LightmapHelper.avg(lcl,lxl,lzl,lxzl);

		if(lc == 0 && lx == 0 && lxz != 0) {
			return LightmapHelper.avg(lxz, avgc);
		}else if(avgs == 0) {
			return avgc;
		}

		return LightmapHelper.avg(avgs, avgc);
		 */
	}

	private static final int[] PARAMS = new int[3];
	private static final int[] PARAMS4 = new int[4];

	@Unique
	private void set(int a, int b, int c) {
		PARAMS[0] = a;
		PARAMS[1] = b;
		PARAMS[2] = c;
	}

	@Unique
	private void set(int a, int b, int c, int d) {
		PARAMS4[0] = a;
		PARAMS4[1] = b;
		PARAMS4[2] = c;
		PARAMS4[3] = d;
	}

	@Unique
	private int mixNonZero(int bits) {
		int mix = 0;
		for (int i = 0; i < 3; i++) {
			int val = PARAMS[i];
			if(((bits << i) & 0b100) > 0) continue;
			if (mix == 0) {
				mix = val;
			}else if (val > 0) {
				mix = Lmh.avg(mix, val);
			}
		}
		return mix;
	}

	@Unique
	private int mixNonZero4(int bits) {
		int mix = 0;

		for (int i = 0; i < 4; i++) {

		}

		int it = 1;
		for (int i = 0; i >= 0; i += it) {
			int val = PARAMS4[i];

			if (mix == 0) {
				mix = val;
			}else if (val > 0) {
				mix = Lmh.avg(mix, val);
			}

			if(i >= 1 && it > 0) {
				it *= -1;
				i = 3;
			}
		}
		return mix;
	}

	@Unique
	private boolean isSolid(int x, int y, int z) {
		return Blocks.solid[renderBlocks.blockAccess.getBlockId(x,y,z)];
	}

	@Unique
	private boolean isSolidCoord(int lmc) {
		return (lmc & 1) == 1;
	}
}
