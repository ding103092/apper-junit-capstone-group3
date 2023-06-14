package group3;

import group3.accounts.Account;
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
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FlightAppTest {
    private FlightService fs;
    private Account tempAcc;
    private FlightService flightService;
    private Account testAccount;
    private Flight testFlight;

    @BeforeEach
    public void setUp() {
        fs = new FlightService();
        tempAcc = new Account("id", "password");
        Airport departure = new Airport("DEP", new Address("Departure City", "Departure State",3000,"Philippines"));
        Airport arrival = new Airport("ARR", new Address("Arrival City", "Arrival State",3000,"Philippines"));
        Airplane airplane = new Airplane("ABC123", "2021","Captain Albert",2023);
        LocalDate flightDate = LocalDate.now();
        testFlight = new Flight("FL123", departure, arrival, airplane, flightDate);
        testAccount = new Account("John Doe", "john@example.com", AccountType.PASSENGER);
    }

    @Test
    public void testRegisterUser() {
        fs.registerUser("user", Const.ADDR1, "email", "phone", tempAcc);
        assertEquals(1, fs.numberOfAccounts());
    }

    @Test
    public void testRegisterUserDuplicateAccount() {
        fs.registerUser("user", Const.ADDR1, "email", "phone", tempAcc);
        fs.registerUser("user", Const.ADDR1, "email", "phone", tempAcc);
        assertEquals(1, fs.numberOfAccounts());
    }

    @Test
    public void testNumberOfRegisteredUsers() {
        fs.registerUser("user", Const.ADDR1, "email", "phone", tempAcc);
        assertEquals(1, fs.numberOfRegisteredUsers());
    }

    @Test
    public void testAddFlight() {
        fs.addFlight(Const.FL1);
        fs.addFlight(Const.FL2);
        fs.addFlight(Const.FL3);
        assertEquals(3, fs.getNumberOfFlights());
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
}
