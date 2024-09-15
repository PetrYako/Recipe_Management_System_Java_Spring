package recipes.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import recipes.controller.dto.UserRequest;
import recipes.model.User;
import recipes.model.repository.UserRepository;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createUser(UserRequest userRequest) {
        userRepository.findByEmail(userRequest.getEmail().toLowerCase())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("User with this email already exists");
                });
        User user = new User(
                userRequest.getEmail().toLowerCase(),
                passwordEncoder.encode(userRequest.getPassword())
        );
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username.toLowerCase())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
