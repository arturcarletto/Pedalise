package br.com.pedalise.pedalisebackend.user.service;

import br.com.pedalise.pedalisebackend.user.entity.User;
import br.com.pedalise.pedalisebackend.user.entity.UserDTO;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDTO create(@NotNull User user);

    UserDTO getUserByEmail(@NotNull String email);

    UserDTO getUserByUsername(@NotNull String username);

    UserDTO getUserById(@NotNull UUID id);

    UserDTO deleteUserById(@NotNull UUID id);

}