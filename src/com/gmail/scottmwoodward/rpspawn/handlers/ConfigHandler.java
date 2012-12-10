package com.gmail.scottmwoodward.rpspawn.handlers;

import com.gmail.scottmwoodward.rpspawn.RPSpawn;

public class ConfigHandler {
    private static RPSpawn plugin;

    public ConfigHandler(RPSpawn plugin){
        ConfigHandler.plugin = plugin;
    }
    public static double getDouble(String label){
        return plugin.getConfig().getDouble(label);
    }
    public static String getDoubleAsString(String label){
        return String.valueOf(plugin.getConfig().getDouble(label));
    }
    public static String getString(String label){
        return plugin.getConfig().getString(label);
    }
    public static int getInt(String label){
        return plugin.getConfig().getInt(label);
    }
    public static String getIntAsString(String label){
        return String.valueOf(plugin.getConfig().getInt(label));
    }
}
