package com.lijie.oauth2clientdandiandenglu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

@SpringBootApplication
@EnableOAuth2Sso
public class Oauth2ClientDandiandengluApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2ClientDandiandengluApplication.class, args);
    }

}
