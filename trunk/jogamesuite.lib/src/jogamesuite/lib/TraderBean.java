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
import java.io.Serializable;

/**
 *
 * @author Joaquin
 */
public class TraderBean implements Serializable {

    private Resource tradeIn;
    private Resource tradeOut;
    private double tradeInRatio;
    private double tradeOutRatio;
    public static final String PROP_TRADEIN = "tradeIn";
    public static final String PROP_TRADEOUT = "tradeOut";
    public static final String PROP_TRADEINRATIO = "tradeInRatio";
    public static final String PROP_TRADEOUTRATIO = "tradeOutRatio";
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public TraderBean() {
    }

    public TraderBean(Resource tradeIn, Resource tradeOut) {
        this.tradeIn = tradeIn;
        this.tradeOut = tradeOut;
    }

    public TraderBean(Resource tradeIn, Resource tradeOut, double tradeInRatio, double tradeOutRatio) {
        this.tradeIn = tradeIn;
        this.tradeOut = tradeOut;
        this.tradeInRatio = tradeInRatio;
        this.tradeOutRatio = tradeOutRatio;
    }

    /**
     * Get the value of tradeOutRatio
     *
     * @return the value of tradeOutRatio
     */
    public double getTradeOutRatio() {
        return tradeOutRatio;
    }

    /**
     * Set the value of tradeOutRatio
     *
     * @param tradeOutRatio new value of tradeOutRatio
     */
    public void setTradeOutRatio(double tradeOutRatio) {
        double oldTradeOutRatio = this.tradeOutRatio;
        this.tradeOutRatio = tradeOutRatio;
        propertyChangeSupport.firePropertyChange(PROP_TRADEOUTRATIO, oldTradeOutRatio, tradeOutRatio);
    }

    /**
     * Get the value of tradeInRatio
     *
     * @return the value of tradeInRatio
     */
    public double getTradeInRatio() {
        return tradeInRatio;
    }

    /**
     * Set the value of tradeInRatio
     *
     * @param tradeInRatio new value of tradeInRatio
     */
    public void setTradeInRatio(double tradeInRatio) {
        double oldTradeInRatio = this.tradeInRatio;
        this.tradeInRatio = tradeInRatio;
        propertyChangeSupport.firePropertyChange(PROP_TRADEINRATIO, oldTradeInRatio, tradeInRatio);
    }

    /**
     * Get the value of tradeOut
     *
     * @return the value of tradeOut
     */
    public Resource getTradeOut() {
        return tradeOut;
    }

    /**
     * Set the value of tradeOut
     *
     * @param tradeOut new value of tradeOut
     */
    public void setTradeOut(Resource tradeOut) {
        Resource oldTradeOut = this.tradeOut;
        this.tradeOut = tradeOut;
        propertyChangeSupport.firePropertyChange(PROP_TRADEOUT, oldTradeOut, tradeOut);
    }

    /**
     * Get the value of tradeIn
     *
     * @return the value of tradeIn
     */
    public Resource getTradeIn() {
        return tradeIn;
    }

    /**
     * Set the value of tradeIn
     *
     * @param tradeIn new value of tradeIn
     */
    public void setTradeIn(Resource tradeIn) {
        Resource oldTradeIn = this.tradeIn;
        this.tradeIn = tradeIn;
        propertyChangeSupport.firePropertyChange(PROP_TRADEIN, oldTradeIn, tradeIn);
    }

    /**
     * Trades the amount of the Out Resource for the In Resource.
     * @param amount
     * @return In resource amount
     */
    public long tradeBackwards(long amount){
        return (long)(amount * tradeInRatio/tradeOutRatio);
    }
    /**
     * Trades the amount of the In Resource for the Out Resource.
     * @param amount
     * @return Out resource amount
     */
    public long tradeForward(long amount){
        return (long) (amount * tradeOutRatio/tradeInRatio);
    }

    /**
     * Add PropertyChangeListener.
     *
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}