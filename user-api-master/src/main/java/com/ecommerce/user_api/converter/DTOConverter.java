package com.ecommerce.user_api.converter;

import com.ecommerce.user_api.dto.UserDTO;
import com.ecommerce.user_api.model.User;

public class DTOConverter {

    public static UserDTO convert(User user) { UserDTO userDTO = new UserDTO(); userDTO.setNome(user.getNome()); userDTO.setEndereco(user.getEndereco()); userDTO.setCpf(user.getCpf());
        return userDTO;
    }

}
