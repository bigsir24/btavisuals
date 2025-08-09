package bigsir.btavisuals.mixin;

import bigsir.btavisuals.BTAVisuals;
import net.minecraft.client.render.TextureManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MobRenderer.class, remap = false)
public abstract class MobRendererMixin<T extends Mob> extends EntityRenderer<T> {
	@Inject(method = "renderSpecials", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/MobRenderer;renderLivingLabel(Lnet/minecraft/client/render/tessellator/Tessellator;Lnet/minecraft/core/entity/Mob;Ljava/lang/String;DDDIZ)V", ordinal = 1), cancellable = true)
	public void renderOtherIcon(Tessellator tessellator, T entity, double x, double y, double z, CallbackInfo ci) {
		if (BTAVisuals.animalLabels.value == 1) {
			GL11.glPushMatrix();
			GL11.glTranslatef((float) x, (float) y + entity.getHeadHeight() + 0.8F - 3 / 32.0F, (float) z);
			GL11.glNormal3f(0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-this.renderDispatcher.viewLerpYaw, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(this.renderDispatcher.viewLerpPitch, 1.0F, 0.0F, 0.0F);
			GL11.glScalef(-0.026666671F, -0.026666671F, 0.026666671F);
			GL11.glDisable(2896);
			GL11.glDepthMask(false);
			GL11.glDisable(2929);

			TextureManager manager = renderDispatcher.textureManager;
			manager.bindTexture(manager.loadTexture("/assets/btavisuals/textures/icons/labeliconsmirrored.png"));

			float offset = 0.001F;
			int size = 6;
			float xOffset = 0;
			int code = getTexCode(entity);
			float minU = (code % 4) * 0.25F;
			float minV = (code / 4) * 0.25F;

			GL11.glEnable(3553);
			GL11.glEnable(GL11.GL_BLEND);
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, 0.25F);
			tessellator.addVertexWithUV(-size - xOffset, -size, 0.0, minU + offset, minV + offset);
			tessellator.addVertexWithUV(-size - xOffset, size, 0.0, minU + offset, minV + 8/32.0F - offset);
			tessellator.addVertexWithUV(size - xOffset, size, 0.0, minU + 8/32.0F - offset, minV + 8/32.0F - offset);
			tessellator.addVertexWithUV(size - xOffset, -size, 0.0, minU + 8/32.0F - offset, minV + offset);
			tessellator.draw();
			GL11.glEnable(2929);

			GL11.glDepthMask(true);
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
			tessellator.addVertexWithUV(-size - xOffset, -size, 0.0, minU + offset, minV + offset);
			tessellator.addVertexWithUV(-size - xOffset, size, 0.0, minU + offset, minV + 8/32.0F - offset);
			tessellator.addVertexWithUV(size - xOffset, size, 0.0, minU + 8/32.0F - offset, minV + 8/32.0F - offset);
			tessellator.addVertexWithUV(size - xOffset, -size, 0.0, minU + 8/32.0F - offset, minV + offset);
			tessellator.draw();
			GL11.glEnable(2896);
			GL11.glDisable(3042);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glPopMatrix();
			ci.cancel();
		}else if (BTAVisuals.animalLabels.value == 2){
			ci.cancel();
		}
	}

	@Unique
	public int getTexCode(Mob mob) {
		int mode = BTAVisuals.colorModeLabels.value - 1;
		if (mode > -1) return mode;

		String name = mob.nickname;
		return Math.abs(name.charAt(0) ^ name.charAt(name.length() / 2) ^ name.charAt(name.length() - 1)) % 16;
	}
}
