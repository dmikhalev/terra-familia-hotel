package cs.vsu.hotel.service;

import cs.vsu.hotel.entity.Feedback;
import cs.vsu.hotel.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public Feedback findById(Long id) {
        return feedbackRepository.findById(id).orElse(null);
    }

    public List<Feedback> findAll() {
        return new ArrayList<>(feedbackRepository.findAll());
    }

    public Feedback saveFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public void deleteFeedback(Long id) {
        feedbackRepository.deleteById(id);
    }

    public List<Feedback> getFeedbackByRoomId(Long roomId) {
        return new ArrayList<>(feedbackRepository.getFeedbackByRoomIdOrderByIdDesc(roomId));
    }
}
