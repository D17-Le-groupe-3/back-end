package fr.diginamic.digiday.repositories;

import fr.diginamic.digiday.entities.CompanyHoliday;
import fr.diginamic.digiday.enums.CompanyHolidayType;
import fr.diginamic.digiday.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CompanyHolidayRepository extends JpaRepository<CompanyHoliday, Integer> {

    @Query("select ch from CompanyHoliday ch where year(ch.date) = ?1")
    List<CompanyHoliday> findByYear(Integer year);

    @Query("select count(ch) from CompanyHoliday ch where (ch.status is null or ch.status = 'VALIDATED') and (ch.date between ?1 and ?2)")
    Integer findCountBetweenDates(LocalDate startDate, LocalDate endDate);

    List<CompanyHoliday> findByTypeAndStatus(CompanyHolidayType companyHolidayType, LeaveStatus status);
}
