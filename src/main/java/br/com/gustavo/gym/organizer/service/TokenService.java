package br.com.gustavo.gym.organizer.service;

import br.com.gustavo.gym.organizer.model.UsersModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(UsersModel usersModel){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(usersModel.getEmail())
                    .withClaim("lastPasswordChange", usersModel.getLastPasswordChange() != null
                            ? usersModel.getLastPasswordChange().toString()
                            : "")
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
        } catch(JWTCreationException exception){
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    public String validateToken(String token, UsersModel user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token);

            String tokenEmail = decodedJWT.getSubject();
            String tokenLastChange = decodedJWT.getClaim("lastPasswordChange").asString();
            String userLastChange = user.getLastPasswordChange() != null
                    ? user.getLastPasswordChange().toString()
                    : "";

            if (!tokenLastChange.equals(userLastChange)) {
                return null;
            }

            return tokenEmail;

        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    public String getEmailFromToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            return decodedJWT.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
