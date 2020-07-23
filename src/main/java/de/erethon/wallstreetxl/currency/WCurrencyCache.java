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
package de.erethon.wallstreetxl.currency;

import com.greatmancode.craftconomy3.Common;
import com.greatmancode.craftconomy3.currency.Currency;
import com.greatmancode.craftconomy3.currency.CurrencyManager;
import de.erethon.wallstreetxl.WallstreetXL;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * @author Daniel Saukel
 */
public class WCurrencyCache {

    Common cc3 = WallstreetXL.getInstance().getCraftConomy();
    CurrencyManager manager = cc3.getCurrencyManager();

    private Set<WCurrency> currencies = new HashSet<>();
    private WCurrency main;

    public WCurrencyCache(File file) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        for (String name : manager.getCurrencyNames()) {
            Currency currency = manager.getCurrency(name);
            if (currency == null) {
                continue;
            }
            WCurrency wCurrency = new WCurrency(currency, config.getDouble(name + ".standard"), config.getDouble(name + ".max"),
                    config.getDouble(name + ".min"), config.getDouble(name + ".fluctuations"), config.getDouble(name + ".development"));
            currencies.add(wCurrency);
            if (config.getBoolean(name + ".isMain")) {
                main = wCurrency;
            }
        }
    }

    public Set<WCurrency> getCurrencies() {
        return currencies;
    }

    public WCurrency getMain() {
        return main;
    }

    public WCurrency getByName(String name) {
        for (WCurrency currency : currencies) {
            if (currency.getName().equalsIgnoreCase(name)) {
                return currency;
            }
        }
        return null;
    }

}
