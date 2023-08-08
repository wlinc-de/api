package de.wlinc.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Table
@Entity
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String user;
    @ManyToMany
    private List<Domain> domains;

}
