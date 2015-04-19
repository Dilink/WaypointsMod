package fr.dilink.waypoints.handlers;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import fr.dilink.waypoints.gui.GuiWaypoints;
import fr.dilink.waypoints.util.Util;

public class KeyHandler
{
	/** Key index for easy handling */
	private static int baseID = 0;
	public static final int
	GUI_WAYPOINTS = baseID++;
	/** Key descriptions; use a language file to localize the description later */
	private static final String[] desc = {
		"key." + Util.MOD_ID + ".openWaypoints.desc"
	};
	/** Default key values */
	private static final int[] keyValues = {
		Keyboard.KEY_0
	};
	private final KeyBinding[] keys;

	public KeyHandler() {
		keys = new KeyBinding[desc.length];
		for (int i = 0; i < desc.length; ++i) {
			keys[i] = new KeyBinding(desc[i], keyValues[i], Util.MOD_NAME);
			ClientRegistry.registerKeyBinding(keys[i]);
		}
	}
	/**
	 * KeyInputEvent is in the FML package, so we must register to the FML event bus
	 */
	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {
		// FMLClientHandler.instance().getClient().inGameHasFocus
		if (!FMLClientHandler.instance().isGUIOpen(GuiChat.class)) {
			EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
			if (keys[GUI_WAYPOINTS].isPressed()) {
				FMLClientHandler.instance().displayGuiScreen(FMLClientHandler.instance().getClient().thePlayer, new GuiWaypoints(FMLClientHandler.instance().getClient().thePlayer));
			}
		}
	}
}