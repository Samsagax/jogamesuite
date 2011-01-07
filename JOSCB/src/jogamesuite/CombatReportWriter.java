/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jogamesuite;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import jogamesuite.lib.CombatReportReader;
import jogamesuite.lib.Fleet;
import jogamesuite.lib.ForumWriter;
import jogamesuite.lib.PlayerInfo;
import jogamesuite.lib.Resource;
import jogamesuite.lib.ShipType;

/**
 *
 * @author Joaquin
 */
public class CombatReportWriter extends ForumWriter {

    private boolean showCoord = false;
    private boolean showTech = false;
    final private CombatReportReader reader;

    CombatReportWriter(CombatReportReader reader, int forumType, int colorType) {
        super(forumType, colorType);
        this.reader = reader;
    }

    public boolean isShowCoord() {
        return showCoord;
    }

    public void setShowCoord(boolean showCoord) {
        this.showCoord = showCoord;
    }

    public boolean isShowTech() {
        return showTech;
    }

    public void setShowTech(boolean showTech) {
        this.showTech = showTech;
    }

    public String writeReport(int codeType, boolean showTech, boolean showCoord) {
        setCodeType(codeType);
        setShowTech(showTech);
        setShowCoord(showCoord);
        return writeReport();
    }

    private String writeReport() {
        String s = "";
        s += writeReportHeader();
        s += writeAttackerSummary();
        s += writeDefenderSummary();
        s += writeResultSummary();
        s += writeDebrisSummary();
        s += writeProfitSummary();
        s += writeReportFooter();
        return s;
    }

    private String writeAttackerSummary() {
        PlayerInfo[] att = reader.getAttacker();
        String h = tagTitle("Atacantes (" + att.length + "):")
                + NL;
        return h + writePlayerSummary(att, color[2]);
    }

    private String writeDefenderSummary() {
        PlayerInfo[] def = reader.getDefender();
        String h = tagTitle("Defensores (" + def.length + "):")
                + NL;
        return h + writePlayerSummary(def, color[3]);
    }

    private String writePlayerSummary(PlayerInfo[] player, String colorString) {
        String str = "";
        for (int i = 0; i < player.length; i++) {
            str += writeFleetSummary(player[i], colorString) + NL;
            String t = "Pérdidas: ";
            for (Resource r : Resource.values()) {
                t += tagBold(tagColor(integerFormat.format(player[i].getLoss(r)), color[5]));
                t += " " + r.toString();
                if (r != Resource.DEUTERIO) {
                    t += ", ";
                }
            }
            t = tagColor(t, color[0]);
            t += NL;
            String u = tagColor("Total: "
                    + tagBold(tagColor(integerFormat.format(player[i].getTotalLoss()), color[5]))
                    + " unidades.", color[0])
                    + NL + NL;
            str += tagItalic(t + u);
        }
        return str;
    }

    private String writeFleetSummary(PlayerInfo p, String colorString) {
        String str = "";
        String h = tagName(p.getName(), colorString)
                + NL;
        String[] k = new String[3];
        if (showTech) {
            DecimalFormat techFormat = percentFormat;
            techFormat.setMultiplier(10);
            k[0] = techFormat.format(p.getTech(0));
            k[1] = techFormat.format(p.getTech(1));
            k[2] = techFormat.format(p.getTech(2));
        } else {
            for (int i = 0; i < k.length; i++) {
                k[i] = "XXX%";
            }
        }
        h += tagColor(
                tagFont(
                tagFontSize(
                "Armas: "
                + tagBold(tagColor(k[0], color[1]))
                + " Escudos: "
                + tagBold(tagColor(k[1], color[1]))
                + " Casco: "
                + tagBold(tagColor(k[2], color[1])), 11), "Tahoma"), color[0]);
        h += NL + NL;
        str += h;
        String b = writeShipSummary(p, colorString);
        str += tagItalic(b);
        return str;
    }

