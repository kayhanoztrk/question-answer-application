package com.project.questionanswerapp.entity;

import javax.persistence.*;
import lombok.Data;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private int avatar;
}
