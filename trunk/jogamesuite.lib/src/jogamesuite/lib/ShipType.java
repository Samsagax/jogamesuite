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
public enum ShipType {

    NAVE_PEQUEÑA_CARGA("Nave pequeña de carga", //name
    "(\\QP.Carga\\E){1}", //compacterREGEX
    setCostArray(2000, 2000, 0), //cost
    400, //hull
    10, //shield
    5, //attack
    5000, //speed
    5000, //payload
    20, //consumption
    0, //EngineType
    setFastFireArray( //fast fire array
    3, -3,
    8, 5,
    10, 5,
    12, -250,
    13, -3)),
    NAVE_GRANDE_CARGA("Nave grande de carga", //name
    "(\\QGr.Carga\\E){1}", //compacterREGEX
    setCostArray(6000, 6000, 0), //cost
    1200, //hull
    25, //shield
    5, //attack
    7500, //speed
    25000, //payload
    50, //consumption
    0, //EngineType
    setFastFireArray( //fast fire array
    8, 5,
    10, 5,
    12, -250,
    13, -3)),
    CAZA_LIGERO("Cazador ligero", //name
    "(\\QCazador L.\\E){1}", //compacterREGEX
    setCostArray(3000, 1000, 0), //cost
    400, //hull
    10, //shield
    40, //attack
    12500, //speed
    50, //payload
    20, //consumption
    0, //EngineType
    setFastFireArray( //fast fire array
    4, -6,
    8, 5,
    10, 5,
    12, -200)),
    CAZA_PESADO("Cazador pesado", //name
    "(\\QCazador P.\\E){1}", //compacterREGEX
    setCostArray(6000, 4000, 0), //cost
    1000, //hull
    25, //shield
    150, //attack
    10000, //speed
    100, //payload
    75, //consumption
    0, //EngineType
    setFastFireArray( //fast fire array
    0, 3,
    8, 5,
    10, 5,
    12, -100,
    13, -4)),
    CRUCERO("Crucero", //name
    "(\\QCrucero\\E){1}", //compacterREGEX
    setCostArray(20000, 7000, 2000), //cost
    2700, //hull
    50, //shield
    400, //attack
    15000, //speed
    800, //payload
    300, //consumption
    0, //EngineType
    setFastFireArray( //fast fire array
    2, 6,
    8, 5,
    10, 5,
    12, -33,
    13, -4,
    14, 10)),
    NAVE_BATALLA("Nave de batalla", //name
    "(\\QNave de Batalla\\E){1}", //compacterREGEX
    setCostArray(45000, 15000, 0), //cost
    6000, //hull
    200, //shield
    1000, //attack
    10000, //speed
    1500, //payload
    500, //consumption
    0, //EngineType
    setFastFireArray( //fast fire array
    8, 5,
    10, 5,
    12, -30,
    13, -7)),
    COLONIZADOR("Nave colonizadora", //name
    "(\\QColonizador\\E){1}", //compacterREGEX
    setCostArray(10000, 20000, 0), //cost
    3000, //hull
    100, //shield
    5000, //attack
    2500, //speed
    7500, //payload
    1000, //consumption
    0, //EngineType
    setFastFireArray( //fast fire array
    8, 5,
    10, 5,
    12, -250)),
    RECICLADOR("Reciclador", //name
    "(\\QReciclador\\E){1}", //compacterREGEX
    setCostArray(10000, 6000, 2000), //cost
    1600, //hull
    10, //shield
    1, //attack
    2000, //speed
    20000, //payload
    300, //consumption
    0, //EngineType
    setFastFireArray( //fast fire array
    8, 5,
    10, 5,
    12, -250)),
    SONDA("Sonda de espionaje", //name
    "(\\QSonda\\E){1}", //compacterREGEX
    setCostArray(0, 1000, 0), //cost
    100, //hull
    0.01, //shield
    0.01, //attack
    100000000, //speed
    5, //payload
    1, //consumption
    0, //EngineType
    setFastFireArray( //fast fire array
    0, -5,
    1, -5,
    2, -5,
    3, -5,
    4, -5,
    5, -5,
    6, -5,
    7, -5,
    9, -5,
    11, -5,
    12, -1250,
    13, -5)),
    BOMBARDERO("Bombardero", //name
    "(\\QBombardero\\E){1}", //compacterREGEX
    setCostArray(50000, 25000, 15000), //cost
    7500, //hull
    500, //shield
    1000, //attack
    4000, //speed
    500, //payload
    1000, //consumption
    0, //EngineType
    setFastFireArray( //fast fire array
    8, 5,
    10, 5,
    12, -25,
    14, 20,
    15, 20,
    16, 10,
    18, 10)),
    SATELITE("Satélite solar", //name
    "(\\QSatélite S.\\E){1}", //compacterREGEX
    setCostArray(0, 2000, 500), //cost
    200, //hull
    1, //shield
    1, //attack
    setFastFireArray( //fast fire array
    0, -5,
    1, -5,
    2, -5,
    3, -5,
    4, -5,
    5, -5,
    6, -5,
    7, -5,
    9, -5,
    11, -5,
    12, -1250,
    13, -5)),
    DESTRUCTOR("Destructor", //name
    "(\\QDestructor\\E){1}", //compacterREGEX
    setCostArray(60000, 50000, 15000), //cost
    11000, //hull
    500, //shield
    2000, //attack
    5000, //speed
    2000, //payload
    1000, //consumption
    0, //EngineType
    setFastFireArray( //fast fire array
    8, 5,
    10, 5,
    12, -5,
    13, 2,
    15, 10)),
    ESTRELLA_MUERTE("Estrella de la muerte", //name
    "(\\QEst.Muerte\\E){1}", //compacterREGEX
    setCostArray(5000000, 4000000, 1000000), //cost
    900000, //hull
    50000, //shield
    200000, //attack
    100, //speed
    1000000, //payload
    1, //consumption
    0, //EngineType
    setFastFireArray( //fast fire array
    0, 250,
    1, 250,
    2, 200,
    3, 100,
    4, 33,
    5, 30,
    6, 250,
    7, 250,
    8, 1250,
    9, 25,
    10, 1250,
    11, 5,
    13, 15,
    14, 200,
    15, 200,
    16, 100,
    17, 50,
    18, 100)),
    ACORAZADO("Acorazado", //name
    "(\\QCruc. de Bat.\\E){1}", //compacterREGEX
    setCostArray(30000, 40000, 15000), //cost
    7000, //hull
    400, //shield
    700, //attack
    10000, //speed
    750, //payload
    250, //consumption
    0, //EngineType
    setFastFireArray( //fast fire array
    0, 3,
    1, 3,
    3, 4,
    4, 4,
    5, 7,
    8, 5,
    10, 5,
    11, -2,
    12, -15)),
    LANZAMISILES("Lanzamisiles", //name
    "(\\QMisil\\E){1}", //compacterREGEX
    setCostArray(2000, 0, 0), //cost
    200, //hull
    20, //shield
    80, //attack
    setFastFireArray( //fast fire array
    4, -10,
    9, -20,
    12, -200)),
    LASER_PEQUEÑO("Láser pequeño", //name
    "(\\QLáser Peq.\\E){1}", //compacterREGEX
    setCostArray(1500, 500, 0), //cost
    200, //hull
    25, //shield
    100, //attack
    setFastFireArray( //fast fire array
    9, -20,
    11, -10,
    12, -200)),
    LASER_GRANDE("Láser grande", //name
    "(\\QLáser Gr.\\E){1}", //compacterREGEX
    setCostArray(6000, 2000, 0), //cost
    800, //hull
    100, //shield
    250, //attack
    setFastFireArray( //fast fire array
    9, -10,
    12, -100)),
    CAÑON_GAUSS("Cañón gauss", //name
    "(\\QC.Gauss\\E){1}", //compacterREGEX
    setCostArray(20000, 15000, 2000), //cost
    3500, //hull
    200, //shield
    1100, //attack
    setFastFireArray( //fast fire array
    12, -50)),
    CAÑON_IONICO("Cañón iónico", //name
    "(C.Iónico){1}", //compacterREGEX
    setCostArray(2000, 6000, 0), //cost
    800, //hull
    500, //shield
    150, //attack
    setFastFireArray( //fast fire array
    9, -10,
    12, -100)),
    CAÑON_PLASMA("Cañón de plasma", //name
    "(\\QC.Plasma\\E){1}", //compacterREGEX
    setCostArray(50000, 50000, 30000), //cost
    10000, //hull
    300, //shield
    3000, //attack
    setFastFireArray( //fast fire array
    )),
    CUPULA_PEQUEÑA("Cúpula pequeña de defensa", //name
    "(Cúpula Peq\\.){1}", //compacterREGEX
    setCostArray(10000, 10000, 0), //cost
    2000, //hull
    2000, //shield
    1, //attack
    setFastFireArray( //fast fire array
    )),
    CUPULA_GRANDE("Cúpula grande de defensa", //name
    "(Cúpula Gr\\.){1}", //compacterREGEX
    setCostArray(50000, 50000, 0), //cost
    10000, //hull
    10000, //shield
    1, //attack
    setFastFireArray( //fast fire array
    )),
    MISIL_INTERCEPCION("Misil de intercepción", //name
    setCostArray(8000, 0, 2000), //cost
    800, //hull
    1, //shield
    1), //attack
    MISIL_INTERPLANETARIO("Misil interplanetario", //name
    setCostArray(12500, 2500, 10000), //cost
    1500, //hull
    1, //shield
    12000); //attack
    //Fields
    private String name;
    private String compacterREGEX;
    private Integer[] cost;
    private Integer hull;
    private Integer shield;
    private Integer attack;
    private Integer speed;
    private Integer payload;
    private Integer consumption;
    private Integer[] fastFire;
    private int engine;

