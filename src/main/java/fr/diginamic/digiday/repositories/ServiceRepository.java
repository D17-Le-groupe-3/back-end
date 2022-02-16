package fr.diginamic.digiday.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.diginamic.digiday.entities.Service;

public interface ServiceRepository extends JpaRepository<Service, Integer>
{
}
