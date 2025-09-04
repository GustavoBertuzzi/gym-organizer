package br.com.gustavo.gym.organizer.service;

import br.com.gustavo.gym.organizer.model.UsersModel;
import br.com.gustavo.gym.organizer.repository.UsersRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UsersService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username){
        return usersRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Usuário não encontrado: " + username)
        );
    }

    public UsersModel register (String username, String password){

        UsersModel user = new UsersModel();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        return usersRepository.save(user);
    }

}
