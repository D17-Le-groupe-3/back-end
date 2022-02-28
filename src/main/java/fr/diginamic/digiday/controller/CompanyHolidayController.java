package fr.diginamic.digiday.controller;

import fr.diginamic.digiday.dto.CompanyHolidayDto;
import fr.diginamic.digiday.dto.CreateCompanyHolidayDto;
import fr.diginamic.digiday.entities.CompanyHoliday;
import fr.diginamic.digiday.services.CompanyHolidayService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("company-holidays")
public class CompanyHolidayController {

    private final CompanyHolidayService companyHolidayService;
    private final ModelMapper modelMapper;

    public CompanyHolidayController(CompanyHolidayService companyHolidayService, ModelMapper modelMapper) {
        this.companyHolidayService = companyHolidayService;
        this.modelMapper = modelMapper;
    }

    /**
     * Renvoie une liste de jours fériés et RTT employeurs filtrée par mois et année.
     *
     * @param month (optionnel) mois servant à filtrer
     * @param year année servant à filtrer
     * @return tableau de jours au format JSON
     */
    @GetMapping
    public List<CompanyHolidayDto> getByMonthAndYear(@RequestParam Optional<Integer> month, @RequestParam Integer year) {
    	List<CompanyHoliday> results;
    	if (month.isEmpty())
    		results = companyHolidayService.getCompanyHolidaysByYear(year);
    	else
    		results = companyHolidayService.getCompanyHolidaysByMonthAndYear(month.get(), year);
        return results.stream().map(companyHoliday -> modelMapper.map(companyHoliday, CompanyHolidayDto.class))
            .collect(Collectors.toList());
    }

  /**
     * Répond à une requête post de création de jour férié/RTT employeur.
     *
     * @param CreateCompanyHolidayDto données du jour férié/RTT employeur
     * @return le jour férié/RTT employeur créé
     */
    @PostMapping
    public ResponseEntity<CompanyHolidayDto> createCompanyHoliday(@RequestBody CreateCompanyHolidayDto createCompanyHolidayDto) {
    	return ResponseEntity.ok(modelMapper.map(companyHolidayService.createCompanyHoliday(createCompanyHolidayDto), CompanyHolidayDto.class));
    }

    /**
     * Répond à une requête delete de suppression de jour férié/RTT employeur.
     *
     * @param id id du jour férié/RTT employeur
     * @return un booléen à vrai si la suppression est effectuée
     */
    @DeleteMapping(path = "{id}")
    public Map<String, Boolean> delete(@PathVariable Integer id) {
        return Map.of("companyHolidayDeleted", companyHolidayService.deleteCompanyHoliday(id));
    }
}
