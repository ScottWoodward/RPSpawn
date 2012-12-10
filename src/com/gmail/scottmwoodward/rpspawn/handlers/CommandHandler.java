package com.gmail.scottmwoodward.rpspawn.handlers;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.scottmwoodward.rpspawn.RPSpawn;
import com.gmail.scottmwoodward.rpspawn.commands.SpawnCommand;
import com.gmail.scottmwoodward.rpspawn.helpers.StringBuilder;

public class CommandHandler implements CommandExecutor{

    private RPSpawn plugin;

    public CommandHandler(RPSpawn plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            if(args.length > 0){
                sender.sendMessage(StringBuilder.buildString(plugin.getConfig().getString("usagemessage"),((Player) sender).getName(),ChatColor.AQUA));
            }
            else{
                SpawnCommand.execute(plugin,(Player)sender);
            }
        }
        else{
            sender.sendMessage("You must be logged in as player to teleport to spawn");
        }
        return true;
    }

}
