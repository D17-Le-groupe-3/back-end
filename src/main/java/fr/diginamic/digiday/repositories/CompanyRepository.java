package fr.diginamic.digiday.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.diginamic.digiday.entities.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
}
