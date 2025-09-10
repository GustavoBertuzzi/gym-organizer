package br.com.gustavo.gym.organizer.service;

import br.com.gustavo.gym.organizer.dto.UserDTO.UserTokenDTO;
import br.com.gustavo.gym.organizer.exception.BadCredentialsException;
import br.com.gustavo.gym.organizer.exception.UserNotFoundException;
import br.com.gustavo.gym.organizer.model.UsersModel;
import br.com.gustavo.gym.organizer.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public UserTokenDTO authenticate(String username, String password) {
        UsersModel user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Usuário ou login não encontrados"));

        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(username, password);
            authenticationManager.authenticate(usernamePassword);
        } catch (Exception e) {
            throw new BadCredentialsException("Senha incorreta");
        }

        // Gera o token
        var token = tokenService.generateToken(user);

        return new UserTokenDTO(token);
    }

}
