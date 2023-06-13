package group3.service;

import group3.accounts.Account;
import group3.accounts.Admin;
import group3.accounts.Passenger;
import group3.constants.Address;
import group3.constants.SeatClass;
import group3.flight.Airplane;
import group3.flight.Airport;
import group3.flight.Flight;
import group3.flight.Transaction;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlightService {
    Map<String, Account> accountList;
    List<Passenger>      passengerList;
    List<Admin>          adminList;
    List<Flight>         flightList;
    List<Transaction>    transactionList;

    // List<Transaction> transactions TODO: Add transactions list here

    public FlightService() {
        this.accountList    = new HashMap<>();
        this.flightList     = new ArrayList<>();
        this.passengerList  = new ArrayList<>();
        this.adminList      = new ArrayList<>();
        this.transactionList = new ArrayList<>();
    }

    public void addFlight(String flightNumber, Airport departure, Airport arrival, Airplane plane, LocalDate flightDate) {
        flightList.add(new Flight(flightNumber, departure, arrival, plane, flightDate));
    }

    public void addFlight(Flight flight) {
        flightList.add(flight);
    }

    public void registerUser(String name, Address address, String email, String phone, Account account) {
        // Is user already registered?
        try {
            if(isRegistered(account.getId())) throw new IllegalArgumentException("Account already registered.");

            // Add new account
            accountList.put(account.getId(),account);
            // Update either admin or passenger
            switch(account.getAccountType()) {
                case ADMINISTRATOR -> adminList.add(new Admin(name, address, email, phone, account));
                case PASSENGER     -> passengerList.add(new Passenger(name,address, email, phone, account));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void bookFlight(String flightNumber, int numberOfSeats, SeatClass seatClass, Account account) {
        // Does flight exist?
        // Are there available seats, depending on seatClass?
        // Update amount payable for current user
        try {
            Flight flight = findFlightByNumber(flightNumber);
            if(flight==null) throw new NullPointerException("No flight found.");
            if((flight.getPlane().isSeatAvailable(numberOfSeats,seatClass))) throw new IllegalArgumentException("No seats available.");

            // Updates avail seats (decrease capacity)
            flightList.get(findFlightIndex(flightNumber)).getPlane().setAvailableSeats(numberOfSeats,seatClass);
            // Update passenger bill
            passengerList.get(findPassengerIndex(account)).setAmountPayable(Flight.getAmountPayable(numberOfSeats,seatClass));
            // Add flight to passenger flightList
            passengerList.get(findPassengerIndex(account)).addFlight(flight);
            // Add a transaction to transaction list
            transactionList.add(new Transaction(flight.getFlightDate(),flight.getPlane().getName(),
                    flight.getPlane().getNumberOfFilledSeats(),flight.getPlane().getRevenue()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Helper functions
    public boolean isRegistered(String id) {
        return accountList.containsKey(id);
    }
    public int findFlightIndex(String flightNumber) {
        return flightList.indexOf(findFlightByNumber(flightNumber));
    }
    public int findPassengerIndex(Account account) {
        return passengerList.indexOf(findPassengerByAccount(account));
    }
    public Passenger findPassengerByAccount(Account account) {
        return passengerList.stream()
                .filter(flight->flight.getAccount().equals(account))
                .findFirst()
                .orElse(null); // TODO: Can also throw this as error (TBD)
    }
    public Flight findFlightByNumber(String flightNumber) {
        return flightList.stream()
                .filter(flight -> flight.getFlightNumber().equals(flightNumber))
                .findFirst()
                .orElse(null); // Flight with the given flightNumber not found
    }

    public int numberOfRegisteredUsers() {
        return passengerList.size();
    }

    public int numberOfAccounts() {
        return accountList.size();
    }
}
