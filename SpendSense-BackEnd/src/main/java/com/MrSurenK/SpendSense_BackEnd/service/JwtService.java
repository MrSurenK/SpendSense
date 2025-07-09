package com.MrSurenK.SpendSense_BackEnd.service;

import com.MrSurenK.SpendSense_BackEnd.repository.UserAccountRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;


@Getter
@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.access-expiration}")
    private long jwtExpiration;

    @Value("${security.jwt.refresh-expiration}")
    private long refreshExpiration; //change it to days after testing it out

    private final UserAccountRepo userAccountRepo;

    private final RedisTemplate<String, String>  redisTemplate;

    public JwtService(UserAccountRepo userAccountRepo, RedisTemplate<String,String> redisTemplate){
        this.userAccountRepo = userAccountRepo;
        this.redisTemplate = redisTemplate;
    }

    //JWT Access Token Methods

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String,Object> extraClaims, UserDetails userDetails){
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    public String generateRefreshToken(UserDetails userDetails){
        return buildToken(new HashMap<>(),userDetails, refreshExpiration);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ){
        return Jwts.builder() //Add more info into the claims of JWT
                .claims()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .add(extraClaims)
                .and()
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parser()                       //Step 1: Get a JwtParserBuilder
                .verifyWith(getSignInKey())     //Step 2: Set the signing Key
                .build()                        //Step 3: Build the JWtParser
                .parseSignedClaims(token)       //Step 4: Parse the signed JWT (String implements CharSequence)
                .getPayload();                  //Step 5: Extract the claims
    }

    private SecretKey getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // --- Refresh token methods using Redis ---

    //Generate Refresh Token
    public String generateRefreshToken(String username){
        String refreshToken = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set("refresh:" + username, refreshToken, Duration.ofMillis(refreshExpiration));
        return refreshToken;
    }

    //Validate refresh Tokens
    public boolean validateRefreshToken(String username, String token){
        String storedToken = redisTemplate.opsForValue().get("refresh:" + username);
        return storedToken != null && storedToken.equals(token);
    }

    //Invalidate RefreshTokens
    public void deleteRefreshToken(String accessToken){
        String username = extractUsername(accessToken);
        redisTemplate.delete("refresh:" + username);
        //If upon log out access token is still valid we want to blacklist it so that endpoints cannot be accessed directly
        long expiry = extractExpiration(accessToken).getTime() - System.currentTimeMillis();
        redisTemplate.opsForValue().set("blacklist: " + accessToken, "logout", Duration.ofMillis(expiry));
    }




}
