package com.challenge.demo.dto;

import com.challenge.demo.persistence.WorkHour;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;


public class WorkHourDTO {
    private Integer id;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate date;

    private double hours;

    public WorkHourDTO() {
    }

    public WorkHourDTO(Integer id, LocalDate date, double hours) {
        this.id = id;
        this.date = date;
        this.hours = hours;
    }

    public WorkHourDTO(WorkHour workHour) {
        this(workHour.getUser().getId(),
                workHour.getDate(),
                workHour.getHours());
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    @Override
    public String toString() {
        return "WorkHourDTO{" +
                "id=" + id +
                ", date=" + date +
                ", hours=" + hours +
                '}';
    }
}
