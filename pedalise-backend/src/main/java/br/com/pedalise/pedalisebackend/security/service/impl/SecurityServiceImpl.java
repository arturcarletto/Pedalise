package br.com.pedalise.pedalisebackend.security.service.impl;

import br.com.pedalise.pedalisebackend.security.service.exception.AuthenticationException;
import br.com.pedalise.pedalisebackend.user.entity.User;
import br.com.pedalise.pedalisebackend.user.repository.UserRepository;
import com.auth0.jwt.JWT;
import br.com.pedalise.pedalisebackend.security.AuthenticationRequest;
import br.com.pedalise.pedalisebackend.security.AuthenticationResponse;
import br.com.pedalise.pedalisebackend.security.service.SecurityService;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private final UserDetailsService userDetailsService;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        UserDetails user = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());

        System.out.println(authenticationRequest);
        System.out.println("teste:");
        for (User user1 : userRepository.findAll()) {
            System.out.println(user1);
        }

        if (user == null) {
            throw new AuthenticationException("User not found");
        }

        if (!passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword())) {
            throw new AuthenticationException("Invalid password");
        }

        String token = createToken(user);
        return new AuthenticationResponse(token);
    }

    private String createToken(UserDetails user) {
        return JWT.create()
                .withIssuer("game-ecommerce")
                .withSubject(user.getUsername())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(60 * 60 * 24))
                .sign(getAlgorithm());
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(getJwtKey());
    }
    private String getJwtKey() {
        return System.getenv("JWT_KEY");
    }

    @Override
    public UserDetails extractUser(String token) {
        if (!verifyToken(token)) {
            return null;
        }

        String subject = getVerifier().verify(token).getSubject();

        return userDetailsService.loadUserByUsername(subject);
    }

    @Override
    public boolean verifyToken(String token) {
        try {
            getVerifier().verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }

    private JWTVerifier getVerifier() {
        return JWT.require(getAlgorithm())
                .withIssuer("pedalise-backend")
                .build();
    }
}
