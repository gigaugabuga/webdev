package bsu.irm951.webdev.validators;

import bsu.irm951.webdev.models.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class UserValidator{

    public boolean validate(UserEntity userToAdd) {

        if(userToAdd.getName() == "" || userToAdd.getName() == null || userToAdd.getName().length() > 20 ||
           userToAdd.getPassword() == "" || userToAdd.getPassword() == null || userToAdd.getPassword().length() > 20 ||
           userToAdd.getEmail() == null || userToAdd.getEmail() == "") {

            return false;
        }

        return true;
    }

}
