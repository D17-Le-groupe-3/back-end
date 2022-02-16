package fr.diginamic.digiday.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.diginamic.digiday.entities.LeaveCounters;

public interface LeaveCountersRepository extends JpaRepository<LeaveCounters, Integer> {
}
