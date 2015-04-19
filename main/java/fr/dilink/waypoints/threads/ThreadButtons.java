package fr.dilink.waypoints.threads;

import fr.dilink.waypoints.gui.GuiWaypoints;

public class ThreadButtons extends Thread
{
	GuiWaypoints guiWaypoints;
	
	public ThreadButtons(GuiWaypoints gw) {
		guiWaypoints = gw;
	}
	
	@Override
	public void run() {
		try {
			this.guiWaypoints.waypointsList.disableButtons();
			Thread.sleep(100);
			this.guiWaypoints.waypointsList.enableButtons();
			if(this.guiWaypoints.selectedWP.getDimension() == -1)
				this.guiWaypoints.btni_teleport.enabled = false;
			Thread.currentThread().interrupt();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}