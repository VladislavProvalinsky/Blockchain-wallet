package by.it.academy.miningservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "inputs")
@Getter
@Setter
@NoArgsConstructor
public class Input {

    @Id
    @GeneratedValue(generator = "system-uuid") // генератор UUID
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;

    @Column(name = "value", nullable = false, precision = 19, scale = 4)
    private BigDecimal value;

    public Input(BigDecimal value) {
        this.value = value;
    }
}
