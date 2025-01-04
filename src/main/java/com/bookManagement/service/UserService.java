package com.bookManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookManagement.DAO.OwnerRepository;
import com.bookManagement.DAO.UserRepository;
import com.bookManagement.entity.Owner;
import com.bookManagement.entity.User;
import com.bookManagement.exceptions.UserNotFoundException;

import jakarta.servlet.http.HttpSession;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private HttpSession httpSession;

	@Autowired
	private OwnerRepository ownerRepository;
	
	public User addUserAsUser(User user) {
	    Integer ownerId = (Integer) httpSession.getAttribute("OwnerId");
	    if (ownerId == null) {
	        throw new IllegalStateException("No owner is currently logged in. Please log in first.");
	    }

	    Owner owner = ownerRepository.findById(ownerId)
	            .orElseThrow(() -> new IllegalStateException("Owner not found with ID: " + ownerId));

	    user.setOwner(owner);

	    User savedUser = userRepository.save(user);
	    httpSession.setAttribute("UserName", savedUser.getUsername());
	    httpSession.setAttribute("UserId", savedUser.getId());
	    httpSession.setAttribute("email", savedUser.getEmailId());
	    System.out.println("User registered successfully under owner: " + owner.getName());
	    return savedUser;
	}

	public boolean removeUser(int id) throws UserNotFoundException {
			
			userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found at id "+ id));
				userRepository.deleteById(id);
				System.out.println("User deleted successfully");
				return true;
			
		}
	
	public User updateUser( Integer id , User updatedUserInfo) throws UserNotFoundException {
			
			User existingUser = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found at id "+ id));
			
				existingUser.setName(updatedUserInfo.getName());
				existingUser.setEmailId(updatedUserInfo.getEmailId());
				existingUser.setContactNo(updatedUserInfo.getContactNo());
				
				System.out.println("Owner Updated successfully");
				return userRepository.save(existingUser);
		}
	
	 public User loginUser(String email, String password) throws UserNotFoundException {
	        User user = userRepository.findByEmailId(email);
	        if (user == null) {
	            throw new UserNotFoundException("Invalid email or password");
	        }
	        httpSession.setAttribute("UserName", user.getName());
	        httpSession.setAttribute("UserId", user.getId());
	        return user;
	    }
	 
	 public void logoutUser() {
	        httpSession.invalidate();
	        System.out.println("User session invalidated");
	    }
}
