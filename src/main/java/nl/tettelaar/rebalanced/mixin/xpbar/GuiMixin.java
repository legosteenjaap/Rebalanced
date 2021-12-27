package nl.tettelaar.rebalanced.mixin.xpbar;

import com.google.common.primitives.Ints;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import nl.tettelaar.rebalanced.render.interfaces.FontInterface;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin extends GuiComponent{

    float alpha = 1.0f;

    boolean isFading = true;

    @Shadow @Final
    private Minecraft minecraft;

    @Shadow
    private int screenHeight;

    @Shadow
    private int screenWidth;

    @Shadow
    public Font getFont() {
        throw new AssertionError();
    }

    PoseStack poseStackCapt;

    @Inject(method = "renderExperienceBar", at = @At("HEAD"), cancellable = true)
    public void renderExperienceBarCapturePosestack(PoseStack poseStack, int i, CallbackInfo ci) {
        poseStackCapt = poseStack;
    }

    @Inject(method = "renderExperienceBar", at = @At("RETURN"), cancellable = true)
    public void renderExperienceBar(PoseStack poseStack, int i, CallbackInfo ci) {
        this.setBlitOffset(0);
        if (alpha <= -1) {
            isFading = false;
        } else if (alpha >= 2) {
            isFading = true;
        }
        float transparencyDif = (0.01f * this.minecraft.getDeltaFrameTime());
        if (isFading) transparencyDif = transparencyDif * -1;
        alpha = alpha + transparencyDif;
        System.out.println(alpha);
        int m;
        int l;
        this.minecraft.getProfiler().push("expBar");
        RenderSystem.setShaderTexture(0, GuiComponent.GUI_ICONS_LOCATION);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        int j = this.minecraft.player.getXpNeededForNextLevel();
        if (j > 0) {
            int k = 182;
            l = (int)((this.minecraft.player.experienceProgress) * 183.0f);
            m = this.screenHeight - 32 + 3;
            this.blit(poseStack, i, m, 0, 64, 182, 5);
            if (l > 0) {
                this.blit(poseStack, i, m, 0, 69, l, 5);
            }
        }
        RenderSystem.disableBlend();
        this.minecraft.getProfiler().pop();
        if (this.minecraft.player.experienceLevel > 0) {
            this.minecraft.getProfiler().push("expLevel");
            String k = "" + (this.minecraft.player.experienceLevel);
            l = (this.screenWidth - this.getFont().width(k)) / 2;
            m = this.screenHeight - 31 - 4;
            ((FontInterface)(Object)this.getFont()).enableTransparency();
            int color = setAlpha(0, alpha);
            this.getFont().draw(poseStack, k, (float)(l + 1), (float)m, color);
            this.getFont().draw(poseStack, k, (float)(l - 1), (float)m, color);
            this.getFont().draw(poseStack, k, (float)l, (float)(m + 1), color);
            this.getFont().draw(poseStack, k, (float)l, (float)(m - 1), color);
            this.getFont().draw(poseStack, k, (float)l, (float)m, setAlpha(8453920, alpha));
            ((FontInterface)(Object)this.getFont()).disableTransparency();
            this.minecraft.getProfiler().pop();
        }
    }

    @ModifyConstant(method = "renderExperienceBar", constant = {@Constant(intValue = 0), @Constant(intValue = 8453920)})
    private int changeAlpha (int color) {
        color = setAlpha(color, 2.0f - alpha);
        return color;
    }

    public int setAlphaWithMax (int color, float alpha) {
        float maxAlpha = getAlpha(color);
        System.out.println("alpha: " + alpha);
        System.out.println("maxAlpha: " + maxAlpha);
        if (alpha > maxAlpha) return color;
        return setAlpha(color, alpha);
    }

    public int setAlphaWithMax (int color, float alpha, float maxAlpha) {
        System.out.println("alpha: " + alpha);
        System.out.println("maxAlpha: " + maxAlpha);
        if (alpha > maxAlpha) return setAlpha(color, maxAlpha);
        return setAlpha(color, alpha);
    }

    public int setAlpha (int color, float alpha) {
        if (alpha < 0) {
            alpha = 0;
        } else if (alpha > 1){
            alpha = 1;
        }
        int alphaInt = (int)(255 * alpha);
        color = (color &  0x00FFFFFF) | (alphaInt <<24);
        return color;
    }

    public float getAlpha (int color) {
        return (float)(color >> 24 & 0xFF) / 255.0f;
    }
}
