package br.com.pedalise.pedalisebackend.user.service.impl;

import br.com.pedalise.pedalisebackend.security.PasswordVerifier;
import br.com.pedalise.pedalisebackend.user.entity.User;
import br.com.pedalise.pedalisebackend.user.entity.UserDTO;
import br.com.pedalise.pedalisebackend.user.exception.InvalidEmailException;
import br.com.pedalise.pedalisebackend.user.exception.InvalidPasswordException;
import br.com.pedalise.pedalisebackend.user.exception.InvalidUserException;
import br.com.pedalise.pedalisebackend.user.exception.UserAlreadyExistsException;
import br.com.pedalise.pedalisebackend.user.repository.UserRepository;
import br.com.pedalise.pedalisebackend.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO create(User user) {
        if (user.getId() != null) {
            throw new InvalidUserException("Id already exists");
        }

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        if (user.getUsername().length() <= 6 || user.getUsername().length() >= 20) {
            throw new InvalidUserException("Username must be between 6 and 20 characters");
        }

        if (!Pattern.compile("^(.+)@(\\S+)$").matcher(user.getEmail()).matches()) {
            throw new InvalidEmailException("Invalid Email");
        }

        PasswordVerifier passwordVerifier = new PasswordVerifier();

        if (passwordVerifier.verify(user.getPassword())){
            throw new InvalidPasswordException("Invalid Password");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(user);


        return new UserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(UserDTO::new).orElse(null);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        return userRepository.findByUsername(username).map(UserDTO::new).orElse(null);
    }

    @Override
    public UserDTO getUserById(UUID id) {
        return userRepository.findById(id).map(UserDTO::new).orElse(null);
    }

    @Override
    public UserDTO deleteUserById(UUID id) {
        UserDTO user = getUserById(id);
        userRepository.deleteById(id);
        return user;
    }
}