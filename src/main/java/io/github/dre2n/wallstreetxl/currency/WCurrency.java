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
package io.github.dre2n.wallstreetxl.currency;

import com.greatmancode.craftconomy3.currency.Currency;

/**
 * @author Daniel Saukel
 */
public class WCurrency {

    private Currency cc3;
    private double standard;
    private double max;
    private double min;
    private double fluctuations;
    private double development;

    public WCurrency(Currency currency, double standard, double max, double min, double fluctuations, double development) {
        cc3 = currency;
        this.standard = standard;
        this.max = max;
        this.min = min;
        this.fluctuations = fluctuations;
        this.development = development;
    }

    public Currency getCC3() {
        return cc3;
    }

    public String getName() {
        return cc3.getName();
    }

    public String getSign() {
        return cc3.getSign();
    }

    public double getStandard() {
        return standard;
    }

    public void setStandard(double standard) {
        this.standard = standard;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getFluctuations() {
        return fluctuations;
    }

    public void setFluctuations(double fluctuations) {
        this.fluctuations = fluctuations;
    }

    public double getDevelopment() {
        return development;
    }

    public void setDevelopment(double development) {
        this.development = development;
    }

    public double convert(double amount, WCurrency currency) {
        return amount * standard / currency.standard;
    }

}
