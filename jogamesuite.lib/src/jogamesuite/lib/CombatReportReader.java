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
import java.util.Date;
import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Joaquin
 */
public class CombatReportReader extends ReaderFormatter {

    /*
     * TODO Necesita limpieza el archivo entero.
     */
    private final String report;
    private String firstRound;
    private String lastRound;
    //
    private Date date;
    private int roundCount;
    private int result = 0;
    //
    private PlayerInfo[] attacker;
    private PlayerInfo[] defender;
    private long[] capture = new long[3];
    private long[] debris = new long[3];
    //
    private int PATTERN_FLAG = Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE;

    public CombatReportReader(String report) {
        this.report = report;
        setRoundInfo();
        setNames();
        setAttInfo();
        setDefInfo();
        setResultsInfo();
    }

    private void setRoundInfo() {
        // Necesita limpieza
        String SEPARADOR_RONDAS_REGEX = "\\QLa flota atacante dispara\\E"
                + ".+"
                + "\\Qpuntos de daño.\\E\\n+"
                + "\\QLa flota defensora dispara\\E"
                + ".+"
                + "\\Qpuntos de daño.\\E";
        Pattern p = Pattern.compile(SEPARADOR_RONDAS_REGEX, PATTERN_FLAG);
        String[] s = p.split(report);
        this.firstRound = s[0];
        this.lastRound = s[s.length - 1];
        this.roundCount = s.length - 1;
        String ENCABEZADO_REGEX = "\\QLas siguientes flotas se enfrentaron el (\\E"
                + getDateRegex() + " " + getTimeRegex()
                + "\\Q):\\E";
        Matcher timeMat = Pattern.compile(ENCABEZADO_REGEX, PATTERN_FLAG).matcher(firstRound);
        if (timeMat.find()) {
            try {
                date = DayTimeFormat.parse((timeMat.group(1) + " " + timeMat.group(2)).replaceAll("\\.", "/"));
            } catch (ParseException e) {
                e.printStackTrace();
                date = null;
            }
        } else {
            date = null;
        }
    }

    private void setNames() {
        Pattern n = Pattern.compile("(.+,? ){1,5}vs\\.( .+,?){1,5}", PATTERN_FLAG);
        Matcher m = n.matcher(firstRound);
        m.find();
        Pattern vs = Pattern.compile(" vs\\. ", PATTERN_FLAG);
        String[] nombres = vs.split(m.group());
        Pattern coma = Pattern.compile(", ");
        String[] attackersName = coma.split(nombres[0]);
        String[] defendersName = coma.split(nombres[1]);
        attacker = new PlayerInfo[attackersName.length];
        defender = new PlayerInfo[defendersName.length];
        for (int i = 0; i < attackersName.length; i++) {
            attacker[i] = new PlayerInfo(attackersName[i].trim());
        }
        for (int i = 0; i < defendersName.length; i++) {
            defender[i] = new PlayerInfo(defendersName[i].trim());
        }
    }

    private Fleet[] initFleets(int count) {
        Fleet[] fleets = new Fleet[count];
        for (int i = 0; i < fleets.length; i++) {
            fleets[i] = new Fleet();
        }
        return fleets;
    }

