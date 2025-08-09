package bigsir.btavisuals.mixin;

import bigsir.btavisuals.BTAVisuals;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Font;
import net.minecraft.client.render.RenderBlocks;
import net.minecraft.client.render.TextureManager;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.entity.MobRendererPlayer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MobRendererPlayer.class, remap = false)
public abstract class MobRendererPlayerMixin extends MobRenderer<Player> {
	@Shadow
	public abstract void loadEntityTexture(Player entity);

	@Shadow
	private RenderBlocks containerRenderBlock;

	public MobRendererPlayerMixin(ModelBase model, float shadowSize) {
		super(model, shadowSize);
	}

	@Inject(method = "renderSpecials(Lnet/minecraft/client/render/tessellator/Tessellator;Lnet/minecraft/core/entity/player/Player;DDD)V", at = @At(value = "RETURN", ordinal = 1))
	public void renderOwnNameTag(Tessellator tessellator, Player entity, double d, double d1, double d2, CallbackInfo ci) {
		if (!BTAVisuals.showOwnNameplate.value) return;

		float distanceFromCamera = (float)this.renderDispatcher.camera.distanceTo(entity);
		float maxDistance = entity.isSneaking() ? 32.0F : 64.0F;

		if (distanceFromCamera < maxDistance && !entity.isSneaking()) {
			String s = entity.getDisplayName();
			if (entity.isPlayerSleeping()) {
				if (BTAVisuals.hideNameplates.value == 1) {
					renderTag(tessellator, entity, d, d1, d2, false);
				}else if (BTAVisuals.hideNameplates.value == 0){
					this.renderLivingLabel(tessellator, entity, s, d, d1 - 1.5, d2, 64, false);
				}
			} else {
				if (BTAVisuals.hideNameplates.value == 1) {
					renderTag(tessellator, entity, d, d1 + entity.getHeightOffset(), d2, false);
				}else if (BTAVisuals.hideNameplates.value == 0){
					this.renderLivingLabel(tessellator, entity, s, d, d1 + entity.getHeightOffset(), d2, 64, false);
				}
			}
		}else {
			if (BTAVisuals.hideNameplates.value == 1) {
				renderTag(tessellator, entity, d, d1 + entity.getHeightOffset(), d2, true);
			}else if (BTAVisuals.hideNameplates.value == 0){
				float f = 1.6F;
				float f1 = 0.01666667F * f;
				String s = entity.getDisplayName();
				Font font = this.getFont();
				GL11.glPushMatrix();
				GL11.glTranslatef((float) d + 0.0F, (float) d1 + 2.3F, (float) d2);
				GL11.glNormal3f(0.0F, 1.0F, 0.0F);
				GL11.glRotatef(-this.renderDispatcher.viewLerpYaw, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(this.renderDispatcher.viewLerpPitch, 1.0F, 0.0F, 0.0F);
				GL11.glScalef(-f1, -f1, f1);
				GL11.glDisable(2896);
				GL11.glTranslatef(0.0F, 0.25F / f1, 0.0F);
				GL11.glDepthMask(false);
				GL11.glEnable(3042);
				GL11.glBlendFunc(770, 771);
				GL11.glDisable(3553);
				tessellator.startDrawingQuads();
				int i = font.getStringWidth(s) / 2;
				tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
				tessellator.addVertex(-i - 1, -1.0, 0.0);
				tessellator.addVertex(-i - 1, 8.0, 0.0);
				tessellator.addVertex(i + 1, 8.0, 0.0);
				tessellator.addVertex(i + 1, -1.0, 0.0);
				tessellator.draw();
				GL11.glEnable(3553);
				GL11.glDepthMask(true);
				font.drawString(s, -font.getStringWidth(s) / 2, 0, 553648127);
				GL11.glEnable(2896);
				GL11.glDisable(3042);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glPopMatrix();
			}
		}
	}

	@Redirect(method = "renderSpecials(Lnet/minecraft/client/render/tessellator/Tessellator;Lnet/minecraft/core/entity/player/Player;DDD)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/MobRendererPlayer;renderLivingLabel(Lnet/minecraft/client/render/tessellator/Tessellator;Lnet/minecraft/core/entity/Mob;Ljava/lang/String;DDDIZ)V"))
	public void renderTagIcon(MobRendererPlayer instance, Tessellator tessellator, Mob mob, String string, double x, double y, double z, int distance, boolean depthMask) {
		if (BTAVisuals.hideNameplates.value == 1) {
			renderTag(tessellator, (Player) mob, x, y, z, false);
		}else if (BTAVisuals.hideNameplates.value == 0){
			this.renderLivingLabel(tessellator, (Player) mob, string, x, y,z, distance, depthMask);
		}
	}

