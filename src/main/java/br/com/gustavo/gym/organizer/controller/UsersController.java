package br.com.gustavo.gym.organizer.controller;

import br.com.gustavo.gym.organizer.dto.userDTO.UserLoginDTO;
import br.com.gustavo.gym.organizer.dto.userDTO.UserRegisterDTO;
import br.com.gustavo.gym.organizer.dto.userDTO.UserTokenDTO;
import br.com.gustavo.gym.organizer.model.UsersModel;
import br.com.gustavo.gym.organizer.service.LoginService;
import br.com.gustavo.gym.organizer.service.UsersService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
/*import org.springframework.security.oauth2.core.user.OAuth2User;*/
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
        UsersModel save = usersService.register(userRegisterDTO.email(), userRegisterDTO.password());
        log.info("Usuário registrado com sucesso: {}", save.getEmail());
        return ResponseEntity.ok(save);
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenDTO> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        UserTokenDTO userToken = loginService.authenticate(userLoginDTO.email(), userLoginDTO.password());
        log.info("Usuário logado com sucesso: {}", userLoginDTO.email());
        return ResponseEntity.ok(userToken);
    }

    /* @GetMapping("/google")
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
    } */

    @PutMapping("/me")
    public ResponseEntity<UsersModel> editPerfil(@RequestBody UserRegisterDTO userRegisterDTO, Authentication authentication) {

        String email = authentication.getName();
        UsersModel edit = usersService.updateProfile(userRegisterDTO, email);
        log.info("Usuário atualizado com sucesso");
        return ResponseEntity.ok(edit);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePerfil(Authentication authentication) {
        String email = authentication.getName();
        usersService.deleteByEmail(email);
        return ResponseEntity.ok("Conta deletada com sucesso!");
    }
}