    private void readHeader(PlayerInfo player) {
        Matcher headMatcher = Pattern.compile(player.getName()
                + " (\\[\\d{1}:\\d{1,3}:\\d{1,2}\\])" + "[\\n\\s]*"
                + "Armas: (\\d{1,})% "
                + "Escudos: (\\d{1,})% "
                + "Casco: (\\d{1,})%",
                PATTERN_FLAG).matcher(firstRound);
        // Count fleets for this player
        int fleetCount = 0;
        while (headMatcher.find()) {
            fleetCount++;
        }
        // Initializes fleets for this player
        if (fleetCount != 0) {
            player.setFleetStart(initFleets(fleetCount));
            player.setFleetEnd(initFleets(fleetCount));
            // Set coordinates for each fleet
            headMatcher.reset();
            for (int j = 0; j < fleetCount; j++) {
                headMatcher.find();
                player.setFleetCoord(j, Coordinate.parseCoords(headMatcher.group(1)));
            }
            // Set technologies for this player
            try {
                player.setTech(0, integerFormat.parse(headMatcher.group(2)).intValue() / 10);
                player.setTech(1, integerFormat.parse(headMatcher.group(3)).intValue() / 10);
                player.setTech(2, integerFormat.parse(headMatcher.group(4)).intValue() / 10);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            player.setFleetStart(initFleets(1));
            player.setFleetEnd(initFleets(1));
        }

    }

    private void readStartFleets(PlayerInfo player) {
        int cursor = 0;
        for (int i = 0; i < player.getFleetCount(); i++) {
            Matcher headMatcher = Pattern.compile(
                    "\\Q"
                    + player.getName()
                    + " " + player.getFleetCoord(i).toString() + "\\E[\\n\\s]*"
                    + "Armas: (\\d{1,})% "
                    + "Escudos: (\\d{1,})% "
                    + "Casco: (\\d{1,})%",
                    PATTERN_FLAG).matcher(firstRound);
            if (headMatcher.find(cursor)) {
                String table = matchTable(firstRound, headMatcher.start());
                fillFleet(table, player.getFleetStart(i));
                cursor = headMatcher.end();
            }
        }
    }

    private void readEndFleets(PlayerInfo player) {
        int cursor = 0;
        for (int i = 0; i < player.getFleetCount(); i++) {
            Matcher headMatcher = Pattern.compile(
                    "\\Q"
                    + player.getName()
                    + " " + player.getFleetCoord(i).toString() + "\\E",
                    PATTERN_FLAG).matcher(lastRound);
            Matcher destroyedMatcher = Pattern.compile(
                    "\\Q"
                    + player.getName()
                    + " fué destruido.\\E",
                    PATTERN_FLAG).matcher(lastRound);
            boolean found = headMatcher.find(cursor);
            boolean destroyed = destroyedMatcher.find(cursor);
            if (destroyed && found) {
                int a = headMatcher.start();
                int b = destroyedMatcher.start();
                if (a < b) {
                    destroyed = false;
                    cursor = headMatcher.end();
                }
            } else if (found) {
                cursor = headMatcher.end();
            }
            if (!destroyed) {
                String table = matchTable(lastRound, headMatcher.end());
                fillFleet(table, player.getFleetEnd(i));
                cursor = headMatcher.end();
            } else {
                player.setFleetEnd(i, new Fleet(player.getFleetCoord(i)));
                cursor = destroyedMatcher.end();
            }
        }
    }

    private String matchTable(String round, int cursor) {
        Matcher tableMatcher = Pattern.compile(
                "Tipo\\s+(?:.+)+\\n"
                + "Cantidad\\s+(?:" + getIntegerRegex() + "\\s*)+\\n"
                + "Armas:\\s+(?:" + getIntegerRegex() + "\\s*)+\\n"
                + "Escudos\\s+(?:" + getIntegerRegex() + "\\s*)+\\n"
                + "Casco\\s+(?:" + getIntegerRegex() + "\\s*)+\\n",
                Pattern.CASE_INSENSITIVE).matcher(round);
        if (tableMatcher.find(cursor)) {
            return tableMatcher.group();
        } else {
            return "";
        }
    }

    private void fillFleet(String fleetTable, Fleet fleet) {
        Matcher typeMatcher = Pattern.compile(
                "Tipo\\s+(?:.+)+\\n",
                PATTERN_FLAG).matcher(fleetTable);
        if (typeMatcher.find()) {
            Matcher countMatcher = Pattern.compile(
                    "Cantidad\\s+("
                    + getIntegerRegex()
                    + "\\s*)+\\n",
                    PATTERN_FLAG).matcher(fleetTable);
            countMatcher.find(typeMatcher.end());
            String cant[] = countMatcher.group().split("\\s+");
            String t = typeMatcher.group();
            int k = 1;
            for (ShipType n : EnumSet.range(ShipType.NAVE_PEQUEÑA_CARGA, ShipType.CUPULA_GRANDE)) {
                Matcher p = Pattern.compile(
                        n.getCompacterREGEX(),
                        PATTERN_FLAG).matcher(t);
                if (p.find()) {
                    try {
                        fleet.setShips(n.ordinal(), integerFormat.parse(cant[k]).intValue());
                    } catch (ParseException pe) {
                        pe.printStackTrace();
                        fleet.setShips(n.ordinal(), 0);
                    }
                    k++;
                } else {
                    fleet.setShips(n.ordinal(), 0);
                }
            }
        }
    }

    private void setPlayerInfo(PlayerInfo player) {
        readHeader(player);
        readStartFleets(player);
        readEndFleets(player);
    }

    private void setAttInfo() {
        for (int i = 0; i < attacker.length; i++) {
            setPlayerInfo(attacker[i]);
        }
    }

    private void setDefInfo() {
        for (int i = 0; i < defender.length; i++) {
            setPlayerInfo(defender[i]);
        }
    }

    private void setResultsInfo() {
        String NUM_REGEX = getIntegerRegex();
        boolean attWin = Pattern.compile("El atacante ganó la batalla!\\s+"
                + "Él captura "
                + NUM_REGEX + "\\Q Metal, \\E"
                + NUM_REGEX + "\\Q Cristal y \\E"
                + NUM_REGEX + "\\Q Deuterio.\\E",
                PATTERN_FLAG).matcher(lastRound).find();
        boolean defWin = Pattern.compile("\\QEl defensor ganó la batalla!\\E",
                PATTERN_FLAG).matcher(lastRound).find();
        if (attWin) {
            result = 1;
            Matcher cMat = Pattern.compile("El atacante ganó la batalla!\\s+Él captura (.+)\\Q Metal,\\E(.+)\\QCristal y\\E(.+)\\QDeuterio.\\E",
                    PATTERN_FLAG).matcher(lastRound);
            cMat.find();
            capture[0] = Integer.valueOf(cMat.group(1).replaceAll("\\.", "").trim());
            capture[1] = Integer.valueOf(cMat.group(2).replaceAll("\\.", "").trim());
            capture[2] = Integer.valueOf(cMat.group(3).replaceAll("\\.", "").trim());
        } else if (defWin) {
            result = -1;
            capture[0] = 0;
            capture[1] = 0;
            capture[2] = 0;
        } else {
            result = 0;
            capture[0] = 0;
            capture[1] = 0;
            capture[2] = 0;
        }
        Matcher d = Pattern.compile("\\QEn estas coordenadas flotan ahora \\E"
                + NUM_REGEX + "\\Q Metal y \\E"
                + NUM_REGEX + "\\Q Cristal.\\E",
                PATTERN_FLAG).matcher(lastRound);
        if (d.find()) {
            debris[0] = Integer.valueOf(d.group(1).replaceAll("\\.", "").trim());
            debris[1] = Integer.valueOf(d.group(2).replaceAll("\\.", "").trim());
        } else {
            debris[0] = 0;
            debris[1] = 0;
        }
    }

    public Date getDate() {
        return date;
    }

    public long[] getCapture() {
        return capture;
    }

    public PlayerInfo[] getAttacker() {
        return attacker;
    }

    public PlayerInfo[] getDefender() {
        return defender;
    }

    public String getReporte() {
        return report;
    }

    public long[] getDebris() {
        return debris;
    }

    public int getResult() {
        return result;
    }

    public int getRoundCount() {
        return roundCount;
    }

    public long getAttLoss(int i) {
        return attacker[i].getTotalLoss();
    }

    public long getDefLoss(int i) {
        return defender[i].getTotalLoss();
    }

    public long[] getAttLoss() {
        long[] loss = {0, 0, 0};
        for (int i = 0; i < attacker.length; i++) {
            loss[0] += attacker[i].getLoss(Resource.METAL);
            loss[1] += attacker[i].getLoss(Resource.CRISTAL);
            loss[2] += attacker[i].getLoss(Resource.DEUTERIO);
        }
        return loss;
    }

    public long[] getDefLoss() {
        long[] loss = {0, 0, 0};
        for (int i = 0; i < defender.length; i++) {
            loss[0] += defender[i].getLoss(Resource.METAL);
            loss[1] += defender[i].getLoss(Resource.CRISTAL);
            loss[2] += defender[i].getLoss(Resource.DEUTERIO);
        }
        loss[0] += capture[0];
        loss[1] += capture[1];
        loss[2] += capture[2];
        return loss;
    }

    public long getAttLossTotal() {
        long l = 0;
        for (int i = 0; i < attacker.length; i++) {
            l += attacker[i].getTotalLoss();
        }
        return l;
    }

    public long getDefLossTotal() {
        long l = 0;
        for (int i = 0; i < defender.length; i++) {
            l += defender[i].getTotalLoss();
        }
        return l;
    }

    public long getTotalLosses() {
        return getAttLossTotal() + getDefLossTotal();
    }
}
