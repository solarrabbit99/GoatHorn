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

package com.solarrabbit.goathorn.event;

import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

/**
 * Encapsulates an event where a dispenser is about to equip a horse with an
 * iron horse armor. If cancelled, the relevant iron horse armor will be
 * dispensed as an item.
 */
public class BlockDispenseIronHorseArmorEvent extends BlockDispenseEvent {
    private final LivingEntity target;

    public BlockDispenseIronHorseArmorEvent(Block block, ItemStack dispensed, LivingEntity target) {
        super(block, dispensed, new Vector(0, 0, 0));
        this.target = target;
    }

    /**
     * Get the horse entity that the dispenser is targetting, for which the iron
     * horse armor will equip on if the event is not cancelled.
     * 
     * @return target horse entity
     */
    public LivingEntity getTargetEntity() {
        return this.target;
    }
}
