package ie.mizenlandscapes.timesheets.service;

import ie.mizenlandscapes.timesheets.dto.TimeRecordDTO;
import ie.mizenlandscapes.timesheets.model.TimeRecord;
import ie.mizenlandscapes.timesheets.model.TimeRecordRequest;
import ie.mizenlandscapes.timesheets.model.User;
import ie.mizenlandscapes.timesheets.repository.TimeRecordRepository;
import ie.mizenlandscapes.timesheets.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TimeRecordService {
    private final TimeRecordRepository timeRecordRepository;
    private final UserRepository userRepository;

    public TimeRecordService(TimeRecordRepository timeRecordRepository, UserRepository userRepository) {
        this.timeRecordRepository = timeRecordRepository;
        this.userRepository = userRepository;
    }

    public TimeRecordDTO createTimeRecord(TimeRecordRequest timeRecordRequest) {
        User user = userRepository.findById(timeRecordRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        TimeRecord timeRecord = new TimeRecord(
                timeRecordRequest.getDate(),
                timeRecordRequest.getStartTime(),
                timeRecordRequest.getEndTime(),
                user,
                timeRecordRequest.getClient()

        );
        timeRecord.calculateDuration(); // calculateDuration includes the setDuration method
        System.out.println("TimeRecordService: " + timeRecord.getDuration() + " hours");
        System.out.println("TimeRecord: " + timeRecord);
        timeRecordRepository.save(timeRecord);
        return entityToDto(timeRecord);
    }

    public List<TimeRecordDTO> getAllTimeRecords() {
        List<TimeRecord> timeRecords = timeRecordRepository.findAll();
        return timeRecords.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public Optional<TimeRecordDTO> getTimeRecordById(Long id) {
        Optional<TimeRecord> timeRecordOptional = timeRecordRepository.findById(id);
        return timeRecordRepository.findById(id).map(this::entityToDto);
    }

    public TimeRecordDTO updateTimeRecord(Long id, TimeRecord updatedTimeRecord) {
        TimeRecord timeRecord = timeRecordRepository.getOne(id);
        timeRecord.setDate(updatedTimeRecord.getDate());
        timeRecord.setStartTime(updatedTimeRecord.getStartTime());
        timeRecord.setEndTime(updatedTimeRecord.getEndTime());
        timeRecord.setClient(updatedTimeRecord.getClient());
        timeRecord.calculateDuration();
        timeRecordRepository.save(timeRecord);
        return entityToDto(timeRecord);
    }

    public void deleteTimeRecord(Long id) {
        timeRecordRepository.deleteById(id);
    }

    public List<TimeRecordDTO> findMatchingTimeRecords(LocalDate startDate, LocalDate endDate, Optional<String> client, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<TimeRecord> timeRecords;
        if (client.isPresent()) {
            timeRecords = timeRecordRepository.findByDateBetweenAndClientIsAndUserIs(startDate, endDate, client.get(), user);
        } else {
            timeRecords = timeRecordRepository.findByDateBetweenAndUserIs(startDate, endDate, user);
        }
        return timeRecords.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public List<TimeRecordDTO> findMatchingTimeRecordsForUsers(LocalDate startDate, LocalDate endDate, List<Long> userIds) {
        List<User> users = userRepository.findByIdIn(userIds);

        // Optional: handle situation where some user IDs were not found
        if (users.size() != userIds.size()) {
            // You can throw an exception, return partial result, or handle this situation differently depending on your specific needs
        }

        List<TimeRecord> timeRecords = timeRecordRepository.findByDateBetweenAndUserIsIn(startDate, endDate, users);
        return timeRecords.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public List<TimeRecordDTO> getTimeRecordsByClient(String client) {
        List<TimeRecord> timeRecords = timeRecordRepository.findByClient(client);
        return timeRecords.stream().map(this::entityToDto).collect(Collectors.toList());
    }


    public List<TimeRecordDTO> getTimeRecordsByDates(LocalDate startDate, LocalDate endDate) {
        List<TimeRecord> timeRecords = timeRecordRepository.findByDateBetween(startDate, endDate);
        return timeRecords.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    private TimeRecordDTO entityToDto(TimeRecord timeRecord) {
        TimeRecordDTO dto = new TimeRecordDTO();
        dto.setId(timeRecord.getId());
        dto.setDate(timeRecord.getDate());
        dto.setStartTime(timeRecord.getStartTime());
        dto.setEndTime(timeRecord.getEndTime());
        dto.setDuration(timeRecord.getDuration());
        dto.setClient(timeRecord.getClient());
        dto.setUser(timeRecord.getUser()); //Provides both name and id
        return dto;
    }
}

