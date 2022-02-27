package fr.diginamic.digiday.services;

import fr.diginamic.digiday.entities.*;
import fr.diginamic.digiday.enums.CompanyHolidayType;
import fr.diginamic.digiday.enums.LeaveStatus;
import fr.diginamic.digiday.enums.LeaveType;
import fr.diginamic.digiday.repositories.CompanyHolidayRepository;
import fr.diginamic.digiday.repositories.CompanyRepository;
import fr.diginamic.digiday.repositories.LeaveCountersRepository;
import fr.diginamic.digiday.repositories.LeaveRepository;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Service en charge de passer les demandes d'absence du statut initial à en attente de validation
 * à condition que les règles métiers soient respectées.
 *
 * @author PEV
 */
@Service
public class NightJobService {

    private final LeaveRepository leaveRepository;
    private final LeaveCountersRepository leaveCountersRepository;
    private final CompanyHolidayRepository companyHolidayRepository;
    private final CompanyRepository companyRepository;

    private final FreeMarkerConfigurer freeMarkerConfigurer;

    public NightJobService(LeaveRepository leaveRepository, LeaveCountersRepository leaveCountersRepository, CompanyHolidayRepository companyHolidayRepository, CompanyRepository companyRepository, FreeMarkerConfigurer freeMarkerConfigurer) {
        this.leaveRepository = leaveRepository;
        this.leaveCountersRepository = leaveCountersRepository;
        this.companyHolidayRepository = companyHolidayRepository;
        this.companyRepository = companyRepository;
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    /**
     * Effectue le traitement :
     * <ol>
     *     <li>Valide/Rejette les demandes de congés et mets à jour les compteurs</li>
     *     <li>Valide les RTT employeurs</li>
     *     <li>Envoie les emails aux managers</li>
     * </ol>
     *
     * @return une liste des demandes d'absences et RTT employeurs traitées
     */
    public Map<String, List<? extends BaseEntity>> run() {
        List<Leave> leaves = fetchLeaves();

        Set<User> managersToEmail = new HashSet<>();

        for (Leave leave : leaves) {
            Integer effectiveDuration = leave.getDuration() - getCompanyHolidaysIn(leave);

            LeaveCounters leaveCounters = leaveCountersRepository.findByUser(leave.getUser()).get();

            if (leave.getType().equals(LeaveType.UNPAID_LEAVE))
                leave.setStatus(LeaveStatus.PENDING_VALIDATION);

            if (leave.getType().equals(LeaveType.PAID_LEAVE)) {
                leave.setStatus(getStatus(effectiveDuration, leaveCounters.getRemainingPaidLeaves()));
                if (leave.getStatus().equals(LeaveStatus.PENDING_VALIDATION))
                    leaveCounters.decreaseRemainingPaidLeaves(effectiveDuration);
            }

            if (leave.getType().equals(LeaveType.RTT)) {
                leave.setStatus(getStatus(effectiveDuration, leaveCounters.getRemainingRtt()));
                if (leave.getStatus().equals(LeaveStatus.PENDING_VALIDATION))
                    leaveCounters.decreaseRemainingRtt(effectiveDuration);
            }

            if (leave.getStatus().equals(LeaveStatus.PENDING_VALIDATION))
                managersToEmail.add(leave.getUser().getManager());

            leaveRepository.save(leave);
            leaveCountersRepository.save(leaveCounters);
        }


        List<CompanyHoliday> companyHolidays = fetchCompanyHolidays();
        validateCompanyHolidays(companyHolidays);

        for (User manager : managersToEmail) {
            try {
                sendEmail(manager);
            } catch (MessagingException | IOException | TemplateException e) {
                e.printStackTrace();
            }
        }

        return Map.of(
                "processedLeaves", leaves,
                "processedCompanyHolidays", companyHolidays
        );
    }

    /**
     * Valide les nouvelles RTT employeurs et met à jour les compteurs.
     *
     * @param companyHolidays liste des RTT employeurs au statut initial
     * @see #fetchCompanyHolidays()
     */
    private void validateCompanyHolidays(List<CompanyHoliday> companyHolidays) {
        for (CompanyHoliday companyHoliday : companyHolidays) {
            companyHoliday.setStatus(LeaveStatus.VALIDATED);
            Company company = companyHoliday.getCompany();
            company.decreaseRemainingRtt(1);
            company.increaseRttTaken(1);

            companyHolidayRepository.save(companyHoliday);
            companyRepository.save(company);
        }
    }

    /**
     * Envoie un email à un manager l'informant de nouvelles demandes à valider.
     *
     * @param user le manager
     */
    private void sendEmail(User user) throws MessagingException, IOException, TemplateException {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(user.getEmail());

        Template template = freeMarkerConfigurer.getConfiguration().getTemplate("email.html");
        StringWriter stringWriter = new StringWriter();
        template.process(user, stringWriter);
        helper.setText(stringWriter.toString(), true);

        sender.send(message);
    }

    /**
     * Récupère en base les demandes d'absences au statut initial.
     *
     * @return la liste des demandes d'absences (peut être vide)
     */
    private List<Leave> fetchLeaves() {
        return leaveRepository.findByStatusOrderByStartDateAsc(LeaveStatus.INITIAL);
    }

    /**
     * Récupère en base les RTT employeurs au statut initial.
     *
     * @return la liste des RTT employeurs (peut être vide)
     */
    private List<CompanyHoliday> fetchCompanyHolidays() {
        return companyHolidayRepository.findByTypeAndStatus(CompanyHolidayType.COMPANY_RTT, LeaveStatus.INITIAL);
    }

    /**
     * Donne le nombre de jours fériés ou RTT employeurs présents
     * pendant la durée d'une absence.
     *
     * @param leave la demande d'absence
     * @return le nombre de jours
     */
    private Integer getCompanyHolidaysIn(Leave leave) {
        return companyHolidayRepository.findCountBetweenDates(leave.getStartDate(), leave.getEndDate());
    }

    /**
     * Vérifie si un compteur a assez de jours restants par rapport à une durée d'absence.
     *
     * @param leaveDuration la durée d'une demande d'absence
     * @param counter un compteur, typiquement un attribut de {@link LeaveCounters}
     * @return le status à attribuer à la demande d'absence
     */
    private LeaveStatus getStatus(Integer leaveDuration, Integer counter) {
        return counter - leaveDuration >= 0 ? LeaveStatus.PENDING_VALIDATION : LeaveStatus.REJECTED;
    }
}