    private String writeShipSummary(PlayerInfo player, String colorString) {
        Fleet losses;
        String s = "";
        if (showCoord) {
            for (int i = 0; i < player.getFleetCount(); i++) {
                s += tagBold(tagColor(
                        "Flota " + (i + 1) + " " + player.getFleetStart(i).getCoords().toString(), color[0])) + NL;
                losses = player.getLossFleet(i);
                Fleet had = player.getFleetStart(i);
                for (ShipType ship : Fleet.SHIP_TYPES_COUNT) {
                    if (had.getShips(ship.ordinal()) != 0) {
                        s += writeShipLoss(ship,
                                had.getShips(ship.ordinal()),
                                losses.getShips(ship.ordinal()),
                                colorString);
                    }
                }
            }
        } else {
            losses = player.getLossFleetTotal();
            Fleet had = player.getFleetStartTotal();
            for (ShipType ship : Fleet.SHIP_TYPES_COUNT) {
                if (had.getShips(ship.ordinal()) != 0) {
                    s += writeShipLoss(ship,
                            had.getShips(ship.ordinal()),
                            losses.getShips(ship.ordinal()),
                            colorString);
                }
            }
        }
        return s;
    }

    private String writeShipLoss(ShipType shipType, int had, int lost, String colorString) {
        String s = shipType.getName() + " "
                + writeColoredNumber(had, colorString)
                + ". Perdió "
                + writeColoredNumber(lost, color[4])
                + NL;
        return s;
    }

    private String writeResultSummary() {
        String str = "";
        String h = "";
        switch (reader.getResult()) {
            case 1:
                long[] capture = reader.getCapture();
                h += tagBold(
                        tagColor(
                        tagFontSize("El atacante ganó la batalla!", 12),
                        color[6]) + NL
                        + "Captura: "
                        + tagItalic(
                        tagColor(integerFormat.format(capture[0]), color[5])
                        + " Metal, "
                        + tagColor(integerFormat.format(capture[1]), color[5])
                        + " Cristal, "
                        + tagColor(integerFormat.format(capture[2]), color[5])
                        + " Deuterio."))
                        + NL;
                break;
            case -1:
                h += tagBold(
                        tagColor(
                        tagFontSize("El defensor ganó la batalla!", 12),
                        color[6])) + NL;
                break;
            case 0:
                h += tagBold(
                        tagColor(
                        tagFontSize("El combate termina en empate y ambas flotas regresan a sus planetas de origen.", 12),
                        color[6])) + NL;
                break;
        }
        str += h;
        // Write Losses
        String l = "";
        l += tagBold(tagFontSize(tagColor("Pérdidas: ", color[6]), 12) + NL
                + tagItalic(
                "Atacante: "
                + writeColoredNumber(reader.getAttLossTotal(), color[2]) + NL
                + "Defensor: "
                + writeColoredNumber(reader.getDefLossTotal(), color[3]) + NL
                + "Totales: "
                + writeColoredNumber(reader.getTotalLosses(), color[1]))) + NL;
        str += l + NL;
        return str;
    }

    private String writeDebrisSummary() {
        long[] deb = reader.getDebris();
        int reciclers = (int) ((deb[0] + deb[1]) / 20000);
        if ((deb[0] + deb[1]) % 20000 != 0) {
            reciclers++;
        }
        String str = "";
        str += tagTitle2("Escombros (" + integerFormat.format(reciclers) + " Recicladores)")
                + NL;
        str += tagColor("Metal: "
                + tagFontSize(tagColor(integerFormat.format(deb[0]), color[5]), 12) + NL
                + "Cristal: "
                + tagFontSize(tagColor(integerFormat.format(deb[1]), color[5]), 12), color[0])
                + NL;
        // Write monn chance
        String mc = tagTitle2("Probabilidad de luna: ");
        if ((deb[0] + deb[1]) < 100000) {
            mc += tagColor("No hay suficientes escombros.", color[1]) + NL;
        } else {
            double pl = (deb[0] + deb[1]) / 100000;
            if (pl >= 20) {
                pl = 20;
            }
            mc += tagFontSize(tagItalic(tagColor(integerFormat.format(pl) + "%.", color[4])), 12) + NL;
        }
        str += mc + NL;
        return str;
    }

