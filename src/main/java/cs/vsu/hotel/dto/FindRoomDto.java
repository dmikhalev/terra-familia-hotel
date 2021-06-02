package cs.vsu.hotel.dto;

import cs.vsu.hotel.util.DateUtils;
import lombok.Data;

import java.sql.Date;

@Data
public class FindRoomDto {
    private String startDate;
    private String endDate;
    private int adultCount;
    private int childCount;

    public FindRoomDto(String startDate, String endDate, int adultCount, int childCount) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.adultCount = adultCount;
        this.childCount = childCount;
    }

    public FindRoomDto() {
    }


    public Date getFormatStartDate() {
        return DateUtils.parseDate(startDate);
    }

    public Date getFormatEndDate() {
        return DateUtils.parseDate(endDate);
    }
}
