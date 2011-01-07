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
 * An interface for text formatting in forums
 *
 * @author Joaquin
 */
public interface ForumFormatter {

    final int HTML_CODE = 1;
    final int PHPBB_CODE = 0;

    final int COLOR_DARK = 1;
    final int COLOR_LIGHT = 0;

    String tagBold(String s);
    String tagItalic(String s);
    String tagColor(String s, String colorNumber);
    String tagFont(String s, String font);
    String tagFontSize(String s, int size);
    String tagURL(String URL, String name);
}
