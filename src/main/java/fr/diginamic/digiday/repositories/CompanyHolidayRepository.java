package fr.diginamic.digiday.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.diginamic.digiday.entities.CompanyHoliday;

public interface CompanyHolidayRepository extends JpaRepository<CompanyHoliday, Integer> {
}
