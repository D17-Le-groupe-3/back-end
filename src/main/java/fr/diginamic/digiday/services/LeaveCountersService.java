package fr.diginamic.digiday.services;

import fr.diginamic.digiday.entities.LeaveCounters;
import fr.diginamic.digiday.entities.User;
import fr.diginamic.digiday.exceptions.DigidayNotFoundException;
import fr.diginamic.digiday.repositories.LeaveCountersRepository;
import fr.diginamic.digiday.repositories.UserRepository;
import java.util.Optional;

import org.springframework.stereotype.Service;


/**
 * Classe service de gestion du compteur de solde
 * 
 * @author KULR
 * @since 1.0
 */
@Service
public class LeaveCountersService {
	
	private LeaveCountersRepository leaveCountersRepository;
	private UserRepository userRepository;

	public LeaveCountersService(LeaveCountersRepository leaveCountersRepository, UserRepository userRepository) {
		this.leaveCountersRepository = leaveCountersRepository;
		this.userRepository = userRepository;
	}
	
	/**
	 * Renvoie les compteurs de l'employé selon son id
	 * 
	 * @param idEmployee
	 * @return les compteurs de l'employé
	 */
	public LeaveCounters getLeaveCountersByEmployee(Integer idEmployee) {
		User employee = this.getEmployeeById(idEmployee);
		LeaveCounters leaveCounters = leaveCountersRepository.findByUser(employee).orElseThrow(() -> new DigidayNotFoundException("Leaves counter for this id doesn't exist"));
		return leaveCounters;
	}
	
	 /**
     * <p>
     * Vérifie que le salarié existe en base de donnée.
     * </p>
     * 
     * @param idEmployee : identifiant du salarie
	 * @return un employé s'il est trouvé
     * @throws DigidayNotFoundException si le salarié n'existe pas en base
     * @author KULR
     * @since 1.0
     */
   private User getEmployeeById(Integer idEmployee) throws DigidayNotFoundException {
    	return userRepository.findById(idEmployee).orElseThrow(() -> new DigidayNotFoundException("User with ID " + idEmployee + " does not exist"));
    }


}
