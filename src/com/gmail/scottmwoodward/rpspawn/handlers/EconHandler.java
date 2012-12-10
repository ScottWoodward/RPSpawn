package com.gmail.scottmwoodward.rpspawn.handlers;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.gmail.scottmwoodward.rpspawn.RPSpawn;

public class EconHandler {
    private static RPSpawn plugin;
    private static boolean useEcon;
    private static Economy econ;

    public EconHandler(RPSpawn plugin){
        EconHandler.plugin = plugin;
        setUseEcon(plugin.getConfig().getBoolean("economyenabled"));
    }

    public static boolean useEcon(){
        return useEcon;
    }

    public static boolean vaultInstalled(){
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static void setUseEcon(boolean use){
        useEcon = use;
    }

    public static boolean hasEnoughMoney(Player player, double cost){
        if(econ.getBalance(player.getName())>=cost){
            return true;
        }
        return false;
    }

    public static void takePayment(Player player, double cost){
        econ.withdrawPlayer(player.getName(), cost);
    }

    public static String getPluralCurrency(){
        return econ.currencyNamePlural();
    }

    public static String getSingularCurrency(){
        return econ.currencyNameSingular();
    }
}
