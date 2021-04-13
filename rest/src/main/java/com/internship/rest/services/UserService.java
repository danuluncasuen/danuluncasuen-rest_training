package com.internship.rest.services;

import com.internship.rest.daos.UserDAO;
import com.internship.rest.entities.User;
import com.internship.rest.entities.UserDTO;
import com.internship.rest.exceptionshandler.CustomUserServiceException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Service
@Data
@RequiredArgsConstructor
public class UserService {

    private final UserDAO userDAO;

    public List<UserDTO> getUsers(Long limit) throws CustomUserServiceException {
        try {
            List<UserDTO> userDTOS = new ArrayList<>();
            List<User> usersFromDB = userDAO.findAll();
            if (limit != null) {
                for (int i=0; i<limit; i++) {
                    userDTOS.add(new UserDTO(usersFromDB.get(i)));
                    if (i == usersFromDB.size()-1) {
                        break;
                    }
                }
            } else {
                for (User user: usersFromDB) {
                    userDTOS.add(new UserDTO(user));
                }
            }
            return userDTOS;
        } catch (Exception e) {
            throw new CustomUserServiceException("Data Source issue, could not get the users", INTERNAL_SERVER_ERROR);
        }
    }

    public void addUser(UserDTO userDTO) throws CustomUserServiceException {
        validateUser(userDTO);
        try {
            userDAO.save(new User(userDTO));
        } catch (Exception e) {
            throw new CustomUserServiceException("Data Source issue, user could not be saved", INTERNAL_SERVER_ERROR);
        }
    }

    public void removeUser(Long id) throws CustomUserServiceException {
        try {
            userDAO.deleteById(id);
        } catch (Exception e) {
            throw new CustomUserServiceException("Data Source issue, could not delete user", INTERNAL_SERVER_ERROR);
        }
    }

    public UserDTO getOneUser(Long id) throws CustomUserServiceException {
        Optional<User> optionalUser = userDAO.findById(id);
        if (optionalUser.isPresent()) {
            return new UserDTO(optionalUser.get());
        }
        throw new CustomUserServiceException("The user could not be found", INTERNAL_SERVER_ERROR);
    }

    public void updateUser(UserDTO userDTO) throws CustomUserServiceException {
        Optional<User> optionalUser = userDAO.findById(userDTO.getId());
        userPresent(optionalUser);
        validateUser(userDTO);
        User toBeUpdated = optionalUser.get();
        toBeUpdated.setName(userDTO.getName());
        toBeUpdated.setSurname(userDTO.getSurname());
        toBeUpdated.setAge(userDTO.getAge());
        try {
            userDAO.save(toBeUpdated);
        } catch (Exception e) {
            throw new CustomUserServiceException("Data Source issue, user could not be updated", INTERNAL_SERVER_ERROR);
        }
    }

    private boolean validateUser(UserDTO userDTO) throws CustomUserServiceException {
        try {
            if (validateName(userDTO.getName())) {
                if (validateName(userDTO.getSurname())) {
                    validateAge(userDTO.getAge());
                    return true;
                } else {
                    throw new CustomUserServiceException("You are trying to set an invalid surname", BAD_REQUEST);
                }
            }
            throw new CustomUserServiceException("You are trying to set an invalid name", BAD_REQUEST);
        } catch (NullPointerException npe) {
            throw new CustomUserServiceException("No null values here buddy", BAD_REQUEST);
        }
    }

    private void validateAge(Integer age) throws CustomUserServiceException {
        if (age>=0 && age<120) {
            return;
        }
        throw new CustomUserServiceException("You are trying to set an invalid age", BAD_REQUEST);
    }

    private void userPresent(Optional<User> optionalUser) throws CustomUserServiceException {
        if (optionalUser.isPresent()) {
            return;
        }
        throw new CustomUserServiceException("User not found", INTERNAL_SERVER_ERROR);
    }

    private boolean validateName(String name) {
        return !name.isEmpty() && !name.contains(" ") && name.length() >= 1 && !name.matches(".*\\d.*");
    }
}