    ShipType(String name,
            String compacterREGEX,
            Integer[] cost,
            Integer hull,
            Double shield,
            Double attack,
            Integer speed,
            Integer payload,
            Integer consumption,
            int engine,
            Integer[] fastFire) {
        this.name = name;
        this.compacterREGEX = compacterREGEX;
        this.cost = cost;
        this.hull = hull;
        this.shield = shield.intValue();
        this.attack = attack.intValue();
        this.speed = speed;
        this.payload = payload;
        this.consumption = consumption;
        this.engine = engine;
        this.fastFire = fastFire;
    }

    ShipType(String name,
            String compacterREGEX,
            Integer[] cost,
            Integer hull,
            Integer shield,
            Integer attack,
            Integer speed,
            Integer payload,
            Integer consumption,
            int engine,
            Integer[] fastFire) {
        this.name = name;
        this.compacterREGEX = compacterREGEX;
        this.cost = cost;
        this.hull = hull;
        this.shield = shield;
        this.attack = attack;
        this.speed = speed;
        this.payload = payload;
        this.consumption = consumption;
        this.engine = engine;
        this.fastFire = fastFire;
    }

    ShipType(String name,
            String compacterREGEX,
            Integer[] cost,
            Integer hull,
            Integer shield,
            Integer attack,
            Integer[] fastFire) {
        this.name = name;
        this.compacterREGEX = compacterREGEX;
        this.cost = cost;
        this.hull = hull;
        this.shield = shield;
        this.attack = attack;
        this.speed = 0;
        this.payload = 0;
        this.consumption = 0;
        this.fastFire = fastFire;
    }

