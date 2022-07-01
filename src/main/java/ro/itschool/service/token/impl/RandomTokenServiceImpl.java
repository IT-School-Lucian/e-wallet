package ro.itschool.service.token.impl;

import org.springframework.stereotype.Service;
import ro.itschool.entity.MyUser;
import ro.itschool.service.token.RandomTokenService;

import java.util.UUID;

@Service
public class RandomTokenServiceImpl implements RandomTokenService {

    public String randomToken() {
        return UUID.randomUUID().toString();
    }

}
