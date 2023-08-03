package de.wlinc.api.entity;

import jakarta.persistence.*;
import lombok.*;

@Table
@Entity
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String domain = "wlinc.de";
    private String token;
    private String user;
}
