package ie.mizenlandscapes.timesheets.controller;

import ie.mizenlandscapes.timesheets.dto.TimeRecordDTO;
import ie.mizenlandscapes.timesheets.model.TimeRecordRequest;
import ie.mizenlandscapes.timesheets.service.TimeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin//(origins = "http://localhost:8082")
@RestController
@RequestMapping("/api")
public class TimeRecordController {

    @Autowired
    private TimeRecordService timeRecordService;

    public TimeRecordController(TimeRecordService timeRecordService) {
        this.timeRecordService = timeRecordService;
    }

    @GetMapping("/timerecords")
    public ResponseEntity<List<TimeRecordDTO>> getAll() {
        try {
            List<TimeRecordDTO> timeRecords = timeRecordService.getAllTimeRecords();
            if (timeRecords.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(timeRecords, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/timerecords/findmatching")
    public ResponseEntity<List<TimeRecordDTO>> findMatching(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                                         @RequestParam(required = false) Optional<String> client,
                                                         @RequestParam Long userId) {
        try {
            List<TimeRecordDTO> timeRecords = timeRecordService.findMatchingTimeRecords(startDate, endDate, client, userId);
            if (timeRecords.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(timeRecords, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/timerecords/dates")
    public ResponseEntity<List<TimeRecordDTO>> findByDates(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            List<TimeRecordDTO> timeRecords = timeRecordService.getTimeRecordsByDates(startDate, endDate);
            if (timeRecords.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(timeRecords, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/timerecords/findmatching/multipleusers")
    public ResponseEntity<List<TimeRecordDTO>> findMatchingMultipleUsers(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                                                      @RequestParam String userIds) {
        try {
            List<Long> userIdList = Arrays.stream(userIds.split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            List<TimeRecordDTO> timeRecords = timeRecordService.findMatchingTimeRecordsForUsers(startDate, endDate, userIdList);

            if (timeRecords.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(timeRecords, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/timerecords/client")
    public ResponseEntity<List<TimeRecordDTO>> findByClient(@RequestParam String client) {
        try {
            List<TimeRecordDTO> timeRecords = timeRecordService.getTimeRecordsByClient(client);
            if (timeRecords.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(timeRecords, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/timerecords")
    public ResponseEntity<TimeRecordDTO> createTimeRecord(@RequestBody TimeRecordRequest timeRecordRequest) {
        try { // TODO - update method to use TimeRecordRequest
            TimeRecordDTO newTimeRecord = timeRecordService.createTimeRecord(timeRecordRequest);
            System.out.println("TimeRecordController, given input: " + timeRecordRequest);
            return new ResponseEntity<>(newTimeRecord, HttpStatus.CREATED);
        } catch (Exception e) {
            // Log the error message and stack trace
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/timerecords/{id}")
    public ResponseEntity<HttpStatus> deleteTimeRecord(@PathVariable("id") long id) {
        try {
            timeRecordService.deleteTimeRecord(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
