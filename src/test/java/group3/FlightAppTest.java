package group3;

import group3.accounts.Account;
import group3.accounts.Admin;
import group3.accounts.Passenger;
import group3.accounts.Person;
import group3.constants.AccountType;
import group3.constants.Address;
import group3.constants.Const;
import group3.constants.SeatClass;
import group3.flight.Airplane;
import group3.flight.Airport;
import group3.flight.Flight;
import group3.flight.Transaction;
import group3.service.FlightService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class FlightAppTest {
    private FlightService fs;
    private Account tempAcc;
    private FlightService flightService;
    private Account testAccount;
    private Flight testFlight;
    private Person person;
    private Airport departure;
    private Airport arrival;
    private Airplane airplane;
    private LocalDate flightDate;

    @BeforeEach
    public void setUp() {
        fs = new FlightService();
        tempAcc = new Account("id", "password");
        departure = new Airport("DEP", new Address("Departure City", "Departure State", 3000, "Philippines"));
        arrival = new Airport("ARR", new Address("Arrival City", "Arrival State", 3000, "Philippines"));
        airplane = new Airplane("ABC123", "2021", "Captain Albert", 2023);
        flightDate = LocalDate.now();
        testFlight = new Flight("FL123", departure, arrival, airplane, flightDate);
        testAccount = new Account("John Doe", "john@example.com", AccountType.PASSENGER);
        person = new Person("Name", new Address("City1", "City2", 3000, "Philippines"), "Email", "Phone", tempAcc);
    }


    @Test
    @DisplayName("This test is to verify registration of user")
    public void testRegisterUser() {
        fs.registerUser("user", Const.ADDR1, "email", "phone", tempAcc);
        assertEquals(1, fs.numberOfAccounts());
    }


    @Test
    @DisplayName("This is to test Duplicate User Account")
    public void testRegisterUserDuplicateAccount() {
        fs.registerUser("user", Const.ADDR1, "email", "phone", tempAcc);
        fs.registerUser("user", Const.ADDR1, "email", "phone", tempAcc);
        assertEquals(1, fs.numberOfAccounts());
    }


    @Test
    @DisplayName("This is to check number of registered Users")
    public void testNumberOfRegisteredUsers() {
        fs.registerUser("user", Const.ADDR1, "email", "phone", tempAcc);
        assertEquals(1, fs.numberOfRegisteredUsers());
    }


    @Test
    @DisplayName("This is to verify added flight")
    public void testAddFlight() {
        String flightNumber = "TEST-123";

        //String flightNumber, Airport departure, Airport arrival, Airplane plane, LocalDate flightDate
        fs.addFlight(flightNumber, departure, arrival, airplane, flightDate);

        assertEquals(1, fs.getNumberOfFlights());
        Flight flight = fs.findFlightByNumber(flightNumber);
        assertEquals(flightNumber, flight.getFlightNumber());
        assertEquals(departure, flight.getDeparture());
        assertEquals(arrival, flight.getArrival());
        assertEquals(airplane, flight.getPlane());
        assertEquals(flightDate, flight.getFlightDate());
    }


    @Test
    public void testBookFlight() {
        fs.addFlight(testFlight);
        //fs.registerUser("John Doe", new Address(), "john@example.com", "1234567890", testAccount);
        fs.bookFlight("FL123", 5, SeatClass.ECONOMY, testAccount);
        int bookedSeats = testFlight.getPlane().getNumberOfFilledSeats(SeatClass.ECONOMY);
        assertEquals(5, bookedSeats);
    }


    @Test
    @DisplayName("This is to check Flight by number")
    public void testFindFlightByNumber() {
        fs.addFlight(testFlight);
        Flight foundFlight = fs.findFlightByNumber("FL123");

        Assertions.assertNotNull(foundFlight);
        assertEquals(testFlight, foundFlight);
    }


    @Test
    public void testDisplayTransactionHistory() {
        fs.addFlight(Const.FL1);
        fs.bookFlight(Const.FL1.getFlightNumber(), 5, SeatClass.ECONOMY, tempAcc);
        fs.displayTransactionHistory();
    }

    @Test
    @DisplayName("This is to test created Transaction")
    public void testToStringTransaction() {
        // Create test data
        LocalDate flightDate = LocalDate.of(2023, 3, 20);
        String planeNumber = "FL123";
        int numberOfPassengers = 5;
        double fare = 100.0;

        // Create a Transaction instance with the test data
        Transaction transaction = new Transaction(flightDate, planeNumber, numberOfPassengers, fare);

        // Define the expected string representation of the Transaction instance
        String expectedString = "2023-03-20 | FL123 | 5 | 100.0";

        // Verify that the toString() method returns the expected string
        assertEquals(expectedString, transaction.toString());
    }

    @Test
    public void testToStringAddress() {
        // Create an Address instance with test data
        Address address = new Address("City", "State", 12345, "Country");

        // Define the expected string representation
        String expectedString = "COUNTRY";

        // Verify that the toString() method returns the expected string
        assertEquals(expectedString, address.toString());
    }

    @Test
    public void testGetPassword() {
        // Create an Account instance with test data
        Account account = new Account("Name", "password123");

        // Define the expected password
        String expectedPassword = "password123";

        // Verify that the getPassword() method returns the expected password
        assertEquals(expectedPassword, account.getPassword());
    }

    @Test
    public void testGetName() {
        assertEquals("Name", person.getName());
    }

    @Test
    public void testGetAddress() {
        assertEquals(new Address("City1", "City2", 3000, "Philippines"), person.getAddress());
    }

    @Test
    public void testGetEmail() {
        assertEquals("Email", person.getEmail());
    }

    @Test
    public void testGetPhone() {
        assertEquals("Phone", person.getPhone());
    }

    @Test
    public void testGetAccount() {
        assertEquals(tempAcc, person.getAccount());
    }

    @Test
    public void flightSetFlightNumberTest() {
        testFlight.setFlightNumber("QWE123");
        String flightNumber = testFlight.getFlightNumber();
        Assertions.assertEquals("QWE123", flightNumber);

    }

    @Test
    public void flightGetSetDepartureTest() {
        Airport departure = testFlight.getDeparture();
        Assertions.assertNotNull(departure);
        Assertions.assertEquals("DEP Airport", departure.toString());

        Airport newDeparture = new Airport("NEWDEP", new Address("Departure City", "Departure State", 3000, "Philippines"));
        testFlight.setDeparture(newDeparture);
        departure = testFlight.getDeparture();
        Assertions.assertEquals(newDeparture, departure);
    }

    @Test
    public void flightGetSetArrivalTest() {
        Airport arrival = testFlight.getArrival();
        Assertions.assertNotNull(arrival);
        Assertions.assertEquals("ARR Airport", arrival.toString());

        Airport newArrival = new Airport("NEWARR", new Address("Arrival City", "Arrival State", 3000, "Philippines"));
        testFlight.setArrival(newArrival);
        arrival = testFlight.getArrival();
        Assertions.assertEquals(newArrival, arrival);
    }

    @Test
    public void testGetAmountPayable() {
        double amountPayable = Flight.getAmountPayable(5, SeatClass.ECONOMY);
        assertEquals(25000.0, amountPayable);
    }

    @Test
    public void testFindFlightIndex() {
        fs.addFlight(testFlight);
        int index = fs.findFlightIndex("FL123");
        assertEquals(0, index);
    }

    @Test
    public void testFindPassengerByAccount() {
        fs.registerUser("user", Const.ADDR1, "email", "phone", tempAcc);
        Passenger passenger = fs.findPassengerByAccount(tempAcc);
        assertNotNull(passenger);
        assertEquals("user", passenger.getName());
    }

    @Test
    public void testAdmin() {
        // Test Data
        String name = "John Doe";
        Address address = new Address("City", "State", 1234, "Country");
        String email = "john@example.com";
        String phone = "1234567890";
        Account account = new Account("admin", "password123");

        Admin admin = new Admin(name, address, email, phone, account);

        // Constructor and Getters
        assertEquals(name, admin.getName());
        assertEquals(address, admin.getAddress());
        assertEquals(email, admin.getEmail());
        assertEquals(phone, admin.getPhone());
        assertEquals(account, admin.getAccount());
    }

    @Test
    public void flightSetPlaneTest() {
        testFlight.setPlane(new Airplane("QWE123", "2021", "Captain JD", 2021));
        Airplane newAirplane = testFlight.getPlane();
        Assertions.assertEquals(newAirplane, testFlight.getPlane());
    }
}
