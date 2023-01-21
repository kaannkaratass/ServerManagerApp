package com.project.servermanager.repository;

import com.project.servermanager.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepository extends JpaRepository<Server , Long> {

    Server findByIpAdress(String ipAdress);
    Server findByName(String name);

}
