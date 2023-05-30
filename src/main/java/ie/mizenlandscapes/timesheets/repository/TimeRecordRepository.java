package ie.mizenlandscapes.timesheets.repository;

import ie.mizenlandscapes.timesheets.model.TimeRecord;
import ie.mizenlandscapes.timesheets.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TimeRecordRepository extends JpaRepository<TimeRecord, Long> {
    
    List<TimeRecord> findByDate(LocalDate date);

    List<TimeRecord> findByDateBetween(LocalDate startDate, LocalDate endDate);

    List<TimeRecord> findByDateBetweenAndUserIs(LocalDate startDate, LocalDate endDate, User user);
    
    List<TimeRecord> findByDateBetweenAndClientIsAndUserIs(LocalDate startDate, LocalDate endDate, String client, User user);
    
    List<TimeRecord> findByClient(String client);
    
    List<TimeRecord> findByUser(User user);

    List<TimeRecord> findByDateBetweenAndUserIsIn(LocalDate startDate, LocalDate endDate, List<User> users);
}
