package fr.dilink.waypoints.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import fr.dilink.waypoints.util.Util;

public class GuiImageButton extends GuiButton
{
	private Item item;
	private ResourceLocation texture;
	private GuiImageButtonType type;
	private int nbrHeight;

    //private ResourceLocation cachedLogo;
    //private Dimension cachedLogoDimensions;

	public GuiImageButton(int id, int x, int y, GuiImageButtonType t) {
		super(id, x, y, 14, 14, "");
		this.type = t;
	}
	public GuiImageButton setText(String text) {
		this.displayString = text;
		return this;
	}
	public GuiImageButton setItem(Item item) {
		this.item = item;
		return this;
	}
	public GuiImageButton setTexture(String modID, String tex, int nbrH) {
		this.setTexture(new ResourceLocation(modID, tex), nbrH);
		return this;
	}
	public GuiImageButton setTexture(String tex, int nbrH) {
		this.setTexture("minecraft", tex, nbrH);
		return this;
	}
	public GuiImageButton setTexture(ResourceLocation tex, int nbrH) {
		this.texture = tex;
		this.nbrHeight = nbrH;
		
		
		
		/*try {
			BufferedImage logo = null;
            //IResourcePack pack = FMLClientHandler.instance().getResourcePackFor(WaypointsMod.MOD_ID);
			/*if (pack!=null) {
				logo = pack.getPackImage();
			} else {*/
				/*InputStream logoResource = getClass().getResourceAsStream("/assets/"+WaypointsMod.MOD_ID+"/textures/gui/croix.png");
				if (logoResource != null) {
					logo = ImageIO.read(logoResource);
				}
			//}
			if (logo != null) {
				cachedLogo = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("modlogo", new DynamicTexture(logo));
				cachedLogoDimensions = new Dimension(logo.getWidth(), logo.getHeight());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		return this;
	}
	public void draw(Minecraft mc, int mousX, int mouseY) {
		if (this.visible) {
			ResourceLocation close = new ResourceLocation(Util.MOD_ID, "textures/gui/close.png");
			ResourceLocation icons = new ResourceLocation(Util.MOD_ID, "textures/gui/icons.png");

			FontRenderer fontrenderer = mc.fontRenderer;
			TextureManager tm = mc.getTextureManager();
			tm.bindTexture(icons);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.field_146123_n = mousX >= this.xPosition && mouseY >= this.yPosition && mousX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
			int k = this.getHoverState(this.field_146123_n);
			GL11.glEnable(GL11.GL_BLEND);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 0 + k * this.height, this.width, this.height);
			this.mouseDragged(mc, mousX, mouseY);
			
			if(this.type == GuiImageButtonType.TEXT) {
				int l = 14737632;
				if (packedFGColour != 0) {
					l = packedFGColour;
				} else if (!this.enabled) {
					l = 10526880;
				} else if (this.field_146123_n) {
					l = 16777120;
				}
				this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, l);
			} else if(this.type == GuiImageButtonType.TEXTURE) {
				//cachedLogo
				Minecraft.getMinecraft().getTextureManager().bindTexture(this.texture);
				/*double scaleX = cachedLogoDimensions.width / 200.0;
	            double scaleY = cachedLogoDimensions.height / 65.0;
	            double scale = 1;
	            if (scaleX > 1 || scaleY > 1) {
	                scale = 2.0 / Math.max(scaleX, scaleY);
	            }
	            cachedLogoDimensions.width *= scale;
	            cachedLogoDimensions.height *= scale;*/
	            int top = this.yPosition;
	            int left = this.xPosition;
	            /*Tessellator tess = Tessellator.instance;
	            tess.startDrawingQuads();
	            tess.addVertexWithUV(left,                               top + 35 ,  zLevel, 0, 0.5);
	            tess.addVertexWithUV(left + cachedLogoDimensions.width,  top + 35,  zLevel, 1, 0.5);
	            tess.addVertexWithUV(left + cachedLogoDimensions.width,  top,                                zLevel, 1, 0);
	            tess.addVertexWithUV(left,                               top,                                zLevel, 0, 0);
	            tess.draw();*/
	            
	            int x = this.xPosition;
	            int y = this.yPosition;
	            int u = 0;
	            
	            int v = -14 + k*14;
	            
	            int w = this.width;
	            int h = this.height;

	            float f = 0.00390625F * (256 / w);
	            float f1 = 0.00390625F * (256 / (h*this.nbrHeight));
	            
	            Tessellator tessellator = Tessellator.instance;
	            tessellator.startDrawingQuads();
	            tessellator.addVertexWithUV((double)(x + 0), (double)(y + h), (double)this.zLevel, (double)((float)(u + 0) * f), (double)((float)(v + h) * f1));
	            tessellator.addVertexWithUV((double)(x + w), (double)(y + h), (double)this.zLevel, (double)((float)(u + w) * f), (double)((float)(v + h) * f1));
	            tessellator.addVertexWithUV((double)(x + w), (double)(y + 0), (double)this.zLevel, (double)((float)(u + w) * f), (double)((float)(v + 0) * f1));
	            tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)this.zLevel, (double)((float)(u + 0) * f), (double)((float)(v + 0) * f1));
	            tessellator.draw();
	            
			} else if(this.type == GuiImageButtonType.ITEM) {
				if(this.item != null) {					
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					tm.bindTexture(tm.getResourceLocation(new ItemStack(this.item).getItemSpriteNumber()));
					this.drawTexturedModelRectFromIcon(this.xPosition + 1, this.yPosition + 1, this.item.getIconFromDamage(0), 12, 12);
				}
			}
		}
	}
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {}
	
	public enum GuiImageButtonType {
		TEXT,
		TEXTURE,
		ITEM
	}
}