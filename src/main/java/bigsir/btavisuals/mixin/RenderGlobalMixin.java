package bigsir.btavisuals.mixin;

import bigsir.btavisuals.BTAVisuals;
import net.minecraft.client.Minecraft;
import net.minecraft.client.option.OptionRange;
import net.minecraft.client.render.Lighting;
import net.minecraft.client.render.RenderGlobal;
import net.minecraft.client.render.TextureManager;
import net.minecraft.client.render.camera.ICamera;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.worldtype.WorldTypeFX;
import net.minecraft.client.render.worldtype.WorldTypeFXDispatcher;
import net.minecraft.client.world.WorldClient;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.util.phys.Vec3;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RenderGlobal.class, remap = false)
public abstract class RenderGlobalMixin {
	@Shadow private WorldClient worldObj;

	@Shadow @Final private TextureManager textureManager;

	@Shadow @Final private Minecraft mc;

	@Inject(method = "drawOutlinedBoundingBox", at = @At("HEAD"), cancellable = true)
	public void fixRender(AABB aabb, CallbackInfo ci) {
	}

	@Inject(method = "drawSelectionBox", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glLineWidth(F)V"))
	public void invertOutline(ICamera camera, HitResult hitResult, float partialTick, CallbackInfo ci) {
		/*GL11.glEnable(GL11.GL_BLEND); //FIXME
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4f(1, 1, 1, 0.5F);
		GL11.glBlendFunc(775, 769);*/
	}

	@Inject(method = "drawSelectionBox", at = @At(value = "TAIL"))
	public void invertOutline2(ICamera camera, HitResult hitResult, float partialTick, CallbackInfo ci) {
		//GL11.glEnable(GL11.GL_DEPTH_TEST); //FIXME
		//GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_FILL);
	}

	@Redirect(method = "drawSky", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glColor4f(FFFF)V", ordinal = 0))
	public void kill(float r, float g, float b, float v) {
		if (BTAVisuals.seasonalSunPath.value) GL11.glColor4f(r, g, b, 0); //FIXME
	}

	@Inject(method = "drawSky", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glTranslatef(FFF)V", ordinal = 0))
	public void drawStar(float partialTick, CallbackInfo ci) {
		if (!BTAVisuals.seasonalSunPath.value) return;
		//FIXME

		Tessellator t = Tessellator.instance;

		float size = 30;
		float date = this.worldObj.getWorldTime() / 280_000.0F * 4.0F;
		float day = (this.worldObj.getWorldTime() % 24000.0F) / 24000.0F;

		float angle = this.worldObj.getCelestialAngle(partialTick);

		float dayLength = ((WorldTypeAccessor)this.worldObj.worldType).invokeGetDayLengthTicks(worldObj) / 14400.0F; //Longest day in 14400 ticks

		GL11.glPushMatrix();
		GL11.glRotatef(90, 0, 0, 1);
		GL11.glRotatef(90, 1, 0, 0);
		GL11.glRotatef((float) (90.0F * Math.sin(angle * Math.PI * 2)), 1.0F, 0, 0);
		GL11.glRotatef((float) (dayLength * 60.0F * Math.cos(angle * Math.PI *2)), 0.0F, 0.0F, 1.0F);
		//GL11.glRotatef((float) (-Math.cos(4*Math.PI*day) * 180.0F + 270.0F), 0, 0, 1);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		this.textureManager.loadTexture("/assets/minecraft/textures/terrain/sun.png").bind();

		t.startDrawingQuads();
		t.addVertexWithUV((double)(-size), -100.0, (double)size, 0.0, 1.0);
		t.addVertexWithUV((double)size, -100.0, (double)size, 1.0, 1.0);
		t.addVertexWithUV((double)size, -100.0, (double)(-size), 1.0, 0.0);
		t.addVertexWithUV((double)(-size), -100.0, (double)(-size), 0.0, 0.0);
		t.draw();

		GL11.glPopMatrix();
	}

	@Redirect(method = "renderClouds", at = @At(value = "FIELD", target = "Lnet/minecraft/client/option/OptionRange;value:Ljava/lang/Object;"))
	public Object redirectOption(OptionRange instance) {
		return BTAVisuals.cloudMode.value;
	}

	@ModifyConstant(method = "renderClouds", constant = @Constant(intValue = 32))
	private int val(int constant){
		return 32 * (BTAVisuals.cloudDistance.value + 1);
	}

	@ModifyConstant(method = "renderClouds", constant = @Constant(intValue = 256))
	private int val2(int constant){
		return 256 * (BTAVisuals.cloudDistance.value + 1);
	}

	@ModifyConstant(method = "renderCloudsFancy", constant = @Constant(intValue = 3, ordinal = 1))
	private int fancyRad(int constant){
		return 3 * MathHelper.ceil((BTAVisuals.cloudDistance.value + 1) / 2f);
	}

	@Inject(method = "renderClouds", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tessellator/Tessellator;setColorRGBA_F(FFFF)V", shift = At.Shift.AFTER))
	private void fix(float partialTick, CallbackInfo ci){
		if(BTAVisuals.tintedClouds.value) {
			WorldTypeFX worldTypeFX = WorldTypeFXDispatcher.getInstance().getDispatch(this.mc.currentWorld.getWorldType());
			float celestialAngle = this.worldObj.getCelestialAngle(partialTick);
			float[] sunriseColor = worldTypeFX.getSunriseColor(celestialAngle, partialTick);
			if (sunriseColor != null) {
				float rSun = sunriseColor[0];
				float gSun = sunriseColor[1];
				float bSun = sunriseColor[2];
				float aSun = sunriseColor[3] / 2.0F;
				Vec3 dimensionColor = this.worldObj.getDimensionColor(this.mc.activeCamera, partialTick);
				float rDim = (float) dimensionColor.x;
				float gDim = (float) dimensionColor.y;
				float bDim = (float) dimensionColor.z;
				float red = rDim + (rSun - rDim) * aSun;
				float green = gDim + (gSun - gDim) * aSun;
				float blue = bDim + (bSun - bDim) * aSun;
				Tessellator.instance.setColorRGBA_F(red, green, blue, 0.8F);
			}
		}
	}
}
