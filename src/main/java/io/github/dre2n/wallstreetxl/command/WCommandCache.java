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

import io.github.dre2n.commons.command.DRECommandCache;
import io.github.dre2n.commons.javaplugin.DREPlugin;

/**
 * @author Daniel Saukel
 */
public class WCommandCache extends DRECommandCache {

    public AddItemCommand addItem = new AddItemCommand();
    public CreateCommand create = new CreateCommand();
    public ExchangeCommand exchange = new ExchangeCommand();
    public HelpCommand help = new HelpCommand();
    public MainCommand main = new MainCommand();
    public OpenCommand open = new OpenCommand();
    public RemoveItemCommand removeItem = new RemoveItemCommand();

    public WCommandCache(DREPlugin plugin) {
        super("wallstreetxl", plugin);
        addCommand(addItem);
        addCommand(create);
        addCommand(exchange);
        addCommand(help);
        addCommand(main);
        addCommand(open);
        addCommand(removeItem);
    }

}
