package com.bookManagement.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bookManagement.entity.Owner;
import com.bookManagement.entity.User;
import com.bookManagement.service.OwnerService;
import com.bookManagement.service.UserService;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationsController {

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private UserService userService;

    
    @PostMapping("/registerasowner")
    public ResponseEntity<String> registerAsOwner(@RequestBody Owner owner) {
        try {
            Owner registerOwner = ownerService.addUserAsOwner(owner);
            if (registerOwner != null) {
                return new ResponseEntity<>("Owner registered successfully", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Unable to register owner", HttpStatus.BAD_REQUEST);
            }
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while registering owner.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @DeleteMapping("/logoutOwner")
    public ResponseEntity<String> logoutOwner() {
        try {
            ownerService.logoutOwner();
            return new ResponseEntity<>("Logged out successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred during logout.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateOwner/{id}")
    public ResponseEntity<String> updateOwner(@PathVariable Integer id, @RequestBody Owner owner) {
        try {
            ownerService.updateOwner(id, owner);
            return new ResponseEntity<>("Owner details updated successfully at id: " + id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while updating owner details", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteOwner/{id}")
    public ResponseEntity<String> removeOwner(@PathVariable String email) {
        try {
            ownerService.removeOwner(email);
            return new ResponseEntity<>("Owner removed successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while removing owner", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // User Methods
    @PostMapping("/registerAsUser")
    public ResponseEntity<String> registerAsUser(@RequestBody User user) {
        try {
            User registerUser = userService.addUserAsUser(user);
            if (registerUser != null) {
                return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Unable to register user", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred, please try again", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Integer id, @RequestBody User user) {
        try {
            userService.updateUser(id, user);
            return new ResponseEntity<>("User details updated successfully!", HttpStatus.OK);
        } catch (Exception e) {
        	e.printStackTrace();
            return new ResponseEntity<>("An error occurred, please try again", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> removeUser(@PathVariable Integer id) {
        try {
            userService.removeUser(id);
            return new ResponseEntity<>("User deleted successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred, please try again", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/loginOwner")
    public ResponseEntity<String> loginOwner(@RequestBody Map<String, String> credentials) {
        try {
            String email = credentials.get("emailId");
            String password = credentials.get("password");
            Owner owner = ownerService.loginOwner(email, password);
            return new ResponseEntity<>("Owner logged in successfully: " + owner.getName(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid login credentials", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/loginUser")
    public ResponseEntity<String> loginUser(@RequestBody Map<String, String> credentials) {
        try {
            String email = credentials.get("email");
            String password = credentials.get("password");
            User user = userService.loginUser(email, password);
            return new ResponseEntity<>("User logged in successfully: " + user.getName(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
    
    

}
