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

import com.greatmancode.craftconomy3.Cause;
import com.greatmancode.craftconomy3.account.Account;
import de.erethon.commons.chat.MessageUtil;
import de.erethon.commons.command.DRECommand;
import de.erethon.commons.misc.NumberUtil;
import de.erethon.wallstreetxl.WallstreetXL;
import de.erethon.wallstreetxl.config.WMessage;
import de.erethon.wallstreetxl.currency.WCurrency;
import de.erethon.wallstreetxl.currency.WCurrencyCache;
import de.erethon.wallstreetxl.shop.ShopItem;
import org.bukkit.command.CommandSender;

/**
 * @author Daniel Saukel
 */
public class ExchangeCommand extends DRECommand {

    WCurrencyCache currencies = WallstreetXL.getInstance().getCurrencyCache();

    public ExchangeCommand() {
        setCommand("exchange");
        setAliases("e");
        setMinArgs(3);
        setMaxArgs(3);
        setHelp(WMessage.HELP_EXCHANGE.getMessage());
        setPermission("wxl.exchange");
        setConsoleCommand(false);
        setPlayerCommand(true);
    }

    @Override
    public void onExecute(String[] args, CommandSender sender) {
        double amount = NumberUtil.parseDouble(args[1]);
        WCurrency from = currencies.getByName(args[2]);
        WCurrency to = currencies.getByName(args[3]);
        if (from == null || to == null) {
            MessageUtil.sendMessage(sender, WMessage.ERROR_NO_SUCH_CURRENCY.getMessage(from == null ? args[2] : args[3]));
            return;
        }
        double toAmount = from.convert(amount, to);
        Account account = WallstreetXL.getInstance().getCraftConomy().getAccountManager().getAccount(sender.getName(), false);
        account.withdraw(amount, null, from.getName(), Cause.EXCHANGE, "");
        account.deposit(toAmount, null, to.getName(), Cause.EXCHANGE, "");
        MessageUtil.sendMessage(sender, WMessage.CMD_EXCHANGE_SUCCESS.getMessage(ShopItem.format(from, amount), ShopItem.format(to, toAmount)));
    }

}
