package iuh.fit.week5.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
//@ToString
@Table(name = "company")
@NamedQueries({
        @NamedQuery(name = "Company.findAll", query = "select c from Company c")
})
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comp_id", nullable = false)
    private Long id;

    @Column(name = "about", length = 2000)
    private String about;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "comp_name", nullable = false)
    private String compName;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "web_url")
    private String webUrl;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "address", nullable = false)
//    @ToString.Exclude
    private Address address;

    @OneToMany(mappedBy = "company")
//    @ToString.Exclude
    private Set<Job> jobs = new LinkedHashSet<>();

    @Override
    public String toString() {
        return String.format(
                        "Name: %s\n\n" +
                        "About: %s\n\n" +
                        "Email: %s\n\n" +
                        "Phone: %s\n\n" +
                        "Website: %s",
                id, compName, about, email, phone, webUrl
        );
    }
}