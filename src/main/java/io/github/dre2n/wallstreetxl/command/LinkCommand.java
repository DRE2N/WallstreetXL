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
package io.github.dre2n.wallstreetxl.command;

import io.github.dre2n.commons.chat.MessageUtil;
import io.github.dre2n.commons.command.DRECommand;
import io.github.dre2n.wallstreetxl.WallstreetXL;
import io.github.dre2n.wallstreetxl.config.WMessage;
import io.github.dre2n.wallstreetxl.shop.Shop;
import io.github.dre2n.wallstreetxl.shop.ShopCache;
import io.github.dre2n.wallstreetxl.shop.Trader;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Daniel Saukel
 */
public class LinkCommand extends DRECommand {

    ShopCache shops = WallstreetXL.getInstance().getShopCache();

    public LinkCommand() {
        setCommand("link");
        setAliases("l");
        setMinArgs(1);
        setMaxArgs(1);
        setHelp(WMessage.HELP_LINK.getMessage());
        setPermission("wxl.link");
        setConsoleCommand(false);
        setPlayerCommand(true);
    }

    @Override
    public void onExecute(String[] args, CommandSender sender) {
        Shop shop = shops.getByName(args[1]);
        if (shop == null) {
            MessageUtil.sendMessage(sender, WMessage.ERROR_NO_SUCH_SHOP.getMessage(args[1]));
            return;
        }
        Trader.createTrader((Player) sender, args[1]);
        MessageUtil.sendMessage(sender, WMessage.CMD_CREATE_SUCCESS.getMessage(args[1]));
    }

}
