package group3.constants;

public enum SeatClass {
    ECONOMY(5000),
    BUSINESS(10000),
    FIRSTCLASS(15000);

    private final int value;

    private SeatClass(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}