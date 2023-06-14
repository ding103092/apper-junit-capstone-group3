package group3.flight;

import group3.constants.Const;
import group3.constants.SeatClass;

public class Airplane {
    String name;
    String model;
    int manufacturingYear;
    String pilotName;
    int numberOfPassengers;
    int maxCapacity;
    int maxCapacityEconomy = 100;
    int maxCapacityBusiness = 100;
    int maxCapacityFirstClass = 100;
    int availableSeats;
    int availableSeatsEconomy;
    int availableSeatsBusiness;
    int availableSeatsFirstClass;

    public Airplane(String name, String model, String pilotName, int manufacturingYear) {
        this.name = name;
        this.model = model;
        this.pilotName = pilotName;
        this.manufacturingYear = manufacturingYear;
        this.availableSeatsEconomy    = maxCapacityEconomy;
        this.availableSeatsBusiness   = maxCapacityBusiness;
        this.availableSeatsFirstClass = maxCapacityFirstClass;
        this.availableSeats = (availableSeatsEconomy + availableSeatsBusiness + availableSeatsFirstClass);
        this.numberOfPassengers = 0;
        this.maxCapacity = this.availableSeats;
    }
    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    public String getPilotName() {
        return pilotName;
    }

    public int getManufacturingYear() {
        return manufacturingYear;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public int getAvailableSeats(SeatClass seatClass) {
        return switch(seatClass) {
            case ECONOMY -> getAvailableSeatsEconomy();
            case BUSINESS -> getAvailableSeatsBusiness();
            case FIRSTCLASS -> getAvailableSeatsFirstClass();
        };
    }

    public int getNumberOfFilledSeats(SeatClass seatClass) {
        return switch(seatClass) {
            case ECONOMY -> maxCapacityEconomy - getAvailableSeatsEconomy();
            case BUSINESS ->  maxCapacityBusiness - getAvailableSeatsBusiness();
            case FIRSTCLASS -> maxCapacityFirstClass - getAvailableSeatsFirstClass();
        };
    }

    public int getNumberOfFilledSeats() {
        return maxCapacity - getAvailableSeats();
    }

    public void setAvailableSeats(int availableSeats, SeatClass seatClass) {
        switch(seatClass) {
            case ECONOMY -> setAvailableSeatsEconomy(getAvailableSeatsEconomy()-availableSeats);
            case BUSINESS -> setAvailableSeatsBusiness(getAvailableSeatsBusiness()-availableSeats);
            case FIRSTCLASS -> setAvailableSeatsFirstClass(getAvailableSeatsFirstClass()-availableSeats);
        }
    }

    private void updateAvailableSeats() {
        this.availableSeats = (availableSeatsEconomy + availableSeatsBusiness + availableSeatsFirstClass);
    }

    public int getAvailableSeatsEconomy() {
        return availableSeatsEconomy;
    }

    public void setAvailableSeatsEconomy(int availableSeatsEconomy) {
        this.availableSeatsEconomy = availableSeatsEconomy;
        updateAvailableSeats();
    }

    public int getAvailableSeatsBusiness() {
        return availableSeatsBusiness;
    }

    public void setAvailableSeatsBusiness(int availableSeatsBusiness) {
        this.availableSeatsBusiness = availableSeatsBusiness;
        updateAvailableSeats();
    }

    public int getAvailableSeatsFirstClass() {
        return availableSeatsFirstClass;
    }

    public void setAvailableSeatsFirstClass(int availableSeatsFirstClass) {
        this.availableSeatsFirstClass = availableSeatsFirstClass;
        updateAvailableSeats();
    }

    public boolean isSeatAvailable(int numberOfSeats, SeatClass seatClass) {
        return (getAvailableSeats(seatClass)-numberOfSeats) >= 0;
    }

    public double getRevenue() {
        double eFare = (maxCapacityEconomy - getAvailableSeatsEconomy()) * Const.ECONFARE;
        double bFare = (maxCapacityBusiness - getAvailableSeatsBusiness()) * Const.BUSIFARE;
        double fFare = (maxCapacityFirstClass - getAvailableSeatsFirstClass()) * Const.FIRSFARE;

        return eFare + bFare + fFare;
    }

    @Override
    public String toString() {
        return name.toUpperCase() + "-" + model.toUpperCase() + "-" + manufacturingYear;
    }
}
