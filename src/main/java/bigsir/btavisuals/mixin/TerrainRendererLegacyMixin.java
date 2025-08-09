package bigsir.btavisuals.mixin;

import bigsir.btavisuals.BTAVisuals;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.RenderGlobal;
import net.minecraft.client.render.camera.ICamera;
import net.minecraft.client.render.terrain.TerrainRenderer;
import net.minecraft.client.render.terrain.TerrainRendererLegacy;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TerrainRendererLegacy.class, remap = false)
public abstract class TerrainRendererLegacyMixin extends TerrainRenderer {
	public TerrainRendererLegacyMixin(Minecraft minecraft) {
		super(minecraft);
	}

	@Redirect(method = "renderTranslucentTerrain", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderGlobal;sortAndRender(Lnet/minecraft/client/render/camera/ICamera;ID)I", ordinal = 0))
	public int r(RenderGlobal instance, ICamera camera, int renderPass, double partialTick){
		if(BTAVisuals.dualPassOnFast.value) {
			GL11.glColorMask(false, false, false, false);
			int renderCount = this.mc.renderGlobal.sortAndRender(this.mc.activeCamera, renderPass, partialTick);
			GL11.glColorMask(true, true, true, true);
			if (renderCount > 0) this.mc.renderGlobal.callAllDisplayLists(renderPass, partialTick);
		}else {
			this.mc.renderGlobal.sortAndRender(this.mc.activeCamera, renderPass, partialTick);
		}
		return 0;
	}
}
