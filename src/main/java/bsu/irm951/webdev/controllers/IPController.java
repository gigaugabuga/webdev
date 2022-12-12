package bsu.irm951.webdev.controllers;

import bsu.irm951.webdev.models.UserEntity;
import bsu.irm951.webdev.services.IPService;
import bsu.irm951.webdev.services.UserService;
import bsu.irm951.webdev.services.security.JWTService;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="ip", produces="application/json")
public class IPController {

    private UserService userService;
    private IPService ipService;
    private JWTService jwtService;

    public IPController(UserService userService, IPService ipService, JWTService jwtService) {
        this.userService = userService;
        this.ipService = ipService;
        this.jwtService = jwtService;
    }

    @GetMapping("/")
    public String defineIPAddress(@RequestHeader("Authorization") String authHeader,
                                  HttpServletRequest request) {
        try {
            Long id = jwtService.identifyUser(authHeader);
            UserEntity user = userService.findById(id);
            String ipAddress = ipService.findIpAddress(request);
            JSONObject response = new JSONObject();
            response.put("result", true);
            response.put("message", "");
            response.put("data", ipAddress);
            return response.toString();

        } catch (Exception e) {
            JSONObject response = new JSONObject();
            response.put("result", false);
            response.put("message", "Authorization exception");
            response.put("data", "");
            return response.toString();
        }
    }
}
