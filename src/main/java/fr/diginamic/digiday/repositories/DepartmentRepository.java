package fr.diginamic.digiday.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.diginamic.digiday.entities.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
