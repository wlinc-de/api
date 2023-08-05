package de.wlinc.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table
@Entity
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String original_name;
    private String hash;
    private String UUID;
    private String extension;
    private String mime_type;
    private String path;
    private String user;
    private LocalDateTime uploaded_at;
    private LocalDateTime last_used_at;
    private LocalDateTime expires_at;
    private Long size;
    @OneToOne
    private Link link;

}
