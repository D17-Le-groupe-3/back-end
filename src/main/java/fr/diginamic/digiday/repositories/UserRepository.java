package fr.diginamic.digiday.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.diginamic.digiday.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>
{
}
