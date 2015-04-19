package fr.dilink.waypoints.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import fr.dilink.waypoints.WaypointsMod;
import fr.dilink.waypoints.gui.GuiIGNotif;
import fr.dilink.waypoints.handlers.EventsHandler;
import fr.dilink.waypoints.handlers.Waypoint;
import fr.dilink.waypoints.util.Util;

public class PacketSendWaypoint implements IMessage
{
	private String name;
	private Double posX;
	private Double posY;
	private Double posZ;
	private boolean visible;
	private int color;
	private int dimensionID;

	public PacketSendWaypoint() {}
	public PacketSendWaypoint(Waypoint wp) {
		this.name = wp.getName();
		this.posX = wp.getX();
		this.posY = wp.getY();
		this.posZ = wp.getZ();
		this.visible = wp.isVisible();
		this.color = wp.getColor();
		this.dimensionID = wp.getDimension();
	}
	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, this.name);
		buf.writeDouble(this.posX);
		buf.writeDouble(this.posY);
		buf.writeDouble(this.posZ);
		buf.writeBoolean(this.visible);
		buf.writeInt(this.color);
		buf.writeInt(this.dimensionID);
	}
	@Override
	public void fromBytes(ByteBuf buf) {
		this.name = ByteBufUtils.readUTF8String(buf);
		this.posX = buf.readDouble();
		this.posY = buf.readDouble();
		this.posZ = buf.readDouble();
		this.visible = buf.readBoolean();
		this.color = buf.readInt();
		this.dimensionID = buf.readInt();
	}
	public static class Handler implements IMessageHandler<PacketSendWaypoint, IMessage> {
		@Override
		public IMessage onMessage(PacketSendWaypoint message, MessageContext ctx) {
			Util.logger.info(String.format("New Waypoint received : %s %s %s %s %s %s %s", message.name, message.posX, message.posY, message.posZ, message.visible, message.color, message.dimensionID));
			WaypointsMod.proxy.waypoints.add(new Waypoint(message.name, message.posX, message.posY, message.posZ, message.visible, message.color, message.dimensionID, true));
			EventsHandler.getGuiIGNotif().queueHatUnlocked("chooseJob", "test", new ItemStack(Blocks.acacia_stairs), GuiIGNotif.textureAchievement);
			return null; // no response in this case
		}
	}
}