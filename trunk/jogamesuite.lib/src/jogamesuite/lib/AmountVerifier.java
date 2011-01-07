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

import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.NumberFormat;
import java.text.ParseException;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 * This Verifier checks for a long value between MIN_VALUE and MAX_VALUE. Also
 * it is possible to set a DEFAUL_VALUE for the item checked (if not set the
 * DEFAULT_VALUE equals MIN_VALUE.
 *
 * @author Joaquin
 */
public final class AmountVerifier extends InputVerifier implements FocusListener {

    final private long MAX_VALUE;
    final private long MIN_VALUE;
    final private long DEFAULT_VALUE;
    NumberFormat intFormat = NumberFormat.getIntegerInstance();
    private long verifiedValue;

    /**
     * Constructs an InputVerifier with the specified min, max and default values.
     * @param MAX_VALUE
     * @param MIN_VALUE
     * @param DEFAULT_VALUE
     */
    public AmountVerifier(long MAX_VALUE, long MIN_VALUE, long DEFAULT_VALUE) {
        this.MAX_VALUE = MAX_VALUE;
        this.MIN_VALUE = MIN_VALUE;
        this.DEFAULT_VALUE = DEFAULT_VALUE;
    }

    /**
     * Constructs a InputVerifier with the specified min and max values. Default value is set to the minimum
     * @param MAX_VALUE
     * @param MIN_VALUE
     */
    public AmountVerifier(long MAX_VALUE, long MIN_VALUE) {
        this(MAX_VALUE, MIN_VALUE, MIN_VALUE);
    }

    /**
     * Constructs an InputVerifier with the default parameters.
     */
    public AmountVerifier() {
        this(Long.MAX_VALUE, 0);
    }

    /**
     * Verifica la entrada en el componente input. Llama al método verify y
     * mantiene el foco si no es valido.
     * @param input
     * @return if this input JComponent should yield focus.
     */
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

    /**
     * Verifica el componente input. De no ser válido devuelve falso
     * @param input
     * @return Validation of JComponent
     */
    @Override
    public boolean verify(JComponent input) {
        if (input instanceof JTextField) {
            JTextField field = (JTextField) input;
            return checkAmountField(field, false);
        } else {
            return true;
        }
    }

    public void focusGained(FocusEvent e) {
        if (e.getComponent() instanceof JTextField) {
            JTextField field = (JTextField) e.getComponent();
            field.selectAll();
        }
    }

    public void focusLost(FocusEvent e) {
        //TODO lost focus method
    }

    private void showPretty(JComponent input) {
        if (input instanceof JTextField) {
            JTextField field = (JTextField) input;
            checkAmountField(field, true);
        }
    }

    /**
     * Checks for target field for validation. If not valid changes it to the
     * default value if requested by the setIt parameter.
     * @param field
     * @param setIt
     * @return
     */
    private boolean checkAmountField(JTextField field, boolean setIt) {
        long num = DEFAULT_VALUE;
        boolean valid = true;
        try {
            num = intFormat.parse(field.getText()).longValue();
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
            field.setText(intFormat.format(num));
        }
        if (valid){
            verifiedValue = num;
        }
        return valid;
    }

    public long getVerifiedValue() {
        return verifiedValue;
    }
}
