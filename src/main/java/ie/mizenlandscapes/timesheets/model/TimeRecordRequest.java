package ie.mizenlandscapes.timesheets.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class TimeRecordRequest {

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private long userId;
    private String client;

    public TimeRecordRequest() {
    }

    public TimeRecordRequest(LocalDate date, LocalTime startTime, LocalTime endTime, long userId, String client) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userId = userId;
        this.client = client;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

}
