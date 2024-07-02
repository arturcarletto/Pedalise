package br.com.pedalise.pedalisebackend.security.service;

import br.com.pedalise.pedalisebackend.security.AuthenticationRequest;
import br.com.pedalise.pedalisebackend.security.AuthenticationResponse;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;

public interface SecurityService {

    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);

    @Nullable
    UserDetails extractUser(String token);

    boolean verifyToken(String token);
}
