package com.comparus.multipledatasource.service;

import com.comparus.multipledatasource.model.User;
import com.comparus.multipledatasource.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAllUsersWithParams(Map<String, String> queryParams) {
        return userRepository.findAllUsersWithParams(queryParams);
    }
}