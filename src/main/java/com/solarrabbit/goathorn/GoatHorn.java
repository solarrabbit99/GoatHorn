/*
 *  This file is part of GoatHorn. Copyright (c) 2021 SolarRabbit.
 *
 *  ColorBundles is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  ColorBundles is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with ColorBundles. If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.solarrabbit.goathorn;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class GoatHorn extends JavaPlugin {
    private boolean hasItemsAdder;

    @Override
    public void onEnable() {
        this.hasItemsAdder = this.getServer().getPluginManager().getPlugin("ItemsAdder") != null;
        if (this.hasItemsAdder) {
            getServer().getConsoleSender().sendMessage(
                    ChatColor.AQUA + "[ColorBundles] ItemsAdder detected! Waiting for ItemsAdder to load items...");
        } else {
            getServer().getConsoleSender().sendMessage(ChatColor.GOLD
                    + "[ColorBundles] Ignore the error message regarding plugin failing to register ItemsAdder's events if you do not have ItemsAdder installed...");
        }

        if (!this.getDataFolder().exists()) {
            try {
                this.getDataFolder().mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        saveDefaultConfig();
    }

    public boolean hasItemsAdder() {
        return this.hasItemsAdder;
    }
}
