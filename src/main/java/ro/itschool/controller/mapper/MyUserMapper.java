package ro.itschool.controller.mapper;

import ro.itschool.controller.model.AccountDTO;
import ro.itschool.controller.model.MyUserDTO;
import ro.itschool.entity.MyUser;
import ro.itschool.entity.Role;

public class MyUserMapper {
    public static MyUserDTO convertToDTO(MyUser myUser) {
        MyUserDTO myUserDTO = new MyUserDTO();
        myUserDTO.setId(myUser.getId());
        myUserDTO.setUsername(myUser.getUsername());
        myUserDTO.setEmail(myUser.getEmail());
        myUserDTO.setFullName(myUser.getFullName());

//        myUserDTO.setRoles(myUser.getRoles().stream()
//                .map(role -> role.getName().substring(5))
//                .toList());

        myUserDTO.setRoles(myUser.getRoles().stream()
                .map(Role::getName)
                .toList());

        myUserDTO.setAccounts(myUser.getAccounts().stream()
                .map(account -> new AccountDTO(account.getCurrency(), account.getAmount(), account.getIban()))
                .toList());
        return myUserDTO;
    }
}
