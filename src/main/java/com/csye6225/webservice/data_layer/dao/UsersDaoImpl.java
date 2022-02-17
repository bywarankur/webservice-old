package com.csye6225.webservice.data_layer.dao;

import com.csye6225.webservice.model.User;
import com.csye6225.webservice.exceptions.WebServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public class UsersDaoImpl extends JdbcDaoSupport implements UserDao {

    Logger logger = LoggerFactory.getLogger(UsersDaoImpl.class);

    @Autowired
    DataSource dataSource;
    String INSERT_USER_QUERY = "insert into UsersTable (" +
            " id, user_name, first_name, last_name, password, account_created, account_updated) " +
            " values (?,?,?,?,?,?,?)";
    final String SELECT_USER_QUERY = "select * from UsersTable where user_name = ? ";
    final String UPDATE_USER_QUERY = "update UsersTable set " +
            "first_name = ?, last_name = ?, password = ?, account_updated = ? where user_name = ?";
    final String CREATE_TABLE_QUERY = "create table if not exists UsersTable (" +
            "id varchar(60) NOT NULL, " +
            "user_name varchar(60) PRIMARY KEY, " +
            "first_name varchar(60) NOT NULL, " +
            "last_name varchar(60) NOT NULL, " +
            "password varchar(60) NOT NULL, " +
            "account_created varchar(60) NOT NULL, " +
            "account_updated varchar(60) NOT NULL )";

    @PostConstruct
    private void initialize(){
        setDataSource(dataSource);
    }


    @Override
    public User createUser(String first_name, String last_name, String user_name, String password) throws WebServiceException {
        User newlyCreatedUser = new User();
        Instant currentTime = Instant.now();

        newlyCreatedUser.setFirst_name(first_name);
        newlyCreatedUser.setLast_name(last_name);
        newlyCreatedUser.setUsername(user_name);
        newlyCreatedUser.setPassword(password);
        newlyCreatedUser.setAccount_updated(currentTime.toString());
        newlyCreatedUser.setAccount_created(currentTime.toString());

        String userId = UUID.randomUUID().toString();
        newlyCreatedUser.setId(userId);

        int rowsUpdated = 0;
        try {
            rowsUpdated = getJdbcTemplate().update(INSERT_USER_QUERY,
                    newlyCreatedUser.getId(),
                    newlyCreatedUser.getUsername(),
                    newlyCreatedUser.getFirst_name(),
                    newlyCreatedUser.getLast_name(),
                    newlyCreatedUser.getPassword(),
                    newlyCreatedUser.getAccount_created(),
                    newlyCreatedUser.getAccount_updated());
        } catch (Exception e) {
            throw new WebServiceException(
                    "Unexpected exception while creating user with username " + user_name, e);
        }

        if (rowsUpdated != 1) {
            throw new WebServiceException(
                    "Unexpected error while creating user. Rows updated should be one.");
        }

        logger.debug("User [" + user_name + "] created successfully. Rows updated = " + rowsUpdated);
        return newlyCreatedUser;
    }

    @Override
    public User getUser(String user_name) throws WebServiceException {
        User user = null;

        try {
            user = (User) getJdbcTemplate().queryForObject(SELECT_USER_QUERY,
                    new Object[]{user_name},
                    new BeanPropertyRowMapper(User.class));
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No user with username " + user_name + " was found");
        } catch (Exception e) {
            throw new WebServiceException(
                    "Unexpected exception fetching user with username " + user_name, e);
        }

        if (user != null) {
            user.setUsername(user_name);
        }

        return user;
    }

    @Override
    public void updateUser(String first_name, String last_name, String password, String user_name) {
        Instant currentTime = Instant.now();
        int rowsUpdated = 0;
        try {
            rowsUpdated = getJdbcTemplate().update(UPDATE_USER_QUERY,
                    first_name, last_name, password, currentTime.toString(), user_name);
        } catch (Exception e) {
            throw new WebServiceException(
                    "Unexpected exception while updating user with username " + user_name, e);
        }

        if (rowsUpdated != 1) {
            throw new WebServiceException(
                    "Unexpected error while updating user. Rows updated should be one.");
        }

        logger.debug("User [" + user_name + "] has been updated successfully. Rows updated = " + rowsUpdated);
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void createUsersTable() throws WebServiceException {
        try {
            getJdbcTemplate().execute(CREATE_TABLE_QUERY);
        } catch (Exception e) {
            throw new WebServiceException("Unexpected exception while creating Users Table");
        }

        logger.debug("Users Table created successfully");
    }
}
