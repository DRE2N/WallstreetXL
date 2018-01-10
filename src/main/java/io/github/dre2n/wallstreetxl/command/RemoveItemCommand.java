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

import io.github.dre2n.commons.chat.MessageUtil;
import io.github.dre2n.commons.command.DRECommand;
import io.github.dre2n.commons.misc.NumberUtil;
import io.github.dre2n.wallstreetxl.WallstreetXL;
import io.github.dre2n.wallstreetxl.config.WMessage;
import io.github.dre2n.wallstreetxl.shop.AdminShop;
import io.github.dre2n.wallstreetxl.shop.PlayerShop;
import io.github.dre2n.wallstreetxl.shop.Shop;
import io.github.dre2n.wallstreetxl.shop.ShopItem;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Daniel Saukel
 */
public class RemoveItemCommand extends DRECommand {

    WallstreetXL plugin = WallstreetXL.getInstance();

    public RemoveItemCommand() {
        setCommand("removeItem");
        setAliases("remove", "remove");
        setMinArgs(2);
        setMaxArgs(2);
        setHelp(WMessage.HELP_REMOVE_ITEM.getMessage());
        setPermission("wxl.additem");
        setConsoleCommand(true);
        setPlayerCommand(true);
    }

    @Override
    public void onExecute(String[] args, CommandSender sender) {
        Shop shop = plugin.getShopCache().getByName(args[1]);
        if (shop == null) {
            MessageUtil.sendMessage(sender, WMessage.ERROR_NO_SUCH_SHOP.getMessage());
            return;
        }
        if (!sender.hasPermission("wxl.additem.admin") && shop instanceof AdminShop
                || (shop instanceof PlayerShop && !((PlayerShop) shop).getOwner().equals(((Player) sender).getUniqueId()))) {
            MessageUtil.sendMessage(sender, WMessage.ERROR_NO_SUCH_SHOP.getMessage(args[1]));
            return;
        }
        ShopItem item = shop.getItems().size() > NumberUtil.parseInt(args[2], 999) ? shop.getItems().get(NumberUtil.parseInt(args[2], 999)) : null;
        if (item == null) {
            MessageUtil.sendMessage(sender, WMessage.ERROR_INDEX_EMPTY.getMessage());
            return;
        }
        shop.removeItem(item);
        if (sender instanceof Player && shop instanceof PlayerShop) {
            ((Player) sender).getInventory().addItem(item.getItem());
        }
        MessageUtil.sendMessage(sender, WMessage.CMD_REMOVE_ITEM_SUCCESS.getMessage(ShopItem.getItemName(item.getItem()), shop.getName()));
    }

}
