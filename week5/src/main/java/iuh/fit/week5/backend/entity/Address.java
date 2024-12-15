package iuh.fit.week5.backend.entity;

import com.neovisionaries.i18n.CountryCode;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
//@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "street", length = 150)
    private String street;

    @Column(name = "city", length = 50)
    private String city;

    @Column(name = "country")
    private Short country;

    @Column(name = "number", length = 20)
    private String number;

    @Column(name = "zipcode", length = 7)
    private String zipcode;

    @OneToOne(mappedBy = "address")
    private Candidate candidate;

    @OneToOne(mappedBy = "address")
    private Company company;

    @Override
    public String toString() {
        return String.format(
                "Street: %s\n\n" +
                        "City: %s\n\n" +
                        "Country: %s\n\n" +
                        "Number: %s\n\n" +
                        "Zipcode: %s",
                street, city, country, number, zipcode
        );
    }

}