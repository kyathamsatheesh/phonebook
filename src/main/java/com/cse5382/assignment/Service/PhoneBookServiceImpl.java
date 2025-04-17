package com.cse5382.assignment.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cse5382.assignment.Model.PhoneBookEntry;
import com.cse5382.assignment.Repository.PhoneBookRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PhoneBookServiceImpl implements PhoneBookService{
    @Autowired
    PhoneBookRepository phoneBookRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneBookServiceImpl.class);
    @Override
    public List<PhoneBookEntry> list() {
        return phoneBookRepository.list();
    }

    @Override
    public void add(PhoneBookEntry phoneBookEntry) {
    	
        phoneBookRepository.save(phoneBookEntry.getName(),phoneBookEntry.getPhoneNumber());
    }

    @Override
    public boolean deleteByName(String name) {
        return phoneBookRepository.deleteByName(name);
    }


    @Override
    public boolean deleteByNumber(String phoneNumber) {
        return phoneBookRepository.deleteByNumber(phoneNumber);
    }

	@Override
	public PhoneBookEntry findByName(String name) {
		return phoneBookRepository.findByName(name);
		
	}

	@Override
	public PhoneBookEntry findByNumber(String phoneNumber) {
		return phoneBookRepository.findByName(phoneNumber);
		
	}
}
