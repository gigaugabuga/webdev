package bsu.irm951.webdev.services;

import bsu.irm951.webdev.models.UserEntity;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;

@Service
public class FileService {

    public void uploadFile(MultipartFile file, UserEntity user) throws FileUploadException{
        String fileName = file.getOriginalFilename();
        String extension = file.getContentType();
        System.out.println(extension);
        System.out.println(fileName);
        try {
            file.transferTo( new File("C:\\Users\\yard7\\IdeaProjects\\webdev\\src\\main\\resources\\Uploads\\"  + user.getRepository() + "\\" + fileName));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new FileUploadException();
        }
    }

    public ArrayList<String> all(UserEntity user) {
        File folder = new File("C:\\Users\\yard7\\IdeaProjects\\webdev\\src\\main\\resources\\Uploads\\" + user.getRepository());
        ArrayList<String> files = new ArrayList<String>();
        if(folder.listFiles() != null && folder.listFiles().length > 0) {
            for(int i = 0; i < folder.listFiles().length; i++) {
                files.add(folder.listFiles()[i].getName());
            }
        } else {
            return null;
        }
        return files;
    }
}
