package com.bookManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookManagement.DAO.OwnerRepository;
import com.bookManagement.entity.Owner;
import com.bookManagement.exceptions.OwnerNotFoundException;

import jakarta.servlet.http.HttpSession;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private HttpSession httpSession;


    public Owner addUserAsOwner(Owner owner) {
        if (isOwnerRegistered(owner)) {
            throw new IllegalStateException("An owner is already registered please login");
        }
        System.out.println("Owner registered successfully");

        Owner savedOwner = ownerRepository.save(owner);
        httpSession.setAttribute("OwnerName", savedOwner.getName());
        httpSession.setAttribute("OwnerId", savedOwner.getId());

        return savedOwner;
    }

    public boolean isOwnerRegistered(Owner owner) {
        return ownerRepository.findByEmailId(owner.getEmailId()) != null;
    }

    public void logoutOwner() {
        httpSession.invalidate();
        System.out.println("Owner session invalidated");
    }

    public boolean removeOwner(String email) throws OwnerNotFoundException {
       if( ownerRepository.findByEmailId(email) != null) {
    	   ownerRepository.deleteByEmailId(email);
           System.out.println("Owner deleted successfully");
           return true;
       }
        return false;
    }

    public Owner updateOwner(int id, Owner updatedOwnerInfo) throws OwnerNotFoundException {
        Owner existingOwner = ownerRepository.findById(id)
            .orElseThrow(() -> new OwnerNotFoundException("Owner not found at id " + id));

        existingOwner.setName(updatedOwnerInfo.getName());
        existingOwner.setEmailId(updatedOwnerInfo.getEmailId());
        existingOwner.setContactNo(updatedOwnerInfo.getEmailId());

        System.out.println("Owner updated successfully");
        return ownerRepository.save(existingOwner);
    }
    
    public Owner loginOwner(String email, String password) throws OwnerNotFoundException {
        Owner owner = ownerRepository.findByEmailId(email);
        if (owner == null || !owner.getPassword().equals(password)) {
            throw new OwnerNotFoundException("Invalid email or password");
        }

        httpSession.setAttribute("OwnerName", owner.getName());
        httpSession.setAttribute("OwnerId", owner.getId());
        return owner;
    }
    
  
    
}
