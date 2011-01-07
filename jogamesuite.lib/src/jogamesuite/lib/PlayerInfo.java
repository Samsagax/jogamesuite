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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author Joaquin
 */
public class PlayerInfo {

    private String name;
    public static final String PROP_NAME = "name";
    private Fleet[] fleetEnd;
    public static final String PROP_FLEETEND = "fleetEnd";
    private Fleet[] fleetStart;
    public static final String PROP_FLEETSTART = "fleetStart";
    private int[] tech = {0, 0, 0};
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public PlayerInfo() {
    }

    public PlayerInfo(String name) {
        this.name = name;
    }

    public void initFleets(int fleetCount){

    }

    public void setFleetCoord(int index, Coordinate coord) {
        this.fleetStart[index].setCoords(coord);
        this.fleetEnd[index].setCoords(coord);
    }

    public Coordinate getFleetCoord(int index) {
        return this.fleetStart[index].getCoords();
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        String oldName = this.name;
        this.name = name;
        propertyChangeSupport.firePropertyChange(PROP_NAME, oldName, name);
    }

    /**
     * Get the value of tech
     *
     * @return the value of tech
     */
    public int[] getTech() {
        return tech;
    }

    /**
     * Set the value of tech
     *
     * @param tech new value of tech
     */
    public void setTech(int[] tech) {
        this.tech = tech;
    }

    /**
     * Get the value of tech at specified index
     *
     * @param index
     * @return the value of tech at specified index
     */
    public int getTech(int index) {
        return this.tech[index];
    }

    /**
     * Set the value of tech at specified index.
     *
     * @param index
     * @param newTech new value of tech at specified index
     */
    public void setTech(int index, int newTech) {
        this.tech[index] = newTech;
    }

    /**
     * Get the value of fleetStart
     *
     * @return the value of fleetStart
     */
    public Fleet[] getFleetStart() {
        return fleetStart;
    }

    /**
     * Set the value of fleetStart
     *
     * @param fleetStart new value of fleetStart
     */
    public void setFleetStart(Fleet[] fleet) {
        Fleet[] oldFleet = this.fleetStart;
        this.fleetStart = fleet;
        propertyChangeSupport.firePropertyChange(PROP_FLEETSTART, oldFleet, fleet);
    }

    /**
     * Get the value of fleetStart at specified index
     *
     * @param index
     * @return the value of fleetStart at specified index
     */
    public Fleet getFleetStart(int index) {
        return fleetStart[index];
    }

    /**
     * Set the value of fleetStart at specified index.
     *
     * @param index
     * @param newFleet new value of fleetStart at specified index
     */
    public void setFleetStart(int index, Fleet newFleet) {
        Fleet oldFleet = this.fleetStart[index];
        this.fleetStart[index] = newFleet;
        propertyChangeSupport.fireIndexedPropertyChange(PROP_FLEETSTART, index, oldFleet, newFleet);
    }

    /**
     * Get the value of fleetEnd
     *
     * @return the value of fleetEnd
     */
    public Fleet[] getFleetEnd() {
        return fleetEnd;
    }

    /**
     * Set the value of fleetEnd
     *
     * @param fleetEnd new value of fleetEnd
     */
    public void setFleetEnd(Fleet[] fleetEnd) {
        Fleet[] oldFleetEnd = this.fleetEnd;
        this.fleetEnd = fleetEnd;
        propertyChangeSupport.firePropertyChange(PROP_FLEETEND, oldFleetEnd, fleetEnd);
    }

    /**
     * Get the value of fleetEnd at specified index
     *
     * @param index
     * @return the value of fleetEnd at specified index
     */
    public Fleet getFleetEnd(int index) {
        return fleetEnd[index];
    }

    /**
     * Set the value of fleetEnd at specified index.
     *
     * @param index
     * @param newFleetEnd new value of fleetEnd at specified index
     */
    public void setFleetEnd(int index, Fleet newFleetEnd) {
        Fleet oldFleetEnd = this.fleetEnd[index];
        this.fleetEnd[index] = newFleetEnd;
        propertyChangeSupport.fireIndexedPropertyChange(PROP_FLEETEND, index, oldFleetEnd, newFleetEnd);
    }

    /**
     * Add PropertyChangeListener.
     *
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
    }

    public long getLoss(Resource rec) {
        long loss = 0;
        Fleet losses = getLossFleetTotal();
        for (ShipType s : Fleet.SHIP_TYPES_COUNT) {
            loss += losses.getShips(s.ordinal()) * s.getCost()[rec.ordinal()];
        }
        return loss;
    }

    public long getTotalLoss() {
        long loss = 0;
        for (Resource r : Resource.values()) {
            loss += getLoss(r);
        }
        return loss;
    }

    public Fleet getFleetStartTotal() {
        Fleet result = new Fleet();
        for (int i = 0; i < fleetStart.length; i++) {
            Fleet fleet = fleetStart[i];
            result = result.addFleet(fleet);
        }
        return result;
    }

    public Fleet getFleetEndTotal() {
        Fleet result = new Fleet();
        for (int i = 0; i < fleetEnd.length; i++) {
            Fleet fleet = fleetEnd[i];
            result = result.addFleet(fleet);
        }
        return result;
    }

    public Fleet getLossFleetTotal() {
        Fleet losses = new Fleet();
        for (int i = 0; i < fleetStart.length; i++) {
            losses = losses.addFleet(getLossFleet(i));
        }
        return losses;
    }

    public Fleet getLossFleet(int index) {
        final Fleet result = fleetStart[index].substractFleet(fleetEnd[index]);
        return result;
    }

    public int getFleetCount() {
        return fleetStart.length;
    }
}
