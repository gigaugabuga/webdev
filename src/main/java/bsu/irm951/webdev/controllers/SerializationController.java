package bsu.irm951.webdev.controllers;

import bsu.irm951.webdev.models.UserEntity;
import bsu.irm951.webdev.services.SerializationService;
import bsu.irm951.webdev.services.UserService;
import bsu.irm951.webdev.services.security.JWTService;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value="serialize", produces="application/json")
public class SerializationController {

    private UserService userService;
    private JWTService jwtService;
    private SerializationService serializationService;

    public SerializationController(UserService userService, JWTService jwtService, SerializationService serializationService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.serializationService = serializationService;
    }

    @GetMapping("/serialize")
    public String serialize(@RequestHeader("Authorization") String authHeader) {
        try {
            Long id = jwtService.identifyUser(authHeader);
            UserEntity user = userService.findById(id);

            String serializedBook = serializationService.serialize();
            JSONObject response = new JSONObject();
            response.put("result", true);
            response.put("message", "success");
            response.put("data", serializedBook);
            return response.toString();

        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            JSONObject response = new JSONObject();
            response.put("result", false);
            response.put("message", e.getMessage());
            response.put("data", "");
            return response.toString();
        } catch (Exception e) {
            JSONObject response = new JSONObject();
            response.put("result", false);
            response.put("message", "Access prohibited");
            response.put("data", "");
            return response.toString();
        }
    }

    @GetMapping("/deserialize")
    public String deserialize(@RequestHeader("Authorization") String authHeader) {
        try {
            Long id = jwtService.identifyUser(authHeader);
            UserEntity user = userService.findById(id);

            String deserializedBook = serializationService.deserialize();
            JSONObject response = new JSONObject();
            response.put("result", true);
            response.put("message", "success");
            response.put("data", deserializedBook);
            return response.toString();

        } catch (Exception e) {
            JSONObject response = new JSONObject();
            response.put("result", false);
            response.put("message", e.getMessage());
            response.put("data", "");
            return response.toString();
        }
    }
}
