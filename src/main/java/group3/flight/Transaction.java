package group3.flight;

import java.time.LocalDate;

public class Transaction {
    LocalDate flightDate;
    String planeNumber;
    int numberOfPassengers;
    double fare;

    public Transaction(LocalDate flightDate, String planeNumber, int numberOfPassengers, double fare) {
        this.flightDate = flightDate;
        this.planeNumber = planeNumber;
        this.numberOfPassengers = numberOfPassengers;
        this.fare = fare;
    }

    @Override
    public String toString() {
        return flightDate.toString() + " | "
                + planeNumber + " | "
                + numberOfPassengers + " | "
                + fare;
    }
}
