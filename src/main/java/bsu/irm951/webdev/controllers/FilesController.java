package bsu.irm951.webdev.controllers;

import bsu.irm951.webdev.models.UserEntity;
import bsu.irm951.webdev.services.FileService;
import bsu.irm951.webdev.services.UserService;
import bsu.irm951.webdev.services.security.JWTService;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@RestController
@RequestMapping(value="file", produces="application/json")
public class FilesController {

    private FileService fileService;
    private JWTService jwtService;
    private UserService userService;

    public FilesController(FileService fileService, JWTService jwtService, UserService userService) {
        this.fileService = fileService;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            UserEntity user = userService.findById(1L);

            try{
                fileService.uploadFile(file, user);
                ArrayList<String> files = fileService.all(user);
                JSONObject response = new JSONObject();
                response.put("result", true);
                response.put("message", "");
                response.put("data", files);
                return response.toString();

            } catch (FileUploadException fileUploadException) {
                JSONObject response = new JSONObject();
                response.put("result", false);
                response.put("message", "Unable to upload file");
                response.put("data", "");
                return response.toString();
            }

        } catch (Exception e) {
            JSONObject response = new JSONObject();
            response.put("result", false);
            response.put("message", "Authorization exception");
            response.put("data", "");
            return response.toString();
        }
    }

    @GetMapping("/all")
    public String findAllFiles(@RequestHeader("Authorization") String authHeader) {
        try {
            Long id = jwtService.identifyUser(authHeader);
            UserEntity user = userService.findById(id);
            try {
                ArrayList<String> files = fileService.all(user);
                JSONObject response = new JSONObject();
                response.put("result", true);
                response.put("message", "");
                response.put("data", files.size() == 0 ? "Files not found" : files);
                return response.toString();
            } catch (Exception e) {
                JSONObject response = new JSONObject();
                response.put("result", false);
                response.put("message", e.getMessage());
                response.put("data", "");
                return response.toString();
            }

        } catch (Exception e) {
            JSONObject response = new JSONObject();
            response.put("result", false);
            response.put("message", "Authorization exception");
            response.put("data", "");
            return response.toString();
        }
    }
}
