package org.ac.API.JWT;

import com.fasterxml.jackson.databind.JsonNode;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class JWTUtil {
    private static final Logger logger = Logger.getLogger(JWTUtil.class.getName());

    public static String generateToken(String username, List<String> roles) {
        JsonNode rootNode = null;
        try {
            //System.out.println(System.getProperty("user.dir"));
            rootNode = new ObjectMapper().readTree(new File("AccessService/src/main/resources/JWTspecifications.json"));
        } catch (IOException e) {
            logger.severe("[-] Errore lettura Json per TokenJWT");
            System.exit(-1);
        }

        Key key = Keys.hmacShaKeyFor(rootNode.path("SECRET_KEY").asText().getBytes());

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + rootNode.path("Exp").asInt()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
