package com.challenge.demo.persistence;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "worked_hours")
@IdClass(WorkHourId.class)
public class WorkHour {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "hours", nullable = false)
    // TODO - add validation?
    private double hours;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createTime = LocalDateTime.now();

    public WorkHour() {
    }

    public WorkHour(User user, LocalDate date, double hours) {
        this.user = user;
        this.date = date;
        this.hours = hours;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkHour workHour = (WorkHour) o;
        return Objects.equals(getUser(), workHour.getUser()) && Objects.equals(getDate(), workHour.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getDate());
    }

    @Override
    public String toString() {
        return "WorkHour{" +
                "user=" + user +
                ", date=" + date +
                ", hours=" + hours +
                ", createTime=" + createTime +
                '}';
    }
}
