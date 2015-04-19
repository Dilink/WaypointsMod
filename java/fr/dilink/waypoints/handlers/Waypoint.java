package fr.dilink.waypoints.handlers;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import fr.dilink.waypoints.util.SaveUtil;
import fr.dilink.waypoints.util.Util;

public class Waypoint
{
	private long field_146016_i;
	private float field_146014_j;
	private boolean field_146015_k = true;
	private Tessellator tess = Tessellator.instance;
	private Minecraft mc = Minecraft.getMinecraft();
	private static final ResourceLocation field_147523_b = new ResourceLocation("textures/entity/beacon_beam.png");

	private String name;
	private Double posX, posY, posZ;
	private Boolean visible, isShared;
	private Color color;
	private double distance;
	private int dimensionID;

	public Waypoint(String n, double x, double y, double z, boolean v, int c, int dimID, boolean share) {
		this.name = n;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		this.visible = v;
		this.color = new Color(c);
		this.dimensionID = dimID;
		this.isShared = share;
	}

	public String getName() {
		return this.name;
	}
	public double getX() {
		return this.posX;
	}
	public double getY() {
		return this.posY;
	}
	public double getZ() {
		return this.posZ;
	}
	public int getColor() {
		return this.color.getRGB();
	}
	public String getStrPosition() {
		return this.getX() + " / " + this.getY() + " / " + this.getZ();
	}
	public boolean isVisible() {
		return this.visible;
	}
	public void toggleVisibility() {
		this.visible = !this.visible;
	}
	public void setVisibility(boolean b) {
		this.visible = b;
	}
	public int getDimension() {
		return this.dimensionID;
	}
	public boolean isShared() {
		return this.isShared;
	}
	public float getDistance(EntityLivingBase player) {
		if(player != null) {
			double[] d = Util.getCenterOfBlock(this.posX, this.posY, this.posZ);
			return Math.round( Math.sqrt( Math.pow(player.posX - d[0], 2) + Math.pow(player.posY - d[1], 2) + Math.pow(player.posZ - d[2], 2) ) );
		} else {
			return 0;
		}
	}
	public String getDistanceStr(EntityLivingBase player) {
		return (int)distance + "m";
	}
	public boolean equals(Waypoint object2) {
		return object2 instanceof Waypoint && name.equals(object2.name) && posX.equals(object2.posX) && posY.equals(object2.posY) && posZ.equals(object2.posZ) && visible.equals(object2.visible) && color.equals(object2.color);
	}
	private float animationMovement() {
		if (!this.field_146015_k) return 0.0F;
		else {
			int i = (int)(mc.thePlayer.worldObj.getTotalWorldTime() - this.field_146016_i);
			this.field_146016_i = mc.thePlayer.worldObj.getTotalWorldTime();
			if (i > 1) {
				this.field_146014_j -= (float)i / 40.0F;
				if (this.field_146014_j < 0.0F) this.field_146014_j = 0.0F;
			}
			this.field_146014_j += 0.025F;
			if (this.field_146014_j > 1.0F) this.field_146014_j = 1.0F;
			return this.field_146014_j;
		}
	}
	public NBTTagCompound getNBTCompound() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		nbttagcompound.setString(SaveUtil.DataType.data_name.str(), this.getName());
		nbttagcompound.setDouble(SaveUtil.DataType.data_posX.str(), this.getX());
		nbttagcompound.setDouble(SaveUtil.DataType.data_posY.str(), this.getY());
		nbttagcompound.setDouble(SaveUtil.DataType.data_posZ.str(), this.getZ());
		nbttagcompound.setInteger(SaveUtil.DataType.data_color.str(), this.getColor());
		return nbttagcompound;
	}
	public void draw(RenderWorldLastEvent event) {
		float f1_ = this.animationMovement();

		RenderManager renderManager = RenderManager.instance;
		EntityLivingBase player = renderManager.livingPlayer;

		distance = this.getDistance(player);

		if((player != null) && this.isVisible() && (player.dimension == this.getDimension()) && (f1_ > 0.0F)) {
			float number = 1.0F;
			FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
			if(player != null) {
				double playerX = player.prevPosX + (player.posX - player.prevPosX) * event.partialTicks;
				double playerZ = player.prevPosZ + (player.posZ - player.prevPosZ) * event.partialTicks;

				int yLevel = 0;
				while((this.posY-yLevel > 1) && !(player.worldObj.isSideSolid((int)this.getX(), ((int)this.getY()-yLevel), (int)this.getZ(), ForgeDirection.UNKNOWN, false))) {
					yLevel++;
				}

				double playerY = player.prevPosY + (player.posY - player.prevPosY) * event.partialTicks + yLevel;

				byte b0_ = 1;
				float f2_ = (float)mc.thePlayer.worldObj.getTotalWorldTime() + number;
				float f3_ = -f2_ * 0.2F - (float)MathHelper.floor_float(-f2_ * 0.1F);
				double d3_ = (double)f2_ * 0.025D * (1.0D - (double)(b0_ & 1) * 2.5D);

				double d5_ = (double)b0_ * 0.2D;
				double d7_ = 0.5D + Math.cos(d3_ + 2.356194490192345D) * d5_;
				double d9_ = 0.5D + Math.sin(d3_ + 2.356194490192345D) * d5_;
				double d11_ = 0.5D + Math.cos(d3_ + (Math.PI / 4D)) * d5_;
				double d13_ = 0.5D + Math.sin(d3_ + (Math.PI / 4D)) * d5_;
				double d15_ = 0.5D + Math.cos(d3_ + 3.9269908169872414D) * d5_;
				double d17_ = 0.5D + Math.sin(d3_ + 3.9269908169872414D) * d5_;
				double d19_ = 0.5D + Math.cos(d3_ + 5.497787143782138D) * d5_;
				double d21_ = 0.5D + Math.sin(d3_ + 5.497787143782138D) * d5_;
				double d23_ = (double)(256.0F * f1_);
				double d25_ = 0.0D;
				double d27_ = 1.0D;
				double d28_ = (double)(-1.0F + f3_);
				double d29_ = (double)(256.0F * f1_) * (0.5D / d5_) + d28_;

				double d4_ = 0.2D;
				double d6_ = 0.8D;
				double d8_ = 0.2D;
				double d10_ = 0.2D;
				double d12_ = 0.8D;
				double d14_ = 0.8D;
				double d16_ = 0.8D;
				double d18_ = (double)(256.0F * f1_);
				double d20_ = 0.0D;
				double d22_ = 1.0D;
				double d24_ = (double)(-1.0F + f3_);
				double d26_ = (double)(256.0F * f1_) + d24_;
				double d30_ = 0.2D;

				GL11.glPushMatrix();
				GL11.glTranslated(-playerX - 0.5D, -playerY - 0.5D, -playerZ - 0.5D);

				GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 10497.0F);
				GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 10497.0F);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_CULL_FACE);
				GL11.glDisable(GL11.GL_BLEND);
				/*GL11.glEnable(GL11.GL_LINE_SMOOTH);
				GL11.glHint( GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST );*/
				/*GL11.glBegin(GL11.GL_LINE_STRIP);
				GL11.glVertex3f(mx+0.5f, my, mz+0.5f);
				GL11.glVertex3f(mx+0.5f, my+(256-my), mz+0.5f);
				GL11.glEnd();*/
				/*tess.startDrawingQuads(); //Method 1
				tess.addVertex(mx, my, mz); //Origin
				tess.addVertex(mx, my + 1, mz); //1 unit up
				tess.addVertex(mx + 1, my + 1, mz); //1 unit up and to the right
				tess.addVertex(mx + 1, my, mz); //1 unit to the right
				tess.draw(); //Method 2*/
				this.mc.renderEngine.bindTexture(field_147523_b);
				GL11.glEnable(GL11.GL_BLEND);
				OpenGlHelper.glBlendFunc(770, 771, 1, 0);
				GL11.glDepthMask(false);

				//Colonne
				tess.startDrawingQuads();
				tess.setColorRGBA(color.getRed(), color.getGreen(), color.getBlue(), 52); // ORANGE     / WHITE:255, 255, 255
				//tess.setColorRGBA(255, 165, 0, 82); // ORANGE     / WHITE:255, 255, 255
				tess.addVertexWithUV(posX + d7_, posY + d23_, posZ + d9_, d27_, d29_);
				tess.addVertexWithUV(posX + d7_, posY, posZ + d9_, d27_, d28_);
				tess.addVertexWithUV(posX + d11_, posY, posZ + d13_, d25_, d28_);
				tess.addVertexWithUV(posX + d11_, posY + d23_, posZ + d13_, d25_, d29_);
				tess.addVertexWithUV(posX + d19_, posY + d23_, posZ + d21_, d27_, d29_);
				tess.addVertexWithUV(posX + d19_, posY, posZ + d21_, d27_, d28_);
				tess.addVertexWithUV(posX + d15_, posY, posZ + d17_, d25_, d28_);
				tess.addVertexWithUV(posX + d15_, posY + d23_, posZ + d17_, d25_, d29_);
				tess.addVertexWithUV(posX + d11_, posY + d23_, posZ + d13_, d27_, d29_);
				tess.addVertexWithUV(posX + d11_, posY, posZ + d13_, d27_, d28_);
				tess.addVertexWithUV(posX + d19_, posY, posZ + d21_, d25_, d28_);
				tess.addVertexWithUV(posX + d19_, posY + d23_, posZ + d21_, d25_, d29_);
				tess.addVertexWithUV(posX + d15_, posY + d23_, posZ + d17_, d27_, d29_);
				tess.addVertexWithUV(posX + d15_, posY, posZ + d17_, d27_, d28_);
				tess.addVertexWithUV(posX + d7_, posY, posZ + d9_, d25_, d28_);
				tess.addVertexWithUV(posX + d7_, posY + d23_, posZ + d9_, d25_, d29_);
				tess.draw();
				//Couronne
				tess.startDrawingQuads();
				tess.setColorRGBA(color.getRed(), color.getGreen(), color.getBlue(), 26); // ORANGE     / WHITE:255, 255, 255
				//tess.setColorRGBA(255, 165, 0, 26); // ORANGE     / WHITE:255, 255, 255
				tess.addVertexWithUV(posX + d30_, posY + d18_, posZ + d4_, d22_, d26_);
				tess.addVertexWithUV(posX + d30_, posY, posZ + d4_, d22_, d24_);
				tess.addVertexWithUV(posX + d6_, posY, posZ + d8_, d20_, d24_);
				tess.addVertexWithUV(posX + d6_, posY + d18_, posZ + d8_, d20_, d26_);
				tess.addVertexWithUV(posX + d14_, posY + d18_, posZ + d16_, d22_, d26_);
				tess.addVertexWithUV(posX + d14_, posY, posZ + d16_, d22_, d24_);
				tess.addVertexWithUV(posX + d10_, posY, posZ + d12_, d20_, d24_);
				tess.addVertexWithUV(posX + d10_, posY + d18_, posZ + d12_, d20_, d26_);
				tess.addVertexWithUV(posX + d6_, posY + d18_, posZ + d8_, d22_, d26_);
				tess.addVertexWithUV(posX + d6_, posY, posZ + d8_, d22_, d24_);
				tess.addVertexWithUV(posX + d14_, posY, posZ + d16_, d20_, d24_);
				tess.addVertexWithUV(posX + d14_, posY + d18_, posZ + d16_, d20_, d26_);
				tess.addVertexWithUV(posX + d10_, posY + d18_, posZ + d12_, d22_, d26_);
				tess.addVertexWithUV(posX + d10_, posY, posZ + d12_, d22_, d24_);
				tess.addVertexWithUV(posX + d30_, posY, posZ + d4_, d20_, d24_);
				tess.addVertexWithUV(posX + d30_, posY + d18_, posZ + d4_, d20_, d26_);
				tess.draw();

				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glDepthMask(true);

				int maxDistance = 24;
				if (distance <= maxDistance) {











					float f = 1.6F;
					float f1 = 0.016666668F * f;
					GL11.glPushMatrix();
					GL11.glTranslated(0D, yLevel, 0D);
					GL11.glTranslatef((float)this.getX() + 0.5F, (float)this.getY() + 0.5F, (float)this.getZ() + 0.5F);
					GL11.glNormal3f(0.0F, 1.0F, 0.0F);
					GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
					GL11.glScalef(-f1, -f1, f1);
					GL11.glDisable(GL11.GL_LIGHTING);
					GL11.glDepthMask(false);
					GL11.glDisable(GL11.GL_DEPTH_TEST);
					GL11.glEnable(GL11.GL_BLEND);

					OpenGlHelper.glBlendFunc(770, 771, 1, 0);
					Tessellator tessellator = Tessellator.instance;

					/*Logger logger = LogManager.getLogger();
				    Marker marker = MarkerManager.getMarker("ARMA");
				    logger.info(marker, "Sound engine started");*/

					byte b0 = 0;

					tessellator.startDrawingQuads();
					int j = fontRenderer.getStringWidth(name) / 2;
					tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
					tessellator.addVertex((double)(-j - 1), (double)(-1 + b0), 0.0D);
					tessellator.addVertex((double)(-j - 1), (double)(8 + b0), 0.0D);
					tessellator.addVertex((double)(j + 1), (double)(8 + b0), 0.0D);
					tessellator.addVertex((double)(j + 1), (double)(-1 + b0), 0.0D);
					tessellator.draw();
					//fontRenderer.drawString(name, -fontRenderer.getStringWidth(name) / 2, b0, 553648127);
					fontRenderer.drawString(name, -fontRenderer.getStringWidth(name) / 2, b0, -1);//this.color.getRGB()

					String textDistance = this.getDistanceStr(player);

					b0 = 10;
					tessellator.startDrawingQuads();
					int jDistance = fontRenderer.getStringWidth(textDistance) / 2;
					tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
					//					tessellator.addVertex((double)(-jDistance - 1), (double)(-1 + b0), 0.0D);
					//					tessellator.addVertex((double)(-jDistance - 1), (double)(8 + b0), 0.0D);
					//					tessellator.addVertex((double)(jDistance + 1), (double)(8 + b0), 0.0D);
					//					tessellator.addVertex((double)(jDistance + 1), (double)(-1 + b0), 0.0D);
					tessellator.addVertex(-10, (double)(-1 + b0), 0.0D);
					tessellator.addVertex(-10, (double)(8 + b0), 0.0D);
					tessellator.addVertex(10, (double)(8 + b0), 0.0D);
					tessellator.addVertex(10, (double)(-1 + b0), 0.0D);
					tessellator.draw();
					//fontRenderer.drawString(textDistance, -fontRenderer.getStringWidth(textDistance) / 2, b0, 553648127);
					fontRenderer.drawString(textDistance, -fontRenderer.getStringWidth(textDistance) / 2, b0, -1);

					GL11.glDepthMask(true);
					GL11.glEnable(GL11.GL_LIGHTING);
					GL11.glEnable(GL11.GL_DEPTH_TEST);
					GL11.glDisable(GL11.GL_BLEND);
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					GL11.glPopMatrix();
				}
				GL11.glPopMatrix();
			}
		}
	}
}