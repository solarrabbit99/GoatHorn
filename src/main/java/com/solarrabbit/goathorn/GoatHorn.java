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

package com.solarrabbit.goathorn;

import java.util.Optional;
import com.solarrabbit.goathorn.command.GiveItem;
import com.solarrabbit.goathorn.command.ReloadConfig;
import com.solarrabbit.goathorn.listener.GoatDeathListener;
import com.solarrabbit.goathorn.listener.HornUseListener;
import com.solarrabbit.goathorn.listener.HorseArmorEquipListener;
import com.solarrabbit.goathorn.listener.SmeltingListener;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;

public final class GoatHorn extends JavaPlugin implements Listener {
    private boolean hasItemsAdder;
    private ItemStack sampleHorn;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        getCommand("ghreload").setExecutor(new ReloadConfig(this));
        getCommand("ghgive").setExecutor(new GiveItem(this));

        PluginManager manager = getServer().getPluginManager();
        this.hasItemsAdder = this.getServer().getPluginManager().getPlugin("ItemsAdder") != null;
        if (this.hasItemsAdder) {
            getServer().getConsoleSender().sendMessage(
                    ChatColor.AQUA + "[GoatHorn] ItemsAdder detected! Waiting for ItemsAdder to load items...");
            manager.registerEvents(this, this);
        } else {
            this.loadItem();
        }

        manager.registerEvents(new SmeltingListener(this), this);
        manager.registerEvents(new HorseArmorEquipListener(this),this);
        manager.registerEvents(new GoatDeathListener(this), this);
        manager.registerEvents(new HornUseListener(this), this);
    }

    @EventHandler
    public void onItemsLoadEvent(ItemsAdderLoadDataEvent evt) {
        loadItem();
    }

    public ItemStack getItem() {
        return this.sampleHorn.clone();
    }

    public boolean isHorn(ItemStack item) {
        if (this.hasItemsAdder) {
            CustomStack stack = CustomStack.byItemStack(item);
            return stack == null ? false : stack.getNamespacedID().equals("goathorn:goathorn");
        } else {
            return Optional.ofNullable(item).map(ItemStack::getItemMeta).filter(ItemMeta::hasCustomModelData)
                    .map(ItemMeta::getCustomModelData).filter(i -> i.equals(this.getConfig().getInt("model-data")))
                    .isPresent();
        }
    }

    private void loadItem() {
        if (this.sampleHorn != null)
            return;
        if (this.hasItemsAdder) {
            this.sampleHorn = CustomStack.getInstance("goathorn:goathorn").getItemStack();
        } else {
            this.sampleHorn = new ItemStack(Material.IRON_HORSE_ARMOR);
            ItemMeta meta = this.sampleHorn.getItemMeta();
            meta.setCustomModelData(this.getConfig().getInt("model-data"));
            this.sampleHorn.setItemMeta(meta);
        }
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[GoatHorn] Loaded item!");
    }
}
