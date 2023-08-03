package kz.jussan.bot.model;

import lombok.Data;
import org.hibernate.annotations.Entity;
import org.hibernate.annotations.Type;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

import static java.util.Objects.nonNull;

@Data
public class Request {

    private Long id;
    private String userId;
    private Type type;
    private Status status;
    private LocalDateTime received;
    private LocalDateTime finished;
    private Payload payload;

    public enum Type {
        CLIMATE_CONTROL
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(id, request.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean isValid() {
        return nonNull(userId) && nonNull(type);
    }

    public enum Status {
        AWAIT,
        DECLINED,
        FINISHED
    }
}
