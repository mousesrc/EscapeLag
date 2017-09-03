package com.mcml.space.config;

import java.util.ArrayList;
import java.util.List;

public class Default { //Sotr你那个判定大小写我真的很难受
	
	public static List<String> NoCrowdedEntityTypeList(){
		List<String> NoCrowdedEntityTypeList = new ArrayList<String>();
		NoCrowdedEntityTypeList.add("ZOMBIE");
		NoCrowdedEntityTypeList.add("SKELETON");
		NoCrowdedEntityTypeList.add("SPIDER");
		NoCrowdedEntityTypeList.add("CREEPER");
		NoCrowdedEntityTypeList.add("SHEEP");
		NoCrowdedEntityTypeList.add("PIG");
		NoCrowdedEntityTypeList.add("CHICKEN");
		return NoCrowdedEntityTypeList;
	}
	
	public static List<String> PluginErrorMessageBlockerMessage(){
		List<String> NoCrowdedEntityTypeList = new ArrayList<String>();
		NoCrowdedEntityTypeList.add("ErrorPluginName");
		NoCrowdedEntityTypeList.add("ErrorPluginMessage");
		return NoCrowdedEntityTypeList;
	}
	
	public static List<String> EntityClearClearEntityType(){
		List<String> NoCrowdedEntityTypeList = new ArrayList<String>();
		NoCrowdedEntityTypeList.add("ZOMBIE");
		NoCrowdedEntityTypeList.add("SKELETON");
		NoCrowdedEntityTypeList.add("SPIDER");
		NoCrowdedEntityTypeList.add("CREEPER");
		NoCrowdedEntityTypeList.add("SHEEP");
		NoCrowdedEntityTypeList.add("PIG");
		NoCrowdedEntityTypeList.add("CHICKEN");
		return NoCrowdedEntityTypeList;
	}
	
	public static List<String> AntiRedstoneRemoveBlockList(){
		List<String> AntiRedstoneRemoveBlockList = new ArrayList<String>();
		AntiRedstoneRemoveBlockList.add("REDSTONE_WIRE");
		AntiRedstoneRemoveBlockList.add("DIODE_BLOCK_ON");
		AntiRedstoneRemoveBlockList.add("DIODE_BLOCK_OFF");
		AntiRedstoneRemoveBlockList.add("REDSTONE_TORCH_ON");
		AntiRedstoneRemoveBlockList.add("REDSTONE_TORCH_OFF");
		AntiRedstoneRemoveBlockList.add("REDSTONE_BLOCK");
		return AntiRedstoneRemoveBlockList;
	}
	
	public static List<String> nscExcludeWorlds(){
		List<String> nscExcludeWorlds = new ArrayList<String>();
		nscExcludeWorlds.add("world");
		nscExcludeWorlds.add("world_nether");
		nscExcludeWorlds.add("world_the_end");
		return nscExcludeWorlds;
	}
	
	public static List<String> AntiSpamDirtyList(){
		List<String> AntiSpamDirtyList = new ArrayList<String>();
		AntiSpamDirtyList.add("智障");
		AntiSpamDirtyList.add("傻逼");
		AntiSpamDirtyList.add("妈逼");
		return AntiSpamDirtyList;
	}

}
