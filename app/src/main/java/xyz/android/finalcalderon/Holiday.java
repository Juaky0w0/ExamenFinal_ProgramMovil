package xyz.android.finalcalderon;

public class Holiday {
    private String date;
    private String name;
    private String country;

    public Holiday(String date, String name, String country) {
        this.date = date;
        this.name = name;
        this.country = country;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }
}


