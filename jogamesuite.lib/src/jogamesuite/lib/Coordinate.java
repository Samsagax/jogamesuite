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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Joaquin
 */
public class Coordinate {

    private int[] coords;

    public static Coordinate parseCoords(String coordString){
        int[] coords = {0, 0, 0};
        if (coordString != null) {
            Matcher m = Pattern.compile(toRegex()).matcher(coordString);
            if (m.find()) {
                coords[0] = Integer.parseInt(m.group(1));
                coords[1] = Integer.parseInt(m.group(2));
                coords[2] = Integer.parseInt(m.group(3));
            }
        }
        return new Coordinate(coords);
    }

    private Coordinate(int[] coords) {
        this.coords = coords;
    }

    public int[] getArray(){
        return coords;
    }

    @Override
    public String toString(){
        return "["+ coords[0] + ":" + coords[1] + ":" + coords[2] + "]";
    }

    public static String toRegex(){
        return "\\[(\\d{1})\\:(\\d{1,3})\\:(\\d{1,2})\\]";
    }

}
