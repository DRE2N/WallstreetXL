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

import de.erethon.vignette.api.PaginatedInventoryGUI;
import de.erethon.wallstreetxl.WallstreetXL;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Villager;

/**
 * @author Daniel Saukel
 */
public abstract class Shop {

    public static final String YAML = ".yml";

    protected File file;
    protected FileConfiguration config;
    protected String name;
    protected String title;
    protected List<ShopItem> items = new ArrayList<>();
    protected Villager villager;
    protected PaginatedInventoryGUI gui;

    /* Getters and setters */
    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ShopItem> getItems() {
        return items;
    }

    public void addItem(ShopItem item) {
        items.add(item);
        gui.add(item.getButton());
        save();
    }

    public void removeItem(ShopItem item) {
        items.remove(item);
        gui.clear();
        items.forEach(i -> gui.add(i.getButton()));
        save();
    }

    public Villager getVillager() {
        return villager;
    }

    public void setVillager(Villager villager) {
        this.villager = villager;
    }

    public PaginatedInventoryGUI getGUI() {
        return gui;
    }

    /* Persistence */
    public void delete() {
        WallstreetXL.getInstance().getShopCache().remove(this);
        file.delete();
    }

    public void save() {
        serialize();
        try {
            config.save(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void serialize() {
        config.set("title", title);
        config.set("items", ShopItem.serializeList(items));
        if (villager != null) {
            config.set("traderLocation", villager.getLocation());
        }
    }

}
