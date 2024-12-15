package iuh.fit.week5.backend.service;

import iuh.fit.week5.backend.entity.Address;
import iuh.fit.week5.backend.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.datafaker.Faker;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public void fakeDataAddress() {
        Faker faker = new Faker();

        Address address = new Address();
        address.setStreet(faker.address().streetAddress());
        address.setCity(faker.address().city());
        address.setCountry((short) faker.number().numberBetween(1, 250)); // Assuming country is stored as a short
        address.setNumber(faker.address().buildingNumber());
        address.setZipcode(faker.address().zipCode());

        addressRepository.save(address);
    }

    public void delete(Address address) {
    }
}
