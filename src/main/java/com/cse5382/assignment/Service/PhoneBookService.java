package com.cse5382.assignment.Service;

import com.cse5382.assignment.Model.PhoneBookEntry;

import java.util.List;

public interface PhoneBookService {
    public List<PhoneBookEntry> list();
    public void add(PhoneBookEntry phoneBookEntry);

    public boolean deleteByName(String name);

    public boolean deleteByNumber(String phoneNumber);
    
    public PhoneBookEntry findByName(String name);

    public PhoneBookEntry findByNumber(String phoneNumber);
}
