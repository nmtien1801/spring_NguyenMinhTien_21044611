package iuh.fit.week5.backend.repository;

import iuh.fit.week5.backend.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
