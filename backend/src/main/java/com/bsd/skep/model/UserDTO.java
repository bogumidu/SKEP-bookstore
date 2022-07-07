package com.bsd.skep.model;

import com.bsd.skep.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private String username;

    public static UserDTO fromUser(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.username = user.getUsername();
        return userDTO;
    }

}
