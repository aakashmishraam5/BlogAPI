package com.springboot.blog.security;

import com.springboot.blog.exception.BlogAPIException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
@Value("${app.jwt-secret}")
    private String jwtSecret;
@Value("${app-jwt-expiration-milliseconds}")
    private Long jwtExpirationDate;

//generate JWT token
    public String generateToken(Authentication authentication){
    String username=authentication.getName();

    Date currentDate=new Date();

    Date expirteDate=new Date(currentDate.getTime()+jwtExpirationDate);
    String token= Jwts.builder().setSubject(username).
            setIssuedAt(new Date()).setExpiration(expirteDate)
            .signWith(key()).compact();
    return token;
    }
    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    //get username from jwt token
    public String getUsername(String token){
        return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject();
    }
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
        }catch (MalformedJwtException malformedJwtException){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Invalid jwt token");
        }catch (ExpiredJwtException expiredJwtException){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Expired jwt token");
        }catch (UnsupportedJwtException unsupportedJwtException){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Unsupported JWT token");
        }catch (IllegalArgumentException illegalArgumentException){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"JWT claims Illegal arguments null or empty");
        }

        return true;
    }
}
