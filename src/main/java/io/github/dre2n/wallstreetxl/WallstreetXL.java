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
package io.github.dre2n.wallstreetxl;

import com.greatmancode.craftconomy3.Common;
import com.greatmancode.craftconomy3.tools.interfaces.Loader;
import io.github.dre2n.commons.compatibility.Internals;
import io.github.dre2n.commons.config.MessageConfig;
import io.github.dre2n.commons.javaplugin.DREPlugin;
import io.github.dre2n.commons.javaplugin.DREPluginSettings;
import io.github.dre2n.wallstreetxl.command.WCommandCache;
import io.github.dre2n.wallstreetxl.config.WConfig;
import io.github.dre2n.wallstreetxl.config.WMessage;
import io.github.dre2n.wallstreetxl.currency.WCurrencyCache;
import io.github.dre2n.wallstreetxl.shop.RandomShopCache;
import io.github.dre2n.wallstreetxl.util.PageGUICache;
import java.io.File;
import java.io.IOException;
import org.bukkit.event.HandlerList;

/**
 * @author Daniel Saukel
 */
public class WallstreetXL extends DREPlugin {

    private static WallstreetXL instance;
    private Common cc3;

    public static File CURRENCIES;
    public static File SHOPS;

    private WConfig wConfig;
    private MessageConfig messageConfig;
    private PageGUICache pageGUIs;
    private WCommandCache wCommands;
    private WCurrencyCache wCurrencies;
    private RandomShopCache shops;

    public WallstreetXL() {
        /*
         * ##########################
         * ####~BRPluginSettings~####
         * ##########################
         * #~Internals~##INDEPENDENT#
         * #~SpigotAPI~##~~~false~~~#
         * #~~~~UUID~~~##~~~false~~~#
         * #~~Economy~~##~~~true~~~~#
         * #Permissions##~~~false~~~#
         * #~~Metrics~~##~~~false~~~#
         * #Resource ID##~~~~????~~~#
         * ##########################
         */

        settings = new DREPluginSettings(false, false, true, false, false, Internals.INDEPENDENT);
        CURRENCIES = new File(getDataFolder(), "currencies.yml");
        SHOPS = new File(getDataFolder(), "shops");
    }

    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;
        loadCore();
    }

    public void loadCore() {
        loadCraftConomy();
        loadWConfig();
        loadMessageConfig();
        loadPageGUIs();
        loadCurrencies();
        loadShops();
        loadWCommands();
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

    public MessageConfig getMessageConfig() {
        return messageConfig;
    }

    public void loadMessageConfig() {
        messageConfig = new MessageConfig(WMessage.class, new File(getDataFolder(), "language.yml"));
    }

    public PageGUICache getPageGUICache() {
        return pageGUIs;
    }

    public void loadPageGUIs() {
        if (pageGUIs != null) {
            HandlerList.unregisterAll(pageGUIs);
        }
        pageGUIs = new PageGUICache();
        manager.registerEvents(pageGUIs, this);
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

    public RandomShopCache getShopCache() {
        return shops;
    }

    public void loadShops() {
        if (!SHOPS.exists()) {
            SHOPS.mkdir();
        }
        shops = new RandomShopCache(SHOPS);
    }

}
