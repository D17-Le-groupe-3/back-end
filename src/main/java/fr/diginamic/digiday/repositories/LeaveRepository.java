package fr.diginamic.digiday.repositories;

import fr.diginamic.digiday.entities.Leave;
import fr.diginamic.digiday.entities.User;
import fr.diginamic.digiday.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LeaveRepository extends JpaRepository<Leave, Integer> {
    List<Leave> findByUserDepartmentIdAndStatusIn(Integer departmentId, List<LeaveStatus> statusList);

    Optional<Leave> findByIdAndStatus(Integer id, LeaveStatus status);
    
    /**
     * <p>Recherche de congés par utilisateur</p>
     * @param user: Données utilisateur
     * @return Liste de congés
     */
    @Query("select l from Leave l where l.user = ?1")
    List<Leave> findByUser(User user);
    
}
