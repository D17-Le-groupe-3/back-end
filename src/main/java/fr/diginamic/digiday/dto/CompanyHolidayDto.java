package fr.diginamic.digiday.dto;

/**
 * {@code CompanyHolidayDto} est utilisé lors de la communication avec le front.
 * Elle est la DTO dérivée de {@link fr.diginamic.digiday.entities.CompanyHoliday CompanyHoliday}.
 * @see fr.diginamic.digiday.controller.CompanyHolidayController#getByYear
 * @author PEV
 * @since 1.0
 */
public class CompanyHolidayDto {
    private Integer id;
    private String date;
    private String status;
    private String type;
    private String comment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
