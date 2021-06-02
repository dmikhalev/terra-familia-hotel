package cs.vsu.hotel.repository;

import cs.vsu.hotel.entity.Feedback;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface FeedbackRepository extends PagingAndSortingRepository<Feedback, Long> {

    List<Feedback> findAll();

    List<Feedback> getFeedbackByRoomIdOrderByIdDesc(Long roomId);
}
