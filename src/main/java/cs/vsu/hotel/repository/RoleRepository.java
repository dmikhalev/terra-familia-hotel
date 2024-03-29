package cs.vsu.hotel.repository;

import cs.vsu.hotel.entity.Role;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
