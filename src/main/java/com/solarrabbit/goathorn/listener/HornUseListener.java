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

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

// TODO
public class HornUseListener implements Listener {
    public HornUseListener(Plugin plugin) {

    }

    @EventHandler
    public void onHornUse(PlayerInteractEvent evt) {
        if (evt.getItem() == null || evt.getItem().getType() != Material.IRON_HORSE_ARMOR)
            return;

    }

    // private boolean hasHornInBothHand(Player player) {

    // }

    // @Override
    // public void onPacketReceiving(PacketEvent event) {
    // if (event.getPacketType() == Server.NAMED_SOUND_EFFECT ||
    // event.getPacketType() == Server.ENTITY_SOUND) {
    // List<SoundCategory> lst = event.getPacket().getSoundCategories().getValues();
    // lst.forEach(category -> plugin.getLogger().info(event.getPacketType() + ": "
    // + category));

    // List<Sound> soundlst = event.getPacket().getSoundEffects().getValues();
    // soundlst.forEach(sound -> plugin.getLogger().info(event.getPacketType() + ":
    // " + sound));
    // } else {
    // List<ItemStack> lst = event.getPacket().getItemModifier().getValues();
    // lst.forEach(item -> plugin.getLogger().info(event.getPacketType() + ": " +
    // item.getType()));
    // }
    // }
}
