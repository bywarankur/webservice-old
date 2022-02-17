package com.csye6225.webservice.service;

import com.csye6225.webservice.controller.request.CreateUserRequest;
import com.csye6225.webservice.controller.request.UpdateUserRequest;
import com.csye6225.webservice.data_layer.dao.UserDao;
import com.csye6225.webservice.model.User;
import com.csye6225.webservice.exceptions.WebServiceException;
import com.csye6225.webservice.model.UserCredentialDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
@ResponseStatus
public class UserManagementService implements UserDetailsService {

    Logger logger = LoggerFactory.getLogger(UserManagementService.class);

    @Autowired
    UserDao userDao;
    @Autowired
    CustomPasswordEncoder customPasswordEncoder;

    public UserManagementService(UserDao userDao, CustomPasswordEncoder customPasswordEncoder) {
        this.userDao = userDao;
        this.customPasswordEncoder = customPasswordEncoder;
    }

    public void createUsersDataStorage() throws WebServiceException {
        userDao.createUsersTable();
    }

    public User createUser(CreateUserRequest createUserRequest) throws WebServiceException {
        logger.info("Calling DAO to create a new user");

        // Create new user
        //user.setPassword(userPasswordEncoder().encode(user.getPassword()));
        createUserRequest.setPassword(customPasswordEncoder.encode(createUserRequest.getPassword()));
        User newUser = userDao.createUser(createUserRequest.getFirst_name(),
                createUserRequest.getLast_name(),
                createUserRequest.getUsername(),
                createUserRequest.getPassword());
        logger.info("Created new user = " + newUser.getId());

        return newUser;
    }

    public boolean userAlreadyExists(String userName) throws WebServiceException {
        User alreadyExistingUser = getUser(userName);
        if (alreadyExistingUser == null) {
            return false; // user does not exists in DB
        }

        return true; // user exists in DB
    }

    public User getUser(String userName) throws WebServiceException {
        User alreadyExistingUser = userDao.getUser(userName);
        return alreadyExistingUser;
    }

    public User updateUser(UpdateUserRequest updateUserRequest) throws WebServiceException {
        updateUserRequest.setPassword(customPasswordEncoder.encode(updateUserRequest.getPassword()));
        //updateUserRequest.setPassword(userPasswordEncoder().encode(updateUserRequest.getPassword()));
        userDao.updateUser(updateUserRequest.getFirst_name(),
                updateUserRequest.getLast_name(),
                updateUserRequest.getPassword(),
                updateUserRequest.getUsername());
        return userDao.getUser(updateUserRequest.getUsername());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUser(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username " + username + " does not exists");
        }

        user.setUsername(username);
        logger.info("User Details - " + user.getUsername() + "  password = " + user.getPassword());
        return new UserCredentialDetails(user);
    }
}
