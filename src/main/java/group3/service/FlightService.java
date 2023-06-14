package group3.service;

import group3.accounts.Account;
import group3.accounts.Admin;
import group3.accounts.Passenger;
import group3.constants.AccountType;
import group3.constants.Address;
import group3.constants.SeatClass;
import group3.flight.Airplane;
import group3.flight.Airport;
import group3.flight.Flight;
import group3.flight.Transaction;

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

    public int getNumberOfFlights() {
        return flightList.size();
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

    public void bookFlight(String flightNumber, int numberOfSeats, SeatClass seatClass, Account account) throws NoSeatAvailableException, NoFlightFoundException, InvalidAccountException {
        // Does flight exist?
        // Are there available seats, depending on seatClass?
        // Update amount payable for current user
        try {
            if(account.getAccountType().equals(AccountType.PASSENGER)) {
                Flight flight = findFlightByNumber(flightNumber);
                if(flight==null) throw new NoFlightFoundException("No flight found.");
                if(!(flight.getPlane().isSeatAvailable(numberOfSeats,seatClass))) throw new NoSeatAvailableException("No seats available.");

                // Updates avail seats (decrease capacity)
                flightList.get(findFlightIndex(flightNumber)).getPlane().setAvailableSeats(numberOfSeats,seatClass);
                // Update passenger bill
                passengerList.get(findPassengerIndex(account)).setAmountPayable(Flight.getAmountPayable(numberOfSeats,seatClass));
                // Add flight to passenger flightList
                passengerList.get(findPassengerIndex(account)).addFlight(flight);
                // Add a transaction to transaction list
                transactionList.add(new Transaction(flight.getFlightDate(),flight.getPlane().getName(),
                        flight.getPlane().getNumberOfFilledSeats(),flight.getPlane().getRevenue()));
            } else {
                throw new InvalidAccountException("Account not a passenger.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public void displayAllAccounts(Account account) throws InvalidAccountException {
        if(account.getAccountType() == AccountType.ADMINISTRATOR) {
            accountList.values().forEach(System.out::println);
        } else throw new InvalidAccountException("Invalid account type: " + account.getAccountType());
    }

    public void displayAllPassengers(Account account) throws InvalidAccountException {
        if(account.getAccountType() == AccountType.ADMINISTRATOR) {
            passengerList.forEach(System.out::println);
        } else throw new InvalidAccountException("Invalid account type: " + account.getAccountType());
    }

    public void displayAllAdmins(Account account) throws InvalidAccountException {
        if(account.getAccountType() == AccountType.ADMINISTRATOR) {
            adminList.forEach(System.out::println);
        } else throw new InvalidAccountException("Invalid account type: " + account.getAccountType());
    }

    public void displayAllFlight(Account account) throws InvalidAccountException {
        if(account.getAccountType() == AccountType.ADMINISTRATOR) {
            flightList.forEach(System.out::println);
        } else throw new InvalidAccountException("Invalid account type: " + account.getAccountType());
    }

    public void displayTransactionHistory() {
        System.out.println("Every Transaction History: ");
        transactionList.forEach(System.out::println);
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
                .orElse(null);
    }
    public Flight findFlightByNumber(String flightNumber) {
        return flightList.stream()
                .filter(flight -> flight.getFlightNumber().equals(flightNumber))
                .findFirst()
                .orElse(null);
    }

    public int numberOfRegisteredUsers() {
        return passengerList.size();
    }

    public int numberOfAccounts() {
        return accountList.size();
    }

    // Custom exceptions
    public static class InvalidAccountException extends Exception {
        public InvalidAccountException(String message) {
            super(message);
        }
    }

    public static class NoFlightFoundException extends Exception {
        public NoFlightFoundException(String message) {
            super(message);
        }
    }

    public static class NoSeatAvailableException extends Exception {
        public NoSeatAvailableException(String message) {
            super(message);
        }
    }

}
