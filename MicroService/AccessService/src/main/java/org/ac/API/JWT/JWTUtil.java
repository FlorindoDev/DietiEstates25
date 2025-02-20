package org.ac.API.JWT;

import DBLib.Postgres.ManagementConnectionPostgre;
import com.fasterxml.jackson.databind.JsonNode;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class JWTUtil {
    private static final Logger logger = Logger.getLogger(JWTUtil.class.getName());

    public static String generateToken(String email, List<String> roles) {
        JsonNode rootNode = null;
        try {

            InputStream inputStream = ManagementConnectionPostgre.class.getResourceAsStream("/JWTspecifications.json");
            String jsonString = new Scanner(inputStream, StandardCharsets.UTF_8).useDelimiter("\\A").next();

            rootNode = new ObjectMapper().readTree(jsonString);
        } catch (IOException e) {
            logger.severe("[-] Errore lettura Json per TokenJWT");
            System.exit(-1);
        }

        Key key = Keys.hmacShaKeyFor(rootNode.path("SECRET_KEY").asText().getBytes());

        return Jwts.builder()
                .setSubject(email)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + rootNode.path("Exp").asInt()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
