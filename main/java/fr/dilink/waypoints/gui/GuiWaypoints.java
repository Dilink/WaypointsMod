package fr.dilink.waypoints.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.config.GuiCheckBox;
import fr.dilink.waypoints.WaypointsMod;
import fr.dilink.waypoints.gui.components.GuiImageButton;
import fr.dilink.waypoints.gui.components.GuiImageButton.GuiImageButtonType;
import fr.dilink.waypoints.gui.components.GuiSlotWaypointsList;
import fr.dilink.waypoints.handlers.EventsHandler;
import fr.dilink.waypoints.handlers.Waypoint;
import fr.dilink.waypoints.threads.ThreadButtons;
import fr.dilink.waypoints.util.GuiUtil;
import fr.dilink.waypoints.util.SaveUtil;
import fr.dilink.waypoints.util.Util;

public class GuiWaypoints extends GuiScreen
{
	public EntityPlayer player;
	private GuiButton btn_cancel, btn_add, btn_randomize;
	private GuiCheckBox btn_range;
	public GuiImageButton btni_teleport, btni_delete, btni_visibility, btni_modify;
	private GuiTextField tf_x, tf_y, tf_z, tf_name, tf_color, tf_range;
	protected int xSize = 171;
	protected int ySize = 166;
	private int randomColor, prevColor;
	private int k, l;
	public GuiSlotWaypointsList waypointsList;
	private int selected = -1;
	public Waypoint selectedWP;
	//private int listWidth;
	private List<Waypoint> waypoints;
	public Minecraft minec;
	public FontRenderer fontRenderer;
	private List<String> hexaColorList, randomizeColorList, rangeList;
	private Thread threadButtons;
	public boolean isModifying;

	private List<GuiTextField> canBeFocused;
	private int focusedID = 0;

	ResourceLocation eye = new ResourceLocation(Util.MOD_ID, "textures/gui/eye.png");
	ResourceLocation eye_cut = new ResourceLocation(Util.MOD_ID, "textures/gui/eye_cut.png");

