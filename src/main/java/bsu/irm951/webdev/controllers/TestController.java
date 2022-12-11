package bsu.irm951.webdev.controllers;


import bsu.irm951.webdev.models.UserEntity;
import bsu.irm951.webdev.services.TestService;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = "application/json")
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/test")
    public String testRoute(@RequestParam("name") String name,
                            @RequestParam("password") String password,
                            @RequestParam("email") String email) {
        JSONObject response = new JSONObject();
        UserEntity user = null;

        try {
            user = testService.registerUser(new UserEntity(name, password, email));
        } catch (Exception e) {
            response.put("result", false);
            response.put("message", "");
            response.put("data", user);
            return response.toString();
        }

        response.put("result", true);
        response.put("message", "");
        response.put("data", user);
        return response.toString();
    }
}
