package kz.jussan.bot.model;

import lombok.Data;

@Data
public class ClimateControlRequest implements Payload {
    private String zone;
    private ClimateControlAction action;

}
