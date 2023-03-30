package com.example.beshoes.controller;


import com.example.beshoes.config.JWTGenerator;
import com.example.beshoes.models.User;
import com.example.beshoes.models.request.AddRoletoUserRequest;
import com.example.beshoes.models.request.CreateUserRequest;
import com.example.beshoes.models.request.LoginRequest;
import com.example.beshoes.models.response.LoginResponse;
import com.example.beshoes.models.response.UserDto;
import com.example.beshoes.repository.RoleRepository;
import com.example.beshoes.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTGenerator jwtGenerator;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/registry")
    public ResponseEntity<String> registry(@RequestBody CreateUserRequest request){
        if (userRepository.existsByUsername(request.getUsername())){
            return new ResponseEntity<>("User existed!" , HttpStatus.BAD_REQUEST);
        }

        var user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var role = roleRepository.findByName("USER");
        user.getRoles().add(role.get());

        userRepository.save(user);
        return new ResponseEntity("User registry success!", HttpStatus.OK);
    }


    @PostMapping("/login")
    public LoginResponse authenticateUser(@RequestBody LoginRequest loginRequest) {

        // Xác thực từ username và password.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        String token = jwtGenerator.generatorToken(authentication);
        return new LoginResponse(token);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addRoletoUser")
    public ResponseEntity<String> addRoletoUser(@RequestBody AddRoletoUserRequest request){
        var user = userRepository.findByUsername(request.getUsername());
        var role = roleRepository.findByName(request.getRolename());
        if (user != null){
            user.get().getRoles().add(role.get());
            userRepository.save(user.get());
            return new ResponseEntity<>("Add Success!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Add Error!", HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/getListUser")
    public List<UserDto> getAll(){
        var users =  userRepository.findAll();
        var userDto = users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
        return userDto;
    }

}
