package fr.diginamic.digiday.repositories;

import fr.diginamic.digiday.entities.CompanyHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyHolidayRepository extends JpaRepository<CompanyHoliday, Integer> {

    @Query("select ch from CompanyHoliday ch where year(ch.date) = ?1")
    List<CompanyHoliday> findByYear(Integer year);
    
    @Query("select ch from CompanyHoliday ch where month(ch.date) = ?1 and year(ch.date) = ?2")
    List<CompanyHoliday> findByMonthAndYear(Integer month, Integer year);
}
