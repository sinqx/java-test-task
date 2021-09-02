package com.testTask.test.service;

import com.testTask.test.entity.User;
import com.testTask.test.model.AuthModel;

public interface UserService {
    User findById(Long id);
    User saveWithPasswordEncode(User user) throws Exception;
    User findByPhoneNumber(Long phoneNumber);
    String getTokenByAuthModel(AuthModel authModel);
    User save(User user);
    String deleteById(Long id);
    User findByLogin(String username);
}
