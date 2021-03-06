package com.example.server.service.impl;

import com.example.entity.Person;
import com.example.service.PersonService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class PersonServiceImpl implements PersonService {

    private static ConcurrentHashMap<Integer, Person> map = new ConcurrentHashMap(){{
        put(1, new Person(1, "春花", 18));
        put(2, new Person(2, "张三", 20));
        put(3, new Person(3, "李四", 25));
    }};

    @Override
    public Person load(Integer id) {
        return map.get(id);
    }

    @Override
    public Person update(Person record) {
        map.put(record.getId(), record);
        return map.get(record.getId());
    }
}
