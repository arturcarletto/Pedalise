package br.com.pedalise.pedalisebackend.service.Impl;

import br.com.pedalise.pedalisebackend.entity.User;
import br.com.pedalise.pedalisebackend.exception.InvalidUserException;
import br.com.pedalise.pedalisebackend.repository.UserRepository;
import br.com.pedalise.pedalisebackend.service.UserService;
import lombok.AllArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User create(@NotNull User user) {

        if (user.getId() != null)
            throw new InvalidUserException("User id must be null");

        if (user.getName() == null || user.getName().isBlank()) {
            throw new InvalidUserException("User name must not be null or blank");
        }

        if (userRepository.findById(user.getId()).isPresent())
            throw new InvalidUserException("User with id %s already exists".formatted(user.getId()));

        return userRepository.save(user);
    }

}