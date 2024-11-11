package topg.contact_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import topg.contact_service.dto.ContactDto;
import topg.contact_service.dto.ContactResponseDto;
import topg.contact_service.model.Contact;
import topg.contact_service.service.ContactService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contact/{userId}/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Contact> createContact(@PathVariable("userId") String userId, @RequestBody ContactDto contactDto) {
        Contact contact = contactService.createContact(contactDto, userId);
        return ResponseEntity.ok(contact);
    }

    @GetMapping("/{contactId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ContactResponseDto> getContactById(
            @PathVariable("userId") String userId,
            @PathVariable("contactId") String contactId) {
        ContactResponseDto contactResponse = contactService.getContactById(userId, contactId);
        return ResponseEntity.ok(contactResponse);
    }


    @PutMapping("/{contactId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Contact> updateContact(
            @PathVariable("userId") String userId,
            @PathVariable("contactId") String contactId,
            @RequestBody ContactDto contactDto) {
        Contact contactResponse = contactService.updateContact(userId, contactId, contactDto);
        return ResponseEntity.ok(contactResponse);
    }


    @DeleteMapping("/{contactId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteById(
            @PathVariable("userId") String userId,
            @PathVariable("contactId") String contactId) {
        String result = contactService.deleteById(userId, contactId);
        return ResponseEntity.ok(result);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ContactResponseDto>> getAllContacts(@PathVariable("userId") String userId) {
        List<ContactResponseDto> contacts = contactService.findAllContacts(userId);
        return ResponseEntity.ok(contacts);
    }


    @GetMapping("/by-name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ContactResponseDto>> getContactByName(
            @PathVariable("userId") String userId,
            @PathVariable("name") String name) {
        List<ContactResponseDto> contacts = contactService.getContactByName(userId, name);
        return ResponseEntity.ok(contacts);
    }
}
