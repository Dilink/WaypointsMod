package fr.dilink.waypoints;

import java.io.File;

import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import fr.dilink.waypoints.handlers.EventsHandler;
import fr.dilink.waypoints.packets.PacketSendWaypoint;
import fr.dilink.waypoints.proxy.CommonProxy;
import fr.dilink.waypoints.util.SaveUtil;
import fr.dilink.waypoints.util.Util;

@Mod(modid = Util.MOD_ID, name = Util.MOD_NAME, version = Util.MOD_VERSION)
public class WaypointsMod
{
	@Mod.Instance(Util.MOD_ID)
	public static WaypointsMod instance;

	@SidedProxy(clientSide = "fr.dilink.waypoints.proxy.ClientProxy", serverSide = "fr.dilink.waypoints.proxy.CommonProxy")
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void PreInit(FMLPreInitializationEvent event) {
		this.proxy.configurationDir = event.getModConfigurationDirectory().getParentFile();
		File file = new File(this.proxy.configurationDir, Util.waypointsFile);
		if(!file.exists()) SaveUtil.writeDefaultSaveNBT();
		this.proxy.network = NetworkRegistry.INSTANCE.newSimpleChannel(Util.MOD_ID);
		this.proxy.network.registerMessage(PacketSendWaypoint.Handler.class, PacketSendWaypoint.class, 0, Side.CLIENT);
	}
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		this.proxy.loadClientThings();
		MinecraftForge.EVENT_BUS.register(new EventsHandler());
		FMLCommonHandler.instance().bus().register(new EventsHandler());
	}
}