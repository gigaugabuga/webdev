package bsu.irm951.webdev.controllers;

import bsu.irm951.webdev.services.LogarithmService;
import bsu.irm951.webdev.services.security.JWTService;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = "application/json")
public class TasksController {

    private final LogarithmService logarithmService;
    private final JWTService jwtService;
    public TasksController(LogarithmService logarithmService, JWTService jwtService) {
        this.logarithmService = logarithmService;
        this.jwtService = jwtService;
    }

    @GetMapping("/log")
    public String evaluateLog(@RequestHeader("Authorization") String authHeader,
                              @RequestParam("log") Double log){

        try {
            long id = jwtService.identifyUser(authHeader);
            if(id == 0) {
                JSONObject response = new JSONObject();
                response.put("result", true);
                response.put("message", "authorization failed");
                response.put("data", "");
                return response.toString();
            }
        } catch(Exception e) {
            JSONObject response = new JSONObject();
            response.put("result", true);
            response.put("message", "authorization failed");
            response.put("data", "");
            return response.toString();
        }
        try{
            Double result = logarithmService.evaluateLogarithm(log);
            JSONObject response = new JSONObject();
            response.put("result", true);
            response.put("message", "");
            response.put("data", "" + result);
            return response.toString();
        } catch(Exception e) {
            JSONObject response = new JSONObject();
            response.put("result", false);
            response.put("message", "unable to evaluate log");
            response.put("data", "");
            return response.toString();
        }
    }

}
