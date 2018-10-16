package com.gmail.berndivader.speechbubblesaddon;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.berndivader.speechbubblesaddon.healthbar.HealthbarHandler;

import io.lumine.xikage.mythicmobs.MythicMobs;

public 
class 
SpeechBubblesAddon
extends
JavaPlugin {
	private static SpeechBubblesAddon plugin;
	public static PluginManager pluginmanager;
	public static MythicMobs mythicmobs;
	public static HealthbarHandler healthbarhandler;
	public static Logger logger;
	
	
	static {
		pluginmanager=Bukkit.getPluginManager();
		mythicmobs=(MythicMobs)pluginmanager.getPlugin("MythicMobs");
		logger=Bukkit.getLogger();
	}
	
	public static SpeechBubblesAddon getPlugin() {
		return plugin;
	}
	
	
	@Override
	public void onEnable() {
		healthbarhandler=new HealthbarHandler(this);
	}
	
	@Override
	public void onDisable() {
		if (healthbarhandler!=null) {
			healthbarhandler.removeHealthbars();
			healthbarhandler.removeSpeechBubbles();
		}
		pluginmanager.disablePlugin(this);
	}	
	

}
