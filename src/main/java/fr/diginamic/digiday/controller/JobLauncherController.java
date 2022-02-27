package fr.diginamic.digiday.controller;

import fr.diginamic.digiday.dto.CompanyHolidayDto;
import fr.diginamic.digiday.dto.LeaveDto;
import fr.diginamic.digiday.entities.BaseEntity;
import fr.diginamic.digiday.services.NightJobService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class JobLauncherController {

    private final NightJobService nightJobService;
    private final ModelMapper modelMapper;

    public JobLauncherController(NightJobService nightJobService, ModelMapper modelMapper) {
        this.nightJobService = nightJobService;
        this.modelMapper = modelMapper;
    }

    /**
     * Déclenche le traitement de nuit.
     * @see NightJobService
     *
     * @return une liste des demandes d'absences et RTT employeurs traitées
     */
    @RequestMapping("run-batch")
    public Map<String, List<?>> runBatch() {
        Map<String, List<? extends BaseEntity>> result = nightJobService.run();

        return Map.of(
                "processedLeaves", result.get("processedLeaves").stream().map(leave -> modelMapper.map(leave, LeaveDto.class)).collect(Collectors.toList()),
                "processedCompanyHolidays", result.get("processedCompanyHolidays").stream().map(companyHoliday -> modelMapper.map(companyHoliday, CompanyHolidayDto.class)).collect(Collectors.toList())
        );
    }
}
