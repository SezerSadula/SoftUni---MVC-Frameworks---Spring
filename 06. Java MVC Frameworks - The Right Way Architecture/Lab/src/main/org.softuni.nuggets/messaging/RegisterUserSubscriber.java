package org.softuni.nuggets.messaging;

import org.softuni.nuggets.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import java.util.List;
import java.util.Map;

@Component
public class RegisterUserSubscriber {
    private final UserService userService;

    @Autowired
    public RegisterUserSubscriber(UserService userService) {
        this.userService = userService;
    }

    @JmsListener(destination = "register-user")
    public void onRegister(Message message) throws JMSException {
        if(message instanceof MapMessage) {
            MapMessage mappedMessage = (MapMessage)message;

            String username = (String) mappedMessage.getObject("username");
            List<String> preferences =
                    (List<String>) mappedMessage.getObject("preferences");

            this.userService.addPreferences(username, preferences);
        }
    }
}
