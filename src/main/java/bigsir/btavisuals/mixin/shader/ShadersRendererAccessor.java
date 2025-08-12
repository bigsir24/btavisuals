package bigsir.btavisuals.mixin.shader;


import net.minecraft.client.render.shader.ShadersRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.nio.FloatBuffer;

@Mixin(value = ShadersRenderer.class, remap = false)
public interface ShadersRendererAccessor {
	@Accessor
	FloatBuffer getMatrixBuffer();
}
