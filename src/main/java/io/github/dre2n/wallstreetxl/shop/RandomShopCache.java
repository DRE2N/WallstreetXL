/*
 * Copyright (C) 2017 Daniel Saukel
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

import io.github.dre2n.commons.misc.FileUtil;
import io.github.dre2n.wallstreetxl.WallstreetXL;
import io.github.dre2n.wallstreetxl.util.PageGUI;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * @author Daniel Saukel
 */
public class RandomShopCache implements Listener {

    private Set<RandomShop> shops = new HashSet<>();

    public RandomShopCache(File folder) {
        Bukkit.getPluginManager().registerEvents(this, WallstreetXL.getInstance());
        FileUtil.getFilesForFolder(folder).forEach(f -> shops.add(new RandomShop(f)));
    }

    public Set<RandomShop> getRandomShops() {
        return shops;
    }

    public RandomShop getByName(String name) {
        for (RandomShop shop : shops) {
            if (shop.getName().equalsIgnoreCase(name)) {
                return shop;
            }
        }
        return null;
    }

    public RandomShop getByInventory(Inventory inventory) {
        for (RandomShop shop : shops) {
            if (shop.getGUI().getPages().contains(inventory)) {
                return shop;
            }
        }
        return null;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();
        if (inventory == null) {
            return;
        }
        RandomShop shop = getByInventory(inventory);
        if (shop == null) {
            return;
        }
        event.setCancelled(true);
        PageGUI.playSound(event);
        ItemStack button = inventory.getItem(event.getSlot());
        if (button == null) {
            return;
        }
        ShopItem item = null;
        for (ShopItem shopItem : shop.getItems()) {
            if (shopItem.getButton().equals(button)) {
                item = shopItem;
            }
        }
        if (item == null) {
            return;
        }
        item.deal(player);
    }

}
