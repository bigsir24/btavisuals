package bigsir.btavisuals.mixin;

import bigsir.btavisuals.BTAVisuals;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Player.class, remap = false)
public abstract class PlayerMixin extends Mob {
	public PlayerMixin(@Nullable World world) {
		super(world);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	public void changeTexId(World world, CallbackInfo ci) {
		this.textureIdentifier = NamespaceID.getPermanent(BTAVisuals.MOD_ID, "char");
	}
}
