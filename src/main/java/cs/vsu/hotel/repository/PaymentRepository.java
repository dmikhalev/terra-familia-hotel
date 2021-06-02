package cs.vsu.hotel.repository;

import cs.vsu.hotel.entity.Payment;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PaymentRepository extends PagingAndSortingRepository<Payment, Long> {
}
