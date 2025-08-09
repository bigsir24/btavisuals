package bigsir.btavisuals.mixin;

import bigsir.btavisuals.BTAVisuals;
import net.minecraft.client.Minecraft;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.Option;
import net.minecraft.client.option.OptionRange;
import net.minecraft.core.lang.I18n;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = GameSettings.class, remap = false)
public abstract class GameSettingsMixin {

	@Shadow
	@Final
	public Minecraft mc;

	@Inject(method = "optionChanged", at = @At("HEAD"))
	public void reloadRenderers(Option<?> option, CallbackInfo ci) {
		if (option == BTAVisuals.smoothWater || option == BTAVisuals.leafPileGraphics) {
			mc.renderGlobal.loadRenderers();
		}
	}

	@Inject(method = "getDisplayString", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/lang/I18n;getInstance()Lnet/minecraft/core/lang/I18n;"), cancellable = true)
	public void optionString(Option<?> option, CallbackInfoReturnable<String> cir) {
		if (option == BTAVisuals.leavesMode) {
			cir.setReturnValue(BTAVisuals.translateRange(BTAVisuals.leavesModeString, BTAVisuals.leavesMode));
		} else if (option == BTAVisuals.cloudMode || option == BTAVisuals.grassMode) {
			cir.setReturnValue(I18n.getInstance().translateKey(((OptionRange) option).value == 1 ? "options.btavisuals.fancy" : "options.btavisuals.fast"));
		} else if (option == BTAVisuals.rainOpacity || option == BTAVisuals.snowOpacity || option == BTAVisuals.rainParticleAmount || option == BTAVisuals.animationTicks) {
			cir.setReturnValue(option.value + "%");
		} else if (option == BTAVisuals.snowType) {
			cir.setReturnValue(BTAVisuals.translateRange(BTAVisuals.snowTypeString, BTAVisuals.snowType));
		} else if (option == BTAVisuals.colorMode || option == BTAVisuals.colorModeLabels) {
			String translated = I18n.getInstance().translateKey("options.btavisuals.default");
			OptionRange opt = (OptionRange) option;
			cir.setReturnValue(opt.value == 0 ? translated : String.valueOf(opt.value - 1));
		} else if (option == BTAVisuals.animalLabels || option == BTAVisuals.hideNameplates) {
			cir.setReturnValue(BTAVisuals.translateRange(BTAVisuals.animalLabelsString, (OptionRange) option));
		} else if (option == BTAVisuals.sideLightDirection) {
			cir.setReturnValue(BTAVisuals.translateRange(BTAVisuals.sideLightString, (OptionRange) option));
		} else if (option == BTAVisuals.fireOverlay) {
			cir.setReturnValue(BTAVisuals.translateRange(BTAVisuals.fireOverlayString, (OptionRange) option));
		} else if (option == BTAVisuals.selectorType) {
			cir.setReturnValue(BTAVisuals.translateRange(BTAVisuals.selectorTypeString, (OptionRange) option));
		} else if (option == BTAVisuals.fireOverlayOffset) {
			cir.setReturnValue(String.valueOf(((OptionRange)option).value / 100.0F));
		}
	}
}
