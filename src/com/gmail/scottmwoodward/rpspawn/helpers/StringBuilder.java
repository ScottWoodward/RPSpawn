package com.gmail.scottmwoodward.rpspawn.helpers;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.gmail.scottmwoodward.rpspawn.handlers.ConfigHandler;
import com.gmail.scottmwoodward.rpspawn.handlers.EconHandler;

import java.lang.Character;

public class StringBuilder {
    public static String buildString(String message, String sender, ChatColor color){
        message = message.replace("%cc", ConfigHandler.getDoubleAsString("currencycost"));
        message = message.replace("%cd", ConfigHandler.getDoubleAsString("cooldown"));
        message = message.replace("%ic", ConfigHandler.getIntAsString("itemcost"));
        message = message.replace("%sn", ConfigHandler.getString("spellname"));
        if(EconHandler.useEcon()){
            if(ConfigHandler.getDouble("currencycost")==(double)1){
                message = message.replace("%ct", EconHandler.getSingularCurrency());
            }
            else{
                message = message.replace("%ct", EconHandler.getPluralCurrency());
            }
        }
        else{
            message = message.replace("%ct", "Coins");
        }
        message = message.replace("%c", ConfigHandler.getIntAsString("casttime"));
        char[] item = Material.getMaterial(ConfigHandler.getInt("itemid")).name().toLowerCase().toCharArray();
        Character.toUpperCase(item[0]);        

        message = message.replace("%im", new String(item));
        message = message.replace("%p", sender);
        return color+message;
    }
}
