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

import java.util.EnumSet;

/**
 *
 * @author Joaquin
 */
public class Fleet {

    final public static EnumSet<ShipType> SHIP_TYPES_COUNT = EnumSet.range(ShipType.NAVE_PEQUEÑA_CARGA, ShipType.CUPULA_GRANDE);
    private Coordinate coords = Coordinate.parseCoords("[0:0:0]");
    private int[] ships = getNoShips();
    

    public Fleet() {
    }

    public Fleet(Coordinate coords) {
        this.coords = coords;
    }

    public Fleet(int[] ships) {
        this.ships = ships;
    }

    public Fleet(Coordinate coords, int[] ships) {
        this.coords = coords;
        this.ships = ships;
    }

    public Coordinate getCoords() {
        return coords;
    }

    public void setCoords(Coordinate coords) {
        this.coords = coords;
    }

    /**
     * Get the value of ships
     *
     * @return the value of ships
     */
    public int[] getShips() {
        return ships;
    }

    /**
     * Set the value of ships
     *
     * @param ships new value of ships
     */
    public void setShips(int[] ships) {
        this.ships = ships;
    }

    /**
     * Get the value of ships at specified index
     *
     * @param index
     * @return the value of ships at specified index
     */
    public int getShips(int index) {
        return this.ships[index];
    }

    /**
     * Set the value of ships at specified index.
     *
     * @param index
     * @param newShips new value of ships at specified index
     */
    public void setShips(int index, int newShips) {
        this.ships[index] = newShips;
    }

    public Fleet substractFleet(Fleet anotherFleet) {
        int[] resultShips = new int[SHIP_TYPES_COUNT.size()];
        for (int i = 0; i < SHIP_TYPES_COUNT.size(); i++) {
            resultShips[i] = this.ships[i] - anotherFleet.getShips(i);
        }
        return new Fleet(resultShips);
    }

    public Fleet addFleet(Fleet anotherFleet) {
        int[] resultShips = new int[SHIP_TYPES_COUNT.size()];
        for (int i = 0; i < SHIP_TYPES_COUNT.size(); i++) {
            resultShips[i] = this.ships[i] + anotherFleet.getShips(i);
        }
        return new Fleet(resultShips);
    }
    
    private static int[] getNoShips() {
        int[] zeros = new int[SHIP_TYPES_COUNT.size()];
        java.util.Arrays.fill(zeros, 0);
        return zeros;
    }
    
//    private static Fleet getEmptyFleet() {
//        int[] zeros = getNoShips();
//        Coordinate coord = Coordinate.parseCoords(null);
//        return new Fleet(coord, zeros);
//    }

    @Override
    public String toString() {
        String s = this.coords.toString() + "\n";
        for (ShipType sh : SHIP_TYPES_COUNT) {
            if (this.ships[sh.ordinal()] != 0) {
                s += sh.toString() + " " + ships[sh.ordinal()] + "\n";
            }
        }
        return s;
    }

    
}
