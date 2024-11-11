package topg.user_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import topg.user_service.dto.UserDto;
import topg.user_service.dto.UserResponse;
import topg.user_service.model.User;
import topg.user_service.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // UUID generator function that returns a string representation
    private String generateUUID() {
        return UUID.randomUUID().toString();  // Generate and return UUID as string
    }

    public User registerUser(UserDto userDto) {
        // Check if the username or email already exists
        if (userRepository.existsByUsername(userDto.username())) {
            throw new IllegalArgumentException("Username is already taken");
        }
        if (userRepository.existsByEmail(userDto.email())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        // Generate UUID as string
        String generatedUUID = generateUUID();

        // Build the user object with the generated UUID
        var user = User.builder()
                .id(generatedUUID)  // Use the generated UUID string
                .username(userDto.username())
                .email(userDto.email())
                .password(passwordEncoder.encode(userDto.password()))
                .build();

        try {
            return userRepository.save(user);  // Save and return the user
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Failed to save user due to data integrity violation", e);
        }
    }

    public UserResponse getUserById(String id) {
        return userRepository.findById(id)
                .map(user -> new UserResponse(
                        user.getId(),       // Include the id if needed
                        user.getUsername(),
                        user.getEmail()
                        // Add other fields if required
                ))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public List<UserResponse> getAllUsers() {
       return userRepository.findAll()
               .stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail()
                ))
               .collect(Collectors.toList());
  }
}
