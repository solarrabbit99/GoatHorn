package com.solarrabbit.goathorn.listener;

import com.solarrabbit.goathorn.GoatHorn;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
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
