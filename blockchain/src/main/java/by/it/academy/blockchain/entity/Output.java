package by.it.academy.blockchain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "outputs")
@Getter
@Setter
@NoArgsConstructor
public class Output {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;

    @Column(name = "value", nullable = false, precision = 19, scale = 4)
    private BigDecimal value;

    public Output(BigDecimal value) {
        this.value = value;
    }
}
