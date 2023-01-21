package com.project.servermanager.service.implementation;

import com.project.servermanager.enumeration.Status;
import com.project.servermanager.model.Server;
import com.project.servermanager.repository.ServerRepository;
import com.project.servermanager.service.ServerService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import static java.lang.Boolean.TRUE;

@Service
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {

    private final ServerRepository serverRepository ;

    public ServerServiceImpl(ServerRepository serverRepository){
        this.serverRepository = serverRepository;
    }


    @Override
    public Server create(Server server) {
        log.info("Saving new server {}", server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepository.save(server);
    }


    @Override
    public Server ping(String ipAdress) throws IOException {
        log.info("Pinging server : {}" , ipAdress);
        Server server = serverRepository.findByIpAdress(ipAdress);
        InetAddress address = null;
        address = InetAddress.getByName(ipAdress);
        server.setStatus(address.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);
        serverRepository.save(server);
        return server;
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all server");
        return serverRepository.findAll(PageRequest.of(0 , limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("Calling server by id" ,id);
        return serverRepository.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        log.info("Updating this server" ,server.getName());
        return serverRepository.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting this id" ,id);
        serverRepository.deleteById(id);
        return TRUE;
    }
    private String setServerImageUrl() {
        String[] imageNames =  {"server1.png","server2.png","server3.png","server4.png"};
        ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image/" + imageNames[new Random().nextInt(4)]).toUriString();
        return Arrays.stream(imageNames).findAny().get();
    }
}
