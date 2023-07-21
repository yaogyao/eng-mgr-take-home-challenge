package com.challenge.demo.persistence;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class WorkHourId implements Serializable {
    private User user;
    private LocalDate date;

    public WorkHourId() {
    }

    public WorkHourId(User user, LocalDate date) {
        this.user = user;
        this.date = date;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkHourId that = (WorkHourId) o;
        return Objects.equals(user, that.user) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, date);
    }
}
