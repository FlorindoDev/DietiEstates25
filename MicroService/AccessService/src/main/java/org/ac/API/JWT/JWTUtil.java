package org.ac.API.JWT;

import com.fasterxml.jackson.databind.JsonNode;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import com.fasterxml.jackson.databind.ObjectMapper;


import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class JWTUtil {
    private static final Logger logger = Logger.getLogger(JWTUtil.class.getName());

    public static String generateToken(String email, List<String> roles) {
        JsonNode rootNode = null;
        try {

            InputStream inputStream = JWTUtil.class.getResourceAsStream("/JWTspecifications.json");
            String jsonString = new Scanner(inputStream, StandardCharsets.UTF_8).useDelimiter("\\A").next();
            rootNode = new ObjectMapper().readTree(jsonString);

        } catch (IOException e) {
            logger.severe("[-] Errore lettura Json per TokenJWT");
            System.exit(-1);
        }

        byte[] keyBytes = Decoders.BASE64.decode(rootNode.path("SECRET_KEY").asText());
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                .claim("sub",email)
                .claim("roles", roles)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(rootNode.path("Exp").asInt())))
                .signWith(key)
                .compact();
    }
}
