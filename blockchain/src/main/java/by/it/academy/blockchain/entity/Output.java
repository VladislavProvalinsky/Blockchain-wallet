package by.it.academy.blockchain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "outputs")
@Getter
@Setter
@NoArgsConstructor
public class Output {

    @Id
    @GeneratedValue(generator = "system-uuid") // генератор UUID
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;

    @Column(name = "value", nullable = false, scale = 2)
    private Double value;

    public Output(double value) {
        this.value = value;
    }
}
