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
 *
 * @author Joaquin
 */
public enum Technology {

    ESPIONAJE("Tecnología de espionaje"),
    COMPUTACION("Tecnología de computación"),
    MILITAR("Tecnología militar"),
    DEFENSA("Tecnología de defensa"),
    BLINDAJE("Tecnología de blindaje"),
    ENERGIA("Tecnología de energía"),
    HIPERESPACIO("Tecnología de hiperespacio"),
    MOTOR_COMBUSTION("Motor de combustión"),
    MOTOR_IMPULSO("Motor de impulso"),
    PROPULSOR_HIPERESPACIAL("Propulsor hiperespacial"),
    LASER("Tecnología láser"),
    IONICA("Tecnología iónica"),
    PLASMA("Tecnología de plasma"),
    RED_INTERGALACTICA("Red de investigación intergaláctica"),
    ASTROFISICA("Astrofísica"),
    GRAVITON("Tecnología de gravitón");
    // Fields
    private String name;

    Technology(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Technology{" + "name=" + name + '}';
    }
    
}
