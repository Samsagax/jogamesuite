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

import java.io.Serializable;

/**
 *
 * @author Joaquin
 */
public class Trader implements Serializable {

    private static final double[] DEFAULT_RATIO = {3, 2, 1};
    private TraderBean[] trader = new TraderBean[Resource.values().length];
    private double[] ratio = new double[Resource.values().length];
    public static final int METAL = Resource.METAL.ordinal();
    public static final int CRISTAL = Resource.CRISTAL.ordinal();
    public static final int DEUTERIUM = Resource.DEUTERIO.ordinal();
    private Resource resource;

    public Trader() {
        this(Resource.METAL, DEFAULT_RATIO);
    }
    
    public Trader(Resource toTrade) {
        this(toTrade, DEFAULT_RATIO);
    }

    public Trader(Resource toTrade, double[] ratio) {
        this.ratio = ratio;
        this.resource = toTrade;
        updateTraders();
    }

    private void updateTraders() {
        for (Resource r : Resource.values()) {
            int out = r.ordinal();
            int in = resource.ordinal();
            trader[out] = new TraderBean(resource, r, ratio[in], ratio[out]);
        }
    }

    public long[] tradeSimple(final long amount) {
        long[] a = new long[Resource.values().length];
        for (int i = 0; i < a.length; i++) {
            a[i] = trader[i].tradeForward(amount);
        }
        return a;
    }

    public long[] tradeFixed(long amount, Resource fixed, final long fixedAmount) {
        int index = fixed.ordinal();
        amount -= trader[index].tradeBackwards(fixedAmount); //TODO Checkear si es más chico.
        long[] r = tradeSimple(amount);
        r[index] = fixedAmount;
        return r;
    }

    public double[] getRatio() {
        return ratio;
    }

    public double getRatio(int resIndex) {
        return ratio[resIndex];
    }

    public void setRatio(double[] ratio) {
        this.ratio = ratio;
        updateTraders();
    }

    public void setRatio(int resIndex, double ratio) {
        this.ratio[resIndex] = ratio;
        updateTraders();
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
        updateTraders();
    }

}
