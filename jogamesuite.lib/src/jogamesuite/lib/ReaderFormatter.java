/*
 *  Copyright (C) 2010 Joaquin
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package jogamesuite.lib;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Pattern;

/**
 *
 * @author Joaquin
 */
public class ReaderFormatter implements ReaderFormats {

    protected DecimalFormat integerFormat = initIntegerFormat();
    protected DateFormat DayTimeFormat = DateFormat.getInstance();
    protected int PATTERN_CASE_FLAG = Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE;

    /**
     * Returns a date regular expresion as a capturin group
     * @return
     */
    public String getDateRegex() {
        return "(\\d{1,2}.\\d{1,2}.\\d{2,4})";
    }

    /**
     * Returns a time regular expression as a capturing group
     * @return
     */
    public String getTimeRegex() {
        return "(\\d{1,2}\\:\\d{1,2}\\:\\d{1,2})";
    }

    public String getIntegerRegex() {
        char sep = integerFormat.getDecimalFormatSymbols().getGroupingSeparator();
        return "([\\d" + "[\\" + sep + "]]+)";
    }

    public String getNameRegex() {
        return "(.+)";
    }

    private DecimalFormat initIntegerFormat() {
        DecimalFormat intFormat = (DecimalFormat) NumberFormat.getInstance();
        intFormat.setParseIntegerOnly(true);
        return intFormat;
    }

}
