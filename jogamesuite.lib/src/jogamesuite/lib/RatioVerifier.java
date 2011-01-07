/*
 *  Copyright (C) 2010 joaquin
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

import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author joaquin
 */
public class RatioVerifier extends InputVerifier implements FocusListener {

    final private double MAX_VALUE;
    final private double MIN_VALUE;
    private double verifiedValue;
    DecimalFormat doubleFormat = (DecimalFormat) NumberFormat.getNumberInstance();

    public RatioVerifier() {
        this(5, 1);
    }

    public RatioVerifier(double MAX_VALUE) {
        this(MAX_VALUE, 1);
    }

    public RatioVerifier(double MAX_VALUE, double MIN_VALUE) {
        this.MAX_VALUE = MAX_VALUE;
        this.MIN_VALUE = MIN_VALUE;
    }

    @Override
    public boolean shouldYieldFocus(JComponent input) {
        boolean checked = verify(input);
        showPretty(input);
        if (!checked) {
            Toolkit.getDefaultToolkit().beep();
            focusGained(new FocusEvent(input, FocusEvent.FOCUS_GAINED));
        }
        return checked;
    }

    @Override
    public boolean verify(JComponent input) {
        if (input instanceof JTextField) {
            JTextField field = (JTextField) input;
            return checkRatioField(field, false);
        } else {
            return true;
        }
    }

    public void focusGained(FocusEvent fe) {
        if (fe.getComponent() instanceof JTextField) {
            JTextField field = (JTextField) fe.getComponent();
            field.selectAll();
        }
    }

    public void focusLost(FocusEvent fe) {
    }

    private void showPretty(JComponent input) {
        if (input instanceof JTextField) {
            JTextField field = (JTextField) input;
            checkRatioField(field, true);
        }
    }

    /**
     * Checks for target field for validation. If not valid changes it to the
     * default value if requested by the setIt parameter.
     * @param field
     * @param setIt
     * @return
     */
    private boolean checkRatioField(JTextField field, boolean setIt) {
        double num = MIN_VALUE;
        boolean valid = true;
        try {
            num = doubleFormat.parse(field.getText().replaceAll("\\.", ",")).doubleValue();
        } catch (ParseException pe) {
            pe.printStackTrace();
            valid = false;
        }
        if ((num <= MIN_VALUE) || (num >= MAX_VALUE)) {
            valid = false;
            if (setIt) {
                if (num <= MIN_VALUE) {
                    num = MIN_VALUE;
                } else if (num >= MAX_VALUE) {
                    num = MAX_VALUE;
                }
            }
        }
        if (setIt) {
            field.setText(doubleFormat.format(num));
        }
        return valid;
    }
}
