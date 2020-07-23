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
package de.erethon.wallstreetxl;

import com.greatmancode.craftconomy3.Common;
import com.greatmancode.craftconomy3.tools.interfaces.Loader;
import de.erethon.commons.compatibility.Internals;
import de.erethon.commons.javaplugin.DREPlugin;
import de.erethon.commons.javaplugin.DREPluginSettings;
import de.erethon.commons.misc.FileUtil;
import de.erethon.commons.misc.Registry;
import de.erethon.wallstreetxl.command.WCommandCache;
import de.erethon.wallstreetxl.config.WConfig;
import de.erethon.wallstreetxl.currency.WCurrencyCache;
import de.erethon.wallstreetxl.integration.FactionsXLIntegration;
import de.erethon.wallstreetxl.shop.AdminShop;
import de.erethon.wallstreetxl.shop.PlayerShop;
import de.erethon.wallstreetxl.shop.Shop;
import de.erethon.wallstreetxl.shop.Trader;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.entity.Villager;

/**
 * @author Daniel Saukel
 */
public class WallstreetXL extends DREPlugin {

    private static WallstreetXL instance;
    private Common cc3;

    public static File CURRENCIES;
    public static File ADMIN_SHOPS;
    public static File PLAYER_SHOPS;

    private WConfig wConfig;
    private WCommandCache wCommands;
    private WCurrencyCache wCurrencies;
    private Registry<String, Shop> shops;
    private Set<Villager> traderCache = new HashSet<>();

    public WallstreetXL() {
        settings = DREPluginSettings.builder()
                .economy(true)
                .internals(Internals.INDEPENDENT)
                .build();
        CURRENCIES = new File(getDataFolder(), "currencies.yml");
        ADMIN_SHOPS = new File(getDataFolder(), "adminShops");
        PLAYER_SHOPS = new File(getDataFolder(), "playerShops");
    }

    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;
        loadCore();
    }

    @Override
    public void onDisable() {
        clearTraderCache();
    }

    public void loadCore() {
        loadCraftConomy();
        loadWConfig();
        loadCurrencies();
        loadShops();
        loadWCommands();
        manager.registerEvents(new Trader(), this);
        if (manager.getPlugin("FactionsXL") != null) {
            manager.registerEvents(new FactionsXLIntegration(), this);
        }
    }

    public static WallstreetXL getInstance() {
        return instance;
    }

    public Common getCraftConomy() {
        return cc3;
    }

    public void loadCraftConomy() {
        cc3 = (Common) ((Loader) manager.getPlugin("Craftconomy3")).getCommon();
    }

    public WConfig getWConfig() {
        return wConfig;
    }

    public void loadWConfig() {
        wConfig = new WConfig(new File(getDataFolder(), "config.yml"));
    }

    @Override
    public WCommandCache getCommandCache() {
        return wCommands;
    }

    public void loadWCommands() {
        wCommands = new WCommandCache(this);
        wCommands.register(this);
    }

    public WCurrencyCache getCurrencyCache() {
        return wCurrencies;
    }

    public void loadCurrencies() {
        if (!CURRENCIES.exists()) {
            try {
                CURRENCIES.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        wCurrencies = new WCurrencyCache(CURRENCIES);
    }

    public Registry<String, Shop> getShopCache() {
        return shops;
    }

    public void loadShops() {
        ADMIN_SHOPS.mkdir();
        PLAYER_SHOPS.mkdir();
        shops = new Registry<>();
        FileUtil.getFilesForFolder(ADMIN_SHOPS).forEach(f -> {
            Shop shop = new AdminShop(f);
            shops.add(shop.getName(), shop);
        });
        FileUtil.getFilesForFolder(PLAYER_SHOPS).forEach(f -> {
            Shop shop = new PlayerShop(f);
            shops.add(shop.getName(), shop);
        });
    }

    public Set<Villager> getTraderCache() {
        return traderCache;
    }

    public void clearTraderCache() {
        for (Villager villager : traderCache) {
            villager.remove();
        }
        traderCache.clear();
    }

}
