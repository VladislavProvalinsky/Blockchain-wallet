package by.it.academy.blockchain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

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

    @Column(name = "value", nullable = false, scale = 2)
    private Double value;

    public Input(double value) {
        this.value = value;
    }
}
