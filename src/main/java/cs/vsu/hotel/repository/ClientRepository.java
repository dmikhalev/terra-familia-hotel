package cs.vsu.hotel.repository;

import cs.vsu.hotel.entity.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {
    List<Client> findAll();

    Optional<Client> findByPassportId(Long passportId);

    @Query("SELECT c FROM Client c WHERE LOWER(CONCAT(c.firstName, ' ', c.lastName)) LIKE LOWER(CONCAT('%',:name,'%')) ")
    List<Client> findByPartOfName(@Param("name") String name);
}
