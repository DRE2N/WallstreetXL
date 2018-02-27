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
package io.github.dre2n.wallstreetxl.config;

import de.erethon.commons.chat.MessageUtil;
import de.erethon.commons.config.Message;
import de.erethon.commons.javaplugin.DREPlugin;
import io.github.dre2n.wallstreetxl.WallstreetXL;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * An enumeration of all messages.
 * The values are fetched from the language file.
 *
 * @author Daniel Saukel
 */
public enum WMessage implements Message {

    CMD_ADD_ITEM_SUCCESS("cmd.addItem.success", "&7Successfully added &4&v1 &7to &4&v2&7."),
    CMD_CREATE_SUCCESS("cmd.create.success", "&7Successfully created the shop &4&v1&7."),
    CMD_DELETE_SUCCESS("cmd.delete.success", "&7Successfully removed the shop &4&v1&7."),
    CMD_EXCHANGE_SUCCESS("cmd.exchange.success", "&7Successfully exchanged &4&v1 &7for &4&v2&7."),
    CMD_MAIN_HELP("cmd.main.help", "&7Type in &o/ws help&r &7for further information."),
    CMD_MAIN_WELCOME("cmd.main.welcome", "&7Welcome to &4Wallstreet&fXL"),
    CMD_REMOVE_ITEM_SUCCESS("cmd.removeItem.success", "&7Successfully remove &4&v1 &7from &4&v2&7."),
    CMD_RELOAD_SUCCESS("cmd.reload.success", "&7Successfully reloaded WXL."),
    ERROR_INDEX_EMPTY("error.indexEmpty", "&4There is no item at this index number."),
    ERROR_NO_ITEM_IN_HAND("error.noItemInHand", "&4You do not have an item in your hand."),
    ERROR_NO_SUCH_CURRENCY("error.noSuch.currency", "&4The currency &7&v1 &4is not registered."),
    ERROR_NO_SUCH_SHOP("error.noSuch.shop", "&4The shop &7&v1 &4does not exist."),
    ERROR_NOT_ENOUGH("error.notEnough", "&7&v1 &4does not have &7&v2&4."),
    ERROR_SHOP_EXISTS("error.shopExists", "&4The shop &7&v1 &4already exists."),
    HELP_ADD_ITEM("help.addItem", "/ws addItem [shop] [buy|sell] [currency] [price] - Adds the good in your hand to the shop"),
    HELP_CREATE("help.create", "/ws create [name] ([title])- Creates a new player or admin shop"),
    HELP_EXCHANGE("help.exchange", "/ws exchange [amount] [old currency] [new currency]"),
    HELP_DELETE("help.delete", "/ws delete [shop] - Deletes an existing shop"),
    HELP_HELP("help.help", "/ws help [page] - Shows the help page"),
    HELP_LINK("help.link", "/ws link [shop] - Creates a trader for a existing shop"),
    HELP_LOGS("help.logs", "/ws logs [shop] - Shows the trade logs of a player shop"),
    HELP_MAIN("help.main", "/ws - General status information"),
    HELP_OPEN("help.open", "/ws open [Name] - Opens a shop"),
    HELP_RELOAD("help.reload", "/ws reload - Reloads the config and all shops"),
    HELP_REMOVE_ITEM("help.removeItem", "/ws remove [shop] [index] - Removes a good from the shop"),
    HELP_TRADER("help.trader", "/ws trader [name] ([title])- Creates a new player shop with a trader"),
    SHOP_BOUGHT("shop.bought", "&7You bought &4&v1 &7for &4&v2&7."),
    SHOP_BUY("shop.buy", "&4Click to buy"),
    SHOP_SELL("shop.sell", "&aClick to sell"),
    SHOP_SOLD("shop.sold", "&7You sold &4&v1 &7for &4&v2&7."),
    NEXT_PAGE("nextPage", "&6&lNEXT PAGE"),
    PREVIOUS_PAGE("previousPage", "&6&lPREVIOUS PAGE"),
    TRADER_NAME("trader.name", "&1&l[Trader] &6&v1");

    private String identifier;
    private String message;

    WMessage(String identifier, String message) {
        this.identifier = identifier;
        this.message = message;
    }

    /* Getters and setters */
    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String getMessage() {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    @Override
    public String getMessage(String... args) {
        return WallstreetXL.getInstance().getMessageConfig().getMessage(this, args);
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    /* Actions */
    /**
     * Sends the message to the console.
     */
    public void debug() {
        MessageUtil.log(DREPlugin.getInstance(), getMessage());
    }

    /* Statics */
    /**
     * @param identifer
     * the identifer to set
     */
    public static Message getByIdentifier(String identifier) {
        for (Message message : values()) {
            if (message.getIdentifier().equals(identifier)) {
                return message;
            }
        }

        return null;
    }

    /**
     * @return a FileConfiguration containing all messages
     */
    public static FileConfiguration toConfig() {
        FileConfiguration config = new YamlConfiguration();
        for (WMessage message : values()) {
            config.set(message.getIdentifier(), message.message);
        }
        return config;
    }

}
