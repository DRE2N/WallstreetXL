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
import de.erethon.wallstreetxl.WallstreetXL;
import de.erethon.wallstreetxl.config.WMessage;
import de.erethon.wallstreetxl.shop.PlayerShop;
import de.erethon.wallstreetxl.shop.Shop;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
        Shop abstractShop = plugin.getShopCache().get(args[1]);
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
