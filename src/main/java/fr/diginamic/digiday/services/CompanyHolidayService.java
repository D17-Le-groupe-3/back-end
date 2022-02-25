package fr.diginamic.digiday.services;

import fr.diginamic.digiday.entities.CompanyHoliday;
import fr.diginamic.digiday.enums.CompanyHolidayType;
import fr.diginamic.digiday.enums.LeaveStatus;
import fr.diginamic.digiday.exceptions.DigidayBadRequestException;
import fr.diginamic.digiday.exceptions.DigidayNotFoundException;
import fr.diginamic.digiday.repositories.CompanyHolidayRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Classe service de gestion des jours fériés et RTT employeurs
 *
 * @author PEV
 * @since 1.0
 */
@Service
public class CompanyHolidayService {

    private final CompanyHolidayRepository companyHolidayRepository;

    public CompanyHolidayService(CompanyHolidayRepository companyHolidayRepository) {
        this.companyHolidayRepository = companyHolidayRepository;
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
