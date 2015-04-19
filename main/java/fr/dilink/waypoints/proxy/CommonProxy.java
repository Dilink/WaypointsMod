package fr.dilink.waypoints.proxy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import fr.dilink.waypoints.WaypointsMod;
import fr.dilink.waypoints.handlers.Waypoint;

public class CommonProxy
{
	public int dimension = 0;
	
	public boolean multiplayer;
	public boolean connected = false;
	private String worldName;

	public String hostname;
	public String ip;
	public String port;

	public File configurationDir;
	public List<Waypoint> waypoints = new ArrayList<Waypoint>();
	public static SimpleNetworkWrapper network;

	public void loadClientThings() {};

	public String getSaveNode() {
		if(WaypointsMod.proxy.connected) {
			if(WaypointsMod.proxy.multiplayer) {
				return this.hostname+this.ip+this.port;
			} else {
				if(FMLClientHandler.instance().getServer().getEntityWorld() != null)
					return FMLClientHandler.instance().getServer().getEntityWorld().getWorldInfo().getWorldName();
				return "";
			}
		}
		return "";
	}
	public void createSphereCallList() {}
}