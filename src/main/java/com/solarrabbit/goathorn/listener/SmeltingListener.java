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
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceStartSmeltEvent;

public class SmeltingListener implements Listener {
    private GoatHorn plugin;

    public SmeltingListener(GoatHorn plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSmelt(FurnaceStartSmeltEvent evt) {
        if (!this.plugin.isHorn(evt.getSource()))
            return;
        evt.setTotalCookTime(0);
    }

    @EventHandler
    public void onBurn(FurnaceBurnEvent evt) {
        Furnace furnace = (Furnace) evt.getBlock().getState();
        if (this.plugin.isHorn(furnace.getSnapshotInventory().getSmelting()))
            evt.setCancelled(true);
    }
}
