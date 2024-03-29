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

/**
 *
 * @author Joaquin
 */
public enum Resource {

    METAL,
    CRISTAL,
    DEUTERIO;

    @Override
    public String toString() {
        String s = this.name();
        String up = s.substring(0, 1);
        String low = s.substring(1).toLowerCase();
        return up + low;
    }
}
