package com.lijie.adminserversecurity;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient //被eureka发现
@EnableAdminServer //@EnableAdminServer
@SpringBootApplication
public class AdminServerSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminServerSecurityApplication.class, args);
    }

}
