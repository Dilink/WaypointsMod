package fr.dilink.waypoints.threads;

import cpw.mods.fml.client.FMLClientHandler;
import fr.dilink.waypoints.WaypointsMod;
import fr.dilink.waypoints.util.SaveUtil;

public class ThreadLogin extends Thread
{
	@Override
	public void run() {
		try {
			while(FMLClientHandler.instance().getClient().thePlayer == null) {
				Thread.sleep(500);
			}
			WaypointsMod.proxy.dimension = FMLClientHandler.instance().getClient().thePlayer.dimension;
			SaveUtil.readNBT();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}