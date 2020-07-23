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

import de.erethon.commons.command.DRECommandCache;
import de.erethon.commons.javaplugin.DREPlugin;

/**
 * @author Daniel Saukel
 */
public class WCommandCache extends DRECommandCache {

    public AddItemCommand addItem = new AddItemCommand();
    public CreateCommand create = new CreateCommand();
    public DeleteCommand delete = new DeleteCommand();
    public ExchangeCommand exchange = new ExchangeCommand();
    public HelpCommand help = new HelpCommand();
    public LinkCommand link = new LinkCommand();
    public LogsCommand logs = new LogsCommand();
    public MainCommand main = new MainCommand();
    public OpenCommand open = new OpenCommand();
    public RemoveItemCommand removeItem = new RemoveItemCommand();
    public TraderCommand trader = new TraderCommand();

    public WCommandCache(DREPlugin plugin) {
        super("wallstreetxl", plugin);
        addCommand(addItem);
        addCommand(create);
        addCommand(exchange);
        addCommand(delete);
        addCommand(help);
        addCommand(link);
        addCommand(logs);
        addCommand(main);
        addCommand(open);
        addCommand(removeItem);
        addCommand(trader);
    }

}
