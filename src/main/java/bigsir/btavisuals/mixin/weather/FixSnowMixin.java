package bigsir.btavisuals.mixin.weather;

import bigsir.btavisuals.BTAVisuals;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.world.weather.Weather;
import org.lwjgl.opengl.GL11;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = WorldRenderer.class, remap = false)
public abstract class FixSnowMixin {
	@Redirect(method = "renderRainSnow", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glColor4f(FFFF)V", ordinal = 0))
	public void fixColorSnow(float r, float g, float b, float a, @Local(name = "weather") Weather weather) {
		float opacity = Math.min(a, BTAVisuals.snowOpacity.value / 100.0F);
		Tessellator.instance.setColorRGBA_F(r, g, b, opacity);
	}

	@Inject(method = "renderRainSnow", at = @At(value = "JUMP", opcode = Opcodes.IF_ICMPNE, ordinal = 0, shift = At.Shift.AFTER))
	public void newMergedDraw1(float partialTick, CallbackInfo ci) {
		//System.out.println("huh1");
		Tessellator.instance.startDrawingQuads();
	}

	@Inject(method = "renderRainSnow", at = @At(value = "JUMP", opcode = Opcodes.GOTO, ordinal = 4, shift = At.Shift.BEFORE))
	public void newMergedDraw2(float partialTick, CallbackInfo ci) {
		//System.out.println("huh2");
		GL11.glColor4f(1, 1, 1, 1);
		Tessellator.instance.draw();
	}

	@Redirect(method = "renderRainSnow", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;draw()V", ordinal = 0))
	public void mergeDraw1(Tessellator instance) {
		//instance.draw();
	}

	@Redirect(method = "renderRainSnow", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;startDrawingQuads()V", ordinal = 0))
	public void mergeDraw2(Tessellator instance) {
		//instance.startDrawingQuads();
	}

	@Redirect(method = "renderRainSnow", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;setTranslation(DDD)V", ordinal = 0))
	public void noTranslation(Tessellator instance, double x, double y, double z) {
		instance.setTranslation(0, 0, 0);
	}

	@Redirect(method = "renderRainSnow", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 0))
	public void redirectAddVertex0(Tessellator instance, double x, double y, double z, double u, double v,
								   @Local(name = "nextPosX") double nextPosX,
								   @Local(name = "nextPosY") double nextPosY,
								   @Local(name = "nextPosZ") double nextPosZ)
	{
		instance.addVertexWithUV(x - nextPosX, y - nextPosY, z - nextPosZ, u, v);
	}

	@Redirect(method = "renderRainSnow", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 1))
	public void redirectAddVertex1(Tessellator instance, double x, double y, double z, double u, double v,
								   @Local(name = "nextPosX") double nextPosX,
								   @Local(name = "nextPosY") double nextPosY,
								   @Local(name = "nextPosZ") double nextPosZ)
	{
		instance.addVertexWithUV(x - nextPosX, y - nextPosY, z - nextPosZ, u, v);
	}

	@Redirect(method = "renderRainSnow", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 2))
	public void redirectAddVertex2(Tessellator instance, double x, double y, double z, double u, double v,
								   @Local(name = "nextPosX") double nextPosX,
								   @Local(name = "nextPosY") double nextPosY,
								   @Local(name = "nextPosZ") double nextPosZ)
	{
		instance.addVertexWithUV(x - nextPosX, y - nextPosY, z - nextPosZ, u, v);
	}

	@Redirect(method = "renderRainSnow", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 3))
	public void redirectAddVertex3(Tessellator instance, double x, double y, double z, double u, double v,
								   @Local(name = "nextPosX") double nextPosX,
								   @Local(name = "nextPosY") double nextPosY,
								   @Local(name = "nextPosZ") double nextPosZ)
	{
		instance.addVertexWithUV(x - nextPosX, y - nextPosY, z - nextPosZ, u, v);
	}

	@Redirect(method = "renderRainSnow", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 4))
	public void redirectAddVertex4(Tessellator instance, double x, double y, double z, double u, double v,
								   @Local(name = "nextPosX") double nextPosX,
								   @Local(name = "nextPosY") double nextPosY,
								   @Local(name = "nextPosZ") double nextPosZ)
	{
		instance.addVertexWithUV(x - nextPosX, y - nextPosY, z - nextPosZ, u, v);
	}

	@Redirect(method = "renderRainSnow", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 5))
	public void redirectAddVertex5(Tessellator instance, double x, double y, double z, double u, double v,
								   @Local(name = "nextPosX") double nextPosX,
								   @Local(name = "nextPosY") double nextPosY,
								   @Local(name = "nextPosZ") double nextPosZ)
	{
		instance.addVertexWithUV(x - nextPosX, y - nextPosY, z - nextPosZ, u, v);
	}

	@Redirect(method = "renderRainSnow", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 6))
	public void redirectAddVertex6(Tessellator instance, double x, double y, double z, double u, double v,
								   @Local(name = "nextPosX") double nextPosX,
								   @Local(name = "nextPosY") double nextPosY,
								   @Local(name = "nextPosZ") double nextPosZ)
	{
		instance.addVertexWithUV(x - nextPosX, y - nextPosY, z - nextPosZ, u, v);
	}

	@Redirect(method = "renderRainSnow", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;addVertexWithUV(DDDDD)V", ordinal = 7))
	public void redirectAddVertex7(Tessellator instance, double x, double y, double z, double u, double v,
								   @Local(name = "nextPosX") double nextPosX,
								   @Local(name = "nextPosY") double nextPosY,
								   @Local(name = "nextPosZ") double nextPosZ)
	{
		instance.addVertexWithUV(x - nextPosX, y - nextPosY, z - nextPosZ, u, v);
	}
}
