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

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Joaquin
 */
public class SpyReportReader extends ReaderFormatter {

    private String report;
    private String fleetString = "";
    private String defenceString = "";
    private String buildingString = "";
    private String technologyString = "";
    private Date date;
    private String playerName;
    private Coordinate coord;
    private String planetName;
    private long[] resources = new long[Resource.values().length];
    private long[] fleet = new long[ShipType.values().length];
    private long[] building = new long[Building.values().length];
    private long[] technology = new long[Technology.values().length];
    //Constantes
    private final String FLEET_REGEX = "\\b\\QEscuadrón\\E\\b";
    private final String DEFENCE_REGEX = "\\b\\QDefensa\\E\\b";
    private final String BUILDING_REGEX = "\\b\\QEdificio\\E\\b";
    private final String TECH_REGEX = "\\b\\QInvestigación\\E\\b";

    public SpyReportReader(String report) {
        this.report = report;
        readIt();
    }

    private void readIt() {
        readDateAndHour();
        readNamesAndCoords();
        readResources();
        splitReport();
        readFleet();
        readDefence();
        readBuildings();
        readTechs();
    }

    private void readDateAndHour() {
        Matcher headTime = Pattern.compile("\\QFecha:\\E\\s+"
                + getDateRegex()
                + "\\s+"
                + getTimeRegex(),
                PATTERN_CASE_FLAG).matcher(report);
        if (headTime.find()) {
            try {
                this.date = DayTimeFormat.parse((headTime.group(1) + " " + headTime.group(2)).replaceAll("\\.", "/"));
            } catch (ParseException pe) {
                pe.printStackTrace();
                this.date = null;
            }
        } else {
            Matcher reportTime = Pattern.compile("\\Qal \\E"
                    + "(\\d{1,2}\\-\\d{1,2}) "
                    + getTimeRegex()
                    +"\\n",
                PATTERN_CASE_FLAG).matcher(report);
            if (reportTime.find()) {
                String ds[] = reportTime.group(1).split("\\-");
                String y = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
                String h = reportTime.group(2);
                try { //Localizar el calendario es una mejor forma
                    this.date = DayTimeFormat.parse(ds[1] + "/" + ds[0] + "/" + y + " " + h);
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                    this.date = null;
                }
            } else {
                this.date = null;
            }
        }
    }

