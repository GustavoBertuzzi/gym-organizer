package br.com.gustavo.gym.organizer.service;

import br.com.gustavo.gym.organizer.dto.UserDTO.UserTokenDTO;
import br.com.gustavo.gym.organizer.model.UsersModel;
import br.com.gustavo.gym.organizer.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public UserTokenDTO authenticate(String username, String password){
        var usernamePassword = new UsernamePasswordAuthenticationToken(username, password);
        var auth = authenticationManager.authenticate(usernamePassword);
        UsersModel user = (UsersModel) auth.getPrincipal();
        var token = tokenService.generateToken(user);

        return new UserTokenDTO(token);
    }

}
