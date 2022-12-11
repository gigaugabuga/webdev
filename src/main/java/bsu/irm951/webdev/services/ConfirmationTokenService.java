package bsu.irm951.webdev.services;

import bsu.irm951.webdev.models.ConfirmationTokenEntity;
import bsu.irm951.webdev.models.UserEntity;
import bsu.irm951.webdev.repositories.ConfirmationTokenRepository;
import bsu.irm951.webdev.repositories.UserRepository;
import bsu.irm951.webdev.services.security.SecretEncryption;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository tokenRepository;

    public ConfirmationTokenService(ConfirmationTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public ConfirmationTokenEntity store(ConfirmationTokenEntity confirmationToken) {
        tokenRepository.save(confirmationToken);
        return confirmationToken;
    }

    public ConfirmationTokenEntity findByAppUserId(UserEntity userEntity) {
        Optional<ConfirmationTokenEntity> optToken = tokenRepository.findByAppUserId(userEntity);
        if(optToken.isPresent()){
            return optToken.get();
        } else {
            return null;
        }
    }

    public ConfirmationTokenEntity updateConfirmedAt(LocalDateTime confirmedAt, ConfirmationTokenEntity token) {
        tokenRepository.updateConfirmedAt(confirmedAt, token.getId());
        return token;
    }

    public ConfirmationTokenEntity delete() {
        return null;
    }

    public void removeByUserId(UserEntity user) {
        tokenRepository.removeByUserId(user);
    }
}
