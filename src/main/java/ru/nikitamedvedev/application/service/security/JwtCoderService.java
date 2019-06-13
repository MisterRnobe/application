package ru.nikitamedvedev.application.service.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.nikitamedvedev.application.service.dto.JwtAccount;
import ru.nikitamedvedev.application.service.dto.Role;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtCoderService {

    private final Algorithm algorithm;

    public JwtAccount decode(String token) {
        val verify = JWT.require(algorithm)
                .build()
                .verify(token);
        val claims = verify.getClaims();

        val login = Optional.ofNullable(claims.get("login"))
                .map(Claim::asString)
                .orElseThrow(() -> new IllegalStateException("Login is not present!"));
        val groupName = Optional.ofNullable(claims.get("groupName"))
                .map(Claim::asString)
                .orElse(null);
        val groupId = Optional.ofNullable(claims.get("groupId"))
                .map(Claim::asLong)
                .orElse(null);
        val role = Optional.ofNullable(claims.get("role"))
                .map(Claim::asString)
                .map(Role::valueOf)
                .orElseThrow(() -> new IllegalStateException("Role is not present!"));
        val availableUntil = Optional.ofNullable(claims.get("availableUntil"))
                .map(Claim::asString)
                .map(OffsetDateTime::parse)
                .orElseThrow(() -> new IllegalStateException("Date is not present!"));
        return new JwtAccount(
                login,
                groupName,
                groupId,
                role,
                availableUntil
        );
    }

    public String encode(JwtAccount jwtAccount) {
        return JWT.create()
                .withClaim("login", jwtAccount.getLogin())
                .withClaim("groupId", jwtAccount.getGroupId())
                .withClaim("groupName", jwtAccount.getGroupName())
                .withClaim("role", jwtAccount.getRole().toString())
                .withClaim("availableUntil", jwtAccount.getAvailableUntil().toString())
                .sign(algorithm);
    }
}
