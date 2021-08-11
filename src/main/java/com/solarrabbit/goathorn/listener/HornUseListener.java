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

import java.util.HashSet;
import java.util.UUID;
import com.solarrabbit.goathorn.GoatHorn;
import com.solarrabbit.goathorn.event.PlayerBlareHornEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class HornUseListener implements Listener {
    private HashSet<UUID> cooldowns;
    private GoatHorn plugin;

    public HornUseListener(GoatHorn plugin) {
        this.plugin = plugin;
        this.cooldowns = new HashSet<>();
    }

    @EventHandler
    public void onHornUse(PlayerInteractEvent evt) {
        if (evt.useItemInHand() == Result.DENY || evt.getAction() != Action.RIGHT_CLICK_AIR)
            return;
        ItemStack item = evt.getItem();
        if (!this.plugin.isHorn(item))
            return;
        Player player = evt.getPlayer();
        if (this.hasHornInBothHand(player) && evt.getHand() == EquipmentSlot.OFF_HAND)
            return;
        if (cooldowns.contains(player.getUniqueId()))
            return;

        PlayerBlareHornEvent event = new PlayerBlareHornEvent(player, item);
        Bukkit.getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            playSound(player);
            cooldown(player);
        }
    }

    private void playSound(Player player) {
        float volume = (float) this.plugin.getConfig().getDouble("horn-use.volume");
        float pitch = (float) this.plugin.getConfig().getDouble("horn-use.pitch");
        player.getWorld().playSound(player.getLocation(), Sound.EVENT_RAID_HORN, volume, pitch);
    }

    private void cooldown(Player player) {
        int cooldownTicks = this.plugin.getConfig().getInt("horn-use.cooldown.duration");
        if (this.plugin.getConfig().getBoolean("horn-use.cooldown.show-animation")) {
            player.setCooldown(Material.IRON_HORSE_ARMOR, cooldownTicks);
        }
        UUID uuid = player.getUniqueId();
        cooldowns.add(uuid);
        Bukkit.getScheduler().runTaskLater(this.plugin, () -> this.cooldowns.remove(uuid), cooldownTicks);
    }

    private boolean hasHornInBothHand(Player player) {
        EntityEquipment equipment = player.getEquipment();
        ItemStack main = equipment.getItemInMainHand();
        ItemStack off = equipment.getItemInOffHand();
        return this.plugin.isHorn(main) && this.plugin.isHorn(off);
    }
}
