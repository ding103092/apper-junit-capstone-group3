package group3.constants;

import group3.flight.Airplane;
import group3.flight.Airport;
import group3.flight.Flight;

import java.time.LocalDate;

public class Const {
    public static final Address ADDR1 = new Address("CITY A","STATE A", 1234, "Philippines");
    public static final Address ADDR2 = new Address("CITY B","STATE B", 1235, "Philippines");
    public static final Airport SRCX = new Airport("SRC-X",ADDR1);
    public static final Airport DSTX = new Airport("DST-X",ADDR2);
    public static final Airplane PLANE1 = new Airplane("PLANE 1","MODEL 1", "PILOT 1", 1999);
    public static final Airplane PLANE2 = new Airplane("PLANE 2","MODEL 2", "PILOT 2", 2021);
    public static final Airplane PLANE3 = new Airplane("PLANE 3","MODEL 3", "PILOT 3", 2019);
    public static final Flight FL1 = new Flight("SKYJ865",SRCX,DSTX,PLANE1, LocalDate.of(2018,8,22));
    public static final Flight FL2 = new Flight("SKYJ866",DSTX,SRCX,PLANE2, LocalDate.of(2018,5,22));
    public static final Flight FL3 = new Flight("SKYJ867",SRCX,DSTX,PLANE3, LocalDate.of(2018,3,22));

    public static final double ECONFARE = 5000;
    public static final double BUSIFARE = 10000;
    public static final double FIRSFARE = 15000;
}
