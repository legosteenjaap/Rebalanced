package nl.tettelaar.rebalanced.mixin.xpbar;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.util.Mth;
import nl.tettelaar.rebalanced.api.ClientAPI;
import nl.tettelaar.rebalanced.render.interfaces.FontInterface;
import org.lwjgl.system.MathUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;

import static org.lwjgl.opengl.GL11.GL_ADD;
import static org.lwjgl.opengl.GL14.GL_MAX;
import static org.lwjgl.opengl.GL14C.GL_FUNC_ADD;

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

    @Redirect(method = "renderExperienceBar(Lcom/mojang/blaze3d/vertex/PoseStack;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V", ordinal = 0))
    private void injected(Gui gui, PoseStack poseStack, int x, int y, int textureX, int textureY, int width, int height) {
        if (ClientAPI.getXPCost() > 0) {
            if (alpha <= -0.5) {
                isFading = false;
            } else if (alpha >= 1.5) {
                isFading = true;
            }
            float transparencyDif = (0.05f * this.minecraft.getDeltaFrameTime());
            if (isFading) transparencyDif = transparencyDif * -1;
            alpha = alpha + transparencyDif;
        } else {
            alpha= 0.0f;
        }
        this.blit(poseStack, x, y, textureX, textureY, width, height);
        ClientAPI.updateAltXP(ClientAPI.getXPCost(), ClientAPI.isLevel());
        int textureWidthAlt = (int)(ClientAPI.getAltXPProgress() * 183.0f);
        int textureWidth = (int)(this.minecraft.player.experienceProgress * 183.0f);
        int textureHeight = this.screenHeight - 32 + 3;
        if (textureWidthAlt > 0) {
            RenderSystem.enableBlend();
            this.blit(poseStack, x, textureHeight, 0, 69, Math.min(textureWidth, textureWidthAlt), 5);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, Mth.clamp(alpha, 0.0f, 1.0f));
            this.blit(poseStack, x, textureHeight, 0, 69, Math.max(textureWidth, textureWidthAlt), 5);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.disableBlend();
        } else if (textureWidth > 0) {
            this.blit(poseStack, x, textureHeight, 0, 69, textureWidth, 5);
        }
    }
    @Redirect(method = "renderExperienceBar(Lcom/mojang/blaze3d/vertex/PoseStack;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V", ordinal = 1))
    private void removeXPProgressBlit(Gui gui, PoseStack poseStack, int x, int y, int textureX, int textureY, int width, int height) {
    }

    @Redirect(method = "renderExperienceBar(Lcom/mojang/blaze3d/vertex/PoseStack;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;draw(Lcom/mojang/blaze3d/vertex/PoseStack;Ljava/lang/String;FFI)I", ordinal = 0))
    private int replaceXPNumDraw(Font font, PoseStack poseStack, String string, float a, float b, int color) throws Exception {
        if (ClientAPI.getAltXPLevel() >= 0 && ClientAPI.getXPCost() > 0) {
            String XP = "" + minecraft.player.experienceLevel;
            String altXP;
            if (ClientAPI.getAltXPLevel() > 0) altXP = "" + ClientAPI.getAltXPLevel();
            else {
                altXP = "";
            }
            int l = (this.screenWidth - this.getFont().width(XP)) / 2;
            int m = this.screenHeight - 31 - 4;
            ((FontInterface)(Object)this.getFont()).enableTransparency();
            int colorBack = setAlpha(0, 1.0f - alpha);
            int colorFront = setAlpha(8453920, 1.0f - alpha);
            int colorBackAlt = setAlpha(0, alpha);
            int colorFrontAlt = setAlpha(8453920, alpha);
            RenderSystem.enableBlend();
            this.getFont().draw(poseStack, XP, (float) (l + 1), (float) m, colorBack);
            this.getFont().draw(poseStack, XP, (float) (l - 1), (float) m, colorBack);
            this.getFont().draw(poseStack, XP, (float) l, (float) (m + 1), colorBack);
            this.getFont().draw(poseStack, XP, (float) l, (float) (m - 1), colorBack);
            this.getFont().draw(poseStack, XP, (float) l, (float) m, colorFront);
            l = (this.screenWidth - this.getFont().width(altXP)) / 2;
            this.getFont().draw(poseStack, altXP, (float) (l + 1), (float) m, colorBackAlt);
            this.getFont().draw(poseStack, altXP, (float) (l - 1), (float) m, colorBackAlt);
            this.getFont().draw(poseStack, altXP, (float) l, (float) (m + 1), colorBackAlt);
            this.getFont().draw(poseStack, altXP, (float) l, (float) (m - 1), colorBackAlt);
            this.getFont().draw(poseStack, altXP, (float) l, (float) m, colorFrontAlt);
            ((FontInterface)(Object)this.getFont()).disableTransparency();
            RenderSystem.disableBlend();
        } else if (minecraft.player.experienceLevel > 0) {
            String k = "" + minecraft.player.experienceLevel;
            int l = (this.screenWidth - this.getFont().width(k)) / 2;
            int m = this.screenHeight - 31 - 4;
            this.getFont().draw(poseStack, k, (float) (l + 1), (float) m, 0);
            this.getFont().draw(poseStack, k, (float) (l - 1), (float) m, 0);
            this.getFont().draw(poseStack, k, (float) l, (float) (m + 1), 0);
            this.getFont().draw(poseStack, k, (float) l, (float) (m - 1), 0);
            this.getFont().draw(poseStack, k, (float) l, (float) m, 8453920);
        }
        return 0;
    }

    @Redirect(method = "renderExperienceBar(Lcom/mojang/blaze3d/vertex/PoseStack;I)V", slice = @Slice(from = @At(ordinal = 0, value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;draw(Lcom/mojang/blaze3d/vertex/PoseStack;Ljava/lang/String;FFI)I")), at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;draw(Lcom/mojang/blaze3d/vertex/PoseStack;Ljava/lang/String;FFI)I"))
    private int removeXPNumDraw(Font font, PoseStack poseStack, String string, float a, float b, int color) {
        return 0;
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
