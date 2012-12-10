package com.gmail.scottmwoodward.rpspawn;

import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.scottmwoodward.rpspawn.handlers.CommandHandler;
import com.gmail.scottmwoodward.rpspawn.handlers.ConfigHandler;
import com.gmail.scottmwoodward.rpspawn.handlers.EconHandler;

public class RPSpawn extends JavaPlugin{
    private static Queue<String> cooldownList = new LinkedList<String>();

    public void onEnable(){
        getCommand("spawn").setExecutor(new CommandHandler(this));
        this.saveDefaultConfig();
        new ConfigHandler(this);
        new EconHandler(this);
        if(EconHandler.useEcon()){
            if(!EconHandler.vaultInstalled()){
                getLogger().info("Vault missing, disabling economy functions.");
                EconHandler.setUseEcon(false);
            }
        }
    }

    public static Queue<String> getCooldownList(){
        return cooldownList;
    }
}
