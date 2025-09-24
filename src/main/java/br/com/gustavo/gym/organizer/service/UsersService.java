package br.com.gustavo.gym.organizer.service;

import br.com.gustavo.gym.organizer.dto.userDTO.UserRegisterDTO;
import br.com.gustavo.gym.organizer.exception.CredentialAlreadyInUseException;
import br.com.gustavo.gym.organizer.exception.DuplicateUserException;
import br.com.gustavo.gym.organizer.exception.ExerciseAlreadyInUse;
import br.com.gustavo.gym.organizer.exception.UserNotFoundException;
import br.com.gustavo.gym.organizer.model.ExercisesModel;
import br.com.gustavo.gym.organizer.model.UsersModel;
import br.com.gustavo.gym.organizer.repository.ExercisesRepository;
import br.com.gustavo.gym.organizer.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UsersService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private ExercisesRepository exercisesRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return usersRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado: " + email));
    }

    public UsersModel register(String email, String password) {
        Optional<UsersModel> existingUser = usersRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            throw new CredentialAlreadyInUseException("Este email já está em uso por outro usuário.");
        }

        UsersModel user = new UsersModel();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        return usersRepository.save(user);
    }

    public UsersModel updateProfile(UserRegisterDTO dto, String email) {
        UsersModel user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado: " + email));

        if (dto.email() != null && !dto.email().isBlank() &&
                !dto.email().equals(user.getEmail()) &&
                usersRepository.findByEmail(dto.email()).isPresent()) {
            throw new DuplicateUserException("Email já está em uso por outro usuário.");
        }

        if (dto.email() != null && !dto.email().isBlank()) {
            user.setEmail(dto.email());
        }

        if (dto.password() != null && !dto.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.password()));
        }

        return usersRepository.save(user);
    }

    @Transactional
    public void deleteByEmail(String email) {
        UsersModel user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado: " + email));

        List<ExercisesModel> exercise = exercisesRepository.findByUser(user);

        if(!exercise.isEmpty()){
            throw new ExerciseAlreadyInUse("Impossível deletar conta, existem exercicios vinculados ao usuário");
        }

        usersRepository.deleteByEmail(email);
    }
}
