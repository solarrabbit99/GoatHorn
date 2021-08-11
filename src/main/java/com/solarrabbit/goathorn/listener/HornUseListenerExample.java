package com.solarrabbit.goathorn.listener;

import com.solarrabbit.goathorn.event.PlayerBlareHornEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class HornUseListenerExample implements Listener {
    @EventHandler
    public void onBlare(PlayerBlareHornEvent event) {
        Bukkit.broadcastMessage("event cancelling");
        event.setCancelled(true);
    }
}
