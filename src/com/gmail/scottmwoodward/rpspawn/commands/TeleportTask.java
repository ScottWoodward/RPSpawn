package com.gmail.scottmwoodward.rpspawn.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.Effect;

import com.gmail.scottmwoodward.rpspawn.RPSpawn;
import com.gmail.scottmwoodward.rpspawn.handlers.ConfigHandler;
import com.gmail.scottmwoodward.rpspawn.handlers.EconHandler;
import com.gmail.scottmwoodward.rpspawn.helpers.InventoryHelper;
import com.gmail.scottmwoodward.rpspawn.helpers.StringBuilder;

public class TeleportTask implements Runnable{

    private RPSpawn plugin;
    private Player player;
    private int castTime;
    private Location loc;
    private int id;
    private Location spawn;

    public TeleportTask(RPSpawn plugin, Player player){
        super();
        this.plugin = plugin;
        this.player = player;
        this.loc = player.getLocation();
        this.castTime = ConfigHandler.getInt("casttime");
        this.spawn = new Location(player.getWorld(),player.getWorld().getSpawnLocation().getX(),player.getWorld().getSpawnLocation().getY(),player.getWorld().getSpawnLocation().getZ());
    }

    public void setID(int id){
        this.id = id;
    }

    public void run(){
        for(int i=0;i<8;i++){
            player.getWorld().playEffect(player.getLocation(), Effect.SMOKE, i);
        }
        spawn.getChunk().load();
        if(player.getLocation().distance(loc)>1){
            player.sendMessage(StringBuilder.buildString(plugin.getConfig().getString("interruptmessage"),player.getName(),ChatColor.AQUA));
            Bukkit.getScheduler().cancelTask(id);
        }
        if(castTime>0){
            if(plugin.getConfig().getBoolean("casttime")){
                player.sendMessage(String.valueOf(castTime));
            }
            castTime--;
        }
        else{
            if(EconHandler.useEcon()){
                if(EconHandler.hasEnoughMoney(player, plugin.getConfig().getDouble("currencycost"))){
                    EconHandler.takePayment(player, plugin.getConfig().getDouble("currencycost"));
                }
                else{
                    player.sendMessage(StringBuilder.buildString(plugin.getConfig().getString("insufficientfundsmessage"),player.getName(),ChatColor.AQUA));
                    Bukkit.getScheduler().cancelTask(id);
                    return;
                }
            }

            if(ConfigHandler.getInt("itemcost")>0){
                if(!InventoryHelper.hasComponents(player, ConfigHandler.getInt("itemid"), ConfigHandler.getInt("itemcost"))){
                    player.sendMessage(StringBuilder.buildString(plugin.getConfig().getString("insufficientcomponentssmessage"),player.getName(),ChatColor.AQUA));
                    Bukkit.getScheduler().cancelTask(id);
                    return;
                }
                else{
                    InventoryHelper.removeComponents(player, ConfigHandler.getInt("itemid"), ConfigHandler.getInt("itemcost"));
                }
            }

            player.teleport(spawn);
            player.sendMessage(StringBuilder.buildString(plugin.getConfig().getString("completionmessage"),player.getName(),ChatColor.AQUA));
            RPSpawn.getCooldownList().add(player.getName());
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
                public void run(){
                    RPSpawn.getCooldownList().remove(player.getName());
                    player.sendMessage(StringBuilder.buildString(plugin.getConfig().getString("cooldowncompletemessage"),player.getName(),ChatColor.AQUA));
                }
            }, (long)(ConfigHandler.getDouble("cooldown")*20));
            Bukkit.getScheduler().cancelTask(id);
        }


    }

}
