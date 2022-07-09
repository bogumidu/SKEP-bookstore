package com.bsd.skep.model;

import com.bsd.skep.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private String username;
    private String type;

    public static UserDTO fromUser(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.username = user.getUsername();
        userDTO.type = user.getRole();
        return userDTO;
    }

}
