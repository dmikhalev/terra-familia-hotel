package cs.vsu.hotel.controller;

import cs.vsu.hotel.dto.FeedbackDto;
import cs.vsu.hotel.entity.Feedback;
import cs.vsu.hotel.entity.Room;
import cs.vsu.hotel.service.FeedbackService;
import cs.vsu.hotel.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;


@Controller
@RequestMapping("/comments")
public class FeedbackController {

    private final RoomService roomService;
    private final FeedbackService feedbackService;

    public FeedbackController(RoomService roomService, FeedbackService feedbackService) {
        this.roomService = roomService;
        this.feedbackService = feedbackService;
    }

    @GetMapping()
    public String createCommentsPage(Model model) {
        SortedMap<Integer, List<FeedbackDto>> roomNumberToFeedbacks = new TreeMap<>();
        for (int i = 1; i <= 4; i++) {
            Room room = roomService.findRoomByNumber(i);
            if (room != null) {
                List<Feedback> feedbacks = feedbackService.getFeedbackByRoomId(room.getId());
                List<FeedbackDto> feedbackDtos = new ArrayList<>();
                feedbacks.forEach(feedback -> feedbackDtos.add(
                        new FeedbackDto(feedback.getId(), feedback.getUsername(), feedback.getComment(), feedback.getIsLike())));
                roomNumberToFeedbacks.put(i, feedbackDtos);
            }
        }
        model.addAttribute("roomNumberToFeedbacks", roomNumberToFeedbacks);
        return "comments";
    }

    @PostMapping()
    public String addComment(Model model, FeedbackDto feedbackDto) {
        if (feedbackDto.getName() != null && !feedbackDto.getName().isEmpty()
                && feedbackDto.getComment() != null && !feedbackDto.getComment().isEmpty()) {
            Room room = roomService.findRoomByNumber(feedbackDto.getRoomNumber());
            if (room != null) {
                Feedback feedback = new Feedback(
                        feedbackDto.isLike(),
                        feedbackDto.getComment(),
                        feedbackDto.getName(),
                        room);
                feedbackService.saveFeedback(feedback);
                System.out.println(feedback.getComment());
            }
        }
        return createCommentsPage(model);
    }

    @PostMapping(path = "delete/" + "{id}")
    public String deleteComment(Model model, @PathVariable Long id) {
        feedbackService.deleteFeedback(id);
        return createCommentsPage(model);
    }
}
