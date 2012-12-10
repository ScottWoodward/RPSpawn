package com.gmail.scottmwoodward.rpspawn.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.gmail.scottmwoodward.rpspawn.RPSpawn;
import com.gmail.scottmwoodward.rpspawn.handlers.ConfigHandler;
import com.gmail.scottmwoodward.rpspawn.handlers.EconHandler;
import com.gmail.scottmwoodward.rpspawn.helpers.InventoryHelper;
import com.gmail.scottmwoodward.rpspawn.helpers.StringBuilder;

public class SpawnCommand{

    public static void execute(RPSpawn plugin, Player player){
        if(RPSpawn.getCooldownList().contains(player.getName())){
            player.sendMessage(StringBuilder.buildString(plugin.getConfig().getString("cooldownmessage"),player.getName(),ChatColor.AQUA));
            return;
        }
        if(EconHandler.useEcon()){
            if(!EconHandler.hasEnoughMoney(player, plugin.getConfig().getDouble("currencycost"))){
                player.sendMessage(StringBuilder.buildString(plugin.getConfig().getString("insufficientfundsmessage"),player.getName(),ChatColor.AQUA));
                return;
            }
        }
        if(ConfigHandler.getInt("itemcost")>0){
            if(!InventoryHelper.hasComponents(player,ConfigHandler.getInt("itemid"),ConfigHandler.getInt("itemcost"))){
                player.sendMessage(StringBuilder.buildString(plugin.getConfig().getString("insufficientcomponentssmessage"),player.getName(),ChatColor.AQUA));
                return;
            }
        }

        player.sendMessage(StringBuilder.buildString(plugin.getConfig().getString("castmessage"), player.getName(),ChatColor.AQUA));;
        double maxDist = plugin.getConfig().getDouble("localcastdistance");
        if(maxDist>0 && plugin.getConfig().getBoolean("castingtonearbyplayers")){
            for (Player other : Bukkit.getOnlinePlayers()) {
                if(other.getWorld().equals(player.getWorld())){
                    if (other.getLocation().distance(player.getLocation()) <= maxDist && !other.equals(player)) {
                        other.sendMessage(StringBuilder.buildString(plugin.getConfig().getString("externalcast"),player.getName(),ChatColor.BLUE));
                    }
                }
            }
        }


        TeleportTask task = new TeleportTask(plugin,player);
        task.setID(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, task, 20L, 20L));
    }

}