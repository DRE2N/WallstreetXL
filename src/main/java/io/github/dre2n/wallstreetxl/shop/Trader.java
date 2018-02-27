/*
 * Copyright (C) 2017-2018 Daniel Saukel
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
package io.github.dre2n.wallstreetxl.shop;

import de.erethon.commons.chat.MessageUtil;
import io.github.dre2n.wallstreetxl.WallstreetXL;
import io.github.dre2n.wallstreetxl.config.WMessage;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

/**
 * @author Daniel Saukel
 */
public class Trader implements Listener {

    public static final String WXL_META = "WXL-Shop";

    public static Villager createTrader(OfflinePlayer owner, Location location, String shopName) {
        Villager villager = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);
        villager.setProfession(Villager.Profession.NITWIT);
        villager.setAI(false);
        villager.setMetadata(WXL_META, new FixedMetadataValue(WallstreetXL.getInstance(), shopName));
        villager.setCustomName(WMessage.TRADER_NAME.getMessage(owner != null ? owner.getName() : "Admin Shop"));
        WallstreetXL.getInstance().getTraderCache().add(villager);
        return villager;
    }

    public static boolean isTrader(Entity entity) {
        if (!(entity instanceof Villager)) {
            return false;
        }
        return entity.hasMetadata(WXL_META);
    }

    public static Shop getShopByTrader(Entity entity) {
        if (isTrader(entity)) {
            return WallstreetXL.getInstance().getShopCache().getByName(entity.getMetadata(WXL_META).get(0).asString());
        } else {
            return null;
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Entity villager = event.getRightClicked();
        if (!isTrader(villager)) {
            return;
        }
        event.setCancelled(true);
        Shop shop = getShopByTrader(villager);
        if (shop == null) {
            villager.remove();
            MessageUtil.log(WallstreetXL.getInstance(), "Invalid trader found and removed!");
            return;
        }
        shop.getGUI().open(event.getPlayer());
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity villager = event.getEntity();
        if (!isTrader(villager)) {
            return;
        }
        Shop shop = getShopByTrader(villager);
        if (shop instanceof PlayerShop) {
            shop.delete();
            for (ShopItem item : shop.getItems()) {
                villager.getWorld().dropItem(villager.getLocation(), item.getItem());
            }
        }
        WallstreetXL.getInstance().getTraderCache().remove((Villager) villager);
    }

}
