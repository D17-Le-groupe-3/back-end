package fr.diginamic.digiday.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.diginamic.digiday.entities.Leave;

public interface LeaveRepository extends JpaRepository<Leave, Integer>
{
}
