package com.gmail.scottmwoodward.rpspawn.helpers;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryHelper {

    public static boolean hasComponents(Player player, int itemid, int itemnumber){
        Inventory inv = player.getInventory();
        ItemStack stack;
        int onHand = 0;
        if(inv.contains(itemid)){
            for(int i=0; i<inv.getSize();i++){
                if(inv.getItem(i)!=null){
                    stack = inv.getItem(i);

                    if(stack.getTypeId()==itemid){
                        onHand += stack.getAmount();
                    }
                }

            }
            if(onHand >= itemnumber){
                return true;
            }

        }
        return false;
    }

    public static void removeComponents(Player player, int itemid, int itemnumber){
        Inventory inv = player.getInventory();
        int toBeRemoved = itemnumber;
        for(int i=0;i<inv.getSize();i++){
            if(inv.getItem(i)!=null){
                if(inv.getItem(i).getTypeId()==itemid){
                    if(inv.getItem(i).getAmount()>itemnumber){
                        inv.getItem(i).setAmount(inv.getItem(i).getAmount()-itemnumber);
                        return;
                    }
                    else if(inv.getItem(i).getAmount()==itemnumber){
                        inv.clear(i);
                        return;
                    }
                    else{
                        toBeRemoved = toBeRemoved - inv.getItem(i).getAmount();
                        inv.clear(i);
                    }
                }
            }
        }
    }
}
