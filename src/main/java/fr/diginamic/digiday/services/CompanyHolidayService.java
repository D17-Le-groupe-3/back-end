package fr.diginamic.digiday.services;

import fr.diginamic.digiday.entities.CompanyHoliday;
import fr.diginamic.digiday.repositories.CompanyHolidayRepository;
import org.springframework.stereotype.Service;

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
}
