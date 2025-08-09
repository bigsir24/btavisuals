package bigsir.btavisuals.mixin;

import bigsir.btavisuals.BTAVisuals;
import bigsir.btavisuals.util.Corner;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.RenderBlocks;
import net.minecraft.client.render.block.model.BlockModel;
import net.minecraft.client.render.block.model.BlockModelFenceThin;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicFenceThin;
import net.minecraft.core.enums.LightLayer;
import net.minecraft.core.util.helper.Side;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = BlockModelFenceThin.class, remap = false)
public abstract class BlockModelFenceThinMixin<T extends BlockLogicFenceThin> extends BlockModelStandard<T> {

	public BlockModelFenceThinMixin(Block<T> block) {
		super(block);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 24))
	public void vn1(Tessellator instance, double x, double y, double z, double u, double v, @Local(name = "x") int xx, @Local(name = "y") int yy, @Local(name = "z") int zz) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.EAST, Corner.MAX_MIN);
		float brightness = getLightAvgF(xx, yy, zz, Side.EAST, Corner.MAX_MIN);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
			brightness = 0.85F;
		}

		instance.setColorOpaque_F(brightness,brightness,brightness);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 25))
	public void vn2(Tessellator instance, double x, double y, double z, double u, double v, @Local(name = "x") int xx, @Local(name = "y") int yy, @Local(name = "z") int zz) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.EAST, Corner.MIN_MIN);
		float brightness = getLightAvgF(xx, yy, zz, Side.EAST, Corner.MIN_MIN);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
			brightness = 0.85F;
		}

		instance.setColorOpaque_F(brightness,brightness,brightness);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 26))
	public void vn3(Tessellator instance, double x, double y, double z, double u, double v, @Local(name = "x") int xx, @Local(name = "y") int yy, @Local(name = "z") int zz) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.EAST, Corner.MIN_MAX);
		float brightness = getLightAvgF(xx, yy, zz, Side.EAST, Corner.MIN_MAX);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
			brightness = 0.85F;
		}

		instance.setColorOpaque_F(brightness,brightness,brightness);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 27))
	public void vn4(Tessellator instance, double x, double y, double z, double u, double v, @Local(name = "x") int xx, @Local(name = "y") int yy, @Local(name = "z") int zz) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.EAST, Corner.MAX_MAX);
		float brightness = getLightAvgF(xx, yy, zz, Side.EAST, Corner.MAX_MAX);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
			brightness = 0.85F;
		}

		instance.setColorOpaque_F(brightness,brightness,brightness);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 28))
	public void vn5(Tessellator instance, double x, double y, double z, double u, double v, @Local(name = "x") int xx, @Local(name = "y") int yy, @Local(name = "z") int zz) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.WEST, Corner.MIN_MIN);
		float brightness = getLightAvgF(xx, yy, zz, Side.WEST, Corner.MIN_MIN);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
			brightness = 0.85F;
		}

		instance.setColorOpaque_F(brightness,brightness,brightness);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 29))
	public void vn6(Tessellator instance, double x, double y, double z, double u, double v, @Local(name = "x") int xx, @Local(name = "y") int yy, @Local(name = "z") int zz) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.WEST, Corner.MAX_MIN);
		float brightness = getLightAvgF(xx, yy, zz, Side.WEST, Corner.MAX_MIN);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
			brightness = 0.85F;
		}

		instance.setColorOpaque_F(brightness,brightness,brightness);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 30))
	public void vn7(Tessellator instance, double x, double y, double z, double u, double v, @Local(name = "x") int xx, @Local(name = "y") int yy, @Local(name = "z") int zz) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.WEST, Corner.MAX_MAX);
		float brightness = getLightAvgF(xx, yy, zz, Side.WEST, Corner.MAX_MAX);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
			brightness = 0.85F;
		}

		instance.setColorOpaque_F(brightness,brightness,brightness);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 31))
	public void vn8(Tessellator instance, double x, double y, double z, double u, double v, @Local(name = "x") int xx, @Local(name = "y") int yy, @Local(name = "z") int zz) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.WEST, Corner.MIN_MAX);
		float brightness = getLightAvgF(xx, yy, zz, Side.WEST, Corner.MIN_MAX);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
			brightness = 0.85F;
		}

		instance.setColorOpaque_F(brightness,brightness,brightness);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 32))
	public void vs1(Tessellator instance, double x, double y, double z, double u, double v, @Local(name = "x") int xx, @Local(name = "y") int yy, @Local(name = "z") int zz) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.EAST, Corner.MAX_MIN);
		float brightness = getLightAvgF(xx, yy, zz, Side.EAST, Corner.MAX_MIN);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
			brightness = 0.85F;
		}

		instance.setColorOpaque_F(brightness,brightness,brightness);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 33))
	public void vs2(Tessellator instance, double x, double y, double z, double u, double v, @Local(name = "x") int xx, @Local(name = "y") int yy, @Local(name = "z") int zz) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.EAST, Corner.MIN_MIN);
		float brightness = getLightAvgF(xx, yy, zz, Side.EAST, Corner.MIN_MIN);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
			brightness = 0.85F;
		}

		instance.setColorOpaque_F(brightness,brightness,brightness);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 34))
	public void vs3(Tessellator instance, double x, double y, double z, double u, double v, @Local(name = "x") int xx, @Local(name = "y") int yy, @Local(name = "z") int zz) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.EAST, Corner.MIN_MAX);
		float brightness = getLightAvgF(xx, yy, zz, Side.EAST, Corner.MIN_MAX);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
			brightness = 0.85F;
		}

		instance.setColorOpaque_F(brightness,brightness,brightness);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 35))
	public void vs4(Tessellator instance, double x, double y, double z, double u, double v, @Local(name = "x") int xx, @Local(name = "y") int yy, @Local(name = "z") int zz) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.EAST, Corner.MAX_MAX);
		float brightness = getLightAvgF(xx, yy, zz, Side.EAST, Corner.MAX_MAX);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
			brightness = 0.85F;
		}

		instance.setColorOpaque_F(brightness,brightness,brightness);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 36))
	public void vs5(Tessellator instance, double x, double y, double z, double u, double v, @Local(name = "x") int xx, @Local(name = "y") int yy, @Local(name = "z") int zz) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.WEST, Corner.MIN_MIN);
		float brightness = getLightAvgF(xx, yy, zz, Side.WEST, Corner.MIN_MIN);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
			brightness = 0.85F;
		}

		instance.setColorOpaque_F(brightness,brightness,brightness);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 37))
	public void vs6(Tessellator instance, double x, double y, double z, double u, double v, @Local(name = "x") int xx, @Local(name = "y") int yy, @Local(name = "z") int zz) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.WEST, Corner.MAX_MIN);
		float brightness = getLightAvgF(xx, yy, zz, Side.WEST, Corner.MAX_MIN);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
			brightness = 0.85F;
		}

		instance.setColorOpaque_F(brightness,brightness,brightness);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 38))
	public void vs7(Tessellator instance, double x, double y, double z, double u, double v, @Local(name = "x") int xx, @Local(name = "y") int yy, @Local(name = "z") int zz) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.WEST, Corner.MAX_MAX);
		float brightness = getLightAvgF(xx, yy, zz, Side.WEST, Corner.MAX_MAX);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
			brightness = 0.85F;
		}

		instance.setColorOpaque_F(brightness,brightness,brightness);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 39))
	public void vs8(Tessellator instance, double x, double y, double z, double u, double v, @Local(name = "x") int xx, @Local(name = "y") int yy, @Local(name = "z") int zz) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.WEST, Corner.MIN_MAX);
		float brightness = getLightAvgF(xx, yy, zz, Side.WEST, Corner.MIN_MAX);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
			brightness = 0.85F;
		}

		instance.setColorOpaque_F(brightness,brightness,brightness);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 40))
	public void vw1(Tessellator instance, double x, double y, double z, double u, double v, @Local(name = "x") int xx, @Local(name = "y") int yy, @Local(name = "z") int zz) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.NORTH, Corner.MAX_MIN);
		float brightness = getLightAvgF(xx, yy, zz, Side.NORTH, Corner.MAX_MIN);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
			brightness = 0.85F;
		}

		instance.setColorOpaque_F(brightness,brightness,brightness);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 41))
	public void vw2(Tessellator instance, double x, double y, double z, double u, double v, @Local(name = "x") int xx, @Local(name = "y") int yy, @Local(name = "z") int zz) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.NORTH, Corner.MIN_MIN);
		float brightness = getLightAvgF(xx, yy, zz, Side.NORTH, Corner.MIN_MIN);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
			brightness = 0.85F;
		}

		instance.setColorOpaque_F(brightness,brightness,brightness);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 42))
	public void vw3(Tessellator instance, double x, double y, double z, double u, double v, @Local(name = "x") int xx, @Local(name = "y") int yy, @Local(name = "z") int zz) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.NORTH, Corner.MIN_MAX);
		float brightness = getLightAvgF(xx, yy, zz, Side.NORTH, Corner.MIN_MAX);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
			brightness = 0.85F;
		}

		instance.setColorOpaque_F(brightness,brightness,brightness);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 43))
	public void vw4(Tessellator instance, double x, double y, double z, double u, double v, @Local(name = "x") int xx, @Local(name = "y") int yy, @Local(name = "z") int zz) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.NORTH, Corner.MAX_MAX);
		float brightness = getLightAvgF(xx, yy, zz, Side.NORTH, Corner.MAX_MAX);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
			brightness = 0.85F;
		}

		instance.setColorOpaque_F(brightness,brightness,brightness);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 44))
	public void vw5(Tessellator instance, double x, double y, double z, double u, double v, @Local(name = "x") int xx, @Local(name = "y") int yy, @Local(name = "z") int zz) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.SOUTH, Corner.MIN_MIN);
		float brightness = getLightAvgF(xx, yy, zz, Side.SOUTH, Corner.MIN_MIN);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
			brightness = 0.85F;
		}

		instance.setColorOpaque_F(brightness,brightness,brightness);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 45))
	public void vw6(Tessellator instance, double x, double y, double z, double u, double v, @Local(name = "x") int xx, @Local(name = "y") int yy, @Local(name = "z") int zz) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.SOUTH, Corner.MAX_MIN);
		float brightness = getLightAvgF(xx, yy, zz, Side.SOUTH, Corner.MAX_MIN);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
			brightness = 0.85F;
		}

		instance.setColorOpaque_F(brightness,brightness,brightness);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 46))
	public void vw7(Tessellator instance, double x, double y, double z, double u, double v, @Local(name = "x") int xx, @Local(name = "y") int yy, @Local(name = "z") int zz) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.SOUTH, Corner.MAX_MAX);
		float brightness = getLightAvgF(xx, yy, zz, Side.SOUTH, Corner.MAX_MAX);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
			brightness = 0.85F;
		}

		instance.setColorOpaque_F(brightness,brightness,brightness);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 47))
	public void vw8(Tessellator instance, double x, double y, double z, double u, double v, @Local(name = "x") int xx, @Local(name = "y") int yy, @Local(name = "z") int zz) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.SOUTH, Corner.MIN_MAX);
		float brightness = getLightAvgF(xx, yy, zz, Side.SOUTH, Corner.MIN_MAX);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
			brightness = 0.85F;
		}

		instance.setColorOpaque_F(brightness,brightness,brightness);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 48))
	public void ve1(Tessellator instance, double x, double y, double z, double u, double v, @Local(name = "x") int xx, @Local(name = "y") int yy, @Local(name = "z") int zz) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.NORTH, Corner.MAX_MIN);
		float brightness = getLightAvgF(xx, yy, zz, Side.NORTH, Corner.MAX_MIN);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
			brightness = 0.85F;
		}

		instance.setColorOpaque_F(brightness,brightness,brightness);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 49))
	public void ve2(Tessellator instance, double x, double y, double z, double u, double v, @Local(name = "x") int xx, @Local(name = "y") int yy, @Local(name = "z") int zz) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.NORTH, Corner.MIN_MIN);
		float brightness = getLightAvgF(xx, yy, zz, Side.NORTH, Corner.MIN_MIN);
		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
			brightness = 0.85F;
		}

		instance.setColorOpaque_F(brightness,brightness,brightness);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 50))
	public void ve3(Tessellator instance, double x, double y, double z, double u, double v, @Local(name = "x") int xx, @Local(name = "y") int yy, @Local(name = "z") int zz) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.NORTH, Corner.MIN_MAX);
		float brightness = getLightAvgF(xx, yy, zz, Side.NORTH, Corner.MIN_MAX);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
			brightness = 0.85F;
		}

		instance.setColorOpaque_F(brightness,brightness,brightness);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 51))
	public void ve4(Tessellator instance, double x, double y, double z, double u, double v, @Local(name = "x") int xx, @Local(name = "y") int yy, @Local(name = "z") int zz) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.NORTH, Corner.MAX_MAX);
		float brightness = getLightAvgF(xx, yy, zz, Side.NORTH, Corner.MAX_MAX);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
			brightness = 0.85F;
		}

		instance.setColorOpaque_F(brightness,brightness,brightness);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 52))
	public void ve5(Tessellator instance, double x, double y, double z, double u, double v, @Local(name = "x") int xx, @Local(name = "y") int yy, @Local(name = "z") int zz) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.SOUTH, Corner.MIN_MIN);
		float brightness = getLightAvgF(xx, yy, zz, Side.SOUTH, Corner.MIN_MIN);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
			brightness = 0.85F;
		}

		instance.setColorOpaque_F(brightness,brightness,brightness);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 53))
	public void ve6(Tessellator instance, double x, double y, double z, double u, double v, @Local(name = "x") int xx, @Local(name = "y") int yy, @Local(name = "z") int zz) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.SOUTH, Corner.MAX_MIN);
		float brightness = getLightAvgF(xx, yy, zz, Side.SOUTH, Corner.MAX_MIN);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
			brightness = 0.85F;
		}

		instance.setColorOpaque_F(brightness,brightness,brightness);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 54))
	public void ve7(Tessellator instance, double x, double y, double z, double u, double v, @Local(name = "x") int xx, @Local(name = "y") int yy, @Local(name = "z") int zz) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.SOUTH, Corner.MAX_MAX);
		float brightness = getLightAvgF(xx, yy, zz, Side.SOUTH, Corner.MAX_MAX);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
			brightness = 0.85F;
		}

		instance.setColorOpaque_F(brightness,brightness,brightness);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 55))
	public void ve8(Tessellator instance, double x, double y, double z, double u, double v, @Local(name = "x") int xx, @Local(name = "y") int yy, @Local(name = "z") int zz) {

		if (!BTAVisuals.smoothWater.value || !BTAVisuals.AO()) {
			instance.addVertexWithUV(x, y, z, u, v);
			return;
		}

		int ll = getLightAvg(xx, yy, zz, Side.SOUTH, Corner.MIN_MAX);
		float brightness = getLightAvgF(xx, yy, zz, Side.SOUTH, Corner.MIN_MAX);

		if (LightmapHelper.isLightmapEnabled()) {
			instance.setLightmapCoord(ll);
			brightness = 0.85F;
		}

		instance.setColorOpaque_F(brightness,brightness,brightness);
		instance.addVertexWithUV(x,y,z,u,v);
	}

	@Unique
	private int getLight(int x, int y, int z) {
		RenderBlocks rb = BlockModel.renderBlocks;
		return rb.blockAccess.getLightmapCoord(x, y, z, rb.blockAccess.getSavedLightValue(LightLayer.Block, x, y, z));
	}

	@Unique
	private float getLightF(int x, int y, int z) {
		RenderBlocks rb = BlockModel.renderBlocks;
		return rb.getBlockBrightness(rb.blockAccess, x, y, z);
	}

	@Unique
	private float getLightAvgF(int x, int y, int z, Side side, Corner corner) {
		Side c1 = corner.getCorner1(side);
		Side c2 = corner.getCorner2(side);

		float lcl = getLightF(x, y, z);
		float lxl = getLightF(x + c1.getOffsetX(), y + c1.getOffsetY(), z + c1.getOffsetZ());
		float lzl = getLightF(x + c2.getOffsetX(), y + c2.getOffsetY(), z + c2.getOffsetZ());
		float lxzl = getLightF(x + c1.getOffsetX() + c2.getOffsetX(), y + c1.getOffsetY() + c2.getOffsetY(), z + c1.getOffsetZ() + c2.getOffsetZ());

		if(lxzl != 0 && lzl != 0 && lxl != 0 && lcl != 0) {
			return (lcl + lxl + lzl + lxzl) / 4.0F;
		}else if(lxzl == 0 && lzl != 0 && lxl != 0 && lcl != 0) {
			return (lcl + lxl + lzl) / 3.0F;
		}else if(lxzl == 0 && lzl == 0 && lxl != 0 && lcl != 0) {
			return (lcl + lxl) / 2.0F;
		}else if(lxzl == 0 && lzl != 0 && lxl == 0 && lcl != 0) {
			return (lcl + lzl) / 2.0F;
		}else if (lxzl != 0 && lzl == 0 && lxl != 0 && lcl != 0){
			return (lxzl + lxl + lcl) / 3.0F;
		}else if (lxzl != 0 && lzl != 0 && lxl == 0 && lcl != 0){
			return (lxzl + lzl + lcl) / 3.0F;
		}else {
			return lcl;
		}
	}

	@Unique
	private int getLightAvg(int x, int y, int z, Side side, Corner corner) {
		Side c1 = corner.getCorner1(side);
		Side c2 = corner.getCorner2(side);

		int lcl = getLight(x, y, z);
		int lxl = getLight(x + c1.getOffsetX(), y + c1.getOffsetY(), z + c1.getOffsetZ());
		int lzl = getLight(x + c2.getOffsetX(), y + c2.getOffsetY(), z + c2.getOffsetZ());
		int lxzl = getLight(x + c1.getOffsetX() + c2.getOffsetX(), y + c1.getOffsetY() + c2.getOffsetY(), z + c1.getOffsetZ() + c2.getOffsetZ());

		if(lxzl != 0 && lzl != 0 && lxl != 0 && lcl != 0) {
			return LightmapHelper.avg(lxl, lxzl, lzl, lcl);
		}else if(lxzl == 0 && lzl != 0 && lxl != 0 && lcl != 0) {
			return LightmapHelper.avg(lxl, lcl, lzl, lcl);
		}else if(lxzl == 0 && lzl == 0 && lxl != 0 && lcl != 0) {
			return LightmapHelper.avg(lcl, lxl);
		}else if(lxzl == 0 && lzl != 0 && lxl == 0 && lcl != 0) {
			return LightmapHelper.avg(lcl, lzl);
		}else if (lxzl != 0 && lzl == 0 && lxl != 0 && lcl != 0){
			return LightmapHelper.avg(lxzl, lxl, lxl, lcl);
		}else if (lxzl != 0 && lzl != 0 && lxl == 0 && lcl != 0){
			return LightmapHelper.avg(lxzl, lzl, lzl, lcl);
		}else {
			return lcl;
		}

		/*
		if(lxzl != 0 && lzl != 0 && lxl != 0 && lcl != 0) {
			System.out.println("1");
			return LightmapHelper.avg(lxl, lxzl, lzl, lcl);
		}else if(lxzl == 0 && lzl != 0 && lxl != 0 && lcl != 0) {
			System.out.println("2");
			return LightmapHelper.avg(lxl, LightmapHelper.avg(lzl, lcl));
		}else if(lxzl == 0 && lzl == 0 && lxl != 0 && lcl != 0) {
			System.out.println("3");
			return LightmapHelper.avg(lcl, lxl);
		}else if(lxzl == 0 && lzl != 0 && lxl == 0 && lcl != 0) {
			System.out.println("4");
			return LightmapHelper.avg(lcl, lzl);
		}else if (lxzl != 0 && lzl == 0 && lxl != 0 && lcl != 0){
			System.out.println("5");
			return LightmapHelper.avg(lxzl, LightmapHelper.avg(lxl, lcl));
		}else if (lxzl != 0 && lzl != 0 && lxl == 0 && lcl != 0){
			System.out.println("6");
			return LightmapHelper.avg(lxzl, LightmapHelper.avg(lzl, lcl));
		}else {
			System.out.println("7");
			return lcl;
		}*/
	}
}
