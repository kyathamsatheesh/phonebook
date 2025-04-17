package com.cse5382.assignment.Controller;

import com.cse5382.assignment.Model.PhoneBookEntry;
import com.cse5382.assignment.Service.PhoneBookService;
import com.cse5382.assignment.Service.PhoneBookServiceImpl;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class Controller {
	
	private static final Logger logger = LoggerFactory.getLogger(Controller.class);
	
    @Autowired
    PhoneBookService phoneBookService;

    @GetMapping(path = "phoneBook/list")
    public List<PhoneBookEntry> list(){
    	logger.info("This is method name is phonebook list...");
    	List<PhoneBookEntry> list = phoneBookService.list();
    	logger.info("Phone book List {}",list);
        return list;
    }

    @PostMapping(path = "phoneBook/add")
    public ResponseEntity<?> add(@RequestBody @Valid PhoneBookEntry phoneBookEntry){
    	logger.info("This is method name is add user...");
        try {
        	String normalizedNumber = normalizePhoneNumber(phoneBookEntry.getPhoneNumber());
            phoneBookEntry.setPhoneNumber(normalizedNumber); 
            logger.info("Given User name is {}",phoneBookEntry.getName());
            logger.info("Given User phone number is {}",phoneBookEntry.getPhoneNumber());
        	if(phoneBookService.findByNumber(phoneBookEntry.getPhoneNumber())==null)
        	{
        		 phoneBookService.add(phoneBookEntry);
        		 Map<String, Object> response = new HashMap<>();
                 response.put("status", HttpStatus.CREATED.value());
                 response.put("message", "Phone book entry added successfully");
                 logger.info("Phone book entry added successfully {}",response);
                 return new ResponseEntity<>(response, HttpStatus.CREATED);
        	}
        	else
        	{
        		Map<String, Object> response = new HashMap<>();
                response.put("status", HttpStatus.CONFLICT.value());
                response.put("message", "Phone number already exists");
                logger.info("Phone number already exists {}",response);
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        	}
        		
            
        }catch(Exception e){
            Map<String, Object> error = new HashMap<>();
            error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            error.put("message", "Internal server error occurred");
            logger.error("Internal server error occurred {}",error);
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "phoneBook/deleteByName")
    public ResponseEntity<?> deleteByName(@RequestParam String name){
    	logger.info("This is method name is deleteByName...");
        try {
	            boolean isdeleted = phoneBookService.deleteByName(name);
	            if(isdeleted)
	            {
		   		 	Map<String, Object> response = new HashMap<>();
		            response.put("status", HttpStatus.CREATED.value());
		            response.put("message", "Phone book entry removed successfully");
		            logger.info("deleteByName method  - deleted {}",response);
		            return new ResponseEntity<>(response, HttpStatus.OK);
	            }
	            else
	            {
	            	Map<String, Object> response = new HashMap<>();
		            response.put("status", HttpStatus.CREATED.value());
		            response.put("message", "No Phone book entry exist!");
		            logger.info("deleteByName - No Phone book entry exist {}",response);
		            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	            }

        }catch(Exception e){
        	Map<String, Object> error = new HashMap<>();
        	error.put("status", HttpStatus.CREATED.value());
        	error.put("message", "Somthing went wrong..!");
            logger.error("deleteByName - Internal server error occurred {}",error);
            return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "phoneBook/deleteByNumber")
    public ResponseEntity<?> deleteByNumber(@RequestParam String number){
    	logger.info("This is method name is deleteByNumber...");
        try {
            boolean isdeleted = phoneBookService.deleteByNumber(number);
            
            if(isdeleted)
            {
	   		 	Map<String, Object> response = new HashMap<>();
	            response.put("status", HttpStatus.CREATED.value());
	            response.put("message", "Phone book entry removed successfully");
	            logger.info("deleteByNumber method  - deleted {}",response);
	            return new ResponseEntity<>(response, HttpStatus.OK);
            }
            else
            {
            	Map<String, Object> response = new HashMap<>();
	            response.put("status", HttpStatus.CREATED.value());
	            response.put("message", "No Phone book entry exist!");
	            logger.info("deleteByNumber - No Phone book entry exist {}",response);
	            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }catch(Exception e){
            Map<String, Object> error = new HashMap<>();
        	error.put("status", HttpStatus.CREATED.value());
        	error.put("message", "Somthing went wrong..!");
            logger.error("deleteByNumber - Internal server error occurred {}",error);
            return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    public static String normalizePhoneNumber(String phoneNumber) {
	    return phoneNumber.replaceAll("[^\\d]", ""); // keep only digits
	}

}
