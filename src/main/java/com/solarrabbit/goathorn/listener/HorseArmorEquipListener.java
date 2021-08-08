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
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
    public void onArmorEquip(InventoryClickEvent evt) {
        Inventory inventory = evt.getView().getTopInventory();
        if (!(inventory instanceof HorseInventory))
            return;

        if (evt.isShiftClick()) {
            ItemStack item = evt.getCurrentItem();
            // TODO shift-clicking with armor slot already occupied should be allowed
            if (item != null && this.plugin.isHorn(item)) {
                evt.setCancelled(true);
                return;
            }
        } else if (evt.getClickedInventory().equals(inventory)) {
            ItemStack item = evt.getCursor();
            if (item != null && this.plugin.isHorn(item)) {
                evt.setCancelled(true);
                return;
            }
        }
    }
}
