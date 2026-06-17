package com.banking.system.banking_system.controller.user;

import com.banking.system.banking_system.dto.ApiResponse;
import com.banking.system.banking_system.service.user.UserService;
import com.banking.system.banking_system.users.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService ;
    @GetMapping("/user")
    public ApiResponse<List<Users>> GetAllUser ()
    {
       return userService.GetAllUser() ;
    }
    @GetMapping("/test")
    public String test(Authentication auth) {
        return auth.getAuthorities().toString();
    }
    @GetMapping("user/{id}")
    public ApiResponse <Users> GetUser (@PathVariable Long id )
    {
        return userService.GetUser(id) ;
    }
    @DeleteMapping("user/{id}")
    public ApiResponse <String> DeleteUser (@PathVariable Long id )
    {
        return userService.DeleteUser(id) ;
    }

}
