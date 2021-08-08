/*
 *  This file is part of GoatHorn. Copyright (c) 2021 SolarRabbit.
 *
 *  GoatHorn is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  GoatHorn is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with GoatHorn. If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.solarrabbit.goathorn.command;

import com.solarrabbit.goathorn.GoatHorn;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GiveItem implements CommandExecutor {
    private GoatHorn plugin;

    public GiveItem(GoatHorn plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            return false;
        }
        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null) {
            sender.sendMessage(ChatColor.RED + "Cannot find specified player!");
            return false;
        }

        if (args.length < 2) {
            this.giveItems(sender, targetPlayer, 1);
            return true;
        } else {
            int amount = getPositiveInteger(args[1]);
            if (amount <= 0) {
                sender.sendMessage(ChatColor.RED + "Invalid input amount of items...");
                return false;
            } else {
                giveItems(sender, targetPlayer, amount);
                return true;
            }
        }
    }

    private void giveItems(CommandSender requester, Player receiver, int requestAmount) {
        Inventory inventory = receiver.getInventory();
        if (getInventoryFreeSlots(inventory) < requestAmount) {
            requester.sendMessage(ChatColor.RED + "Player does not have enough inventory space!");
        } else {
            for (int i = 0; i < requestAmount; i++) {
                inventory.addItem(this.plugin.getItem());
            }
            requester.sendMessage(ChatColor.GREEN + "Gave " + receiver.getName() + " " + requestAmount + " goat horns");
        }
    }

    private int getInventoryFreeSlots(Inventory inventory) {
        int freeSlots = 0;
        for (int i = 0; i < 36; i++) {
            if (inventory.getItem(i) == null) {
                freeSlots++;
            }
        }
        return freeSlots;
    }

    private int getPositiveInteger(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
