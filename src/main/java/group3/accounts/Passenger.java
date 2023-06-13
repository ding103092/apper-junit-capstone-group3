package group3.accounts;

import group3.flight.Flight;
import group3.constants.Address;

import java.util.ArrayList;
import java.util.List;

public class Passenger extends Person {
    double amountPayable = 0;
    List<Flight> flightList;

    // Add transaction of some sorts here
    public Passenger(String name, Address address, String email, String phone, Account account) {
        super(name, address, email, phone, account);
        flightList = new ArrayList<>();
    }

    public double getAmountPayable() {
        return amountPayable;
    }
    public void setAmountPayable(double amount) {
        this.amountPayable = amount;
    }

    public void addFlight(Flight flight) {
        flightList.add(flight);
    }

    @Override
    public String toString() {
        return "Passenger " + this.getName();
    }
}
