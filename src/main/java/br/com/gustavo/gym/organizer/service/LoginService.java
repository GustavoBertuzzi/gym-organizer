package br.com.gustavo.gym.organizer.service;

import br.com.gustavo.gym.organizer.dto.userDTO.UserTokenDTO;
import br.com.gustavo.gym.organizer.exception.BadCredentialsException;
import br.com.gustavo.gym.organizer.exception.UserNotFoundException;
import br.com.gustavo.gym.organizer.model.UsersModel;
import br.com.gustavo.gym.organizer.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public UserTokenDTO authenticate(String email, String password) {
        UsersModel user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Usuário ou login não encontrados"));

        try {
            // Usa o e-mail como "username" para o Spring Security
            var authToken = new UsernamePasswordAuthenticationToken(email, password);
            authenticationManager.authenticate(authToken);
        } catch (Exception e) {
            throw new BadCredentialsException("Senha incorreta");
        }

        // Gera o token JWT
        var token = tokenService.generateToken(user);

        return new UserTokenDTO(token);
    }
}
