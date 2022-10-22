package com.example.post.api.controller;

import com.example.post.api.dto.AuthenticationRequestDto;
import com.example.post.api.dto.AuthenticationResponseDto;
import com.example.post.api.dto.TokenRefreshRequestDto;
import com.example.post.api.dto.TokenRefreshResponseDto;
import com.example.post.api.security.JwtUser;
import com.example.post.api.security.JwtUtil;
import com.example.post.core.entity.RefreshToken;
import com.example.post.core.entity.User;
import com.example.post.core.service.RefreshTokenService;
import com.example.post.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                          RefreshTokenService refreshTokenService){
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<User> save(@Valid @RequestBody User user){
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody AuthenticationRequestDto authenticationRequestDto){
        try{
            String username = authenticationRequestDto.getUsername();
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, authenticationRequestDto.getPassword()));
            JwtUser userDetails = (JwtUser) authentication.getPrincipal();
            String token = jwtUtil.createToken(username, List.of("ADMIN"));
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
            return new ResponseEntity<>(new AuthenticationResponseDto(token, refreshToken.getToken()), HttpStatus.OK);
        }catch (AuthenticationException e){
            throw new BadCredentialsException("Bad credential ", e);
        }

    }

    @GetMapping(value = "/user/{id}", produces = "application/json")
    public ResponseEntity<User> getUser(@PathVariable long id){
        User user = userService.getById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/refreshtoken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequestDto request){
        String refreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtil.createToken(user.getUsername(), List.of("ADMIN"));
                    return ResponseEntity.ok(new TokenRefreshResponseDto(token, refreshToken));
                }).orElseThrow();
    }
}
