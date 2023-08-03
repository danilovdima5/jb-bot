package kz.jussan.bot.model;

import lombok.Data;
import org.hibernate.annotations.Entity;
import org.hibernate.annotations.Type;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
public class Request {

    @Id
    private Long id;
    private String userId;
    private Type type;
    private LocalDateTime received;
    private LocalDateTime finished;
    @org.hibernate.annotations.Type(type = "json")
    private Payload payload;

    public enum Type {
        CLIMATE_CONTROL
    }

    public enum Status {
        AWAIT,
        DECLINED,
        FINISHED
    }
}
