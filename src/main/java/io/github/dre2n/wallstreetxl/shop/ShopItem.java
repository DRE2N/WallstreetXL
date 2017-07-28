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

import com.greatmancode.craftconomy3.Cause;
import com.greatmancode.craftconomy3.account.Account;
import io.github.dre2n.commons.chat.MessageUtil;
import io.github.dre2n.wallstreetxl.WallstreetXL;
import io.github.dre2n.wallstreetxl.config.WMessage;
import io.github.dre2n.wallstreetxl.currency.WCurrency;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author Daniel Saukel
 */
public class ShopItem implements ConfigurationSerializable {

    WallstreetXL plugin = WallstreetXL.getInstance();

    private ItemStack item;
    private WCurrency currency;
    private double price;
    private boolean buy;

    public ShopItem(ItemStack item, WCurrency currency, double price, boolean buy) {
        this.item = item;
        this.currency = currency;
        this.price = price;
        this.buy = buy;
    }

    public ShopItem(Map<String, Object> map) {
        item = (ItemStack) map.get("item");
        currency = plugin.getCurrencyCache().getByName((String) map.get("currency"));
        price = (double) map.get("price");
        buy = (boolean) map.get("buy");
    }

    /* Getters and setters */
    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public ItemStack getButton() {
        ItemStack button = item.clone();
        ItemMeta meta = button.getItemMeta();
        assert meta != null : "Meta is null";
        List<String> lore = null;
        if (meta.hasLore()) {
            lore = meta.getLore();
        } else {
            lore = new ArrayList<>();
        }
        ChatColor color = (buy ? ChatColor.DARK_RED : ChatColor.GREEN);
        lore.add(color + (buy ? WMessage.SHOP_BUY : WMessage.SHOP_SELL).getMessage());
        lore.add(color + format(currency, price));
        meta.setLore(lore);
        button.setItemMeta(meta);
        return button;
    }

    public WCurrency getCurrency() {
        return currency;
    }

    public void setCurrency(WCurrency currency) {
        this.currency = currency;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isBuy() {
        return buy;
    }

    /* Actions */
    public boolean deal(Player player) {
        Account account = plugin.getCraftConomy().getAccountManager().getAccount(player.getName(), false);
        if (buy) {
            if (account.hasEnough(price, null, currency.getName())) {
                player.getInventory().addItem(item);
                account.withdraw(price, null, currency.getName(), Cause.BANK_WITHDRAW, new String());
                MessageUtil.sendMessage(player, WMessage.SHOP_BOUGHT.getMessage(item.getAmount() + " " + getItemName(item), format(currency, price)));
                return true;
            } else {
                MessageUtil.sendMessage(player, WMessage.ERROR_NOT_ENOUGH.getMessage(format(currency, price)));
                return false;
            }
        } else if (player.getInventory().containsAtLeast(item, item.getAmount())) {
            player.getInventory().removeItem(new ItemStack[]{item});
            account.deposit(price, null, currency.getName(), Cause.BANK_DEPOSIT, new String());
            MessageUtil.sendMessage(player, WMessage.SHOP_SOLD.getMessage(item.getAmount() + " " + getItemName(item), format(currency, price)));
            return true;
        } else {
            MessageUtil.sendMessage(player, WMessage.ERROR_NOT_ENOUGH.getMessage(item.getAmount() + " " + getItemName(item)));
            return false;
        }
    }

    /* Persistence */
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("item", item);
        map.put("currency", currency.getName());
        map.put("price", price);
        map.put("buy", buy);
        return map;
    }

    public static List<ShopItem> deserializeList(List<Map<String, Object>> serialized) {
        List<ShopItem> items = new ArrayList<>();
        serialized.forEach(m -> items.add(new ShopItem(m)));
        return items;
    }

    public static List<Map<String, Object>> serializeList(List<ShopItem> items) {
        List<Map<String, Object>> serialized = new ArrayList<>();
        items.forEach(i -> serialized.add(i.serialize()));
        return serialized;
    }

    /* Utils */
    public static String getItemName(ItemStack item) {
        if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
            return item.getItemMeta().getDisplayName();
        } else {
            return item.getType().toString().toLowerCase();
        }
    }

    public static String format(WCurrency currency, double money) {
        String string = String.valueOf(money);
        if (string.endsWith(".0")) {
            string = string.replace(".0", ".-");
        } else if (string.split("\\.")[1].length() < 2) {
            string += "0";
        }
        string += " " + currency.getSign();
        return string;
    }

}
