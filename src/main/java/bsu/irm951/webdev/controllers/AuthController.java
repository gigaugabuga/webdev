package bsu.irm951.webdev.controllers;

import bsu.irm951.webdev.exceptions.*;
import bsu.irm951.webdev.models.UserEntity;
import bsu.irm951.webdev.services.AuthService;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.ValidationException;

@RestController
@RequestMapping(value = "auth", produces = "application/json")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/new")
    public String registerUser(@RequestParam("name") String name,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password) {

        JSONObject response = new JSONObject();
        UserEntity user = new UserEntity(name, password, email);
        try {
            authService.registerUser(user);
        } catch(ValidationException validationException) {
            response.put("result", false);
            response.put("message", "Provided data not valid");
            response.put("data", "");
            return response.toString();
        } catch(InvalidEmailException emailException) {
            response.put("result", false);
            response.put("message", "Provided email is not valid");
            response.put("data", "");
            return response.toString();
        } catch(EmailAlreadyTakenException emailAlreadyTakenException) {
            response.put("result", false);
            response.put("message", "Provided email has already been taken");
            response.put("data", "");
            return response.toString();
        }
        response.put("result", true);
        response.put("message", "User registered");
        response.put("data", "");
        return response.toString();
    }

    @PostMapping("/confirm")
    public String confirmUser(@RequestParam("username") String username,
                              @RequestParam("token") String token) {

        JSONObject response = new JSONObject();
        try{
            authService.confirmUser(username, token);
        } catch(UserNotFoundException userNotFoundException) {
            response.put("result", false);
            response.put("message", "Wrong data provided. Account not found");
            response.put("data", "");
            return response.toString();
        } catch(AccountAlreadyConfirmedException accountAlreadyConfirmedException) {
            response.put("result", false);
            response.put("message", "Account already confirmed");
            response.put("data", "");
            return response.toString();
        } catch(TokenExpiredException tokenExpiredException) {
            response.put("result", false);
            response.put("message", "Token has been expired");
            response.put("data", "");
            return response.toString();
        } catch(WrongTokenException wrongTokenException) {
            response.put("result", false);
            response.put("message", "Provided token does not exist");
            response.put("data", "");
            return response.toString();
        }
        response.put("result", true);
        response.put("message", "Account has been confirmed");
        response.put("data", "");
        return response.toString();
    }

    @PostMapping("/login")
    public String login(@RequestParam(name = "username") String username,
                        @RequestParam(name = "password") String password) {
        JSONObject response = new JSONObject();
        UserEntity user;
        try{
            user = authService.login(username, password);
        } catch(ValidationException validationException) {
            response.put("result", false);
            response.put("message", "Provided data not valid");
            response.put("data", "");
            return response.toString();
        } catch(UserNotFoundException userNotFoundException) {
            response.put("result", false);
            response.put("message", "Wrong data provided. Account not found");
            response.put("data", "");
            return response.toString();
        } catch(WrongTokenException wrongTokenException) {
            response.put("result", false);
            response.put("message", "Provided token does not exist");
            response.put("data", "");
            return response.toString();
        } catch(AccountNotConfirmedException accountNotConfirmedException) {
            response.put("result", false);
            response.put("message", "Account has not been confirmed");
            response.put("data", "");
            return response.toString();
        } catch(WrongPasswordException wrongPasswordException) {
            response.put("result", false);
            response.put("message", "Provided password does not match");
            response.put("data", "");
            return response.toString();
        } catch(Exception e) {
            response.put("result", false);
            response.put("message", "Error while logging in. Try again later");
            response.put("data", "");
            return response.toString();
        }
        response.put("result", true);
        response.put("message", "");
        response.put("data", new Gson().toJson(user));
        return response.toString();
    }

    @PostMapping("/logout")
    public String logout() {
        return null;
    }

}
