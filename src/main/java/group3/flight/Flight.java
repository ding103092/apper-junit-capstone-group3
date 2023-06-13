package group3.flight;

import group3.constants.SeatClass;

import java.time.LocalDate;

public class Flight {
    String flightNumber;
    Airport departure;
    Airport arrival;
    Airplane plane;
    LocalDate flightDate;

    public Flight(String flightNumber, Airport departure, Airport arrival, Airplane plane, LocalDate flightDate) {
        this.flightNumber = flightNumber;
        this.departure = departure;
        this.arrival = arrival;
        this.plane = plane;
        this.flightDate = flightDate;
    }
    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Airport getDeparture() {
        return departure;
    }

    public void setDeparture(Airport departure) {
        this.departure = departure;
    }

    public Airport getArrival() {
        return arrival;
    }

    public void setArrival(Airport arrival) {
        this.arrival = arrival;
    }

    public Airplane getPlane() {
        return plane;
    }

    public void setPlane(Airplane plane) {
        this.plane = plane;
    }

    public LocalDate getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(LocalDate flightDate) {
        this.flightDate = flightDate;
    }

    public static double getAmountPayable(int numberOfSeats, SeatClass seatClass) {
        return seatClass.getValue() * numberOfSeats;
    }

    @Override
    public String toString() {
        return "Flight#" + this.getFlightNumber().toUpperCase() + " | "
                + this.departure.toString().toUpperCase()+"-"+this.arrival.toString().toUpperCase() + " | "
                + this.flightDate.toString() + " | "
                + this.plane.getPilotName().toUpperCase();
    }
}
