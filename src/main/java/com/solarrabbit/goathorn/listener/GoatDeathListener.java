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
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Goat;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class GoatDeathListener implements Listener {
    private GoatHorn plugin;

    public GoatDeathListener(GoatHorn plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onGoatDeath(EntityDeathEvent evt) {
        if (evt.getEntityType() != EntityType.GOAT || !this.plugin.getConfig().getBoolean("loot.enabled"))
            return;
        if (!((Goat) evt.getEntity()).isAdult() && !this.plugin.getConfig().getBoolean("loot.baby"))
            return;

        int dropAmount = getDroppedAmount(this.plugin.getConfig().getDouble("loot.drop-rate"));
        Location location = evt.getEntity().getLocation();
        for (int i = 0; i < dropAmount; i++) {
            location.getWorld().dropItem(location, this.plugin.getItem());
        }
    }

    private int getDroppedAmount(double rate) {
        int intValue = (int) rate;
        intValue += Math.random() <= rate % 1 ? 1 : 0;
        return Math.max(intValue, 0);
    }
}
