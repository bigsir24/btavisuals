package bigsir.btavisuals.mixin;

import bigsir.btavisuals.BTAVisuals;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IntegerSliderElement;
import net.minecraft.client.gui.options.components.ToggleableOptionComponent;
import net.minecraft.client.option.OptionRange;
import net.minecraft.client.option.OptionToggleable;
import net.minecraft.client.render.block.model.BlockModel;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.BlockModelLeaves;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ToggleableOptionComponent.class, remap = false)
public abstract class ToggleableOptionComponentMixin<E> {
	@Shadow
	@Final
	protected OptionToggleable<E> option;

	@Shadow
	protected abstract void onChanged();

	@Shadow
	@Final
	protected IntegerSliderElement slider;

	@Inject(method = "init", at = @At("TAIL"))
	public void fixSlidersInit(Minecraft mc, CallbackInfo ci) {
		if (this.option instanceof OptionRange) this.slider.sliderValue = (int) this.option.value;
	}

	@Inject(method = "resetValue", at = @At("TAIL"))
	public void fixSlidersReset(CallbackInfo ci) {
		if (this.option instanceof OptionRange) this.slider.sliderValue = (int) this.option.value;
	}

	@Inject(method = "buttonReleased", at = @At("HEAD"))
	public void test(CallbackInfo ci) {
		if(option == BTAVisuals.sideLightDirection ||
			option == BTAVisuals.grassMode) {
			Minecraft.getMinecraft().renderGlobal.loadRenderers();
		}else if (option == BTAVisuals.leavesMode) {
			for (Block<?> block : Blocks.blocksList) {
				BlockModel<?> model = BlockModelDispatcher.getInstance().getDispatch(block);

				if (model instanceof BlockModelLeaves) {
					((BlockModelLeaves<?>) model).onRenderLayer(BTAVisuals.leavesMode.value == 3 ? 1 : 0);
				}
			}
			Minecraft.getMinecraft().renderGlobal.loadRenderers();
		}
	}

	@Inject(method = "renderButton", at = @At("TAIL"))
	public void test2(int x, int y, int relativeButtonX, int relativeButtonY, int buttonWidth, int buttonHeight, int relativeMouseX, int relativeMouseY, CallbackInfo ci) {
		if (BTAVisuals.modPage != ((ScreenOptionsAccessor)Minecraft.getMinecraft().currentScreen).getSelectedPage()) return;

		int mouseX = x + relativeMouseX;
		int mouseY = y + relativeMouseY;
		int posX = x + relativeButtonX;
		int posY = y + relativeButtonY;
		boolean mouseOver = mouseX >= posX && mouseY >= posY && mouseX < posX + slider.width && mouseY < posY + slider.height;
		if (mouseX > -1 && mouseY > -1 && mouseOver && (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))) {
			while (Mouse.next()) {
				int scroll = Mouse.getDWheel();
				if (this.option instanceof OptionRange) {
					OptionRange optionRange = ((OptionRange) this.option);

					int prevValue = optionRange.value;
					optionRange.value += scroll;
					optionRange.value = BTAVisuals.clamp(optionRange.value, optionRange.lowest, optionRange.highest);
					this.onChanged();
					this.option.onUpdate();
					slider.sliderValue = (int) option.value;

					//FIXME very awful but I'm lazy
					if (prevValue != optionRange.value && (option == BTAVisuals.sideLightDirection || option == BTAVisuals.smoothWater ||
						option == BTAVisuals.grassMode || option == BTAVisuals.leavesMode)) {
						Minecraft.getMinecraft().renderGlobal.loadRenderers();
					}
				}
			}
		}
	}
}
