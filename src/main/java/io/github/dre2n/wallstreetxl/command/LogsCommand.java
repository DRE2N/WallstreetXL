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
import io.github.dre2n.commons.misc.NumberUtil;
import io.github.dre2n.wallstreetxl.WallstreetXL;
import io.github.dre2n.wallstreetxl.config.WMessage;
import io.github.dre2n.wallstreetxl.currency.WCurrency;
import io.github.dre2n.wallstreetxl.shop.AdminShop;
import io.github.dre2n.wallstreetxl.shop.PlayerShop;
import io.github.dre2n.wallstreetxl.shop.Shop;
import io.github.dre2n.wallstreetxl.shop.ShopItem;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author Daniel Saukel
 */
public class LogsCommand extends DRECommand {

    WallstreetXL plugin = WallstreetXL.getInstance();

    public LogsCommand() {
        setCommand("logs");
        setAliases("log", "l");
        setMinArgs(1);
        setMaxArgs(1);
        setHelp(WMessage.HELP_LOGS.getMessage());
        setPermission("wxl.logs");
        setConsoleCommand(true);
        setPlayerCommand(true);
    }

    @Override
    public void onExecute(String[] args, CommandSender sender) {
        Shop abstractShop = plugin.getShopCache().getByName(args[1]);
        if (!(abstractShop instanceof PlayerShop)) {
            MessageUtil.sendMessage(sender, WMessage.ERROR_NO_SUCH_SHOP.getMessage(args[1]));
            return;
        }
        PlayerShop shop = (PlayerShop) abstractShop;
        if (!sender.hasPermission("wxl.logs.others") && !shop.getOwner().equals(((Player) sender).getUniqueId())) {
            MessageUtil.sendMessage(sender, WMessage.ERROR_NO_SUCH_SHOP.getMessage(args[1]));
            return;
        }
        for (String log : shop.readLog()) {
            MessageUtil.sendMessage(sender, log);
        }
    }

}