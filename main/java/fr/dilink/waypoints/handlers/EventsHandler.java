package fr.dilink.waypoints.handlers;

import net.minecraftforge.client.event.RenderWorldLastEvent;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.dilink.waypoints.WaypointsMod;
import fr.dilink.waypoints.gui.GuiIGNotif;
import fr.dilink.waypoints.threads.ThreadLogin;

public class EventsHandler
{
	@SubscribeEvent
	public void onRenderWorldLast(RenderWorldLastEvent event) {
		for (int i = 0; i < WaypointsMod.proxy.waypoints.size(); i++) WaypointsMod.proxy.waypoints.get(i).draw(event);
	}
	/*@SubscribeEvent
	public void onInitGuiScreen(GuiScreenEvent.InitGuiEvent e) {
		if(e.gui instanceof GuiMainMenu) e.buttonList.add(new GuiButton(310, 5, 5, 20, 20, "WP"));
	}
	@SubscribeEvent
	public void onDrawGuiScreen(GuiScreenEvent.DrawScreenEvent e) {
		if(e.gui instanceof GuiIngameMenu) e.gui.drawString(e.gui.mc.fontRenderer, "This is a Sample text !", 10, 10, 0xff00ff);
	}
	@SubscribeEvent
	public void onClickGuiScreen(GuiScreenEvent.ActionPerformedEvent e) {}*/

	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent e) {
		WaypointsMod.proxy.waypoints.clear();
	}
	@SubscribeEvent
	public void onConnectToServer(FMLNetworkEvent.ClientConnectedToServerEvent e) {
		WaypointsMod.proxy.connected = true;
		WaypointsMod.proxy.multiplayer = !e.isLocal;
		String fullAddr = e.manager.getSocketAddress().toString();
		String[] addr = fullAddr.split("/");
		if(addr.length > 1) {			
			WaypointsMod.proxy.hostname = addr[0];
			String[] ip_port = addr[1].split(":");
			WaypointsMod.proxy.ip = ip_port[0];
			WaypointsMod.proxy.port = ip_port[1];
			//Util.logger.info("Host:"+WaypointsMod.proxy.hostname+" IP:"+WaypointsMod.proxy.ip+" Port:"+WaypointsMod.proxy.port);
		}/* else {
			Util.logger.info("Localhost");
		}*/
		/*if(FMLClientHandler.instance().getServer() != null && FMLClientHandler.instance().getServer().getEntityWorld() != null) { //CLIENT
			Util.logger.info("");
			Util.logger.info(FMLClientHandler.instance().getServer().getEntityWorld().getWorldInfo().getWorldName());
			Util.logger.info(FMLClientHandler.instance().getServer().getEntityWorld().getWorldInfo().getVanillaDimension());
			Util.logger.info(FMLClientHandler.instance().getServer().getWorldName());
			Util.logger.info("");
		} else { //SERVER
			Util.logger.info("");
			Util.logger.error("WORLD MANQUANT !!");
			Util.logger.info("");
		}*/
		Thread thread_login = new ThreadLogin();
		thread_login.start();
	}
	@SubscribeEvent
	public void onDisconnectFromServer(FMLNetworkEvent.ClientDisconnectionFromServerEvent e) {
		WaypointsMod.proxy.connected = false;
	}
	@SubscribeEvent
	public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent e) {
		WaypointsMod.proxy.waypoints.clear();
	}
	//int prevDimension = 644688469;
	//int dimension = 0;
	/*@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent e) {
		if (e.phase == TickEvent.Phase.START) {
			if(FMLClientHandler.instance().getClient().thePlayer != null) {
				this.dimension = FMLClientHandler.instance().getClient().thePlayer.dimension;
			}
		}
	}*/


	@SideOnly(Side.CLIENT)
	static GuiIGNotif guiIGNotif;
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderTick(TickEvent.RenderTickEvent event) {
		if(guiIGNotif == null) {
			guiIGNotif = new GuiIGNotif(FMLClientHandler.instance().getClient());
		}
		guiIGNotif.updateGui();
	}
	@SideOnly(Side.CLIENT)
	public static GuiIGNotif getGuiIGNotif() {
		return guiIGNotif;
	}
}