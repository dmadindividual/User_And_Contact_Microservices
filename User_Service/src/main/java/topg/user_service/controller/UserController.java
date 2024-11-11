package topg.user_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import topg.user_service.dto.UserDto;
import topg.user_service.dto.UserResponse;
import topg.user_service.model.User;
import topg.user_service.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    // Register a new user
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        userService.registerUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    // Fetch user by ID
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> getUserById(@PathVariable("userId") String userId) {
        UserResponse user = userService.getUserById(userId);
        return ResponseEntity.ok(user); // Return user details with 200 OK status
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
   public ResponseEntity<List<UserResponse>> getAllUsers(){
       List<UserResponse> users = userService.getAllUsers();
       return ResponseEntity.ok(users);
  }



}
