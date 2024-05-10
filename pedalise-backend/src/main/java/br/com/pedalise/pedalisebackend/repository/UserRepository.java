package br.com.pedalise.pedalisebackend.repository;

import br.com.pedalise.pedalisebackend.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
}