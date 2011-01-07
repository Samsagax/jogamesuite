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
public enum Building {

    MINA_METAL("Mina de metal"),
    MINA_CRISTAL("Mina de cristal"),
    SINTETIZADOR_DEUTERIO("Sintetizador de deuterio"),
    PLANTA_SOLAR("Planta de energía solar"),
    PLANTA_FUSION("Planta de fusión"),
    FABRICA_ROBOTS("Fábrica de robots"),
    FABRICA_NANOBOTS("Fábrica de nanobots"),
    HANGAR("Hangar"),
    ALMACEN_METAL("Almacén de metal"),
    ALMACEN_CRISTAL("Almacén de cristal"),
    DEPOSITO_DEUTERIO("Depósito de la alianza"),
    LABORATORIO_INVESTIGACION("Laboratorio de investigación"),
    TERRAFORMER("Terraformer"),
    DEPOSITO_ALIANZA("Depósito de la alianza"),
    BASE_LUNAR("Base lunar"),
    SENSOR_PHALANX("Sensor Phalanx"),
    SALTO_CUANTICO("Salto cuántico"),
    SILO("Silo");

    // Fields
    private String name;

    Building(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Building{" + "name=" + name + '}';
    }
    
}
