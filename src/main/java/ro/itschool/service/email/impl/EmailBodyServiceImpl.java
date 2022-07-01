package ro.itschool.service.email.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.itschool.entity.MyUser;
import ro.itschool.service.email.EmailBodyService;
import ro.itschool.service.token.RandomTokenService;

@Service
public class EmailBodyServiceImpl implements EmailBodyService {

    @Autowired
    RandomTokenService randomTokenService;


    @Override
    public String emailBody(MyUser myUser) {
        return "Hello," +
                "In order to activate your account please access the following link:\n" +
                "http://localhost:8080/activation/" + myUser.getRandomToken();
    }
}
