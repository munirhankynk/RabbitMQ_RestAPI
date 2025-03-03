package com.rabbitmq.RestAPI_RabbitMQ.util;


import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String secretKey = "ea3e5d554b87a46edd304dcdc1a89a9fe69be5bc6e346d54b9064ae83100ead8326639fe9ebdcdc2b3bfd7b47e3d845a40bec15fbec9612a2be89afee4b30c8cc006598d89269956a08f2efe4fcb00dd7a14ad4411344254ba0a4c4a496d28e618f4bad99503b96b2070afc48a10db651cd740b4ea599c0979dc6942c95aa24d20bafc4e274427c312129233ebb02dddfebe1ea93c62896d448946fb461e37bac843869d84ee3111d59b0d55b38dd5c0f13fa014a3308af2af2e5422b4f4f194e75033939727075d6fbe78b4ab25569a82c5b18fa47f44fb50ab073785d7cc5f18f43b4c87964eb8208056b6f57c16c22215e9135422f02e57b5947fd99f982d";

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String validateAndExtractUsername(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException e) {
            throw new RuntimeException("Invalid or expired token");
        }
    }
}
