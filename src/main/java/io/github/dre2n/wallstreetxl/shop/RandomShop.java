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

import io.github.dre2n.wallstreetxl.WallstreetXL;
import io.github.dre2n.wallstreetxl.util.PageGUI;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * @author Daniel Saukel
 */
public class RandomShop {

    public static final String YAML = ".yml";

    private String name;
    private File file;
    private FileConfiguration config;
    private String title;
    private List<ShopItem> items = new ArrayList<>();
    private PageGUI gui;

    public RandomShop(File file) {
        this.file = file;
        config = YamlConfiguration.loadConfiguration(file);
        name = file.getName().replace(YAML, new String());
        title = ChatColor.translateAlternateColorCodes('&', config.getString("title"));
        items = ShopItem.deserializeList((List<Map<String, Object>>) config.getList("items"));
        gui = new PageGUI(title);
        items.forEach(i -> gui.addButton(i.getButton()));
    }

    public RandomShop(String name, String title) {
        file = new File(WallstreetXL.SHOPS, name + YAML);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
        this.name = name;
        this.title = ChatColor.translateAlternateColorCodes('&', title);
        gui = new PageGUI(title);
        save();
    }

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
        gui.addButton(item.getButton());
    }

    public void removeItem(ShopItem item) {
        items.remove(item);
        gui.clear();
        items.forEach(i -> gui.addButton(i.getButton()));
    }

    public PageGUI getGUI() {
        return gui;
    }

    /* Persistence */
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
    }

}
