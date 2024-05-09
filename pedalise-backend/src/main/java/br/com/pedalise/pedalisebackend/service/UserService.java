package br.com.pedalise.pedalisebackend.service;

import br.com.pedalise.pedalisebackend.entity.User;
import org.antlr.v4.runtime.misc.NotNull;

public interface UserService {

    User create(@NotNull User user);

}