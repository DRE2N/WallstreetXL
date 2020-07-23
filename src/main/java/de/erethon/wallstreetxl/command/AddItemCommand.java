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
import de.erethon.commons.misc.NumberUtil;
import de.erethon.wallstreetxl.WallstreetXL;
import de.erethon.wallstreetxl.config.WMessage;
import de.erethon.wallstreetxl.currency.WCurrency;
import de.erethon.wallstreetxl.shop.AdminShop;
import de.erethon.wallstreetxl.shop.PlayerShop;
import de.erethon.wallstreetxl.shop.Shop;
import de.erethon.wallstreetxl.shop.ShopItem;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author Daniel Saukel
 */
public class AddItemCommand extends DRECommand {

    WallstreetXL plugin = WallstreetXL.getInstance();

    public AddItemCommand() {
        setCommand("addItem");
        setAliases("add", "a");
        setMinArgs(4);
        setMaxArgs(4);
        setHelp(WMessage.HELP_ADD_ITEM.getMessage());
        setPermission("wxl.additem");
        setConsoleCommand(true);
        setPlayerCommand(true);
    }

    @Override
    public void onExecute(String[] args, CommandSender sender) {
        Shop shop = plugin.getShopCache().get(args[1]);
        if (shop == null) {
            MessageUtil.sendMessage(sender, WMessage.ERROR_NO_SUCH_SHOP.getMessage(args[1]));
            return;
        }
        if (!sender.hasPermission("wxl.additem.admin") && shop instanceof AdminShop
                || (shop instanceof PlayerShop && !((PlayerShop) shop).getOwner().equals(((Player) sender).getUniqueId()))) {
            MessageUtil.sendMessage(sender, WMessage.ERROR_NO_SUCH_SHOP.getMessage(args[1]));
            return;
        }
        ItemStack item = ((Player) sender).getInventory().getItemInMainHand();
        if (item == null || item.getType() == Material.AIR) {
            MessageUtil.sendMessage(sender, WMessage.ERROR_NO_ITEM_IN_HAND.getMessage());
            return;
        }
        boolean buy = !args[2].equalsIgnoreCase("sell");
        WCurrency currency = plugin.getCurrencyCache().getByName(args[3]);
        if (currency == null) {
            MessageUtil.sendMessage(sender, WMessage.ERROR_NO_SUCH_CURRENCY.getMessage(args[3]));
            return;
        }
        double price = NumberUtil.parseDouble(args[4]);
        shop.addItem(new ShopItem(item, currency, price, buy));
        if (sender instanceof Player && shop instanceof PlayerShop) {
            ((Player) sender).getInventory().removeItem(item);
        }
        MessageUtil.sendMessage(sender, WMessage.CMD_ADD_ITEM_SUCCESS.getMessage(ShopItem.getItemName(item), shop.getName()));
    }

}
