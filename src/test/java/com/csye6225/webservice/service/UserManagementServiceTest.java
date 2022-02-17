package com.csye6225.webservice.service;

import com.csye6225.webservice.controller.request.CreateUserRequest;
import com.csye6225.webservice.controller.request.UpdateUserRequest;
import com.csye6225.webservice.data_layer.dao.UserDao;

import com.csye6225.webservice.model.User;

import org.junit.Assert;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.springframework.test.context.junit4.SpringRunner;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UserManagementServiceTest {
    @InjectMocks
    UserManagementService userManagementService;
    @Mock
    UserDao usersDao;
    @Mock
    CustomPasswordEncoder customPasswordEncoder;

    private static User MOCK_USER;

    User getMockUser() {
        User MOCK_USER = new User();
        MOCK_USER.setId("d290f1ee-6c54-4b01-90e6-d701748f0851");
        MOCK_USER.setFirst_name("Jane");
        MOCK_USER.setLast_name("Doe");
        MOCK_USER.setUsername("jane.doe@example.com");
        MOCK_USER.setPassword("password");
        MOCK_USER.setAccount_created("2016-08-29T09:12:33.001Z");
        MOCK_USER.setAccount_updated("2016-08-29T09:12:33.001Z");

        return MOCK_USER;
    }

    CreateUserRequest getMockUserCreateRequest() {
        User MOCK_USER = getMockUser();
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername(MOCK_USER.getUsername());
        createUserRequest.setPassword(MOCK_USER.getPassword());
        createUserRequest.setFirst_name(MOCK_USER.getFirst_name());
        createUserRequest.setLast_name(MOCK_USER.getLast_name());

        return createUserRequest;
    }

    @Test
    public void createUserTest() {
        usersDao = mock(UserDao.class);
        customPasswordEncoder = mock(CustomPasswordEncoder.class);
        userManagementService = new UserManagementService(usersDao, customPasswordEncoder);

        Mockito.when(usersDao.createUser(any(), any(), any(), any())).thenReturn(getMockUser());
        Mockito.when(customPasswordEncoder.encode(any())).thenReturn("dummy_password");
        User user = userManagementService.createUser(getMockUserCreateRequest());

        Assert.assertEquals(user.getUsername(), getMockUser().getUsername());
        Assert.assertEquals(user.getPassword(), getMockUser().getPassword());
        Assert.assertEquals(user.getFirst_name(), getMockUser().getFirst_name());
        Assert.assertEquals(user.getLast_name(), getMockUser().getLast_name());
        Assert.assertEquals(user.getId(), getMockUser().getId());
        Assert.assertEquals(user.getAccount_created(), getMockUser().getAccount_created());
        Assert.assertEquals(user.getAccount_updated(), getMockUser().getAccount_updated());
    }



    UpdateUserRequest getMockUserUpdateRequest() {
        User MOCK_USER = getMockUser();
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();

        updateUserRequest.setUsername(MOCK_USER.getUsername());
        updateUserRequest.setPassword(MOCK_USER.getPassword());
        updateUserRequest.setFirst_name(MOCK_USER.getFirst_name());
        updateUserRequest.setLast_name(MOCK_USER.getLast_name());

        return updateUserRequest;
    }







}
