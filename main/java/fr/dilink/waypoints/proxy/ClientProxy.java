package fr.dilink.waypoints.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import fr.dilink.waypoints.handlers.KeyHandler;

public class ClientProxy extends CommonProxy
{
	@Override
	public void loadClientThings() {
		FMLCommonHandler.instance().bus().register(new KeyHandler());
	}
}