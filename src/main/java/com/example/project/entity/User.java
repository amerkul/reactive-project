package com.example.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @Column("user_id")
    private long id;
    private String role;
    private String username;
    private String password;

    @Transient
    private List<Billing> billings;

    public User(long id) {
        this.id = id;
    }

    public User(String role, String username, String password) {
        this.role = role;
        this.username = username;
        this.password = password;
    }

}
