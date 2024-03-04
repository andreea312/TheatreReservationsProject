package org.example.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@javax.persistence.Entity
@Table(name = "Show")
public class Show extends Entity<Integer>{
    private String name;
    private LocalDate data;
    private LocalTime timeStart;

    private LocalTime timeEnd;
    private String description;

    public Show(){}

    public Show(String name, LocalDate data, LocalTime timeStart, LocalTime timeEnd, String description) {
        super();
        this.name = name;
        this.data = data;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.description = description;
    }

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    public Integer getId(){ return super.getId();}

    @Override
    public void setId(Integer id){ super.setId(id);}

    @Column(name = "name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "data")
    public LocalDate getData() {
        return data;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }

    @Column(name = "timeStart")
    public LocalTime getTimeStart() {
        return timeStart;
    }
    public void setTimeStart(LocalTime time) {
        this.timeStart = time;
    }

    @Column(name = "timeEnd")
    public LocalTime getTimeEnd() {
        return timeEnd;
    }
    public void setTimeEnd(LocalTime time) {
        this.timeEnd = time;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Show{" +
                "name='" + name + '\'' +
                ", data=" + data +
                ", timeStart=" + timeStart +
                ", timeEnd=" + timeEnd +
                ", description='" + description + '\'' +
                '}';
    }
}
