package br.com.gustavo.gym.organizer.controller;

import br.com.gustavo.gym.organizer.dto.UserDTO.UserEditProfileDTO;
import br.com.gustavo.gym.organizer.dto.UserDTO.UserLoginDTO;
import br.com.gustavo.gym.organizer.dto.UserDTO.UserRegisterDTO;
import br.com.gustavo.gym.organizer.dto.UserDTO.UserTokenDTO;
import br.com.gustavo.gym.organizer.model.UsersModel;
import br.com.gustavo.gym.organizer.service.LoginService;
import br.com.gustavo.gym.organizer.service.UsersService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private LoginService loginService;

    @PostMapping("/register")
    public ResponseEntity<UsersModel> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        UsersModel save = usersService.register(userRegisterDTO.username(), userRegisterDTO.password());
        log.info("Usuário registrado com sucesso: {}", save.getUsername());
        return ResponseEntity.ok(save);
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenDTO> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        UserTokenDTO userToken = loginService.authenticate(userLoginDTO.username(), userLoginDTO.password());
        log.info("Usuário logado com sucesso: {}", userLoginDTO.username());
        return ResponseEntity.ok(userToken);
    }

    @GetMapping("/google")
    public ResponseEntity<String> googleUser(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body("Usuário não autenticado com Google");
        }

        String name = principal.getAttribute("name");
        String email = principal.getAttribute("email");
        String picture = principal.getAttribute("picture");

        return ResponseEntity.ok(
                "Usuário autenticado pelo Google: " + name +
                        " | Email: " + email +
                        " | Foto: " + picture
        );
    }

    @PutMapping("/me")
    public ResponseEntity<UsersModel> editPerfil(@RequestBody UserEditProfileDTO userEditProfileDTO,
                                                 Authentication authentication) {

        if (userEditProfileDTO == null) {
            return ResponseEntity.badRequest().build();
        }

        String username = authentication.getName();
        UsersModel edit = usersService.updateProfile(userEditProfileDTO, username);
        log.info("Usuário atualizado com sucesso");
        return ResponseEntity.ok(edit);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePerfil(Authentication authentication) {
        String username = authentication.getName();
        usersService.deleteByUsername(username);
        return ResponseEntity.ok("Conta deletada com sucesso!");
    }
}
