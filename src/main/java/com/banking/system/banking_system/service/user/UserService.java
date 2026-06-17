package com.banking.system.banking_system.service.user;

import com.banking.system.banking_system.dto.ApiResponse;
import com.banking.system.banking_system.users.entity.Users;
import com.banking.system.banking_system.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository ;
    public ApiResponse<List<Users>> GetAllUser ()
    {
          return ApiResponse.<List<Users>>builder().data(userRepository.findAll()).message("Suusses").success(true).build();
    }
    public  ApiResponse<Users> GetUser (Long id)
    {
        if (userRepository.findById(id).isEmpty())
        {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND , "The User Is Not Found") ;
        }
        return  ApiResponse.<Users>builder()
                .success(true)
                .message("Suusses")
                .data(userRepository.findById(id).get())
                .timestamp(LocalDateTime.now())
                .build() ;
    }
    public ApiResponse<String> DeleteUser (Long id )
    {
        if (userRepository.findById(id).isEmpty())
        {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND , "The User Is Not Found") ;
        }

        userRepository.deleteById(id);
       return ApiResponse.<String>builder()
                .success(true)
                .message("Success")
                .data("The User Is Deleted")
                .timestamp(LocalDateTime.now())
                .build() ;
    }
}
