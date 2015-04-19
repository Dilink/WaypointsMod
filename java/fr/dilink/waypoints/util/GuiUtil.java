package fr.dilink.waypoints.util;

import java.awt.Color;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.opengl.GL11;

public class GuiUtil
{	
	public static void drawRect(int posXColor, int posYColor, int widthColor, int heightColor, int color, int alpha) {
		drawRect(posXColor, posYColor, widthColor, heightColor, new Color(color), alpha);
	}
	public static void drawRect(int posXColor, int posYColor, int widthColor, int heightColor, Color color, int alpha) {
		Tessellator tessellator = Tessellator.instance;
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA(color.getRed(), color.getGreen(), color.getBlue(), alpha);
		tessellator.addVertex((double)posXColor, (double)posYColor + heightColor, 0.0D);
		tessellator.addVertex((double)posXColor + widthColor, (double)posYColor + heightColor, 0.0D);
		tessellator.addVertex((double)posXColor + widthColor, (double)posYColor, 0.0D);
		tessellator.addVertex((double)posXColor, (double)posYColor, 0.0D);
		tessellator.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
	}
}