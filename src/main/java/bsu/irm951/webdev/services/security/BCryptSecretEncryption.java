package bsu.irm951.webdev.services.security;

import bsu.irm951.webdev.models.UserEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class BCryptSecretEncryption implements SecretEncryption{
    @Override
    public String encrypt(String secret){
        String hash = BCrypt.hashpw(secret, BCrypt.gensalt());
        return hash;
    }
    @Override
    public boolean checkValidity(String secret, UserEntity user){
        return BCrypt.checkpw(secret, user.getPassword());
    }
}
