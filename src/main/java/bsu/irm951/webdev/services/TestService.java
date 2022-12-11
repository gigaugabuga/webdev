package bsu.irm951.webdev.services;

import bsu.irm951.webdev.models.UserEntity;
import bsu.irm951.webdev.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    private final UserRepository userRepository;

    public TestService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity registerUser(UserEntity userEntity) {

        userRepository.save(userEntity);

        return userEntity;
    }
}
