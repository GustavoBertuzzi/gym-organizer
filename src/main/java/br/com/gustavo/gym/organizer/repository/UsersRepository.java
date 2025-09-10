package br.com.gustavo.gym.organizer.repository;

import br.com.gustavo.gym.organizer.model.UsersModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<UsersModel, Long> {
    Optional<UsersModel> findByUsername (String username);

    void deleteByUsername(String username);

}
