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
package io.github.dre2n.wallstreetxl.command;

import de.erethon.commons.chat.MessageUtil;
import de.erethon.commons.command.DRECommand;
import io.github.dre2n.wallstreetxl.WallstreetXL;
import io.github.dre2n.wallstreetxl.config.WMessage;
import io.github.dre2n.wallstreetxl.shop.AdminShop;
import io.github.dre2n.wallstreetxl.shop.Shop;
import io.github.dre2n.wallstreetxl.shop.ShopCache;
import org.bukkit.command.CommandSender;

/**
 * @author Daniel Saukel
 */
public class CreateCommand extends DRECommand {

    ShopCache shops = WallstreetXL.getInstance().getShopCache();

    public CreateCommand() {
        setCommand("create");
        setAliases("c");
        setMinArgs(-1);
        setMaxArgs(-1);
        setHelp(WMessage.HELP_CREATE.getMessage());
        setPermission("wxl.create");
        setConsoleCommand(true);
        setPlayerCommand(true);
    }

    @Override
    public void onExecute(String[] args, CommandSender sender) {
        if (args.length < 3) {
            displayHelp(sender);
            return;
        }
        if (shops.getByName(args[1]) != null) {
            MessageUtil.sendMessage(sender, WMessage.ERROR_SHOP_EXISTS.getMessage(args[1]));
            return;
        }
        String title = new String();
        for (String arg : args) {
            if (!title.isEmpty()) {
                title += " ";
            }
            if (args[0] != arg && args[1] != arg) {
                title += arg;
            }
        }
        Shop shop = new AdminShop(args[1], title);
        shops.getShops().add(shop);
        MessageUtil.sendMessage(sender, WMessage.CMD_CREATE_SUCCESS.getMessage(args[1]));
    }

}
