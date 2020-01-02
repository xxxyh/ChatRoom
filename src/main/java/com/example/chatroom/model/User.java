package com.example.chatroom.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    private String email;
    private String userName;
    private String password;
    private Integer lockCounter;
    private Integer resetCounter;
}
