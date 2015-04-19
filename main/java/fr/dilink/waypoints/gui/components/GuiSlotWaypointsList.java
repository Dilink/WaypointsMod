package fr.dilink.waypoints.gui.components;

import java.util.List;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.EnumChatFormatting;
import fr.dilink.waypoints.gui.GuiWaypoints;
import fr.dilink.waypoints.handlers.Waypoint;

public class GuiSlotWaypointsList extends GuiWaypointScrollingList
{
	private GuiWaypoints parent;
	private List<Waypoint> waypoints;

	public GuiSlotWaypointsList(GuiWaypoints guiWaypoints, List<Waypoint> wps, int baseX, int width, int height, int top) {
		super(guiWaypoints.mc, width, 0, top, top + height, baseX, 35);//mc, width, height, top, bottom, left, slotHeight => 35 || 42
		this.parent = guiWaypoints;
		this.waypoints = wps;
	}
	@Override
	protected int getSize() {
		return waypoints.size();
	}
	@Override
	protected void elementClicked(int var1, boolean var2) {
		this.parent.selectModIndex(var1);
	}
	@Override
	protected boolean isSelected(int var1) {
		return this.parent.modIndexSelected(var1);
	}
	@Override
	protected void drawBackground() {
		this.parent.drawDefaultBackground();
	}
	@Override
	protected int getContentHeight() {
		return (this.getSize()) * 35 + 1;
	}
	@Override
	protected void drawSlot(int listIndex, int var2, int var3, int var4, Tessellator var5, int var6) {
		Waypoint  wp = waypoints.get(listIndex);
		EnumChatFormatting prefixName = EnumChatFormatting.RESET;
		if(!wp.isVisible()) prefixName = EnumChatFormatting.STRIKETHROUGH;
		
		this.parent.drawWaympointString(this.parent.fontRenderer.trimStringToWidth(prefixName + wp.getName(), listWidth - 10), 0, this.left, var3 + 2, wp.getColor(), this.top, this.bottom);
		this.parent.drawWaympointString(this.parent.fontRenderer.trimStringToWidth(prefixName + wp.getStrPosition(), listWidth - 10), 1, this.left, var3 + 2, 0xFFFFFF, this.top, this.bottom);
		this.parent.drawWaympointString(this.parent.fontRenderer.trimStringToWidth(prefixName + wp.getDistanceStr(parent.player) + "   Dimension : " +  wp.getDimension(), listWidth - 10), 2, this.left, var3 + 2, 0xFFFFFF, this.top, this.bottom);
		
		this.parent.drawCheckDimension(wp, this.left, var3 + 2, this.top, this.bottom, var6);
	}
	@Override
	protected void drawButtons(int listIndex, int var2, int var3, int var4, Tessellator var5, int var6) {
		this.parent.updateButtons(this.left, var3 + 2, this.top, this.bottom, var6);
	}
	@Override
	public void hideButtons() {
		disableButtons();
		
		this.parent.btni_teleport.visible = false;
		this.parent.btni_delete.visible = false;
		this.parent.btni_visibility.visible = false;
		this.parent.btni_modify.visible = false;
	}
	@Override
	public void enableButtons() {
		this.parent.btni_teleport.enabled = true;
		this.parent.btni_delete.enabled = true;
		this.parent.btni_visibility.enabled = true;
		this.parent.btni_modify.enabled = true;
	}
	@Override
	public void disableButtons() {
		this.parent.btni_teleport.enabled = false;
		this.parent.btni_delete.enabled = false;
		this.parent.btni_visibility.enabled = false;
		this.parent.btni_modify.enabled = false;
	}
}