    private String writeProfitSummary() {
        long[] deb = reader.getDebris();
        int reciclers = (int) ((deb[0] + deb[1]) / 20000);
        if ((deb[0] + deb[1]) % 20000 != 0) {
            reciclers++;
        }
        String p = "";
        p += tagTitle2("Rentabilidad (" + integerFormat.format(reciclers) + " Recicladores):") + NL;
        long[] capt = reader.getCapture();
        long[] attLoss = reader.getAttLoss();
        long attLossTotal = reader.getAttLossTotal();
        long abs = deb[0] + deb[1] + capt[0] + capt[1] + capt[2] - attLossTotal;
        double rel;
        String atrec = "Atacante (Reciclando): ";
        if (attLossTotal == 0) {
            atrec += "[MAX] ";
        } else {
            rel = ((double) abs / (double) attLossTotal);
            atrec += "[" + percentFormat.format(rel) + "] ";
        }
        atrec += integerFormat.format(abs) + NL;
        atrec = tagColor(tagFontSize(tagBold(atrec), 12), color[7]);
        atrec += writeResourcesNumbers(deb[0] + capt[0] - attLoss[0],
                deb[1] + capt[1] - attLoss[1],
                capt[2] - attLoss[2]);
        p += atrec + NL;
        String atnot = "Atacante (Sin Reciclar): ";
        abs = capt[0] + capt[1] + capt[2] - attLossTotal;
        if (attLossTotal == 0) {
            atnot += "[MAX] ";
        } else {
            rel = ((double) abs / (double) attLossTotal);
            atnot += "[" + percentFormat.format(rel) + "] ";
        }
        atnot += integerFormat.format(abs) + NL;
        atnot = tagColor(tagFontSize(tagBold(atnot), 12), color[8]);
        atnot += writeResourcesNumbers(capt[0] - attLoss[0],
                capt[1] - attLoss[1],
                capt[2] - attLoss[2]);
        p += atnot + NL;
        long[] defLoss = reader.getDefLoss();
        long defLossTotal = reader.getDefLossTotal();
        String defrec = "Defensor (Reciclando): ";
        abs = deb[0] + deb[1] - defLossTotal;
        if (defLossTotal == 0) {
            defrec += "[MAX] ";
        } else {
            rel = ((double) abs / (double) defLossTotal);
            defrec += "[" + percentFormat.format(rel) + "] ";
        }
        defrec += integerFormat.format(abs) + NL;
        defrec = tagColor(tagFontSize(tagBold(defrec), 12), color[9]);
        defrec += writeResourcesNumbers(deb[0] - defLoss[0],
                deb[1] - defLoss[1],
                -defLoss[2]);
        p += defrec + NL;
        return p;
    }

    private String writeColoredNumber(long num, String color) {
        return tagColor(integerFormat.format(num), color);
    }

    private String writeResourcesNumbers(long metal, long cristal, long deuterium) {
        String s = tagItalic(
                "("
                + writeColoredNumber(metal, color[5])
                + " Metal, "
                + writeColoredNumber(cristal, color[5])
                + " Cristal, "
                + writeColoredNumber(deuterium, color[5])
                + " Deuterio)")
                + NL;
        return s;
    }

    private String writeReportHeader() {
        Date d = reader.getDate();
        DateFormat dateFormat = DateFormat.getDateInstance();
        DateFormat timeFormat = DateFormat.getTimeInstance();
        // TODO Cambiar el timezone para hacer coincidir la hora con la hora local
        String dStr = "";
        String hStr = "";
        try { // Workarround para problemas en win7 con el formato de fechas
            dStr = dateFormat.format(d);
            hStr = timeFormat.format(d);
        } catch (Exception e) {
            dStr = "desconocido";
            hStr = "hora desconocida";
        }
        String s = "Batalla el día "
                + dStr + " a las "
                + hStr + " (hora server):" + NL
                + "La batalla duró "
                + reader.getRoundCount()
                + " rondas.";
        s = tagHeader(s) + NL + NL;
        return s;
    }

    private String writeReportFooter() {
        String s = "Reporte compactado por ";
        s += tagURL("http://code.google.com/p/jogamesuite/", "JOgame Suite") + NL;
        return tagFooter(s);
    }
}
