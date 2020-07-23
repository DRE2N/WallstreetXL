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
package de.erethon.wallstreetxl.shop;

import de.erethon.commons.misc.SimpleDateUtil;
import de.erethon.commons.player.PlayerUtil;
import de.erethon.vignette.api.PaginatedInventoryGUI;
import de.erethon.vignette.api.layout.PaginatedFlowInventoryLayout;
import de.erethon.vignette.api.layout.PaginatedInventoryLayout.PaginationButtonPosition;
import de.erethon.wallstreetxl.WallstreetXL;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author Daniel Saukel
 */
public class PlayerShop extends Shop {

    private static final String PLACEHOLDER = "&4 | &7";

    private UUID owner;

    public PlayerShop(File file) {
        this.file = file;
        config = YamlConfiguration.loadConfiguration(file);
        owner = UUID.fromString(config.getString("owner"));
        name = file.getName().replace(YAML, "");
        title = ChatColor.translateAlternateColorCodes('&', config.getString("title"));
        items = ShopItem.deserializeList((List<Map<String, Object>>) config.getList("items"));
        gui = new PaginatedInventoryGUI(title);
        gui.setLayout(new PaginatedFlowInventoryLayout(gui, 54, PaginationButtonPosition.BOTTOM));
        items.forEach(i -> gui.add(i.getButton()));
        Location location = (Location) config.get("traderLocation");
        if (location != null) {
            villager = Trader.createTrader(Bukkit.getOfflinePlayer(owner), location, name);
        }
    }

    public PlayerShop(String name, Player owner, String title) {
        file = new File(WallstreetXL.PLAYER_SHOPS, name + YAML);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
        this.owner = owner.getUniqueId();
        this.name = name;
        this.title = ChatColor.translateAlternateColorCodes('&', title);
        gui = new PaginatedInventoryGUI(this.title);
        gui.setLayout(new PaginatedFlowInventoryLayout(gui, 54, PaginationButtonPosition.BOTTOM));
        villager = Trader.createTrader(owner, owner.getLocation(), name);
        save();
    }

    public void log(long timeMillis, Player player, ItemStack item, String returns) {
        String pre = "log." + timeMillis + '.';
        config.set(pre + "player", player.getUniqueId().toString());
        config.set(pre + "good", ShopItem.getItemName(item));
        config.set(pre + "amount", item.getAmount());
        config.set(pre + "returns", returns);
    }

    public List<String> readLog() {
        ArrayList<String> logList = new ArrayList<>();
        ConfigurationSection logs = config.getConfigurationSection("log");
        if (logs != null) {
            for (String d : logs.getKeys(false)) {
                String pre = "log." + d + '.';
                String player = PlayerUtil.getNameFromUniqueId(config.getString(pre + "player"));
                String good = config.getString(pre + "good");
                String amount = config.getString(pre + "amount");
                String returns = config.getString(pre + "returns");
                String date = d;
                try {
                    date = SimpleDateUtil.ddMMyyyy(Long.parseLong(d));
                } catch (NumberFormatException exception) {
                };
                logList.add(returns + PLACEHOLDER + date + PLACEHOLDER + amount + "x " + good + PLACEHOLDER + player);
            }
        }
        return logList;
    }

    /* Getters and setters */
    public UUID getOwner() {
        return owner;
    }

    public void setOwner(OfflinePlayer owner) {
        this.owner = owner.getUniqueId();
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    /* Persistence */
    @Override
    public void delete() {
        super.delete();
        if (villager != null) {
            villager.remove();
        }
    }

    @Override
    public void serialize() {
        super.serialize();
        config.set("owner", owner.toString());
    }

}
