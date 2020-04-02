package com.internship.rest.restcontrollers;

import com.internship.rest.entities.UserDTO;
import com.internship.rest.exceptionshandler.ExceptionsHandler;
import com.internship.rest.services.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@Data
@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/users")
public class RestUser {
    private final UserService userService;
    private final ExceptionsHandler exceptionsHandler;

    @GetMapping("/")
    public ResponseEntity<Object> users() throws Exception {
        return new ResponseEntity<>(userService.getUsers(), OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody UserDTO userDTO) throws Exception {
        userService.addUser(userDTO);
        return new ResponseEntity<>("All good, user saved", CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> remove(@PathVariable Long id) throws Exception {
        userService.removeUser(id);
        return new ResponseEntity<>("User deleted", OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserDetails(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(userService.getOneUser(id), OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateUserDetails(@PathVariable Long id, @RequestBody UserDTO userDTO) throws Exception {
        userDTO.setId(id);
        userService.updateUser(userDTO);
        return new ResponseEntity<>("User updated successfully", OK);
    }
}
