package pix;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public class Pix implements Serializable {

    @Serial
    private static final long serialVersionUID = 2L;

    private Long id;
    private BigDecimal value;
    private String key;
    private Instant timestamp;
    private String message;

    public Pix(Long id, BigDecimal value, String key, Instant timestamp, String message) {
        this.id = id;
        this.value = value;
        this.key = key;
        this.timestamp = timestamp;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Pix{" +
                "id=" + id +
                ", value=" + value +
                ", key='" + key + '\'' +
                ", timestamp=" + timestamp +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pix pix = (Pix) o;
        return Objects.equals(id, pix.id) && Objects.equals(value, pix.value) && Objects.equals(key, pix.key) && Objects.equals(timestamp, pix.timestamp) && Objects.equals(message, pix.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, key, timestamp, message);
    }
}
