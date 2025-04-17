package com.cse5382.assignment.Repository;

import com.cse5382.assignment.Model.PhoneBookEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Component
public class PhoneBookRepository {

    List<PhoneBookEntry> list = new ArrayList<>();
    public List<PhoneBookEntry> list(){
        return  list;
    }

    public void save(String name, String phoneNumber){
        PhoneBookEntry phoneBookEntry = new PhoneBookEntry();
        phoneBookEntry.setPhoneNumber(phoneNumber);
        phoneBookEntry.setName(name);
        list.add(phoneBookEntry);
    }
    
    public PhoneBookEntry findByPhoneNumber(String phoneNumber) {
        return list.stream()
                .filter(entry -> entry.getPhoneNumber().equals(phoneNumber))
                .findFirst()
                .orElse(null);
    }

    public PhoneBookEntry findByName(String name) {
        return list.stream()
                .filter(entry -> entry.getPhoneNumber().equals(name))
                .findFirst()
                .orElse(null);
    }

    public boolean deleteByName(String name){
        int index = IntStream.range(0, list.size())
                .filter(i -> list.get(i).getName().equals(name))
                .findFirst()
                .orElse(-1);
        if(index!=-1){
            list.remove(index);
            return true;
        }
        return false;
    }


    public boolean deleteByNumber(String phoneNumber){
        int index = IntStream.range(0, list.size())
                .filter(i -> list.get(i).getPhoneNumber().equals(phoneNumber))
                .findFirst()
                .orElse(-1);
        if(index!=-1){
            list.remove(index);
            return true;
        }
        return false;
    }

}
