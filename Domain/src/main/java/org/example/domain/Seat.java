package org.example.domain;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "Seat")
public class Seat extends Entity<Integer>{
    private int number;
    private int row;
    private int lodge;
    private float price;
    private Boolean available;

    public Seat(){}

    public Seat(int number, int row, int lodge, float price, Boolean available) {
        this.number = number;
        this.row = row;
        this.lodge = lodge;
        this.price = price;
        this.available = available;
    }

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    public Integer getId(){ return super.getId();}

    @Override
    public void setId(Integer id){ super.setId(id);}

    @Column(name = "number")
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Column(name = "row")
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @Column(name = "lodge")
    public int getLodge() {
        return lodge;
    }

    public void setLodge(int lodge) {
        this.lodge = lodge;
    }

    @Column(name = "price")
    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Column(name = "available")
    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "number=" + number +
                ", row=" + row +
                ", lodge=" + lodge +
                ", price=" + price +
                ", available=" + available +
                '}';
    }
}
