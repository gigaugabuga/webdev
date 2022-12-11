package bsu.irm951.webdev.repositories;

import bsu.irm951.webdev.models.ConfirmationTokenEntity;
import bsu.irm951.webdev.models.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationTokenEntity, Long> {

    public Optional<ConfirmationTokenEntity> findByAppUserId(UserEntity userEntity);

    @Modifying
    @Transactional
    @Query(value = "UPDATE confirmation_token SET confirmation_time = ?1 WHERE id = ?2", nativeQuery = true)
    public int updateConfirmedAt(LocalDateTime confirmedAt, Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM confirmation_token where app_user_id = ?1", nativeQuery = true)
    public int removeByUserId(UserEntity userEntity);

}
