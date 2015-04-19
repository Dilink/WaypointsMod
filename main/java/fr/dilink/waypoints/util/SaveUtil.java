package fr.dilink.waypoints.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import fr.dilink.waypoints.WaypointsMod;
import fr.dilink.waypoints.handlers.Waypoint;

public class SaveUtil
{
	/*public static final String data_array = "waypoints";
	public static final String data_name = "name";
	public static final String data_posX = "posX";
	public static final String data_posY = "posY";
	public static final String data_posZ = "posZ";
	public static final String data_visibility = "visible";
	public static final String data_color = "color";
	public static final String data_ssp = "SSP";
	public static final String data_smp = "SMP";*/

	/*public static void writeNBTFile() {
		try {
			File file = new File(WaypointsMod.instance.configurationDir, WaypointsMod.waypointsFile);
			if(!file.exists()) file.createNewFile();
			FileOutputStream fileoutputstream = new FileOutputStream(file);
			NBTTagCompound nbtTagCompound = new NBTTagCompound();
			NBTTagCompound nbtArray;
			NBTTagList nbtTagList = new NBTTagList();
			nbtTagCompound.setTag(data_array, nbtTagList);
			CompressedStreamTools.writeCompressed(nbtTagCompound, fileoutputstream);
			fileoutputstream.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}*/
	//public static void addToNBTFile(Waypoint wp) {
		/*try {
			File file = new File(WaypointsMod.instance.configurationDir, WaypointsMod.waypointsFile);
			if(!file.exists()) writeNBTFile();
			FileOutputStream fileoutputstream = new FileOutputStream(file);
			NBTTagCompound nbtTagCompound = new NBTTagCompound();
			NBTTagCompound nbtArray;
			NBTTagList nbtTagList = new NBTTagList();
			if(nbtTagCompound.hasKey(data_array)) {
				NBTTagList nbttaglist = nbtTagCompound.getTagList(data_array, 10);
				for (int k = 0; k < nbttaglist.tagCount(); ++k) {
		            NBTTagCompound nbt = nbttaglist.getCompoundTagAt(k);
					//nbtTagCompound.setTag(data_array, nbtTagList);
		            NBTBase t = nbtTagCompound.copy();
		            nbtTagList.appendTag(t);
		        }
			}
			nbtArray = new NBTTagCompound();
			nbtArray.setString(data_name, wp.getName());
			nbtArray.setDouble(data_posX, wp.getX());
			nbtArray.setDouble(data_posY, wp.getY());
			nbtArray.setDouble(data_posZ, wp.getZ());
			nbtArray.setInteger(data_color, wp.getColor());
			nbtTagList.appendTag(nbtArray);
			nbtTagCompound.setTag(data_array, nbtTagList);
			CompressedStreamTools.writeCompressed(nbtTagCompound, fileoutputstream);
			WaypointsMod.instance.waypoints.add(wp);
			fileoutputstream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		/*WaypointsMod.instance.waypoints.add(wp);
		try {
			File file = new File(WaypointsMod.instance.configurationDir, WaypointsMod.waypointsFile);
			if(!file.exists()) writeNBTFile();
			FileOutputStream fileoutputstream = new FileOutputStream(file);
			NBTTagList nbttaglist = new NBTTagList();
			Iterator iterator = WaypointsMod.instance.waypoints.iterator();
			while (iterator.hasNext()) {
				Waypoint waypointData = (Waypoint)iterator.next();
				nbttaglist.appendTag(waypointData.getNBTCompound());
			}
			NBTTagCompound nbtTagCompound = new NBTTagCompound();
			WorldInfo world = FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld().getWorldInfo();
			long seed = world.getSeed();
			nbtTagCompound.setTag(Long.toString(seed), nbttaglist);
			CompressedStreamTools.writeCompressed(nbtTagCompound, fileoutputstream);
		} catch (IOException e) {
			Util.logger.error("Couldn\'t save waypoints list : " +  e);
		}
	}*/
	/*public static void deleteFromNBT(Waypoint selectedWP) {
		for (int i = 0; i < WaypointsMod.instance.waypoints.size(); i++) {
			if(WaypointsMod.instance.waypoints.get(i).equals(selectedWP)) {
				WaypointsMod.instance.waypoints.remove(i);

				try {
					File file = new File(WaypointsMod.instance.configurationDir, WaypointsMod.waypointsFile);
					if(!file.exists()) writeNBTFile();
					FileOutputStream fileoutputstream = new FileOutputStream(file);
					NBTTagList nbttaglist = new NBTTagList();
					Iterator iterator = WaypointsMod.instance.waypoints.iterator();
					while (iterator.hasNext()) {
						Waypoint waypointData = (Waypoint)iterator.next();
						nbttaglist.appendTag(waypointData.getNBTCompound());
					}
					NBTTagCompound nbtTagCompound = new NBTTagCompound();
					WorldInfo world = FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld().getWorldInfo();
					long seed = world.getSeed();
					nbtTagCompound.setTag(Long.toString(seed), nbttaglist);
					CompressedStreamTools.writeCompressed(nbtTagCompound, fileoutputstream);
				} catch (IOException e) {
					Util.logger.error("Couldn\'t save waypoints list : " +  e);
				}
			}
		}
	}*/
	/*public static void testNBTFile() {
		WaypointsMod.instance.waypoints.add(new Waypoint("Test", 0, 80, 0, 0xfff000));
		try {
			File file = new File(WaypointsMod.instance.configurationDir, WaypointsMod.waypointsFile);
			if(!file.exists()) writeNBTFile();
			FileOutputStream fileoutputstream = new FileOutputStream(file);
			NBTTagList nbttaglist = new NBTTagList();
			Iterator iterator = WaypointsMod.instance.waypoints.iterator();
			while (iterator.hasNext()) {
				Waypoint waypointData = (Waypoint)iterator.next();
				nbttaglist.appendTag(waypointData.getNBTCompound());
			}
			NBTTagCompound nbtTagCompound = new NBTTagCompound();
			//nbtTagCompound.setTag(data_array, nbttaglist);

			NBTTagCompound tag = new NBTTagCompound();
			tag.setString(data_name, "Name");
			tag.setDouble(data_posX, 19.5);
			tag.setDouble(data_posY, 81.5);
			tag.setDouble(data_posZ, 0.5);
			tag.setBoolean(data_visibility, true);
			tag.setInteger(data_color, -9337864);

			NBTTagCompound ssp = new NBTTagCompound();
			NBTTagCompound smp = new NBTTagCompound();

			NBTTagList list = new NBTTagList();

			list.appendTag(tag);
			list.appendTag(tag);

			ssp.setTag("WorldName1", list);
			ssp.setTag("WorldName2", list);

			smp.setTag("Host^Ip^Port1", list);
			smp.setTag("Host^Ip^Port2", list);

			//nbtTagCompound.setTag("data", tag);//p_i2157_1_.getCompoundTag("GameRules")

			nbtTagCompound.setTag(data_ssp, ssp);
			nbtTagCompound.setTag(data_smp, smp);

			CompressedStreamTools.writeCompressed(nbtTagCompound, fileoutputstream);
		} catch (IOException e) {
			Util.logger.error("Couldn\'t save waypoints list : " +  e);
		}
	}*/
	public static void readNBT() {
		String nodeName = WaypointsMod.proxy.getSaveNode();
		if(nodeName == null || nodeName.equals("")) return;
		String dirName;
		if(WaypointsMod.proxy.multiplayer)
			dirName = SaveUtil.DataType.data_smp.str();
		else
			dirName = SaveUtil.DataType.data_ssp.str();
		try {
			File file = new File(WaypointsMod.proxy.configurationDir, Util.waypointsFile);
			if(!file.exists()) writeDefaultSaveNBT();
			FileInputStream fileinputstream = new FileInputStream(file);
			NBTTagCompound nbtTagCompound = CompressedStreamTools.readCompressed(fileinputstream);

			if(nbtTagCompound.hasKey(dirName)) {
				NBTTagCompound dir = nbtTagCompound.getCompoundTag(dirName);
				if(dir.hasKey(nodeName)) {
					NBTTagList worldDir = dir.getTagList(nodeName, 10);
					WaypointsMod.proxy.waypoints.clear();
					for (int k = 0; k < worldDir.tagCount(); ++k) {
						NBTTagCompound nbt = worldDir.getCompoundTagAt(k);
						WaypointsMod.proxy.waypoints.add(new Waypoint(nbt.getString(SaveUtil.DataType.data_name.str()), nbt.getDouble(SaveUtil.DataType.data_posX.str()), nbt.getDouble(SaveUtil.DataType.data_posY.str()), nbt.getDouble(SaveUtil.DataType.data_posZ.str()), nbt.getBoolean(SaveUtil.DataType.data_visibility.str()), nbt.getInteger(SaveUtil.DataType.data_color.str()), nbt.getInteger(SaveUtil.DataType.data_dimension.str()), false));
					}
				} else {
					Util.logger.error("KEY " + nodeName + " NOT FOUND !");
				}
			} else {
				Util.logger.error("KEY " + dirName + " NOT FOUND !");
			}
			fileinputstream.close();
		} catch(Exception e) {
			Util.logger.error("Couldn\'t load waypoints list : " +  e);
		}
	}
	public static void writeDefaultSaveNBT() {
		try {
			File file = new File(WaypointsMod.proxy.configurationDir, Util.waypointsFile);
			if(!file.exists()) file.createNewFile();
			FileOutputStream fileoutputstream = new FileOutputStream(file);
			NBTTagCompound nbtTagCompound = new NBTTagCompound();

			NBTTagCompound ssp = new NBTTagCompound();
			NBTTagCompound smp = new NBTTagCompound();

			nbtTagCompound.setTag(SaveUtil.DataType.data_ssp.str(), ssp);
			nbtTagCompound.setTag(SaveUtil.DataType.data_smp.str(), smp);

			CompressedStreamTools.writeCompressed(nbtTagCompound, fileoutputstream);
		} catch (IOException e) {
			Util.logger.error("Couldn\'t save waypoints list : " +  e);
		}
	}
	public static void addToNBT(Waypoint wp) {
		String nodeName = WaypointsMod.proxy.getSaveNode();
		if(nodeName == null || nodeName.equals("")) return;
		String dirName;
		if(WaypointsMod.proxy.multiplayer)
			dirName = SaveUtil.DataType.data_smp.str();
		else
			dirName = SaveUtil.DataType.data_ssp.str();
		try {
			File file = new File(WaypointsMod.proxy.configurationDir, Util.waypointsFile);
			if(!file.exists()) writeDefaultSaveNBT();

			FileInputStream fileinputstream = new FileInputStream(file);
			NBTTagCompound nbtTagCompound = CompressedStreamTools.readCompressed(fileinputstream);
			fileinputstream.close();

			NBTTagCompound dir = nbtTagCompound.getCompoundTag(dirName);

			NBTTagCompound tag = new NBTTagCompound();
			tag.setString(SaveUtil.DataType.data_name.str(), wp.getName());
			tag.setDouble(SaveUtil.DataType.data_posX.str(), wp.getX());
			tag.setDouble(SaveUtil.DataType.data_posY.str(), wp.getY());
			tag.setDouble(SaveUtil.DataType.data_posZ.str(), wp.getZ());
			tag.setBoolean(SaveUtil.DataType.data_visibility.str(), wp.isVisible());
			tag.setInteger(SaveUtil.DataType.data_color.str(), wp.getColor());
			tag.setInteger(SaveUtil.DataType.data_dimension.str(), wp.getDimension());

			if(dir.hasKey(nodeName)) {
				NBTTagList worldDir = dir.getTagList(nodeName, 10);
				worldDir.appendTag(tag);
				//Util.logger.info("///////////////////// INPUT IN NBT : " + nodeName);
			} else {
				NBTTagList list = new NBTTagList();
				list.appendTag(tag);
				dir.setTag(nodeName, list);
				//Util.logger.error("///////////////////// KEY " + nodeName + " MANQUANTE !");
			}

			NBTTagCompound newCompound = new NBTTagCompound();
			newCompound.setTag(SaveUtil.DataType.data_ssp.str(), nbtTagCompound.getCompoundTag(SaveUtil.DataType.data_ssp.str()));
			newCompound.setTag(SaveUtil.DataType.data_smp.str(), nbtTagCompound.getCompoundTag(SaveUtil.DataType.data_smp.str()));
			newCompound.setTag(dirName, dir);
			
			WaypointsMod.proxy.waypoints.add(wp);

			FileOutputStream fileoutputstream = new FileOutputStream(file);
			CompressedStreamTools.writeCompressed(newCompound, fileoutputstream);
		} catch (IOException e) {
			Util.logger.error("Couldn\'t save waypoints list : " +  e);
		}
	}
	public static void removeFromNBT(Waypoint wp) {
		String nodeName = WaypointsMod.proxy.getSaveNode();
		if(nodeName == null || nodeName.equals("")) return;
		String dirName;
		if(WaypointsMod.proxy.multiplayer)
			dirName = SaveUtil.DataType.data_smp.str();
		else
			dirName = SaveUtil.DataType.data_ssp.str();
		try {
			File file = new File(WaypointsMod.proxy.configurationDir, Util.waypointsFile);
			if(!file.exists()) writeDefaultSaveNBT();

			FileInputStream fileinputstream = new FileInputStream(file);
			NBTTagCompound nbtTagCompound = CompressedStreamTools.readCompressed(fileinputstream);
			fileinputstream.close();
			
			NBTTagCompound dir = nbtTagCompound.getCompoundTag(dirName);

			if(dir.hasKey(nodeName)) {
				NBTTagList worldDir = dir.getTagList(nodeName, 10);
				for (int k = 0; k < worldDir.tagCount(); ++k) {
					NBTTagCompound nbt = worldDir.getCompoundTagAt(k);
					Waypoint wayp = new Waypoint(nbt.getString(SaveUtil.DataType.data_name.str()), nbt.getDouble(SaveUtil.DataType.data_posX.str()), nbt.getDouble(SaveUtil.DataType.data_posY.str()), nbt.getDouble(SaveUtil.DataType.data_posZ.str()), nbt.getBoolean(SaveUtil.DataType.data_visibility.str()), nbt.getInteger(SaveUtil.DataType.data_color.str()), nbt.getInteger(SaveUtil.DataType.data_dimension.str()), false);
					if(wayp.equals(wp)) {						
						worldDir.removeTag(k);
						continue;
					}
				}
			} else {
				Util.logger.error("KEY " + nodeName + " NOT FOUND !");
			}
			
			if(WaypointsMod.proxy.waypoints.contains(wp)) {
				for (int l = 0; l < WaypointsMod.proxy.waypoints.size(); l++) {
					if(WaypointsMod.proxy.waypoints.get(l).equals(wp)) {						
						WaypointsMod.proxy.waypoints.remove(l);
						continue;
					}
				}
			}

			NBTTagCompound newCompound = new NBTTagCompound();
			newCompound.setTag(SaveUtil.DataType.data_ssp.str(), nbtTagCompound.getCompoundTag(SaveUtil.DataType.data_ssp.str()));
			newCompound.setTag(SaveUtil.DataType.data_smp.str(), nbtTagCompound.getCompoundTag(SaveUtil.DataType.data_smp.str()));
			newCompound.setTag(dirName, dir);

			FileOutputStream fileoutputstream = new FileOutputStream(file);
			CompressedStreamTools.writeCompressed(newCompound, fileoutputstream);
		} catch (IOException e) {
			Util.logger.error("Couldn\'t save waypoints list : " +  e);
		}
	}
	public static void modifyNBT(Waypoint wp, DataType data, Object obj) {
		String nodeName = WaypointsMod.proxy.getSaveNode();
		if(nodeName == null || nodeName.equals("")) return;
		String dirName;
		if(WaypointsMod.proxy.multiplayer)
			dirName = SaveUtil.DataType.data_smp.str();
		else
			dirName = SaveUtil.DataType.data_ssp.str();
		try {
			File file = new File(WaypointsMod.proxy.configurationDir, Util.waypointsFile);
			if(!file.exists()) writeDefaultSaveNBT();

			FileInputStream fileinputstream = new FileInputStream(file);
			NBTTagCompound nbtTagCompound = CompressedStreamTools.readCompressed(fileinputstream);
			fileinputstream.close();
			
			NBTTagCompound dir = nbtTagCompound.getCompoundTag(dirName);
			Waypoint newWP = null;
			
			if(dir.hasKey(nodeName)) {
				NBTTagList worldDir = dir.getTagList(nodeName, 10);
				for (int k = 0; k < worldDir.tagCount(); ++k) {
					NBTTagCompound nbt = worldDir.getCompoundTagAt(k);
					newWP = new Waypoint(nbt.getString(SaveUtil.DataType.data_name.str()), nbt.getDouble(SaveUtil.DataType.data_posX.str()), nbt.getDouble(SaveUtil.DataType.data_posY.str()), nbt.getDouble(SaveUtil.DataType.data_posZ.str()), nbt.getBoolean(SaveUtil.DataType.data_visibility.str()), nbt.getInteger(SaveUtil.DataType.data_color.str()), nbt.getInteger(SaveUtil.DataType.data_dimension.str()), false);
					if(newWP.equals(wp)) {
						if(obj instanceof Integer) {
							nbt.setInteger(data.str(), (Integer) (obj));
						} else if(obj instanceof String) {
							nbt.setString(data.str(), (String) (obj));
						} else if(obj instanceof Double) {
							nbt.setDouble(data.str(), (Double) (obj));
						} else if(obj instanceof Boolean) {
							nbt.setBoolean(data.str(), (Boolean) (obj));
						}
						continue;
					}
				}
			} else {
				Util.logger.error("KEY " + nodeName + " NOT FOUND !");
			}
			
			NBTTagCompound newCompound = new NBTTagCompound();
			newCompound.setTag(SaveUtil.DataType.data_ssp.str(), nbtTagCompound.getCompoundTag(SaveUtil.DataType.data_ssp.str()));
			newCompound.setTag(SaveUtil.DataType.data_smp.str(), nbtTagCompound.getCompoundTag(SaveUtil.DataType.data_smp.str()));
			newCompound.setTag(dirName, dir);

			FileOutputStream fileoutputstream = new FileOutputStream(file);
			CompressedStreamTools.writeCompressed(newCompound, fileoutputstream);
			
			readNBT();
		} catch (IOException e) {
			Util.logger.error("Couldn\'t save waypoints list : " +  e);
		}
	}
	public enum DataType {
		data_array("waypoints"),
		data_name("name"),
		data_posX("posX"),
		data_posY("posY"),
		data_posZ("posZ"),
		data_visibility("visible"),
		data_color("color"),
		data_dimension("dimension"),
		data_ssp("SSP"),
		data_smp("SMP");
		private final String dataName;
		DataType(String dn) {
			this.dataName = dn;
		}
		@Override
		public String toString() {
			return String.valueOf(this.dataName);
		}
		public String str() {
			return String.valueOf(this.dataName);
		}
	}
}