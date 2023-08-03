package kz.jussan.bot.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskAdministrator {

    private ClimateControlAction action;
    
    private LocalDateTime received;

    private Integer floorNumber;

    private String zone;
    
}

