package br.com.gustavo.gym.organizer.service;

import br.com.gustavo.gym.organizer.dto.UserDTO.UserEditProfileDTO;
import br.com.gustavo.gym.organizer.exception.CredentialAlreadyInUseException;
import br.com.gustavo.gym.organizer.exception.DuplicateUserException;
import br.com.gustavo.gym.organizer.exception.UserNotFoundException;
import br.com.gustavo.gym.organizer.model.UsersModel;
import br.com.gustavo.gym.organizer.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    public UserDetails loadUserByUsername(String username) {
        return usersRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado: " + username));
    }

    public UsersModel register(String username, String password) {
        Optional<UsersModel> existingUser = usersRepository.findByUsername(username);

        if (existingUser.isPresent()) {
            throw new CredentialAlreadyInUseException("Este email já está em uso por outro usuário.");
        }

        UsersModel user = new UsersModel();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        return usersRepository.save(user);
    }

    public UsersModel updateProfile(UserEditProfileDTO dto, String username) {
        UsersModel user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado: " + username));

        if (dto.username() != null && !dto.username().isBlank() &&
                !dto.username().equals(user.getUsername()) &&
                usersRepository.findByUsername(dto.username()).isPresent()) {
            throw new DuplicateUserException("Email já está em uso por outro usuário.");
        }

        if (dto.username() != null && !dto.username().isBlank()) {
            user.setUsername(dto.username());
        }

        if (dto.password() != null && !dto.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.password()));
        }

        return usersRepository.save(user);
    }

    @Transactional
    public void deleteByUsername(String username) {
        UsersModel user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado: " + username));

        usersRepository.deleteByUsername(username);
    }
}
