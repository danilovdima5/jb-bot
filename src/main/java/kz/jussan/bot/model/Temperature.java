package kz.jussan.bot.model;

public enum Temperature {
    WARM ("warm"),
    COLD("cold");

    private String description;

    Temperature(String description) {
        this.description = description;
    }

    public String description(){
        return description;
    }
}
