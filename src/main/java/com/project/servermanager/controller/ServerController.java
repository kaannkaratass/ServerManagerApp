package com.project.servermanager.controller;

import com.project.servermanager.enumeration.Status;
import com.project.servermanager.model.Response;
import com.project.servermanager.model.Server;
import com.project.servermanager.service.implementation.ServerServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/server")
public class ServerController {

    private final ServerServiceImpl serverService;


    public ServerController(ServerServiceImpl serverService) {
        this.serverService = serverService;
    }

    @GetMapping("/list")
    public ResponseEntity<Response> getServers(int limit) {
        return ResponseEntity.ok(Response.builder().timeStamp(now()).data(Map.of("servers", serverService.list(30))).message("Servers retrieved").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
    }

    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
        Server server = serverService.ping(ipAddress);
        return ResponseEntity.ok(Response.builder().timeStamp(now()).data(Map.of("server", server))
                .message(server.getStatus() == Status.SERVER_UP ? "Ping success" : "Ping failed")
                .status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveServer(@RequestBody Server server) throws IOException {

        return ResponseEntity.ok(Response.builder().timeStamp(now()).data(Map.of("server", serverService.create(server)))
                .message("Server created")
                .status(CREATED).statusCode(CREATED.value()).build());
    }

    @GetMapping("/server/{id}")
    public ResponseEntity<Response> getServer(@PathVariable("id") Long id) {

        return ResponseEntity.ok(Response.builder().timeStamp(now()).data(Map.of("server", serverService.get(id)))
                .message("Server retrieved")
                .status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id) {

        return ResponseEntity.ok(Response.builder().timeStamp(now()).
                data(Map.of("deleted", serverService.delete(id)))
                .message("Server deleted")
                .status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
    }



}
