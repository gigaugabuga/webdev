package bsu.irm951.webdev.services.security;

import bsu.irm951.webdev.models.UserEntity;
import bsu.irm951.webdev.services.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.Instant;

@Service
public class JWTService {
    private final UserService userService;

    @Value("${auth.jwt.secret}")
    private String secretKey;

    public JWTService(UserService userService){
        this.userService = userService;
    }

    public String generateJWT(UserEntity user){
        secretKey = "30faa058f27f690c7e9a098d54ebcfb3d8725bcb85ee7907a2d84c69622229e2";
        Algorithm alg = Algorithm.HMAC256(secretKey);

        Instant now = Instant.now();
        Instant exp = null;   //now.plus(10, ChronoUnit.MINUTES);

        return JWT.create().
                withIssuer("auth").
                withAudience("all").
                withSubject("" + user.getId()).
                withIssuedAt(Time.from(now)).
                sign(alg);
    }

    //add deactivation
    public boolean checkToken(String token){
        secretKey = "30faa058f27f690c7e9a098d54ebcfb3d8725bcb85ee7907a2d84c69622229e2";
        try{
            Algorithm alg = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(alg).build();
            try{
                DecodedJWT decodedJWT = verifier.verify(token);

                if(!decodedJWT.getIssuer().equals("auth")){
                    return false;
                }

                //TODO: add service id to all
                if(!decodedJWT.getAudience().contains("all")){
                    return false;
                }

            } catch(JWTVerificationException verificationException){
                verificationException.printStackTrace();
                return false;
            }

        } catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public Long identifyUser(String authHeader) throws Exception{
        Long id = null;
        String userId = null;
        try {
            secretKey = "30faa058f27f690c7e9a098d54ebcfb3d8725bcb85ee7907a2d84c69622229e2";
            Algorithm alg = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(alg).build();
            DecodedJWT decodedJWT = verifier.verify(authHeader.substring(7));
            userId = decodedJWT.getSubject();
        } catch (Exception e) {
            throw new Exception();
        }
        try{
            id = Long.parseLong(userId);
        } catch(Exception e){
            e.printStackTrace();
        }
        return id;
    }
}
