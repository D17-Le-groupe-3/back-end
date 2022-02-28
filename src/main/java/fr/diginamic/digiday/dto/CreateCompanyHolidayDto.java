package fr.diginamic.digiday.dto;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * DTO permetant l'échange d'informations avec le front dans le cadre de la
 * création d'un jour férié ou RTT employeur. Reprends l'ensemble des informations de
 * l'entité sauf le statut et l'id.
 * </p>
 * <p>Liste des attributs</p>
 * <ul>
 * <li>date : Date du jour férié ou RTT employeur</li>
 * <li>type : Type (PUBLIC_HOLIDAY, COMPANY_RTT)</li>
 * <li>comment : commentaire optionnel</li>
 * </ul>
 *
 * @author LPOU
 * @since 1.0
 */

public class CreateCompanyHolidayDto {
	
	@NotNull
	@Future
	private LocalDate date;

	@NotNull
	private String type;

	private String comment;

	public CreateCompanyHolidayDto() {
	}

	public CreateCompanyHolidayDto(LocalDate date, String type, String comment) {
		this.date = date;
		this.type = type;
		this.comment = comment;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
