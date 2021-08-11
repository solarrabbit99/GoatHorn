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

import java.util.Collection;
import java.util.Iterator;
import com.solarrabbit.goathorn.event.BlockDispenseIronHorseArmorEvent;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.level.EntityGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.phys.AABB;

public class CustomDispenseBehavior extends DefaultDispenseItemBehavior {
    private final DispenseItemBehavior defaultBehavior;

    public CustomDispenseBehavior(DispenseItemBehavior defaultBehavior) {
        this.defaultBehavior = defaultBehavior;
    }

    @Override
    public net.minecraft.world.item.ItemStack execute(BlockSource isourceblock,
            net.minecraft.world.item.ItemStack itemstack) {
        Block block = isourceblock.getLevel().getWorld().getBlockAt(isourceblock.getPos().getX(),
                isourceblock.getPos().getY(), isourceblock.getPos().getZ());
        CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack);

        BlockPos blockposition = isourceblock.getPos()
                .shift(((StateHolder<?, ?>) isourceblock.getBlockState()).getValue(DispenserBlock.FACING));
        Collection<AbstractHorse> list = ((EntityGetter) isourceblock.getLevel())
                .getEntitiesOfClass(AbstractHorse.class, new AABB(blockposition), (entityhorseabstract) -> {
                    return ((Entity) entityhorseabstract).isAlive() && entityhorseabstract.canWearArmor();
                });
        Iterator<AbstractHorse> iterator1 = list.iterator();

        AbstractHorse entityhorseabstract;
        do {
            if (!iterator1.hasNext()) {
                return super.execute(isourceblock, itemstack);
            }
            entityhorseabstract = iterator1.next();
        } while (!entityhorseabstract.isArmor(itemstack) || entityhorseabstract.isWearingArmor()
                || !entityhorseabstract.isTamed());

        BlockDispenseIronHorseArmorEvent event = new BlockDispenseIronHorseArmorEvent(block, craftItem.clone(),
                (CraftLivingEntity) ((Entity) entityhorseabstract).getBukkitEntity());

        if (!DispenserBlock.eventFired) {
            ((Level) isourceblock.getLevel()).getCraftServer().getPluginManager().callEvent(event);
        }

        if (event.isCancelled()) {
            return super.execute(isourceblock, itemstack);
        }

        return defaultBehavior.dispense(isourceblock, itemstack);
    }
}
