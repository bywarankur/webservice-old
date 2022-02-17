package com.csye6225.webservice.data_layer.dao;

import com.csye6225.webservice.model.User;
import com.csye6225.webservice.exceptions.WebServiceException;

import java.util.List;

public interface UserDao {

    public final String USERS_TABLE_NAME = "UsersTable";

    User createUser(String first_name, String last_name, String user_name, String password) throws WebServiceException;
    User getUser(String user_name) throws WebServiceException;
    void updateUser(String first_name, String last_name, String password, String user_name) throws WebServiceException;

    List<User> getAllUsers();
    void createUsersTable() throws WebServiceException;
    // https://www.javainuse.com/spring/bootjdbc
}
