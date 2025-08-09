package bigsir.btavisuals.mixin;

import bigsir.btavisuals.GLManager;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.Scissor;
import net.minecraft.client.render.camera.ICamera;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.Global;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.animal.MobCow;
import net.minecraft.core.entity.monster.MobCreeper;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityRenderer.class, remap = false)
public abstract class EntityRendererMixin<T extends Entity> {
	@Inject(method = "renderShadow", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;draw()V", shift = At.Shift.AFTER))
	public void renderCustomShadow(Tessellator tessellator, T entity, double posX, double posY, double posZ, float opacity, float partialTick, CallbackInfo ci) {
		/*double lerpX = entity.xo + (entity.x - entity.xo) * (double)partialTick;
		double lerpY = entity.yo + (entity.y - entity.yo) * (double)partialTick + (double)entity.getShadowHeightOffs();
		double lerpZ = entity.zo + (entity.z - entity.zo) * (double)partialTick;
		drawFanShadow(tessellator, entity, posX, posY, posZ, opacity, lerpX, lerpY, lerpZ, partialTick);*/
	}

	@Unique
	public void drawFanShadow(Tessellator tessellator, T entity, double x, double y, double z, float opacity, double xo, double yo, double zo, float partialTick) {
		ICamera cam = Minecraft.getMinecraft().activeCamera;
		GL11.glPushMatrix();
		GL11.glTranslated(-cam.getX(partialTick), -cam.getY(partialTick), -cam.getZ(partialTick));

		AABB aabb = entity.bb;
		double yMin = yo - entity.getShadowHeightOffs();

		GLManager.glDisable(GL11.GL_TEXTURE_2D);

		GL11.glDepthMask(false);
		tessellator.startDrawing(GL11.GL_TRIANGLE_FAN);
		tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.5F);
		tessellator.addVertex(xo, yMin, zo);
		int order = 12;
		float step = 360.0F / order;
		for (int i = 0; i < order + 1; i++) {
			Vec3 vec = Vec3.getPermanentVec3(1, 0, 0);
			vec.rotateAroundY((float) Math.toRadians(i * step));
			tessellator.addVertex(xo + vec.x, yMin, zo + vec.z);
		}
		tessellator.draw();

		GLManager.restore();
		GL11.glPopMatrix();

	}
}
