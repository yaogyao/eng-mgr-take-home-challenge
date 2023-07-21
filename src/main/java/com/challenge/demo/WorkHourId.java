package com.challenge.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

@Embeddable
public class WorkHourId implements Serializable {
    @ManyToOne @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "date")
    private Date date;

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
