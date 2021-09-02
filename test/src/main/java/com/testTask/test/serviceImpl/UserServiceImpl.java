package com.testTask.test.serviceImpl;

import com.testTask.test.entity.User;
import com.testTask.test.entity.UserRole;
import com.testTask.test.exception.AuthException;
import com.testTask.test.exception.ObjectNotFoundException;
import com.testTask.test.model.AuthModel;
import com.testTask.test.repository.UserRepository;
import com.testTask.test.service.UserRoleService;
import com.testTask.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User save(User user) {
        return userRepository.save(user);
    }


    @Override
    public User saveWithPasswordEncode(User user) {
        Optional<User> userLoginCheck = userRepository.findByLogin(user.getLogin());
        Optional<User> userEmailCheck = userRepository.findByEmail(user.getEmail());

        if (userLoginCheck.isPresent()) {
            throw new AuthException("User with login \"" + user.getLogin() + "\" already exists.");
        } else if (userEmailCheck.isPresent()) {
            throw new AuthException("User with this email is already exists.");
        } else {
            user.setStatus((long) 1);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user = userRepository.save(user);
            UserRole userRole = new UserRole();
            userRole.setRoleName("ROLE_USER");
            userRole.setUser(user);
            userRoleService.save(userRole);
            return user;
        }
    }


    @Override
    public String getTokenByAuthModel(AuthModel authModel) {
        String authResult = "";
        User user = findByLogin(authModel.getLogin());
        if (user == null) authResult = "Wrong login or password";
        else {
            if (passwordEncoder.matches(authModel.getPassword(), user.getPassword())) {
                String loginPassPair = user.getLogin() + ":" + authModel.getPassword();
                authResult = "Basic " + Base64.getEncoder().encodeToString(loginPassPair.getBytes());
            } else authResult = "Wrong login or password";
        }
        return authResult;
    }

    @Override
    public User findByLogin(String username) {
        return userRepository.findByLogin(username).orElseThrow(() ->
                new ObjectNotFoundException("People with that name not found: ", username));
    }

    @Override
    public User findByPhoneNumber(Long phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() ->
                new ObjectNotFoundException("User with phone number \"" + phoneNumber + "\" doesn't exist"));
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("User with id \"" + id + "\" doesn't exist"));
    }

    @Override
    public String deleteById(Long id) {
        User user = findById(id);
        if (user == null) {
            throw new ObjectNotFoundException("User with id \"" + id + "\" doesn't exist");
        } else {
            userRoleService.deleteById(id);
            userRepository.delete(user);
            return "user with id \"" + id + "\" is deleted";
        }
    }
}
