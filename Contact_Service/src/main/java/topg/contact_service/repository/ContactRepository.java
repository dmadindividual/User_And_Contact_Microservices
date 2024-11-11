package topg.contact_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import topg.contact_service.model.Contact;

import java.util.List;
import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, String> {
    List<Contact> findAllByUserId(String userId);

    List<Contact> findAllByName(String name);

    List<Contact> findByUserIdAndNameContainingIgnoreCase(String userId, String name);
}
