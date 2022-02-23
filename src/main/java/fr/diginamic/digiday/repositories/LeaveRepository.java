package fr.diginamic.digiday.repositories;

import fr.diginamic.digiday.entities.Leave;
import fr.diginamic.digiday.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LeaveRepository extends JpaRepository<Leave, Integer> {
    List<Leave> findByUserDepartmentIdAndStatusIn(Integer departmentId, List<LeaveStatus> statusList);

    Optional<Leave> findByIdAndStatus(Integer id, LeaveStatus status);
}
