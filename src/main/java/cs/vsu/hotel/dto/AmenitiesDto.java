package cs.vsu.hotel.dto;

import cs.vsu.hotel.entity.Amenities;
import lombok.Data;

import java.util.List;

@Data
public class AmenitiesDto {

    private List<AmenitiesRow> amenitiesRows;

    public AmenitiesDto() {

    }

    public AmenitiesDto(List<AmenitiesRow> amenitiesRows) {
        this.amenitiesRows = amenitiesRows;
    }

    @Data
    public static class AmenitiesRow {
        private long id;
        private String name;
        private boolean isInRoom1;
        private boolean isInRoom2;
        private boolean isInRoom3;
        private boolean isInRoom4;

        public Amenities toAmenities() {
            Amenities amenities = new Amenities();
            amenities.setName(name);
            return amenities;
        }

        public boolean isInRoom1() {
            return isInRoom1;
        }

        public boolean isInRoom2() {
            return isInRoom2;
        }

        public boolean isInRoom3() {
            return isInRoom3;
        }

        public boolean isInRoom4() {
            return isInRoom4;
        }

        public void setInRoom1(boolean inRoom1) {
            isInRoom1 = inRoom1;
        }

        public void setInRoom2(boolean inRoom2) {
            isInRoom2 = inRoom2;
        }

        public void setInRoom3(boolean inRoom3) {
            isInRoom3 = inRoom3;
        }

        public void setInRoom4(boolean inRoom4) {
            isInRoom4 = inRoom4;
        }

    }
}