package bigsir.btavisuals.mixin;

import bigsir.btavisuals.BTAVisuals;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import net.minecraft.client.gui.container.ScreenContainerAbstract;
import net.minecraft.client.gui.container.ScreenInventory;
import net.minecraft.client.gui.container.ScreenInventoryCreative;
import net.minecraft.core.player.inventory.menu.MenuAbstract;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {ScreenInventory.class, ScreenInventoryCreative.class}, remap = false)
public abstract class ScreenInventoryMixin extends ScreenContainerAbstract {
	@Unique float yRotLast = Float.POSITIVE_INFINITY;
	@Unique float xRotLast;
	@Unique float yBodyRotLast;

	public ScreenInventoryMixin(MenuAbstract container) {
		super(container);
	}

	@ModifyConstant(method = "drawGuiContainerBackgroundLayer", constant = @Constant(floatValue = 1.0F, ordinal = 9))
	private float renderPlayerWithPartialTick(float constant, @Local(ordinal = 0) float partialTick) {
		return BTAVisuals.smoothInvModel.value ? partialTick : 1.0F;
	}

	@Inject(method = "drawGuiContainerBackgroundLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/EntityRenderDispatcher;renderEntityWithPosYaw(Lnet/minecraft/client/render/tessellator/Tessellator;Lnet/minecraft/core/entity/Entity;DDDFF)V"))
	public void loadLastBeforeRender(float f, CallbackInfo ci) {
		this.mc.thePlayer.yRotO = yRotLast;
		this.mc.thePlayer.xRotO = xRotLast;
		this.mc.thePlayer.yBodyRotO = yBodyRotLast;

		if (yRotLast == Float.POSITIVE_INFINITY) {
			this.mc.thePlayer.yRotO = this.mc.thePlayer.yRot;
			this.mc.thePlayer.xRotO = this.mc.thePlayer.xRot;
			this.mc.thePlayer.yBodyRotO = this.mc.thePlayer.yBodyRot;
		}
	}

	@Inject(method = "drawGuiContainerBackgroundLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/EntityRenderDispatcher;renderEntityWithPosYaw(Lnet/minecraft/client/render/tessellator/Tessellator;Lnet/minecraft/core/entity/Entity;DDDFF)V", shift = At.Shift.AFTER))
	public void saveLastAfterRender(float f, CallbackInfo ci) {
		yRotLast = this.mc.thePlayer.yRot;
		xRotLast = this.mc.thePlayer.xRot;
		yBodyRotLast = this.mc.thePlayer.yBodyRot;
	}

	@Inject(method = "drawGuiContainerBackgroundLayer", at = @At("HEAD"))
	public void saveOld(float f, CallbackInfo ci,
						@Share("yRotLast") LocalFloatRef yRotLast,
						@Share("xRotLast") LocalFloatRef xRotLast,
						@Share("yBodyRotLast") LocalFloatRef yBodyRotLast)
	{
		yRotLast.set(this.mc.thePlayer.yRotO);
		xRotLast.set(this.mc.thePlayer.xRotO);
		yBodyRotLast.set(this.mc.thePlayer.yBodyRotO);
	}

	@Inject(method = "drawGuiContainerBackgroundLayer", at = @At("TAIL"))
	public void loadOld(float f, CallbackInfo ci,
						@Share("yRotLast") LocalFloatRef yRotLast,
						@Share("xRotLast") LocalFloatRef xRotLast,
						@Share("yBodyRotLast") LocalFloatRef yBodyRotLast)
	{
		this.mc.thePlayer.yRotO = yRotLast.get();
		this.mc.thePlayer.xRotO = xRotLast.get();
		this.mc.thePlayer.yBodyRotO = yBodyRotLast.get();
	}
}
