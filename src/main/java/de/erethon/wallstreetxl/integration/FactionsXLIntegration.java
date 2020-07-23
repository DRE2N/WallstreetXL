/*
 * Copyright (C) 2017-2020 Daniel Saukel
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.erethon.wallstreetxl.integration;

import de.erethon.factionsxl.FactionsXL;
import de.erethon.factionsxl.faction.Faction;
import de.erethon.factionsxl.protection.EntityProtectionListener;
import de.erethon.wallstreetxl.shop.PlayerShop;
import de.erethon.wallstreetxl.shop.Shop;
import de.erethon.wallstreetxl.shop.Trader;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;

/**
 * @author Daniel Saukel
 */
public class FactionsXLIntegration implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Player damager = EntityProtectionListener.getDamageSource(event.getDamager());
        if (!(damager instanceof Player)) {
            return;
        }
        Entity trader = event.getEntity();
        if (!Trader.isTrader(trader)) {
            return;
        }
        Shop shop = Trader.getShopByTrader(trader);
        if (!(shop instanceof PlayerShop)) {
            return;
        }
        Faction ownerFaction = FactionsXL.getInstance().getFactionCache().getByLocation(trader.getLocation());
        Faction damagerFaction = FactionsXL.getInstance().getFactionCache().getByMember(damager);
        if (!damagerFaction.isInWar(ownerFaction) && !ownerFaction.isAdmin(damager)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPotionSplash(PotionSplashEvent event) {
        for (Entity entity : event.getAffectedEntities()) {
            if (Trader.isTrader(entity)) {
                event.setIntensity(((LivingEntity) entity), 0);
            }
        }
    }

}
