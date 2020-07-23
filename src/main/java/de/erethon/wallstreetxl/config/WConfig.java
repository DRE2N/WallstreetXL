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
package de.erethon.wallstreetxl.config;

import de.erethon.commons.config.DREConfig;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Daniel Saukel
 */
public class WConfig extends DREConfig {

    public static final int CONFIG_VERSION = 1;

    private boolean onJoin = false;
    private Map<String, Date> tracks = new HashMap<>();

    public WConfig(File file) {
        super(file, CONFIG_VERSION);

        if (initialize) {
            initialize();
        }
        load();
    }

    public Map<String, Date> getTracks() {
        return tracks;
    }

    public boolean isOnJoin() {
        return onJoin;
    }

    @Override
    public void initialize() {
        if (!config.contains("onJoin")) {
            config.set("onJoin", onJoin);
        }
    }

    @Override
    public void load() {
        onJoin = config.getBoolean("onJoin", false);
    }

}
