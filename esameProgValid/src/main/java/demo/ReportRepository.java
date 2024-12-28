package demo;

import org.springframework.data.repository.CrudRepository;

public interface ReportRepository  extends CrudRepository<Report, Long> {
    Report findById(long id);
}
