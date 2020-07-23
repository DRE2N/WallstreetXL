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
package de.erethon.wallstreetxl.command;

import de.erethon.commons.chat.MessageUtil;
import de.erethon.commons.command.DRECommand;
import de.erethon.commons.misc.Registry;
import de.erethon.wallstreetxl.WallstreetXL;
import de.erethon.wallstreetxl.config.WMessage;
import de.erethon.wallstreetxl.shop.PlayerShop;
import de.erethon.wallstreetxl.shop.Shop;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Daniel Saukel
 */
public class TraderCommand extends DRECommand {

    Registry<String, Shop> shops = WallstreetXL.getInstance().getShopCache();

    public TraderCommand() {
        setCommand("trader");
        setAliases("t");
        setMinArgs(-1);
        setMaxArgs(-1);
        setHelp(WMessage.HELP_TRADER.getMessage());
        setPermission("wxl.trader");
        setConsoleCommand(false);
        setPlayerCommand(true);
    }

    @Override
    public void onExecute(String[] args, CommandSender sender) {
        if (args.length < 3) {
            displayHelp(sender);
            return;
        }
        if (shops.get(args[1]) != null) {
            MessageUtil.sendMessage(sender, WMessage.ERROR_SHOP_EXISTS.getMessage(args[1]));
            return;
        }
        String title = "";
        for (String arg : args) {
            if (!title.isEmpty()) {
                title += " ";
            }
            if (args[0] != arg && args[1] != arg) {
                title += arg;
            }
        }
        Shop shop = new PlayerShop(args[1], (Player) sender, title);
        shops.add(shop.getName(), shop);
        MessageUtil.sendMessage(sender, WMessage.CMD_CREATE_SUCCESS.getMessage(args[1]));
    }

}
