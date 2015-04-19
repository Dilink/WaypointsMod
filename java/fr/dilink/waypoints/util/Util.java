package fr.dilink.waypoints.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Util
{
	public static final String MOD_ID = "waypoints_mod";
	public static final String MOD_NAME = "Waypoints";
	public static final String MOD_VERSION = "1.0";
	public static final String MOD_ASSETS = MOD_ID + ":";
	public static final String MOD_LANG = MOD_ID + ".";
	public static final String waypointsFile = "waypoints.dat";
	
	public static final Logger logger = LogManager.getLogger(Util.MOD_NAME);
	
	public static double[] getCenterOfBlock(double x, double y, double z) {        
        int a = (int) x;
        int b = (int) y;
        int c = (int) z;
        double xM = x >= 0 ? a + 0.5 : a - 0.5;
        double yM = y >= 0 ? b + 0.5 : b - 0.5;
        double zM = z >= 0 ? c + 0.5 : c - 0.5;
        return new double[] {xM, yM, zM};
	}
}