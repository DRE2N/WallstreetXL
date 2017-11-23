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

import io.github.dre2n.wallstreetxl.util.PageGUI;
import java.util.List;

/**
 * @author Daniel Saukel
 */
public interface Shop {

    /* Getters and setters */
    public String getName();

    public String getTitle();

    public void setTitle(String title);

    public List<ShopItem> getItems();

    public void addItem(ShopItem item);

    public void removeItem(ShopItem item);

    public PageGUI getGUI();

    /* Persistence */
    public void delete();

    public void save();

    public void serialize();

}
