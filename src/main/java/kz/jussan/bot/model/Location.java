package kz.jussan.bot.model;

public enum Location {
    A1 ("Zone 1"),
    A2("Zone 2"),
    A3("Zone 3");

    private String description;

    Location(String description) {
        this.description = description;
    }

    public String description(){
        return description;
    }
}
