package group3;

import group3.accounts.Account;
import group3.accounts.Admin;
import group3.accounts.Passenger;
import group3.accounts.Person;
import group3.constants.*;
import group3.flight.Airplane;
import group3.flight.Airport;
import group3.flight.Flight;
import group3.flight.Transaction;
import group3.service.FlightService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class FlightAppTest {
    private FlightService fs;
    private FlightService fs2;
    private Account tempAcc;
    private Account testAccount;
    private Flight testFlight;
    private Person person;
    private Airport departure;
    private Airport arrival;
    private Airplane airplane;
    private LocalDate flightDate;
    private Passenger testPassenger;

    @BeforeEach
    public void setUp() {
        fs = new FlightService();
        fs2 = new FlightService();
        tempAcc = new Account("id", "password");
        departure = new Airport("DEP", new Address("Departure City", "Departure State", 3000, "Philippines"));
        arrival = new Airport("ARR", new Address("Arrival City", "Arrival State", 3000, "Philippines"));
        airplane = new Airplane("ABC123", "2021", "Captain Albert", 2023);
        flightDate = LocalDate.now();
        testFlight = new Flight("FL123", departure, arrival, airplane, flightDate);
        testAccount = new Account("John Doe", "john@example.com", AccountType.PASSENGER);
        person = new Person("Name", new Address("City1", "City2", 3000, "Philippines"), "Email", "Phone", tempAcc);
        testPassenger = new Passenger("John Doe", new Address("City1", "City2", 3000, "Philippines"), "john@example.com", "1234567890", testAccount);
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
    @DisplayName("This is to check Flight by number")
    public void testFindFlightByNumber() {
        fs.addFlight(testFlight);
        Flight foundFlight = fs.findFlightByNumber("FL123");

        Assertions.assertNotNull(foundFlight);
        assertEquals(testFlight, foundFlight);
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
    @DisplayName("This is to verify created Address")
    public void testToStringAddress() {
        // Create an Address instance with test data
        Address address = new Address("City", "State", 12345, "Country");

        // Define the expected string representation
        String expectedString = "COUNTRY";

        // Verify that the toString() method returns the expected string
        assertEquals(expectedString, address.toString());
    }

    @Test
    @DisplayName("This is to verify expected Password")
    public void testGetPassword() {
        // Create an Account instance with test data
        Account account = new Account("Name", "password123");

        // Define the expected password
        String expectedPassword = "password123";

        // Verify that the getPassword() method returns the expected password
        assertEquals(expectedPassword, account.getPassword());
    }

    @Test
    @DisplayName("This is to test Name parameter in Person class")
    public void testGetName() {
        assertEquals("Name", person.getName());
    }

    @Test
    @DisplayName("This is to test Address parameter in Person class")
    public void testGetAddress() {
        assertEquals(new Address("City1", "City2", 3000, "Philippines"), person.getAddress());
    }

    @Test
    @DisplayName("This is to test Email parameter in Person class")
    public void testGetEmail() {
        assertEquals("Email", person.getEmail());
    }

    @Test
    @DisplayName("This is to test Phone parameter in Person class")
    public void testGetPhone() {
        assertEquals("Phone", person.getPhone());
    }

    @Test
    @DisplayName("This is to test Account parameter in Person class")
    public void testGetAccount() {
        assertEquals(tempAcc, person.getAccount());
    }

    @Test
    @DisplayName("This is to test Flight Number")
    public void flightSetFlightNumberTest() {
        testFlight.setFlightNumber("QWE123");
        String flightNumber = testFlight.getFlightNumber();
        Assertions.assertEquals("QWE123", flightNumber);

    }

    @Test
    @DisplayName("This is to test Departure Location")
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
    @DisplayName("This is to test Arrival Location")
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
    @DisplayName("This is to test GetAmountPayable")
    public void testGetAmountPayable() {
        double amountPayable = Flight.getAmountPayable(5, SeatClass.ECONOMY);
        assertEquals(25000.0, amountPayable);

        double expectedAmount = 100.0;
        testPassenger.setAmountPayable(expectedAmount);
        double actualAmount = testPassenger.getAmountPayable();
        Assertions.assertEquals(expectedAmount, actualAmount);
    }

    @Test
    @DisplayName("This is to test Flight Index")
    public void testFindFlightIndex() {
        fs.addFlight(testFlight);
        int index = fs.findFlightIndex("FL123");
        assertEquals(0, index);
    }

    @Test
    @DisplayName("This is to test Finding Passenger by Account")
    public void testFindPassengerByAccount() {
        fs.registerUser("user", Const.ADDR1, "email", "phone", tempAcc);
        Passenger passenger = fs.findPassengerByAccount(tempAcc);
        assertNotNull(passenger);
        assertEquals("user", passenger.getName());
    }

    @Test
    @DisplayName("This is to test Admin Class")
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
    @DisplayName("This is to test Flight Date")
    public void flightGetSetFlightDateTest() {
        LocalDate dateOfFlight = testFlight.getFlightDate();
        Assertions.assertEquals(dateOfFlight, testFlight.getFlightDate());
        Assertions.assertNotNull(dateOfFlight);

        testFlight.setFlightDate(LocalDate.of(2023, 10, 27));
        Assertions.assertEquals(LocalDate.of(2023, 10, 27), testFlight.getFlightDate());
    }

    @Test
    @DisplayName("This is to test expected Plane Information")
    public void flightSetPlaneTest() {
        testFlight.setPlane(new Airplane("QWE123", "2021", "Captain JD", 2021));
        Airplane newAirplane = testFlight.getPlane();
        Assertions.assertEquals(newAirplane, testFlight.getPlane());
    }

    @Test
    @DisplayName("This is to test Flight number to string conversion")
    public void flightToString() {
        Assertions.assertEquals("Flight#" + this.testFlight.getFlightNumber().toUpperCase() + " | "
                + this.departure.toString().toUpperCase()+" <-> "+this.arrival.toString().toUpperCase() + " | "
                + this.flightDate.toString() + " | "
                + this.airplane.getPilotName().toUpperCase() + " | "
                + this.airplane.getAvailableSeats() + " SEATS", testFlight.toString());
    }

    @Test
    @DisplayName("This is to test created Airport")
    public void testToStringAirport() {
        Airport airport = new Airport("AIRPORT1", new Address("Departure City", "Departure State",3000,"Philippines"));

        assertEquals("AIRPORT1 Airport", airport.toString());
    }

    @Test
    @DisplayName("This is to test created Passenger")
    public void testPassenger() {
        // Test passenger name
        assertEquals("Passenger John Doe", testPassenger.toString());

        // Add a flight test
        assertEquals(0, testPassenger.getFlightList().size());
        assertTrue(testPassenger.getFlightList().isEmpty());
        testPassenger.addFlight(testFlight);
        assertEquals(1, testPassenger.getFlightList().size());

        // Display all flight test
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outStream);
        System.setOut(printStream);

        testPassenger.displayAllFlights();
        String output = outStream.toString().trim();

        Assertions.assertEquals("Flight#" + this.testFlight.getFlightNumber().toUpperCase() + " | "
                + this.departure.toString().toUpperCase()+" <-> "+this.arrival.toString().toUpperCase() + " | "
                + this.flightDate.toString() + " | "
                + this.airplane.getPilotName().toUpperCase() + " | "
                + this.airplane.getAvailableSeats() + " SEATS", output);
    }

    @Test
    @DisplayName("This is to test Airplane details")
    public void testAirplaneDetails() throws FlightService.NoSeatAvailableException, FlightService.NoFlightFoundException, FlightService.InvalidAccountException {
        // Airplane details with default values
        assertAll("Airplane details",
                () -> assertEquals("ABC123", airplane.getName()),
                () -> assertEquals("2021", airplane.getModel()),
                () -> assertEquals("Captain Albert", airplane.getPilotName()),
                () -> assertEquals(2023, airplane.getManufacturingYear()),
                () -> assertEquals(300, airplane.getAvailableSeats()),
                () -> assertEquals(100, airplane.getAvailableSeats(SeatClass.ECONOMY)),
                () -> assertEquals(100, airplane.getAvailableSeats(SeatClass.BUSINESS)),
                () -> assertEquals(100, airplane.getAvailableSeats(SeatClass.FIRSTCLASS))
        );

        // Updating available seats after booking a flight
        fs.addFlight(Const.FL1);
        fs.registerUser("USER",Const.ADDR1,"email","phone",testAccount);
        fs.bookFlight(Const.FL1.getFlightNumber(),5, SeatClass.ECONOMY, testAccount);
        fs.bookFlight(Const.FL1.getFlightNumber(),10, SeatClass.BUSINESS, testAccount);
        fs.bookFlight(Const.FL1.getFlightNumber(),15, SeatClass.FIRSTCLASS, testAccount);
        assertEquals(270,fs.findFlightByNumber(Const.FL1.getFlightNumber()).getPlane().getAvailableSeats());

        // Check if available seats are updated after booking
        assertEquals(95,fs.findFlightByNumber(Const.FL1.getFlightNumber()).getPlane().getAvailableSeats(SeatClass.ECONOMY));
        assertEquals(90,fs.findFlightByNumber(Const.FL1.getFlightNumber()).getPlane().getAvailableSeats(SeatClass.BUSINESS));
        assertEquals(85,fs.findFlightByNumber(Const.FL1.getFlightNumber()).getPlane().getAvailableSeats(SeatClass.FIRSTCLASS));
        assertEquals(5,fs.findFlightByNumber(Const.FL1.getFlightNumber()).getPlane().getNumberOfFilledSeats(SeatClass.ECONOMY));
        assertEquals(10,fs.findFlightByNumber(Const.FL1.getFlightNumber()).getPlane().getNumberOfFilledSeats(SeatClass.BUSINESS));
        assertEquals(15,fs.findFlightByNumber(Const.FL1.getFlightNumber()).getPlane().getNumberOfFilledSeats(SeatClass.FIRSTCLASS));
        assertEquals(30,fs.findFlightByNumber(Const.FL1.getFlightNumber()).getPlane().getNumberOfFilledSeats());

        // Check revenue of the flight
        // 5*5000 + 10*10000 + 15*15000 = 350000
        assertEquals(350000,fs.findFlightByNumber(Const.FL1.getFlightNumber()).getPlane().getRevenue());

        // Test the name of the plane
        assertEquals("PLANE 1-MODEL 1-1999",fs.findFlightByNumber(Const.FL1.getFlightNumber()).getPlane().toString());
        fs.displayTransactionHistory();
    }

    @Test
    @DisplayName("This is to test the custom exceptions")
    public void testCustomExceptions() throws FlightService.NoSeatAvailableException, FlightService.NoFlightFoundException, FlightService.InvalidAccountException {
        // Register as admin
        Account adminAcc = new Account("admin","admin",AccountType.ADMINISTRATOR);
        fs2.registerUser("ADMIN1",Const.ADDR1,"email","phone",adminAcc);

        // No flight found
        Exception e1 = assertThrows(FlightService.NoFlightFoundException.class, () -> fs2.bookFlight(Const.FL3.getFlightNumber(),5, SeatClass.ECONOMY, testAccount));
        assertEquals("No flight found.",e1.getMessage());

        // Now we add the flight and book the seats
        fs2.addFlight(Const.FL3);
        fs2.registerUser("USER",Const.ADDR1,"email","phone",testAccount);
        fs2.bookFlight(Const.FL3.getFlightNumber(),100, SeatClass.ECONOMY, testAccount);
        fs2.bookFlight(Const.FL3.getFlightNumber(),100, SeatClass.BUSINESS, testAccount);
        fs2.bookFlight(Const.FL3.getFlightNumber(),100, SeatClass.FIRSTCLASS, testAccount);

        // Cannot book flight as admin
        Exception e2 = assertThrows(FlightService.InvalidAccountException.class, () -> fs2.bookFlight(Const.FL3.getFlightNumber(),5, SeatClass.ECONOMY, adminAcc));
        assertEquals("Account not a passenger.",e2.getMessage());

        // No more seats available
        Exception e3 = assertThrows(FlightService.NoSeatAvailableException.class, () -> fs2.bookFlight(Const.FL3.getFlightNumber(),5, SeatClass.ECONOMY, testAccount));
        assertEquals("No seats available.",e3.getMessage());

        // Only view account, passenger, flight, admin, transaction list as admin
        assertThrows(FlightService.InvalidAccountException.class, () -> fs2.displayAllAccounts(testAccount));
        assertThrows(FlightService.InvalidAccountException.class, () -> fs2.displayAllFlight(testAccount));
        assertThrows(FlightService.InvalidAccountException.class, () -> fs2.displayAllAdmins(testAccount));
        assertThrows(FlightService.InvalidAccountException.class, () -> fs2.displayAllPassengers(testAccount));

        // Call methods related to display list
        fs2.displayAllAccounts(adminAcc);
        fs2.displayAllFlight(adminAcc);
        fs2.displayAllAdmins(adminAcc);
        fs2.displayAllPassengers(adminAcc);
    }
}
