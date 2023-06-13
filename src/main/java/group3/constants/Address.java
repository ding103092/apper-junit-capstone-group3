package group3.constants;

public record Address(String city, String state, Integer zipCode, String country) {
    @Override
    public String toString() {
        return country.toUpperCase();
    }
}
