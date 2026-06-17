package com.banking.system.banking_system.service.auth;

import com.banking.system.banking_system.dto.ApiResponse;
import com.banking.system.banking_system.dto.LoginRequest;
import com.banking.system.banking_system.dto.RegisterRequest;
import com.banking.system.banking_system.dto.RestPasswordRequest;
import com.banking.system.banking_system.enums.Role;
import com.banking.system.banking_system.service.JwtService;
import com.banking.system.banking_system.users.entity.Users;
import com.banking.system.banking_system.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository ;
    @Autowired
    private PasswordEncoder passwordEncoder ;
    @Autowired
    private JwtService jwtService ;
    @Autowired
    private AuthenticationManager authenticationManager ;

    public ApiResponse<Users> Register (RegisterRequest registerRequest)
    {
        Users user = new Users() ;
        user.setEmail(registerRequest.getEmail());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        userRepository.save(user) ;
        return ApiResponse.<Users>builder().success(true)
                .message("User registered successfully").data(user).timestamp(LocalDateTime.now()).build();
    }

    public ApiResponse<Map> Login (LoginRequest loginRequest)
    {
        if (userRepository.findByEmail(loginRequest.getEmail()).isEmpty())
        {
            throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED ,"Invalid Email or Password") ;
        }
        Users users = userRepository.findByEmail(loginRequest.getEmail()).get();
        if (!passwordEncoder.matches(
                loginRequest.getPassword(),
                users.getPassword())) {

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid Email or Password"
            );
        }

        String token = jwtService.generateToken(loginRequest.getEmail()) ;
        Map<String,Object> data  = new HashMap<>() ;
        data.put("data",users) ;
        data.put("token",token) ;
       return ApiResponse.<Map>builder().data(data).success(true).message("Login successful").timestamp(LocalDateTime.now()).build();
    }
    public ApiResponse<Users> GetMe ()
    {
        Users user = (Users) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return ApiResponse.<Users>builder().success(true).message("Success Request").timestamp(LocalDateTime.now()).data(user).build();
    }
    public  ApiResponse<Map<String , Object>> RestPassword (RestPasswordRequest restPasswordRequest)
    {
        if(userRepository.findById(restPasswordRequest.getId()).isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"The User Isn't Found") ;
        }
        Users users = userRepository.findById(restPasswordRequest.getId()).get() ;
        if (!restPasswordRequest.getNew_password().equals(restPasswordRequest.getConfirm_password()))
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The New Password != Confirm Password") ;
        }
        Map<String ,Object>response = new HashMap<>() ;
        response.put("Old Password is " , passwordEncoder.encode(users.getPassword())) ;
        response.put("New Password is " , passwordEncoder.encode(restPasswordRequest.getConfirm_password())) ;
        users.setPassword(passwordEncoder.encode(restPasswordRequest.getConfirm_password()));
        userRepository.save(users) ;
        return ApiResponse.< Map<String ,Object>>builder()
                .data(response)
                .success(true)
                .timestamp(LocalDateTime.now())
                .message("Success Change")
                .build();

    }

}
