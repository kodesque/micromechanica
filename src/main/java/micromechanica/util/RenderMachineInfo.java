package micromechanica.util;

import micromechanica.common.items.tools.ItemMagnifier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.GuiScreenEvent;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class RenderMachineInfo {

    public static void drawBar(StorageTypes type, int amount, int maxAmount, Color color) {

        int xPosition = 0;
        int yPosition = 0;
        //what the hell are those values?
        //maybe the color will change as it approaches zero (made darker?)

        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        int rgbfordisplay = color.getRGB();
        int i = Math.round(13.0F - (float)amount * 13.0F);
        int j = rgbfordisplay;
        draw(bufferbuilder, xPosition + 2, yPosition + 13, 13, 2, 0, 0, 0, 255);
        draw(bufferbuilder, xPosition + 2, yPosition + 13, i, 1, j >> 16 & 255, j >> 8 & 255, j & 255, 255);
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();


    }

    private static void draw(BufferBuilder renderer, int x, int y, int width, int height, int red, int green, int blue, int alpha)
    {
        renderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        renderer.pos((double)(x + 0), (double)(y + 0), 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos((double)(x + 0), (double)(y + height), 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos((double)(x + width), (double)(y + height), 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos((double)(x + width), (double)(y + 0), 0.0D).color(red, green, blue, alpha).endVertex();
        Tessellator.getInstance().draw();
    }

    public static int getRGBDurabilityForDisplay(int amount, int maxAmount)
    {
        return MathHelper.hsvToRGB(Math.max(0.0F, (float) (1.0F - (double)amount / (double)maxAmount)) / 3.0F, 1.0F, 1.0F);
    }

    public static void drawGrabbingProgressBar(String progressTitle) {

    }

    public static void drawItemStorageTooltip() {

    }

    //bar mechanic should be redone
    //for now this remains here (used by Magnifying Glass)

    @Deprecated
    public static void drawRevealBar(GuiScreenEvent.DrawScreenEvent.Post event) {

        int mouseX = Mouse.getX() * event.getGui().width
                / Minecraft.getMinecraft().displayWidth - ((Minecraft.getMinecraft().displayWidth * 3) / 100);

        int mouseY = (event.getGui().height
                - Mouse.getY() * event.getGui().height
                / Minecraft.getMinecraft().displayHeight - 1) - ((Minecraft.getMinecraft().displayHeight * 3) / 100) ;

        int current = 0;

        if (ItemMagnifier.hoverTimer <= 30) {
            current = 0;
        } else if (ItemMagnifier.hoverTimer <= 60) {
            current = 1;
        } else if (ItemMagnifier.hoverTimer <= 100) {
            current = 2;
        }

        if (ItemMagnifier.drawing) {
            Minecraft.getMinecraft().fontRenderer.drawString(
                    TextFormatting.AQUA + "" + TextFormatting.ITALIC + "Scanning" + ItemMagnifier.dots[current],
                    mouseX,
                    mouseY,
                    0xFFFFFF
            );
        }
    }

}
