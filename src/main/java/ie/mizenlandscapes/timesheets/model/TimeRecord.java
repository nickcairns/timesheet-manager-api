package ie.mizenlandscapes.timesheets.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ie.mizenlandscapes.timesheets.repository.UserRepository;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "hours")

public class TimeRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "duration")
    private double duration;

    @Column(name = "client")
    private String client;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    public TimeRecord() {

    }

    public TimeRecord(LocalDate date, LocalTime startTime, LocalTime endTime, User user, String client) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.user = user;
        this.client = client;
        this.duration = calculateDuration();
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
        this.startTime = roundToNearestFiveMinutes(startTime);
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = roundToNearestFiveMinutes(endTime);
    }

    public LocalTime roundToNearestFiveMinutes(LocalTime time) {
        long minutes = time.getMinute();
        long roundedMinutes = Math.round(minutes / 5.0) * 5;
        return time.truncatedTo(ChronoUnit.HOURS).plusMinutes(roundedMinutes);
    }

    public double calculateDuration() {
        double duration = 0;
        if (startTime != null && endTime != null) {
            duration = (endTime.getHour() - startTime.getHour()) + ((endTime.getMinute() - startTime.getMinute()) / 60.0);
        }
        return Math.round(duration * 100.0) / 100.0; // round to 2 decimal places
    }
    public double getDuration() {
        return duration;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return("User: " + user.getName() + ", Date: " + date + ", Duration: " + duration + " hours" + ", Client: " + client);
    }

}