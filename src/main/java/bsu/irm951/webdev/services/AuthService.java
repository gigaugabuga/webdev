package bsu.irm951.webdev.services;

import bsu.irm951.webdev.exceptions.*;
import bsu.irm951.webdev.models.ConfirmationTokenEntity;
import bsu.irm951.webdev.models.UserEntity;
import bsu.irm951.webdev.services.email.EmailService;
import bsu.irm951.webdev.services.security.BCryptSecretEncryption;
import bsu.irm951.webdev.services.security.JWTService;
import bsu.irm951.webdev.services.security.SecretEncryption;
import bsu.irm951.webdev.validators.UserValidator;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    private final UserService userService;
    private final EmailService emailService;
    private final UserValidator userValidator;
    private final ConfirmationTokenService tokenService;
    private final SecretEncryption passwordEncoder;
    private final JWTService jwtService;

    public AuthService(UserService userService, EmailService emailService, UserValidator userValidator,
                       ConfirmationTokenService tokenService, BCryptSecretEncryption secretEncryption,
                       JWTService jwtService){
        this.userService = userService;
        this.emailService = emailService;
        this.userValidator = userValidator;
        this.tokenService = tokenService;
        this.passwordEncoder = secretEncryption;
        this.jwtService = jwtService;
    }

    public UserEntity registerUser(UserEntity userToAdd) throws ValidationException, InvalidEmailException, EmailAlreadyTakenException{

        UserEntity savedUser = null;

        if(!userValidator.validate(userToAdd)) {
            throw new ValidationException("user not valid");
        }

        UserEntity registeredUserEntity = userService.findByName(userToAdd.getName());
        if(registeredUserEntity != null) {
            ConfirmationTokenEntity registeredConfirmationToken = tokenService.findByAppUserId(registeredUserEntity);
            if(registeredConfirmationToken.getExpirationTime().isBefore(LocalDateTime.now())){
                if(registeredConfirmationToken.getConfirmationTime() == null &&
                        userToAdd.getEmail().compareTo(registeredUserEntity.getEmail()) != 0){
                    tokenService.removeByUserId(registeredUserEntity);
                    userService.delete(registeredUserEntity);
                }
            }
        }

        String token = RandomStringUtils.random(5, "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890");

        try {
            emailService.sendEmail(userToAdd.getEmail(), token);
        } catch(Exception e){
            throw new InvalidEmailException();
        }

        try {
            savedUser = userService.store(userToAdd);
            LocalDateTime currentTime = LocalDateTime.now();
            ConfirmationTokenEntity tokenEntity = new ConfirmationTokenEntity(token, currentTime, null,
                    currentTime.plusMinutes(5), userToAdd);
            tokenService.store(tokenEntity);

        } catch(DataIntegrityViolationException sqlException){
            throw new EmailAlreadyTakenException();
            /*sqlException.printStackTrace();
            String message = sqlException.getRootCause().getMessage();
            response.put("result", false);
            response.put("message", "" + message.substring(message.indexOf("(") + 1, message.indexOf(")")) + " already used");
            return response.toString();*/
        }

        try {
            Path path = Paths.get("C:\\Users\\yard7\\IdeaProjects\\webdev\\src\\main\\resources\\Uploads\\" + savedUser.getRepository());
            Files.createDirectories(path);
        } catch (IOException e) {
            System.err.println("Failed to create directory!" + e.getMessage());
        }

        return savedUser;

    }

    public UserEntity confirmUser(String username, String token) throws UserNotFoundException, AccountAlreadyConfirmedException, TokenExpiredException, WrongTokenException {

        if(username == null || username == "" || username.length() > 20 ||
                token == null || token == ""){

        }

        UserEntity user = userService.findByName(username);

        if(user == null){
            throw new UserNotFoundException();
        }

        ConfirmationTokenEntity confirmationTokenEntity = tokenService.findByAppUserId(user);

        if(confirmationTokenEntity.getConfirmationTime()!=null){
            throw new AccountAlreadyConfirmedException();
        }

        LocalDateTime confirmedAt = LocalDateTime.now();

        if(confirmedAt.isAfter(confirmationTokenEntity.getExpirationTime())){
            tokenService.removeByUserId(user);

            token = RandomStringUtils.random(5, "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890");
            LocalDateTime currentTime = LocalDateTime.now();
            ConfirmationTokenEntity tokenEntity = new ConfirmationTokenEntity(token, currentTime, null,
                    currentTime.plusMinutes(5), user);
            tokenService.store(tokenEntity);
            emailService.sendEmail(user.getEmail(), token);

            throw new TokenExpiredException();
        }

        if(confirmationTokenEntity.getToken().compareTo(token) != 0){
            throw new WrongTokenException();
        }

        confirmationTokenEntity.setConfirmationTime(confirmedAt);
        tokenService.updateConfirmedAt(confirmedAt, confirmationTokenEntity);
        return user;
    }

    public UserEntity login(String identifier, String secret) throws ValidationException, UserNotFoundException, WrongTokenException, AccountNotConfirmedException, WrongPasswordException, Exception {
        UserEntity userEntity = userService.identifyUserEntity(identifier);
        JSONObject response = new JSONObject();
        String jwt = "";

        if(identifier == null || identifier == "" || secret == null || secret == ""){
            throw new ValidationException("Data not valid");
        }

        try {
            if(userEntity == null){
                throw new UserNotFoundException();
            }

            ConfirmationTokenEntity confirmationToken = tokenService.findByAppUserId(userEntity);

            if (confirmationToken == null) {
                throw new WrongTokenException();
            }

            //TODO: regenerate token and send again
            if (confirmationToken.getConfirmationTime() == null) {
                throw new AccountNotConfirmedException();
            }

            //TODO:Email or nickname
            //TODO:All response in json
            String encryptedSecret = passwordEncoder.encrypt(secret);
            if (passwordEncoder.checkValidity(secret, userEntity)) {
                jwt = jwtService.generateJWT(userEntity);
            } else {
                throw new WrongPasswordException();
            }
        } catch(Exception e){
            throw new Exception();
        }

        userEntity.setToken(jwt);

        return userEntity;
    }

    public void logout() {

    }
}
