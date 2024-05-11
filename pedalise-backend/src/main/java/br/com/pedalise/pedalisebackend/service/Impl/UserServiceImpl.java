package br.com.pedalise.pedalisebackend.service.Impl;

import br.com.pedalise.pedalisebackend.entity.User;
import br.com.pedalise.pedalisebackend.exception.InvalidUserException;
import br.com.pedalise.pedalisebackend.repository.UserRepository;
import br.com.pedalise.pedalisebackend.service.UserService;
import lombok.AllArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User create(@NotNull User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        return null;
    }

    @Override
    public User getUserById(UUID id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return List.of();
    }

}