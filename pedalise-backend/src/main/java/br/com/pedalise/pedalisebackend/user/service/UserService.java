package br.com.pedalise.pedalisebackend.user.service;

import br.com.pedalise.pedalisebackend.user.entity.User;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User create(@NotNull User user);

    User getUserByEmail(@NotNull String email);

    User getUserByUsername(@NotNull String username);

    User getUserById(@NotNull UUID id);

    List<User> getAll();

}