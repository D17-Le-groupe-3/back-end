package fr.diginamic.digiday.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.diginamic.digiday.entities.LeaveCounters;
import fr.diginamic.digiday.entities.User;

public interface LeaveCountersRepository extends JpaRepository<LeaveCounters, Integer> {
	
	 /**
     * <p>Recherche des soldes de congés</p>
     * @param user: Données utilisateur
     * @return soldes de congés
     */
	@Query("select lc from LeaveCounters lc where lc.user = ?1 ")
	Optional <LeaveCounters> findByUser(User user);
		
}
