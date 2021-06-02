package cs.vsu.hotel.dto;

import lombok.Data;

@Data
public class FeedbackDto {

    private Long id;
    private String name;
    private String comment;
    private boolean isLike;
    private int roomNumber;

    public FeedbackDto(Long id, String name, String comment, boolean isLike) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.isLike = isLike;
    }

    public FeedbackDto() {
    }
}
