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

package com.solarrabbit.goathorn.listener;

import com.solarrabbit.goathorn.GoatHorn;
import com.solarrabbit.goathorn.event.BlockDispenseIronHorseArmorEvent;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class HorseArmorEquipListener implements Listener {
    private GoatHorn plugin;

    public HorseArmorEquipListener(GoatHorn plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryEquip(InventoryClickEvent evt) {
        Inventory top = evt.getView().getTopInventory();
        if (!(top instanceof HorseInventory))
            return;
        Inventory clicked = evt.getClickedInventory();
        if (clicked == null)
            return;

        if (evt.isShiftClick()) {
            if (clicked.equals(top) || top.getItem(1) != null) {
                return;
            } else {
                ItemStack item = evt.getCurrentItem();
                cancelIfHorn(evt, item);
            }
        } else if ((evt.getAction() == InventoryAction.HOTBAR_SWAP
                || evt.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD) && clicked.equals(top)) {
            int button = evt.getHotbarButton();
            ItemStack item = evt.getView().getBottomInventory().getItem(button);
            cancelIfHorn(evt, item);
        } else if (clicked.equals(top)) {
            ItemStack item = evt.getCursor();
            cancelIfHorn(evt, item);
        }
    }

    @EventHandler
    public void onDispenseArmor(BlockDispenseIronHorseArmorEvent evt) {
        if (this.plugin.isHorn(evt.getItem()))
            evt.setCancelled(true);
    }

    private void cancelIfHorn(Cancellable evt, ItemStack item) {
        if (item != null && this.plugin.isHorn(item)) {
            evt.setCancelled(true);
            return;
        }
    }
}
