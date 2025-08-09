package bigsir.btavisuals.mixin;

import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.item.model.ItemModelBlock;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.core.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ItemModelBlock.class, remap = false)
public abstract class ItemModelBlockMixin extends ItemModelStandard {
	public ItemModelBlockMixin(Item item, String namespace) {
		super(item, namespace);
	}

	@Redirect(method = "renderAsItemEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/LightmapHelper;setLightmapCoord(I)V"))
	public void bright(int lightmapCoord){
		LightmapHelper.setLightmapCoord(this.itemfullBright ? LightmapHelper.getLightmapCoord(15,15) : lightmapCoord);
	}
}
