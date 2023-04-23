package com.project.servermanager.model;

import com.project.servermanager.enumeration.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Server {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    @Column(unique = true)
    @NotNull(message = "ipAdress can not be null")
    private String ipAddress ;
    private String name ;
    private String memory ;
    private String type ;
    private String imageUrl ;
    private Status status ;
}
