package fr.diginamic.digiday.controller;

import fr.diginamic.digiday.dto.CompanyHolidayDto;
import fr.diginamic.digiday.services.CompanyHolidayService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
     * Renvoie une liste de jours fériés et RTT employeurs filtrée par an.
     *
     * @param year année servant à filtrer
     * @return tableau de jours au format JSON
     */
    @GetMapping
    public List<CompanyHolidayDto> getByYear(@RequestParam Integer year) {
        return companyHolidayService.getCompanyHolidaysByYear(year).stream()
            .map(companyHoliday -> modelMapper.map(companyHoliday, CompanyHolidayDto.class))
            .collect(Collectors.toList());
    }
    
    /**
     * Renvoie une liste de jours fériés et RTT employeurs filtrée par mois et année.
     *
     * @param year année servant à filtrer
     * @return tableau de jours au format JSON
     */
    @GetMapping
    public List<CompanyHolidayDto> getByMonthAndYear(@RequestParam Integer month, @RequestParam Integer year) {
        return companyHolidayService.getCompanyHolidaysByYear(year).stream()
            .map(companyHoliday -> modelMapper.map(companyHoliday, CompanyHolidayDto.class))
            .collect(Collectors.toList());
    }
}
