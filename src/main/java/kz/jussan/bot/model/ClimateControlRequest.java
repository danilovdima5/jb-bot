package kz.jussan.bot.model;

import lombok.Data;

@Data
public class ClimateControlRequest implements Payload {
    private String zone;
    private Action action;


    public enum Action {
        WARM,
        COLD,
        HIGH,
        LOW
    }
}
