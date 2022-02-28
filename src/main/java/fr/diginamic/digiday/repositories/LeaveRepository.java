package fr.diginamic.digiday.repositories;

import fr.diginamic.digiday.entities.Leave;
import fr.diginamic.digiday.entities.User;
import fr.diginamic.digiday.enums.LeaveStatus;
import fr.diginamic.digiday.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LeaveRepository extends JpaRepository<Leave, Integer> {
    List<Leave> findByUserDepartmentIdAndUserRoleNotAndStatusIn(Integer departmentId, Role role, List<LeaveStatus> statusList);

    Optional<Leave> findByIdAndStatus(Integer id, LeaveStatus status);
    
    /**
     * <p>Recherche de congés par utilisateur</p>
     * @param user: Données utilisateur
     * @return Liste de congés
     */
    @Query("select l from Leave l where l.user = ?1")
    List<Leave> findByUser(User user);
    
    /**
     * <p>Recherche les congés d'un utilisateur qui croisent une période de temps donnée</p>
     * @param user: Données utilisateur
     * @param startDate: début de la période de temps
     * @param endDate: fin de la période de temps
     * @return Liste de congés
     */
    @Query("select l from Leave l where l.user = ?1 and ((month(l.startDate) = month(?2) and year(l.startDate) = year(?3)) or (month(l.endDate) = month(?2) and year(l.endDate) = year(?3)) or ?2 between l.startDate and l.endDate)")
    List<Leave> findByUserAndTimeInterval(User user, LocalDate startDate, LocalDate endDate);
  
    List<Leave> findByStatusOrderByStartDateAsc(LeaveStatus status);
}