	@Inject(method = "renderSpecials(Lnet/minecraft/client/render/tessellator/Tessellator;Lnet/minecraft/core/entity/player/Player;DDD)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/MobRendererPlayer;getFont()Lnet/minecraft/client/render/Font;"), cancellable = true)
	public void renderTagIconSneaking(Tessellator tessellator, Player entity, double x, double y, double z, CallbackInfo ci) {
		if (BTAVisuals.hideNameplates.value == 1) {
			renderTag(tessellator, entity, x, y, z, true);
			ci.cancel();
		}else if(BTAVisuals.hideNameplates.value == 2) {
			ci.cancel();
		}
	}

	@Inject(method = "drawHeldObject", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/tessellator/Tessellator;instance:Lnet/minecraft/client/render/tessellator/Tessellator;", ordinal = 0))
	public void fixShadingHead(Player player, float partialTick, CallbackInfo ci, @Share("prevAO") LocalBooleanRef prevAO) {
		if (BTAVisuals.fixHeldBlockShading.value) {
			prevAO.set(Minecraft.getMinecraft().gameSettings.ambientOcclusion.value);
			Minecraft.getMinecraft().gameSettings.ambientOcclusion.set(false);
		}
	}

	@Inject(method = "drawHeldObject", at = @At("TAIL"))
	public void fixShadingTail(Player player, float partialTick, CallbackInfo ci, @Share("prevAO") LocalBooleanRef prevAO) {
		if (BTAVisuals.fixHeldBlockShading.value) Minecraft.getMinecraft().gameSettings.ambientOcclusion.set(prevAO.get());
	}

	@Unique
	public void renderTag(Tessellator tessellator, Player entity, double x, double y, double z, boolean sneaking) {
		GL11.glPushMatrix();
		float sneakOffset = sneaking ? -0.35F : 0;
		GL11.glTranslatef((float)x, (float)y + entity.getHeadHeight() + 0.8F - 3/32.0F + sneakOffset, (float)z);
		GL11.glNormal3f(0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-this.renderDispatcher.viewLerpYaw, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(this.renderDispatcher.viewLerpPitch, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(-0.026666671F, -0.026666671F, 0.026666671F);
		GL11.glDisable(2896);
		GL11.glDepthMask(false);
		GL11.glDisable(2929);

		TextureManager manager = renderDispatcher.textureManager;
		manager.bindTexture(manager.loadTexture("/assets/btavisuals/textures/icons/mapicons.png"));

		float offset = 0.001F;
		int size = 6;
		float xOffset = size / 8.0F;
		int code = getTexCode(entity);
		float minU = (code % 4) * 0.25F;
		float minV = (code / 4) * 0.25F;

		GL11.glEnable(3553);
		GL11.glEnable(GL11.GL_BLEND);
		if (sneaking) {
			GL11.glEnable(2929);
			GL11.glDepthMask(true);
		}
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, 0.25F);
		tessellator.addVertexWithUV(-size - xOffset, -size, 0.0, minU + offset, minV + 8 / 32.0F - offset);
		tessellator.addVertexWithUV(-size - xOffset, size, 0.0, minU +  offset, minV + offset);
		tessellator.addVertexWithUV(size - xOffset, size, 0.0, minU + 8 / 32.0F - offset, minV + offset);
		tessellator.addVertexWithUV(size - xOffset, -size, 0.0, minU + 8 / 32.0F - offset, minV + 8 / 32.0F - offset);
		tessellator.draw();
		GL11.glEnable(2929);

		GL11.glDepthMask(true);
		if (!sneaking) {
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
			tessellator.addVertexWithUV(-size - xOffset, -size, 0.0, minU + offset, minV + 8 / 32.0F - offset);
			tessellator.addVertexWithUV(-size - xOffset, size, 0.0, minU + offset, minV + offset);
			tessellator.addVertexWithUV(size - xOffset, size, 0.0, minU + 8 / 32.0F - offset, minV + offset);
			tessellator.addVertexWithUV(size - xOffset, -size, 0.0, minU + 8 / 32.0F - offset, minV + 8 / 32.0F - offset);
			tessellator.draw();
		}
		GL11.glEnable(2896);
		GL11.glDisable(3042);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}

	@Unique
	public int getTexCode(Player player) {
		int mode = BTAVisuals.colorMode.value - 1;
		if (mode > -1) return mode;

		String name = player.username;
        return Math.abs(name.charAt(0) ^ name.charAt(name.length() / 2) ^ name.charAt(name.length() - 1)) % 16;
	}

	@Inject(method = "loadEntityTexture(Lnet/minecraft/core/entity/player/Player;)V", at = @At("HEAD"), cancellable = true)
	public void loadRandomDefaultTex(Player entity, CallbackInfo ci) {
		if (BTAVisuals.allSteve.value) {
			TextureManager manager = this.renderDispatcher.textureManager;
			manager.bindTexture(manager.loadTexture("/assets/btavisuals/textures/entity/char/" + getTexCode(entity) + ".png"));
			ci.cancel();
		}
	}

	@Inject(method = "drawFirstPersonHand", at = @At("HEAD"))
	public void changeHandTex(Player player, boolean isLeft, CallbackInfo ci) {
		if (BTAVisuals.allSteve.value && this.renderDispatcher.textureManager != null) this.loadEntityTexture(player);
	}
}
