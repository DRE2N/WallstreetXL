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
package de.erethon.wallstreetxl.config;

import de.erethon.commons.config.Message;
import de.erethon.commons.config.MessageHandler;
import de.erethon.wallstreetxl.WallstreetXL;

/**
 * An enumeration of all messages.
 * The values are fetched from the language file.
 *
 * @author Daniel Saukel
 */
public enum WMessage implements Message {

    CMD_ADD_ITEM_SUCCESS("cmd.addItem.success"),
    CMD_CREATE_SUCCESS("cmd.create.success"),
    CMD_DELETE_SUCCESS("cmd.delete.success"),
    CMD_EXCHANGE_SUCCESS("cmd.exchange.success"),
    CMD_MAIN_HELP("cmd.main.help"),
    CMD_MAIN_WELCOME("cmd.main.welcome"),
    CMD_REMOVE_ITEM_SUCCESS("cmd.removeItem.success"),
    CMD_RELOAD_SUCCESS("cmd.reload.success"),
    ERROR_INDEX_EMPTY("error.indexEmpty"),
    ERROR_NO_ITEM_IN_HAND("error.noItemInHand"),
    ERROR_NO_SUCH_CURRENCY("error.noSuch.currency"),
    ERROR_NO_SUCH_SHOP("error.noSuch.shop"),
    ERROR_NOT_ENOUGH("error.notEnough"),
    ERROR_SHOP_EXISTS("error.shopExists"),
    HELP_ADD_ITEM("help.addItem"),
    HELP_CREATE("help.create"),
    HELP_EXCHANGE("help.exchange"),
    HELP_DELETE("help.delete"),
    HELP_HELP("help.help"),
    HELP_LINK("help.link"),
    HELP_LOGS("help.logs"),
    HELP_MAIN("help.main"),
    HELP_OPEN("help.open"),
    HELP_RELOAD("help.reload"),
    HELP_REMOVE_ITEM("help.removeItem"),
    HELP_TRADER("help.trader"),
    SHOP_BOUGHT("shop.bought"),
    SHOP_BUY("shop.buy"),
    SHOP_SELL("shop.sell"),
    SHOP_SOLD("shop.sold"),
    NEXT_PAGE("nextPage"),
    PREVIOUS_PAGE("previousPage"),
    TRADER_NAME("trader.name");

    private String path;

    WMessage(String path) {
        this.path = path;
    }

    @Override
    public MessageHandler getMessageHandler() {
        return WallstreetXL.getInstance().getMessageHandler();
    }

    @Override
    public String getPath() {
        return path;
    }

}
