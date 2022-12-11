package bsu.irm951.webdev.services.security;

import bsu.irm951.webdev.models.UserEntity;

public interface SecretEncryption {
    public String encrypt(String password);
    public boolean checkValidity(String secret, UserEntity user);
}
