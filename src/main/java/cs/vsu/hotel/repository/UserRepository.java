package cs.vsu.hotel.repository;

import cs.vsu.hotel.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    Optional<User> findByLogin(String login);

    List<User> findAll();
}
