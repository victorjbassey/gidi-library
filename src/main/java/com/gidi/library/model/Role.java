package com.gidi.library.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String description;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles", cascade = {CascadeType.REFRESH,
            CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    private Set<User> users;
}
