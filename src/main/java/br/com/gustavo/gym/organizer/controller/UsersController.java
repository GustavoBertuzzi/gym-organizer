package br.com.gustavo.gym.organizer.controller;

import br.com.gustavo.gym.organizer.dto.UserDTO.UserLoginDTO;
import br.com.gustavo.gym.organizer.dto.UserDTO.UserTokenDTO;
import br.com.gustavo.gym.organizer.model.UsersModel;
import br.com.gustavo.gym.organizer.repository.UsersRepository;
import br.com.gustavo.gym.organizer.service.LoginService;
import br.com.gustavo.gym.organizer.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.gustavo.gym.organizer.dto.UserDTO.UserRegisterDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private LoginService loginService;

    @PostMapping("/register")
    public ResponseEntity<?> register (@Valid @RequestBody UserRegisterDTO userRegisterDTO){
        try{
            UsersModel save = usersService.register(userRegisterDTO.username(), userRegisterDTO.password());
            log.info("Usuário registrado com sucesso: {}", save.getUsername());
            return ResponseEntity.ok(save);
        }
        catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@Valid @RequestBody UserLoginDTO userLoginDTO){
        try{
            UserTokenDTO userToken = loginService.authenticate(userLoginDTO.username(), userLoginDTO.password());
            log.info("Usuário logado com sucesso: {}", userLoginDTO.username());
            return ResponseEntity.ok(userToken);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Login não encontrado");
        }
    }
}
