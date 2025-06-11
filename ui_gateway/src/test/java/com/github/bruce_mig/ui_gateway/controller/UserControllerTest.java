package com.github.bruce_mig.ui_gateway.controller;


import com.github.bruce_mig.ui_gateway.clients.UserClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static com.github.bruce_mig.ui_gateway.util.TestHelpers.createArbitraryMap;
import static com.github.bruce_mig.ui_gateway.util.TestHelpers.createEmail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    private UserClient mockUserClient;

    private UserController userController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(mockUserClient);
    }

    @Test
    public void addUser_shouldCallTheAuthClient() {
        Map<String, Object> expectedRequest = createArbitraryMap();

        when(mockUserClient.addUser(expectedRequest)).thenReturn(expectedRequest);

        Map<String, Object> response = userController.addUser(expectedRequest).getBody();

        verify(mockUserClient).addUser(expectedRequest);

        assertEquals(expectedRequest, response);
    }

    @Test
    public void getProfile_shouldCallTheAuthClient() {
        String email = createEmail();
        Map<String, Object> expectedResponse = createArbitraryMap();

        when(mockUserClient.getUser(email)).thenReturn(expectedResponse);

        Map<String, Object> response = userController.getProfile(email).getBody();

        verify(mockUserClient).getUser(email);

        assertEquals(expectedResponse, response);
    }

    @Test
    public void deleteUser_shouldCallTheAuthClient() {
        String email = createEmail();
        Map<String, Object> expectedResponse = createArbitraryMap();

        when(mockUserClient.deleteUser(email)).thenReturn(expectedResponse);

        Map<String, Object> response = userController.deleteUser(email).getBody();

        verify(mockUserClient).deleteUser(email);

        assertEquals(expectedResponse, response);
    }
}