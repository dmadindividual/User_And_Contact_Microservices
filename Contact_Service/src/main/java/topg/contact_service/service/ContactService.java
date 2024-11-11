package topg.contact_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import topg.contact_service.dto.ContactResponseDto;
import topg.contact_service.model.Contact;
import topg.contact_service.repository.ContactRepository;
import topg.contact_service.dto.ContactDto;
import topg.contact_service.dto.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final WebClient.Builder webClientBuilder;
    private final ContactRepository contactRepository;

    public UserDto getUserById(String userId) {
        return webClientBuilder
                .baseUrl("http://USER")  // Replace localhost with the Eureka service name
                .build()
                .get()
                .uri("/user/{id}", userId)
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();
    }

    public Contact createContact(ContactDto contactDto, String userId) {
        UserDto userDto = getUserById(userId);


        Contact contact = new Contact();
        contact.setId(UUID.randomUUID().toString());
        contact.setName(contactDto.name());
        contact.setEmail(contactDto.email());
        contact.setPhoneNumber(contactDto.phoneNumber());
        contact.setUserId(userId);

        return contactRepository.save(contact);
    }

    public ContactResponseDto getContactById(String userId, String contactId) {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new IllegalArgumentException("Contact not found"));


        if (!contact.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Contact does not belong to the specified user");
        }

        return new ContactResponseDto(
                contact.getName(),
                contact.getEmail(),
                contact.getPhoneNumber()
        );
    }


    public Contact updateContact(String userId, String contactId, ContactDto contactDto) {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new IllegalArgumentException("Contact not found"));

        // Ensure the contact belongs to the specified user
        if (!contact.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Contact does not belong to the specified user");
        }

        // Update the contact details
        contact.setName(contactDto.name());
        contact.setEmail(contactDto.email());
        contact.setPhoneNumber(contactDto.phoneNumber());
        return contactRepository.save(contact);
    }

    public String deleteById(String userId, String contactId) {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new IllegalArgumentException("Contact not found"));
        if (!contact.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Contact does not belong to the specified user");
        }
        contactRepository.deleteById(contactId);
        return "Contact deleted successfully";
    }
    public List<ContactResponseDto> findAllContacts(String userId) {
        List<Contact> contacts = contactRepository.findAllByUserId(userId);
        if (contacts.isEmpty()) {
            throw new IllegalArgumentException("No contacts found for the specified user");
        }

        return contacts.stream()
                .map(contact -> new ContactResponseDto(
                        contact.getName(),
                        contact.getEmail(),
                        contact.getPhoneNumber()
                ))
                .toList();
    }

    public List<ContactResponseDto> getContactByName(String userId, String name) {
        List<Contact> contacts = contactRepository.findByUserIdAndNameContainingIgnoreCase(userId, name);
        if (contacts.isEmpty()) {
            throw new IllegalArgumentException("No contacts found with the name: " + name);
        }
        return contacts.stream()
                .map(contact -> new ContactResponseDto(
                        contact.getName(),
                        contact.getEmail(),
                        contact.getPhoneNumber()
                ))
                .toList();
    }
}
