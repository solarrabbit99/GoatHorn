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
        if (evt.getItem().getType() != Material.IRON_HORSE_ARMOR)
            return;

    }

    // private boolean hasHornInBothHand() {

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
