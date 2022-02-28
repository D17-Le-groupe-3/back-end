package fr.diginamic.digiday.services;

import fr.diginamic.digiday.dto.CreateCompanyHolidayDto;
import fr.diginamic.digiday.dto.CreateLeaveDto;
import fr.diginamic.digiday.entities.CompanyHoliday;
import fr.diginamic.digiday.enums.CompanyHolidayType;
import fr.diginamic.digiday.enums.LeaveStatus;
import fr.diginamic.digiday.enums.LeaveType;
import fr.diginamic.digiday.exceptions.DigidayBadRequestException;
import fr.diginamic.digiday.exceptions.DigidayNotFoundException;
import fr.diginamic.digiday.repositories.CompanyHolidayRepository;
import fr.diginamic.digiday.repositories.CompanyRepository;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

/**
 * Classe service de gestion des jours fériés et RTT employeurs
 *
 * @author PEV
 * @since 1.0
 */
@Service
public class CompanyHolidayService {

    private final CompanyHolidayRepository companyHolidayRepository;
    private final CompanyRepository companyRepository;


    public CompanyHolidayService(CompanyHolidayRepository companyHolidayRepository, CompanyRepository companyRepository) {
		this.companyHolidayRepository = companyHolidayRepository;
		this.companyRepository = companyRepository;
	}

	/**
     * Liste les jours fériés et RTT employeurs pour une année.
     * Filtre via le champ date de {@link CompanyHoliday}
     *
     * @param year année
     * @return les jours fériés et RTT employeurs correspondants
     */
    public List<CompanyHoliday> getCompanyHolidaysByYear(Integer year) {
        return companyHolidayRepository.findByYear(year);
    }
  
    /**
     * Liste les jours fériés et RTT employeurs pour un mois donné d'une année donnée.
     * Filtre via le champ date de {@link CompanyHoliday}
     *
     * @param month mois
     * @param year année
     * @return les jours fériés et RTT employeurs correspondants
     */
    public List<CompanyHoliday> getCompanyHolidaysByMonthAndYear(Integer month, Integer year) {
        return companyHolidayRepository.findByMonthAndYear(month, year);
    }
    
    /**
     * <p>
     * Créé un jour férié ou RTT employeur après verification des règles de gestion
     * </p>
     * 
     * <ol>
     * <li>Le type doit correspondre à un des type prédéfini</li>
     * <li>Un RTT employeur ne peut être posé un samedi ou un dimanche</li>
     * <li>Le nouveau jour férié ou RTT employeur ne doit pas tomber le même jour qu'un jour férié déjà existant</li>
     * </ol>
     * 
     * @param CreateCompanyHolidayDto : informations du jour férié ou RTT employeur à créer.
	 * @return Le jour férié ou RTT employeur créé
     * @throws DigidayBadRequestException si une règle de gestion n'est pas respectée
     * @author LPOU
     * @since 1.0
     */
    @Transactional
    public CompanyHoliday createCompanyHoliday(CreateCompanyHolidayDto createCompanyHolidayDto) throws DigidayBadRequestException {
    	CompanyHoliday ch = new CompanyHoliday();
    	// Gestion de la date
    	ch.setDate(createCompanyHolidayDto.getDate());
    	companyHolidayRepository.findByDate(ch.getDate()).ifPresent((value) -> {
    		throw new DigidayBadRequestException("There is already a company RTT or public holiday on ");
    	});
    	// Gestion du type
    	checkRulesMatchPredefinedType(createCompanyHolidayDto.getType());
		ch.setType(CompanyHolidayType.valueOf(createCompanyHolidayDto.getType()));
		// Gestion du jour de la semaine
		if (ch.getType() == CompanyHolidayType.COMPANY_RTT)
		{
			checkRulesIsWeekEndDay(ch.getDate());
			ch.setStatus(LeaveStatus.INITIAL);
		}
		ch.setComment(createCompanyHolidayDto.getComment());
		ch.setCompany(companyRepository.findById(1).orElseThrow(() -> new DigidayNotFoundException("Company not found")));
    	return companyHolidayRepository.save(ch);
    }
    
    /**
     * <p>
     * Vérifie que le type renseigné lors d'une création correspond aux type prédéfinis soit :
     * </p>
     * <ul>
     * <li>COMPANY_RTT - RTT employeur</li>
     * <li>PUBLIC_HOLIDAY - Jour férié</li>
     * </ul>
     * 
     * @param String : type brut à vérifier
     * @throws DigidayBadRequestException si le type n'est pas conforme.
     * @author LPOU
     * @see #createCompanyHoliday
     * @since 1.0
     */
    private void checkRulesMatchPredefinedType(String type) throws DigidayBadRequestException {
		try {
		    CompanyHolidayType.valueOf(type);
		} catch (Exception ex) {
		    throw new DigidayBadRequestException("Company holiday type " + type + " does not match an existing one. Expected types are : "
			    + CompanyHolidayType.COMPANY_RTT.toString() + " or " + CompanyHolidayType.PUBLIC_HOLIDAY.toString());
		}
    }
    
    /**
     * <p>
     * Vérifie que la date renseignée lors d'une création n'est pas un Samedi ou Dimanche
     * 
     * @param LocalDate : date à vérifier
     * @throws DigidayBadRequestException si le jour est un Samedi ou Dimanche.
     * @author LPOU
     * @see #createCompanyHoliday
     * @since 1.0
     */
    private void checkRulesIsWeekEndDay(LocalDate date) throws DigidayBadRequestException {
    	if ((date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY))
    		throw new DigidayBadRequestException("Company RTT cannot be created on Saturdays or Sundays.");
    }
  
    /**
     * Supprime un jour férié ou RTT employeur après vérification des règles métier.
     *
     * @param companyHolidayId id du jour férié ou RTT employeur à supprimer
     * @return vrai si la suppression est effectuée
     */
    public boolean deleteCompanyHoliday(Integer companyHolidayId) {
        CompanyHoliday companyHoliday = companyHolidayRepository.findById(companyHolidayId)
                .orElseThrow(() -> new DigidayNotFoundException("Company holiday  of id " + companyHolidayId + "does not exist"));

        if (companyHoliday.getDate().isBefore(LocalDate.now()))
            throw new DigidayBadRequestException("Cannot delete company holiday in the past");

        if (companyHoliday.getType().equals(CompanyHolidayType.COMPANY_RTT) && companyHoliday.getStatus().equals(LeaveStatus.VALIDATED))
            throw new DigidayBadRequestException("Cannot delete validated company holiday");

        companyHolidayRepository.delete(companyHoliday);
        return true;
    }
}
