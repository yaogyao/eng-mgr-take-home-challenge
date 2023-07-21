package com.challenge.demo;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "worked_hours")
public class WorkHour {
    @EmbeddedId
    private WorkHourId workHourId;

    @Column(name = "hours")
    private double hours;

    @Column(name = "created_at")
    private LocalDate createTime;

    public WorkHourId getWorkHourId() {
        return workHourId;
    }

    public void setWorkHourId(WorkHourId workHourId) {
        this.workHourId = workHourId;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkHour workHour = (WorkHour) o;
        return Objects.equals(getWorkHourId(), workHour.getWorkHourId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWorkHourId());
    }
}
