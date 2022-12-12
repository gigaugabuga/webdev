package bsu.irm951.webdev.services;

import bsu.irm951.webdev.models.UserEntity;
import bsu.irm951.webdev.repositories.UserRepository;
import bsu.irm951.webdev.services.email.EmailService;
import bsu.irm951.webdev.services.security.SecretEncryption;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private SecretEncryption secretEncoder;

    public UserService(UserRepository userRepository, EmailService emailService, SecretEncryption secretEncoder) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.secretEncoder = secretEncoder;
    }

    public UserEntity store(UserEntity userToStore) {
        userToStore.setPassword(secretEncoder.encrypt(userToStore.getPassword()));
        userToStore.setRepository(RandomStringUtils.random(32, "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"));
        userRepository.save(userToStore);
        return userToStore;
    }

    public UserEntity find() {
        return null;
    }

    public UserEntity findByName(String name) {
        Optional<UserEntity> optRegisteredUserEntity = userRepository.findByName(name);
        if(optRegisteredUserEntity.isPresent()) {
            return optRegisteredUserEntity.get();
        } else {
            return null;
        }
    }

    public UserEntity findById(Long id) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
        if(optionalUserEntity.isPresent()) {
            return optionalUserEntity.get();
        } else {
            return null;
        }
    }

    public UserEntity update() {
        return null;
    }

    public UserEntity delete(UserEntity user) {
        userRepository.delete(user);
        return user;
    }

    public UserEntity identifyUserEntity(String identifier){
        Optional<UserEntity> optUserByName = userRepository.findByName(identifier);
        Optional<UserEntity> optUserByEmail = userRepository.findByEmail(identifier);
        UserEntity entityToReturn = null;

        if(optUserByName.isPresent()){
            entityToReturn = optUserByName.get();
        } else if(optUserByEmail.isPresent()){
            entityToReturn = optUserByEmail.get();
        }

        return entityToReturn;
    }

}
