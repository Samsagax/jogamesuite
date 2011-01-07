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

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Simple implementación de la interface ForumFormatter para agregar tags de
 * acuerdo con el tipo de código del foro
 *
 * @author Joaquin
 */
public class ForumWriter implements ForumFormatter {

    private int codeType = PHPBB_CODE;
    private int colorType = COLOR_DARK;
    protected String[] color = new String[10];
    protected String NL;
    protected DecimalFormat integerFormat = initIntegerFormat();
    protected DecimalFormat percentFormat = initPercentFormat();

    public ForumWriter(int codeType, int colorType) {
        this.codeType = codeType;
        this.colorType = colorType;
        setColorScheme(colorType);
        setNewLineString(codeType);
    }

    public ForumWriter() {
    }

    private DecimalFormat initIntegerFormat() {
        DecimalFormat intFormat = (DecimalFormat) NumberFormat.getInstance();
        intFormat.setParseIntegerOnly(true);
        return intFormat;
    }

    private DecimalFormat initPercentFormat() {
        DecimalFormat perFormat = (DecimalFormat) NumberFormat.getPercentInstance();
        return perFormat;
    }

    public int getCodeType() {
        return codeType;
    }

    public void setCodeType(int codeType) {
        this.codeType = codeType;
        setNewLineString(codeType);
    }

    public String tagBold(String s) {
        String tagOpen;
        String tagClose;
        switch (codeType) {
            case PHPBB_CODE:
                tagOpen = "[b]";
                tagClose = "[/b]";
                break;
            case HTML_CODE:
                tagOpen = "<b>";
                tagClose = "</b>";
                break;
            default:
                tagOpen = "[b]";
                tagClose = "[/b]";
                break;
        }
        return tagOpen + s + tagClose;
    }

    public String tagItalic(String s) {
        String tagOpen;
        String tagClose;
        switch (codeType) {
            case PHPBB_CODE:
                tagOpen = "[i]";
                tagClose = "[/i]";
                break;
            case HTML_CODE:
                tagOpen = "<i>";
                tagClose = "</i>";
                break;
            default:
                tagOpen = "[i]";
                tagClose = "[/i]";
                break;
        }
        return tagOpen + s + tagClose;
    }

    public String tagColor(String s, String colorNumber) {
        String tagOpen;
        String tagClose;
        switch (codeType) {
            case PHPBB_CODE:
                tagOpen = "[color=" + colorNumber + "]";
                tagClose = "[/color]";
                break;
            case HTML_CODE:
                tagOpen = "<FONT COLOR=" + colorNumber + ">";
                tagClose = "</FONT>";
                break;
            default:
                tagOpen = "[color=" + colorNumber + "]";
                tagClose = "[/color]";
                break;
        }
        return tagOpen + s + tagClose;
    }

    public String tagFont(String s, String font) {
        String tagOpen;
        String tagClose;
        switch (codeType) {
            case PHPBB_CODE:
                tagOpen = "[font=" + font + "]";
                tagClose = "[/font]";
                break;
            case HTML_CODE:
                tagOpen = "<FONT FACE=" + font + ">";
                tagClose = "</FONT>";
                break;
            default:
                tagOpen = "[font=" + font + "]";
                tagClose = "[/font]";
                break;
        }
        return tagOpen + s + tagClose;
    }

    public String tagFontSize(String s, int size) {
        String tagOpen;
        String tagClose;
        switch (codeType) {
            case PHPBB_CODE:
                size += 3;
                tagOpen = "[size=" + size + "]";
                tagClose = "[/size]";
                break;
            case HTML_CODE:
                tagOpen = "<span style=\"font-size:" + size + "px\">";
                tagClose = "</span>";
                break;
            default:
                tagOpen = "[size=" + size + "]";
                tagClose = "[/size]";
                break;
        }
        return tagOpen + s + tagClose;
    }

    public String tagURL(String URL, String name) {
        String tagOpen;
        String tagClose;
        switch (codeType) {
            case PHPBB_CODE:
                tagOpen = "[url=" + URL + "]";
                tagClose = "[/url]";
                break;
            case HTML_CODE:
                tagOpen = "<a href=\"" + URL + "\">";
                tagClose = "</a>";
                break;
            default:
                tagOpen = "[url=" + URL + "]";
                tagClose = "[/url]";
        }
        return tagOpen + name + tagClose;
    }

    public String tagURL(String URL) {
        return tagURL(URL, URL);
    }

    private void setNewLineString(int codeType) {
        switch (codeType) {
            case PHPBB_CODE:
                NL = "\n";
                break;
            case HTML_CODE:
                NL = "<br />\n";
                break;
            default:
                NL = "\n";
        }
    }

    private void setColorScheme(int colorType) {
        String[] colorScheme = new String[10];
        switch (colorType) {
            case COLOR_DARK: // Dark Background
                colorScheme[0] = "#ECF8E0"; // Base
                colorScheme[1] = "#F2F5A9"; // Info
                colorScheme[2] = "#FF8000"; // Attacker Name
                colorScheme[3] = "#00FF00"; // Defender Name
                colorScheme[4] = "#81F7F3"; // Ship Losses
                colorScheme[5] = "#088A08"; // Losses
                colorScheme[6] = "#A9D0F5"; // Results
                colorScheme[7] = "#00FF00"; // Att Rec
                colorScheme[8] = "#FFFF00"; // Att NoRec
                colorScheme[9] = "#FE9A2E"; // Def Rec
                break;
            case COLOR_LIGHT: // Light Background
                colorScheme[0] = "#0B615E"; // Base
                colorScheme[1] = "#B45F04"; // Info
                colorScheme[2] = "#FF1100"; // Attacker Name
                colorScheme[3] = "#0000FF"; // Defender Name
                colorScheme[4] = "#088A85"; // Ship Losses
                colorScheme[5] = "#088A08"; // Losses
                colorScheme[6] = "#0404B4"; // Results
                colorScheme[7] = "#04B404"; //Att Rec
                colorScheme[8] = "#8A4B08"; //Att NoRec
                colorScheme[9] = "#FF0000"; //Def Rec
                break;
        }
        color = colorScheme;
    }

    public String getColor(int index) {
        return color[index];
    }

    public String[] getColorScheme() {
        return color;
    }

    public int getColorType() {
        return colorType;
    }

    public void setColorType(int colorType) {
        this.colorType = colorType;
        setColorScheme(colorType);
    }

    protected String tagTitle(String s) {
        return tagColor(tagFontSize(tagFont(s, "Arial"), 16), color[0]);
    }

    protected String tagTitle2(String s) {
        return tagBold(tagColor(tagFontSize(s, 12), color[6]));
    }

    protected String tagName(String name, String color) {
        return tagBold(tagColor(tagFont(tagFontSize(name, 14), "Verdana"), color));
    }

    protected String tagHeader(String header) {
        return tagBold(tagFontSize(header, 12));
    }

    protected String tagFooter(String footer) {
        return tagBold(tagItalic(tagColor(footer, "#DF7401")));
    }

    protected String tagColoredNumber(long number, String color) {
        return tagItalic(tagColor(integerFormat.format(number), color));
    }

    protected String tagInfoMessageColor(String msg, String color) {
        return tagFontSize(tagColor(msg, color), 12);
    }
}