    ShipType(String name,
            Integer[] cost,
            Integer hull,
            Integer shield,
            Integer attack) {
        this.name = name;
        this.compacterREGEX = "";
        this.cost = cost;
        this.hull = hull;
        this.shield = shield;
        this.attack = attack;
        this.speed = 0;
        this.payload = 0;
        this.consumption = 0;
        this.fastFire = setFastFireArray();
    }

    private static Integer[] setCostArray(Integer... values) {
        Integer[] cost = values;
        if (values.length > 3) {
            System.out.println("Error. Check ShipType's cost declaration");
        }
        return cost;
    }

    private static Integer[] setFastFireArray(Integer... values) {
        Integer[] ff = new Integer[24];
        if (values.length % 2 == 0) {
            int j = 0;
            for (int i = 0; i < ff.length; i++) {
                if ((j < values.length) && (i == values[j])) {
                    ff[i] = values[j + 1];
                    j += 2;
                } else {
                    ff[i] = 0;
                }
            }
        } else {
            System.out.println("Error. Check ShipType's fast fire declaration");
        }
        return ff;
    }

    public Integer getAttack() {
        return attack;
    }

    public String getCompacterREGEX() {
        return compacterREGEX;
    }

    public Integer getConsumption() {
        return consumption;
    }

    public Integer[] getCost() {
        return cost;
    }

    public Integer[] getFastFire() {
        return fastFire;
    }

    public Integer getHull() {
        return hull;
    }

    public String getName() {
        return name;
    }

    public Integer getPayload() {
        return payload;
    }

    public Integer getShield() {
        return shield;
    }

    public Integer getSpeed() {
        return speed;
    }

    public int getEngine() {
        return engine;
    }
}