    private void readNamesAndCoords() {
        Pattern p = Pattern.compile("\\QRecursos en \\E(.+)\\s(\\[\\d{1}:\\d{1,3}:\\d{1,2}\\])\\s+\\Q(Jugadores '\\E(.+)\\Q')\\E", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(report);
        if (m.find()) {
            this.coord = Coordinate.parseCoords(m.group(2));
            this.planetName = m.group(1);
            this.playerName = m.group(3);
        } else {
            this.planetName = "planeta desconocido";
            this.coord = Coordinate.parseCoords(null);
            this.playerName = "Desconocido";
        }
    }

    private void readResources() {
        String NUM_REGEX = getIntegerRegex();
        Matcher m = Pattern.compile("\\QMetal:\\E\\s+" + NUM_REGEX
                + "\\s+\\QCristal:\\E\\s+" + NUM_REGEX
                + "\\s+\\QDeuterio:\\E\\s+" + NUM_REGEX + "\\s+\\QEnergia:\\E", Pattern.CASE_INSENSITIVE).matcher(report);
        if (m.find()) {
            try {
                resources[0] = integerFormat.parse(m.group(1)).longValue();
                resources[1] = integerFormat.parse(m.group(2)).longValue();
                resources[2] = integerFormat.parse(m.group(3)).longValue();
            } catch (ParseException parseException) {
                parseException.printStackTrace();
                resources[0] = 0;
                resources[1] = 0;
                resources[2] = 0;
            }
        } else {
            resources[0] = 0;
            resources[1] = 0;
            resources[2] = 0;
        }
    }

    private void splitReport() {
        Matcher fleetMat = Pattern.compile(FLEET_REGEX, Pattern.CASE_INSENSITIVE).matcher(report);
        Matcher defMat = Pattern.compile(DEFENCE_REGEX, Pattern.CASE_INSENSITIVE).matcher(report);
        Matcher buildingMat = Pattern.compile(BUILDING_REGEX, Pattern.CASE_INSENSITIVE).matcher(report);
        Matcher techMat = Pattern.compile(TECH_REGEX, Pattern.CASE_INSENSITIVE).matcher(report);
        int start = 0;
        int end = start;
        if (fleetMat.find(start)) {
            start = fleetMat.start();
            fleetString = report.substring(start);
            if (defMat.find(start)) {
                end = defMat.start();
                fleetString = report.substring(start, end);
                start = defMat.start();
                defenceString = report.substring(start);
                if (buildingMat.find(start)) {
                    end = buildingMat.start();
                    defenceString = report.substring(start, end);
                    start = buildingMat.start();
                    buildingString = report.substring(start);
                    if (techMat.find(start)) {
                        end = techMat.start();
                        buildingString = report.substring(start, end);
                        start = techMat.start();
                        technologyString = report.substring(start);
                    }
                }
            }
        }
    }

    private void readFleet() {
        if (!fleetString.equals("")) {
            int lastMatch = 0;
            String NUM_REGEX = getIntegerRegex();
            for (ShipType n : EnumSet.range(ShipType.NAVE_PEQUEÑA_CARGA, ShipType.ACORAZADO)) {
                Matcher m = Pattern.compile(n.getName() + "\\s+" + NUM_REGEX, Pattern.CASE_INSENSITIVE).matcher(fleetString);
                if (m.find(lastMatch)) {
                    try {
                        fleet[n.ordinal()] = integerFormat.parse(m.group(1)).intValue();
                    } catch (ParseException pe) {
                        fleet[n.ordinal()] = 0;
                    }
                } else {
                    fleet[n.ordinal()] = 0;
                }
            }
        }
    }

    private void readDefence() {
        if (!defenceString.equals("")) {
            int lastMatch = 0;
            String NUM_REGEX = getIntegerRegex();
            for (ShipType n : EnumSet.range(ShipType.LANZAMISILES, ShipType.MISIL_INTERPLANETARIO)) {
                Matcher m = Pattern.compile(n.getName() + "\\s+" + NUM_REGEX, Pattern.CASE_INSENSITIVE).matcher(defenceString);
                if (m.find(lastMatch)) {
                    try {
                        fleet[n.ordinal()] = integerFormat.parse(m.group(1)).intValue();
                    } catch (ParseException pe) {
                        fleet[n.ordinal()] = 0;
                    }
                } else {
                    fleet[n.ordinal()] = 0;
                }
            }
        }
    }

    private void readBuildings() {
        if (!buildingString.equals("")) {
            int lastMatch = 0;
            String NUM_REGEX = getIntegerRegex();
            for (Building e : Building.values()) {
                Matcher m = Pattern.compile(Pattern.quote(e.getName()) + "\\s+" + NUM_REGEX, Pattern.CASE_INSENSITIVE).matcher(buildingString);
                if (m.find(lastMatch)) {
                    try {
                        building[e.ordinal()] = integerFormat.parse(m.group(1)).intValue();
                    } catch (ParseException pe) {
                        building[e.ordinal()] = 0;
                    }
                } else {
                    building[e.ordinal()] = 0;
                }
            }
        }
    }

    private void readTechs() {
        if (!technologyString.equals("")) {
            int lastMatch = 0;
            String NUM_REGEX = getIntegerRegex();
            for (Technology t : Technology.values()) {
                Matcher m = Pattern.compile(Pattern.quote(t.getName()) + "\\s+" + NUM_REGEX, Pattern.CASE_INSENSITIVE).matcher(technologyString);
                if (m.find(lastMatch)) {
                    try {
                        technology[t.ordinal()] = integerFormat.parse(m.group(1)).intValue();
                    } catch (ParseException pe) {
                        technology[t.ordinal()] = 0;
                    }
                } else {
                    technology[t.ordinal()] = 0;
                }
            }
        }
    }

    public Coordinate getCoord() {
        return coord;
    }

    public Date getDate() {
        return date;
    }

    public long[] getFleet() {
        return fleet;
    }

    public String getPlanetName() {
        return planetName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public long[] getResources() {
        return resources;
    }

    public long[] getTechnology() {
        return technology;
    }

    public long[] getBuilding() {
        return building;
    }

    public boolean hasFleet() {
        if (hasFleetData()) {
            for (ShipType n : EnumSet.range(ShipType.NAVE_PEQUEÑA_CARGA, ShipType.ACORAZADO)) {
                if (fleet[n.ordinal()] != 0) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    public boolean hasBuildings() {
        if (hasBuildingData()) {
            for (Building n : Building.values()) {
                if (building[n.ordinal()] != 0) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    public boolean hasTechnology() {
        if (hasTechnologyData()) {
            for (Technology n : Technology.values()) {
                if (technology[n.ordinal()] != 0) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    public boolean hasDefence() {
        if (hasDefenceData()) {
            for (ShipType n : EnumSet.range(ShipType.LANZAMISILES, ShipType.MISIL_INTERPLANETARIO)) {
                if (fleet[n.ordinal()] != 0) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    public boolean hasFleetData() {
        if (!fleetString.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasDefenceData() {
        if (!defenceString.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasBuildingData() {
        if (!buildingString.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasTechnologyData() {
        if (!technologyString.equals("")) {
            return true;
        } else {
            return false;
        }
    }
}
