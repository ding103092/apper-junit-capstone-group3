package group3;

import group3.accounts.Account;
import group3.constants.Const;
import group3.service.FlightService;

public class FlightApp {
    private static final FlightService fs = new FlightService();

    public static void main(String[] args) {
        // Populate flight
        fs.addFlight(Const.FL1);
        fs.addFlight(Const.FL2);
        fs.addFlight(Const.FL3);

        // Create account
        Account tempAcc = new Account("id","password");
        fs.registerUser("user",Const.ADDR1,"email","phone",tempAcc);
        // Create same account, will not work
        fs.registerUser("user",Const.ADDR1,"email","phone",tempAcc);
        // Should be 1
        System.out.println(fs.numberOfAccounts());
        // Should be still 1
        System.out.println(fs.numberOfRegisteredUsers());
    }
}