	public GuiWaypoints(EntityClientPlayerMP thePlayer) {
		this.player = thePlayer;
		this.threadButtons = new ThreadButtons(this);
	}
	public void initGui() {
		this.waypoints = (ArrayList<Waypoint>) WaypointsMod.proxy.waypoints;
		this.k = (this.width - xSize) / 2;
		this.l = (this.height - ySize) / 2;
		int baseX = this.k + 1;
		int baseY = 15 + 1;

		this.btn_cancel = new GuiButton(0, this.k - 15, this.height - 21, I18n.format("menu.cancel"));
		this.btn_add = new GuiButton(1, baseX + 137, baseY + 60, 35, 20, I18n.format("menu.add"));
		this.btn_add.enabled = false;
		this.btn_randomize = new GuiButton(2, baseX - 1, baseY + 60, 39, 20, I18n.format("menu.randomizeColor"));//99
		//this.btn_range = new GuiButton(7, this.btn_randomize.xPosition + this.btn_randomize.width + 27, this.btn_randomize.yPosition, 20, 20, "S");
		this.btn_range = new GuiCheckBox(7, this.btn_randomize.xPosition + this.btn_randomize.width + 27, this.btn_randomize.yPosition + 5, "", false);

		this.tf_x = new GuiTextField(fontRendererObj, baseX, baseY, 50, 20);
		this.tf_y = new GuiTextField(fontRendererObj, baseX + 60, baseY, 50, 20);
		this.tf_z = new GuiTextField(fontRendererObj, baseX + 120, baseY, 50, 20);
		this.tf_name = new GuiTextField(fontRendererObj, baseX, baseY + 30, 104, 20);
		this.tf_color = new GuiTextField(fontRendererObj, baseX + 114, baseY + 30, 56, 20);

		this.tf_x.setMaxStringLength(7);
		this.tf_y.setMaxStringLength(7);
		this.tf_z.setMaxStringLength(7);
		this.tf_color.setMaxStringLength(8);

		this.tf_name.setFocused(true);

		this.resetTextfields();

		this.buttonList.add(this.btn_cancel);
		this.buttonList.add(this.btn_add);
		this.buttonList.add(this.btn_randomize);

		this.buttonList.add(this.btn_range);

		this.tf_range = new GuiTextField(fontRendererObj, this.btn_range.xPosition + this.btn_range.width + 4, this.btn_range.yPosition - 5 + 1, 44, 18);
		this.tf_range.setMaxStringLength(6);
		this.tf_range.setText("0");

		this.minec = mc;
		this.fontRenderer = fontRendererObj;
		/*for (Waypoint mod : mods) {
			listWidth = Math.max(listWidth, fontRendererObj.getStringWidth(mod.getName()) + 10);
			listWidth = Math.max(listWidth, fontRendererObj.getStringWidth(mod.getStrPosition()) + 10);
		}
		listWidth = Math.min(listWidth, 150);*/
		this.waypointsList = new GuiSlotWaypointsList(this, waypoints, baseX, xSize, (this.height - (this.btn_cancel.height + baseY + 90 + 10)), baseY + 90);
		this.waypointsList.registerScrollButtons(this.buttonList, 7, 8);

		this.btni_teleport = new GuiImageButton(3, baseX + this.xSize - 23, this.waypointsList.top + 4, GuiImageButtonType.ITEM).setItem(Items.ender_pearl);
		this.btni_teleport.enabled = false;
		this.btni_teleport.visible = false;
		this.buttonList.add(this.btni_teleport);

		this.btni_delete = new GuiImageButton(4, baseX + this.xSize - 23, this.waypointsList.top + 20, GuiImageButtonType.TEXTURE).setTexture(Util.MOD_ID, "textures/gui/remove.png", 1);
		this.btni_delete.enabled = false;
		this.btni_delete.visible = false;
		this.buttonList.add(this.btni_delete);

		this.btni_visibility = new GuiImageButton(5, baseX + this.xSize - 40, this.waypointsList.top + 4, GuiImageButtonType.TEXTURE);
		this.btni_visibility.enabled = false;
		this.btni_visibility.visible = false;
		this.btni_visibility.setTexture(eye, 1);
		this.buttonList.add(this.btni_visibility);

		this.btni_modify = new GuiImageButton(6, baseX + this.xSize - 40, this.waypointsList.top + 20, GuiImageButtonType.TEXTURE).setTexture(Util.MOD_ID, "textures/gui/modify.png", 1);
		this.btni_modify.enabled = false;
		this.btni_modify.visible = false;
		this.buttonList.add(this.btni_modify);

		this.hexaColorList = new ArrayList<String>();
		this.hexaColorList.add(I18n.format("menu.tf_color.hexa"));
		this.hexaColorList.add(I18n.format("menu.tf_color.chars"));
		this.randomizeColorList = new ArrayList<String>();
		this.randomizeColorList.add(I18n.format("menu.btn_color.randomize"));
		this.randomizeColorList.add(I18n.format(""));
		this.randomizeColorList.add(I18n.format("menu.btn_color.leftClick"));
		this.randomizeColorList.add(I18n.format("menu.btn_color.rightClick"));
		this.rangeList = new ArrayList<String>();
		this.rangeList.add(I18n.format("menu.tf_range_1"));
		this.rangeList.add(I18n.format("menu.tf_range_2"));
		this.rangeList.add(I18n.format(""));
		this.rangeList.add(I18n.format("menu.tf_range_3"));
		this.rangeList.add("   "+I18n.format("menu.tf_range_4"));

		this.canBeFocused = new ArrayList<GuiTextField>();
		this.canBeFocused.add(this.tf_name);
		this.canBeFocused.add(this.tf_color);
		this.canBeFocused.add(this.tf_range);
		this.canBeFocused.add(this.tf_x);
		this.canBeFocused.add(this.tf_y);
		this.canBeFocused.add(this.tf_z);
	}
	public void drawScreen(int mouseX, int mouseY, float delta) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, delta);

		this.tf_x.drawTextBox();
		this.tf_y.drawTextBox();
		this.tf_z.drawTextBox();
		this.tf_name.drawTextBox();
		this.tf_color.drawTextBox();
		this.tf_range.drawTextBox();

		int currentColorX = this.btn_randomize.xPosition + this.btn_randomize.width + 2;
		int currentColorY = this.btn_randomize.yPosition + 1;
		int currentColorW = 22;
		int currentColorH = 18;

		GuiUtil.drawRect(currentColorX, currentColorY, currentColorW, currentColorH, 0xA0A0A0, 255);
		GuiUtil.drawRect(currentColorX + 1, currentColorY + 1, currentColorW - 2, currentColorH - 2, this.randomColor, 255);

		this.waypointsList.drawScreen(mouseX, mouseY, delta);

		if(this.isModifying)
			this.drawString(fontRendererObj, I18n.format("menu.modifying"), (this.width - this.fontRendererObj.getStringWidth(I18n.format("menu.modifying"))) / 2, 4, 16777120);

		this.btni_teleport.draw(mc, mouseX, mouseY);
		this.btni_delete.draw(mc, mouseX, mouseY);
		this.btni_visibility.draw(mc, mouseX, mouseY);
		this.btni_modify.draw(mc, mouseX, mouseY);

		if(isMouseHover(mouseX, mouseY, delta, this.tf_name.xPosition, this.tf_name.yPosition, this.tf_name.width, this.tf_name.height))
			this.drawToolTip(this.mc.fontRenderer.listFormattedStringToWidth(I18n.format("menu.tf_name"), 105), mouseX, mouseY);
		if(isMouseHover(mouseX, mouseY, delta, this.tf_color.xPosition, this.tf_color.yPosition, this.tf_color.width, this.tf_color.height))
			this.drawToolTip(hexaColorList, mouseX, mouseY);

		if(isMouseHover(mouseX, mouseY, delta, this.tf_x.xPosition, this.tf_x.yPosition, this.tf_x.width, this.tf_x.height))
			this.drawToolTip(this.mc.fontRenderer.listFormattedStringToWidth(I18n.format("menu.tf_x"), 77), mouseX, mouseY);
		if(isMouseHover(mouseX, mouseY, delta, this.tf_y.xPosition, this.tf_y.yPosition, this.tf_y.width, this.tf_y.height))
			this.drawToolTip(this.mc.fontRenderer.listFormattedStringToWidth(I18n.format("menu.tf_y"), 77), mouseX, mouseY);
		if(isMouseHover(mouseX, mouseY, delta, this.tf_z.xPosition, this.tf_z.yPosition, this.tf_z.width, this.tf_z.height))
			this.drawToolTip(this.mc.fontRenderer.listFormattedStringToWidth(I18n.format("menu.tf_z"), 77), mouseX, mouseY);

		if(isMouseHover(mouseX, mouseY, delta, this.btn_randomize.xPosition, this.btn_randomize.yPosition, this.btn_randomize.width, this.btn_randomize.height))
			this.drawToolTip(randomizeColorList, mouseX, mouseY);
		if(isMouseHover(mouseX, mouseY, delta, this.btn_add.xPosition, this.btn_add.yPosition, this.btn_add.width, this.btn_add.height))
			this.drawToolTip(this.mc.fontRenderer.listFormattedStringToWidth(I18n.format("menu.btn_add"), 84), mouseX, mouseY);

		if(isMouseHover(mouseX, mouseY, delta, this.btn_range.xPosition, this.btn_range.yPosition, this.btn_range.width, this.btn_range.height))
			this.drawToolTip(this.mc.fontRenderer.listFormattedStringToWidth(I18n.format("menu.btn_range"), 123), mouseX, mouseY);
		if(isMouseHover(mouseX, mouseY, delta, this.tf_range.xPosition, this.tf_range.yPosition, this.tf_range.width, this.tf_range.height))
			//this.drawToolTip(this.mc.fontRenderer.listFormattedStringToWidth(I18n.format("menu.tf_range"), 189), mouseX, mouseY);
			this.drawToolTip(rangeList, mouseX, mouseY);

		if(isMouseHover(mouseX, mouseY, delta, currentColorX, currentColorY, currentColorW, currentColorH))
			this.drawToolTip(this.mc.fontRenderer.listFormattedStringToWidth(I18n.format("menu.tf_currentColor"), 71), mouseX, mouseY);

		if(this.selectedWP != null) {
			if(this.selectedWP.isVisible())
				this.btni_visibility.setTexture(eye, 1);
			else
				this.btni_visibility.setTexture(eye_cut, 1);
		}
	}
	public boolean isMouseHover(int mouseX, int mouseY, float delta, int xPosition, int yPosition, int w, int h) {
		return mouseX >= xPosition && mouseX < xPosition + w && mouseY >= yPosition && mouseY < yPosition + h;
	}
	protected void keyTyped(char c, int i) {
		super.keyTyped(c, i);
		this.tf_x.textboxKeyTyped(c, i);
		this.tf_y.textboxKeyTyped(c, i);
		this.tf_z.textboxKeyTyped(c, i);
		this.tf_name.textboxKeyTyped(c, i);
		this.tf_color.textboxKeyTyped(c, i);
		this.tf_range.textboxKeyTyped(c, i);
		if (i == 15) {
			if(!this.isShiftKeyDown()) {
				if(this.focusedID+1 < this.canBeFocused.size()) this.focusedID++;
				else this.focusedID = 0;
				for(int g = 0; g < this.canBeFocused.size(); g++) this.canBeFocused.get(g).setFocused(false);
				this.canBeFocused.get(this.focusedID).setFocused(true);
			} else {
				if(this.focusedID-1 >= 0) this.focusedID--;
				else this.focusedID = this.canBeFocused.size()-1;
				for(int g = 0; g < this.canBeFocused.size(); g++) this.canBeFocused.get(g).setFocused(false);
				this.canBeFocused.get(this.focusedID).setFocused(true);
			}
		}
		if(this.tf_color.isFocused()) {
			try {
				if(this.tf_color.getText().length() == 8)
					this.randomColor = Integer.parseInt(this.tf_color.getText().replace("0x",""), 16);
			} catch(NumberFormatException e) {}
		}
	}
	public void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		this.tf_x.mouseClicked(i, j, k);
		this.tf_y.mouseClicked(i, j, k);
		this.tf_z.mouseClicked(i, j, k);
		this.tf_name.mouseClicked(i, j, k);
		this.tf_color.mouseClicked(i, j, k);
		this.tf_range.mouseClicked(i, j, k);
		for (int k2 = 0; k2 < this.canBeFocused.size(); k2++) {
			GuiTextField t = this.canBeFocused.get(k2);
			if(this.isMouseHover(i, j, 0, t.xPosition, t.yPosition, t.width, t.height))
				this.focusedID = k2;
		}
		if(this.btn_randomize.mousePressed(mc, i, j)) {
			if(k == 0) {
				this.prevColor = this.randomColor;
				int newColor = this.randomHexa();
				while (Integer.toHexString(newColor).length() != 6)
					newColor = this.randomHexa();
				this.randomColor = newColor;
				this.tf_color.setText("0x"+Integer.toHexString(this.randomColor));
			} else if(k == 1) {
				this.randomColor = this.prevColor;
				this.tf_color.setText("0x"+Integer.toHexString(this.randomColor));
				this.btn_randomize.func_146113_a(this.mc.getSoundHandler());
			}
		}
	}
	protected void actionPerformed(GuiButton button) {
		if(button == this.btn_add && button.id == this.btn_add.id) {
			this.addWaypoint();
		} else if(button == this.btn_cancel && button.id == this.btn_cancel.id) {
			this.mc.displayGuiScreen((GuiScreen)null);
			this.mc.setIngameFocus();
		} else if(button == this.btni_teleport && button.id == this.btni_teleport.id) {
			this.teleportToWaypoint(this.selectedWP);
		} else if(button == this.btni_delete && button.id == this.btni_delete.id) {
			SaveUtil.removeFromNBT(this.selectedWP);
			this.waypointsList.hideButtons();
		} else if(button == this.btni_visibility && button.id == this.btni_visibility.id) {
			SaveUtil.modifyNBT(this.selectedWP, SaveUtil.DataType.data_visibility, !this.selectedWP.isVisible());
			this.selectedWP.toggleVisibility();
		} else if(button == this.btni_modify && button.id == this.btni_modify.id) {
			this.modifyWaypoint(this.selectedWP);
		}
	}
	@Override
	public void updateScreen() {
		this.k = (this.width - this.xSize) / 2;
		this.l = (this.height - this.ySize) / 2;

		if(this.btn_range.isChecked())
			this.tf_range.setEnabled(true);
		else
			this.tf_range.setEnabled(false);

		if(StringUtils.isAlphanumericSpace(this.tf_name.getText()) && this.tf_color.getText().length()==8 &&  StringUtils.isAlphanumeric(this.tf_color.getText()) && StringUtils.startsWithIgnoreCase(this.tf_color.getText(), "0x") && StringUtils.isNoneBlank(this.tf_name.getText(), this.tf_color.getText(), this.tf_x.getText(), this.tf_y.getText(), this.tf_z.getText()) && StringUtils.isNumeric(this.tf_range.getText()))
			this.btn_add.enabled = true;
		else
			this.btn_add.enabled = false;
	}
	public boolean doesGuiPauseGame() {
		return false;
	}
	public int randomHexa() {
		return new Random().nextInt(16777216);
	}
	public void selectModIndex(int var1) {
		this.selected = var1;
		if (var1 >= 0 && var1 <= waypoints.size()) {
			this.threadButtons.stop();
			this.threadButtons.interrupt();
			if(this.selectedWP != null) {
				if(!this.selectedWP.equals(waypoints.get(this.selected))) {
					this.resetTextfields();
					this.threadButtons = new ThreadButtons(this);
					this.threadButtons.start();
				}
			} else {
				this.threadButtons = new ThreadButtons(this);
				this.threadButtons.start();
			}
			this.selectedWP = waypoints.get(selected);
		} else
			this.selectedWP = null;
	}
	public boolean modIndexSelected(int var1) {
		return var1 == selected;
	}
	public void drawToolTip(List stringList, int x, int y) {
		this.func_146283_a(stringList, x, y);
	}
	public void drawWaympointString(String texte, int ligne, int x, int y, int color, int top, int bottom) {
		int offset = 10; //13
		x += 3;
		y += offset * ligne;
		if((y-2 + offset <= bottom) && (y >= top)) this.fontRendererObj.drawString(texte, x, y, color);
	}
	public void drawCheckDimension(Waypoint wp, int x, int y, int top, int bottom, int var6) {
		float wrongDimfactorScale = 0.47f;
		int wrongDimX = this.width + x - 5;
		int wrongDimY = (int)((var6 + 2)*(1f/wrongDimfactorScale));

		if((player != null) && (y - 2 + 7 <= bottom) && (y - 2 >= top)) {
			if((player.dimension != wp.getDimension())) {
				//this.teleport.enabled = false;
				GL11.glPushMatrix();
				this.mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/beacon.png"));
				GL11.glScalef(wrongDimfactorScale, wrongDimfactorScale, 1);
				this.drawTexturedModalRect((int)((this.width / 2 + 38)*(1f/wrongDimfactorScale)), wrongDimY, 114, 223, 13, 13);
				GL11.glScalef(1f/wrongDimfactorScale, 1f/wrongDimfactorScale, 1);
				GL11.glPopMatrix();
			} else {
				//this.teleport.enabled = true;
			}
			if(wp.isShared()) {
				GL11.glPushMatrix();
				this.mc.renderEngine.bindTexture(new ResourceLocation(Util.MOD_ID, "textures/gui/icons.png"));
				GL11.glScalef(wrongDimfactorScale, wrongDimfactorScale, 1);
				this.drawTexturedModalRect((int)((this.width / 2 + 37)*(1f/wrongDimfactorScale)), wrongDimY + 18, 14, 0, 16, 16);
				GL11.glScalef(1f/wrongDimfactorScale, 1f/wrongDimfactorScale, 1);
				GL11.glPopMatrix();
			}
		}
	}
	public void updateButtons(int x, int y, int top, int bottom, int var6) {
		if (selectedWP != null) {
			if((y - 2 + 16 <= bottom) && (y - 2 >= top)) {
				//this.teleport.enabled = true;
				this.btni_teleport.visible = true;
				//this.visibility.enabled = true;
				this.btni_visibility.visible = true;
			} else {
				//this.teleport.enabled = false;
				this.btni_teleport.visible = false;
				//this.visibility.enabled = false;
				this.btni_visibility.visible = false;
			}
			if((y - 2 + 32 <= bottom) && (y - 2 + 16 >= top)) {
				//this.delete.enabled = true;
				this.btni_delete.visible = true;
				//this.modifiy.enabled = true;
				this.btni_modify.visible = true;
			} else {
				//this.delete.enabled = false;
				this.btni_delete.visible = false;
				//this.modifiy.enabled = false;
				this.btni_modify.visible = false;
			}
			this.btni_teleport.yPosition = var6 + 1;
			this.btni_visibility.yPosition = var6 + 1;
			this.btni_delete.yPosition = var6 + 1 + 17;
			this.btni_modify.yPosition = var6 + 1 + 17;



		} else {
			this.waypointsList.hideButtons();
		}
	}
	public void modifyWaypoint(Waypoint wp) {
		if(this.isModifying == false) {
			this.isModifying = true;
			this.tf_name.setText(wp.getName());
			String hexColor = "0x" + Integer.toHexString(wp.getColor()).substring(2).toUpperCase();
			try {
				if(this.tf_color.getText().length() == 8)
					this.randomColor = Integer.parseInt(hexColor.replace("0x",""), 16);
			} catch(NumberFormatException e) {}
			this.tf_color.setText(hexColor);
			this.tf_x.setText(String.valueOf(wp.getX()));
			this.tf_y.setText(String.valueOf(wp.getY()));
			this.tf_z.setText(String.valueOf(wp.getZ()));

			this.btn_add.displayString = I18n.format("menu.apply");
		} else {
			this.resetTextfields();
		}
	}
	public void teleportToWaypoint(Waypoint wp) {
		((EntityClientPlayerMP) this.player).sendChatMessage("/tp " + this.selectedWP.getX() + " " + (this.selectedWP.getY()-1.5D) + " " + this.selectedWP.getZ());
		this.mc.displayGuiScreen((GuiScreen)null);
		this.mc.setIngameFocus();
	}
	private void addWaypoint() {
		//if(!this.btn_range.isChecked()) {
			if(this.isModifying == false) {
				try {
					double[] d = Util.getCenterOfBlock(Double.parseDouble(this.tf_x.getText()), Double.parseDouble(this.tf_y.getText()), Double.parseDouble(this.tf_z.getText()));
					Waypoint wp = new Waypoint(this.tf_name.getText(), d[0], d[1], d[2], true, Integer.parseInt(this.tf_color.getText().replace("0x",""), 16), this.player.dimension, this.btn_range.isChecked());
					SaveUtil.addToNBT(wp);
				} catch(NumberFormatException e) {
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Error in the coordinates !"));
				}
				this.mc.displayGuiScreen((GuiScreen)null);
				this.mc.setIngameFocus();
			} else {
				double[] d = Util.getCenterOfBlock(Double.parseDouble(this.tf_x.getText()), Double.parseDouble(this.tf_y.getText()), Double.parseDouble(this.tf_z.getText()));
				Waypoint wp = new Waypoint(this.tf_name.getText(), d[0], d[1], d[2], selectedWP.isVisible(), Integer.parseInt(this.tf_color.getText().replace("0x",""), 16), this.player.dimension, false);
				if(!selectedWP.equals(wp)) {
					if(!selectedWP.getName().equals(wp.getName()))
						SaveUtil.modifyNBT(selectedWP, SaveUtil.DataType.data_name, wp.getName());
					if(selectedWP.getX() != wp.getX())
						SaveUtil.modifyNBT(selectedWP, SaveUtil.DataType.data_posX, wp.getZ());
					if(selectedWP.getY() != wp.getY())
						SaveUtil.modifyNBT(selectedWP, SaveUtil.DataType.data_posY, wp.getY());
					if(selectedWP.getZ() != wp.getZ())
						SaveUtil.modifyNBT(selectedWP, SaveUtil.DataType.data_posZ, wp.getZ());
					if(selectedWP.getColor() != wp.getColor())
						SaveUtil.modifyNBT(selectedWP, SaveUtil.DataType.data_color, wp.getColor());
				}
				this.initGui();
			}
		/*} else {
			if(!WaypointsMod.proxy.multiplayer) {
				try {
					double[] d = Util.getCenterOfBlock(Double.parseDouble(this.tf_x.getText()), Double.parseDouble(this.tf_y.getText()), Double.parseDouble(this.tf_z.getText()));
					Waypoint wp = new Waypoint(this.tf_name.getText(), d[0], d[1], d[2], true, Integer.parseInt(this.tf_color.getText().replace("0x",""), 16), this.player.dimension, true);
	
					int range = 0;
	
					if(this.tf_range.getText().length() > 0)
						range = Integer.parseInt(this.tf_range.getText());
					if(range == 0) {
						WaypointsMod.proxy.network.sendToDimension(new PacketSendWaypoint(wp), player.dimension);
					} else {					
						WaypointsMod.proxy.network.sendToAllAround(new PacketSendWaypoint(wp), new TargetPoint(player.dimension, player.posX, player.posY, player.posZ, Math.abs(range)));
					}
				} catch(NumberFormatException e) {
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Error in the coordinates !"));
				}
			}
		}*/
		EventsHandler.getGuiIGNotif().queueHatUnlocked(Util.MOD_NAME, I18n.format("waypoints_mod.notif_ingame_gui.common")+" !", new ItemStack(Blocks.beacon), GuiIGNotif.textureAchievement);
	}
	public void resetTextfields() {
		this.isModifying = false;
		double[] d_p = Util.getCenterOfBlock(player.posX, player.posY, player.posZ);
		this.tf_x.setText(String.valueOf(d_p[0]));
		this.tf_y.setText(String.valueOf(d_p[1]));
		this.tf_z.setText(String.valueOf(d_p[2]));
		this.tf_name.setText(I18n.format("menu.name"));
		this.tf_color.setText("0xFFFFFF");
		try {
			if(this.tf_color.getText().length() == 8)
				this.randomColor = Integer.parseInt(this.tf_color.getText().replace("0x",""), 16);
		} catch(NumberFormatException e) {}
		this.btn_add.displayString = I18n.format("menu.add");
	}
}