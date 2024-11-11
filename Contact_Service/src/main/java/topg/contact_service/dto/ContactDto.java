package topg.contact_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ContactDto(
        @NotNull(message = "User ID is required") String userId,
        @NotNull(message = "Name is required") String name,
        @NotNull(message = "Email is required") @Email(message = "Email should be valid") String email,
        @Size(min = 11, message = "Phone number must be at least 11 characters") String phoneNumber
) {
}
