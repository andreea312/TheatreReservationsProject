package org.example.domain;

import javax.persistence.*;
import java.util.Set;

@javax.persistence.Entity
@Table(name = "Theatre")
public class Theatre extends Entity<Integer>{
    private String name;
    private String address;
    private String validationCode;
    Theatre(){}

    public Theatre(String name, String address, String validationCode) {
        super();
        this.name = name;
        this.address = address;
        this.validationCode = validationCode;
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

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "validationCode")
    public String getValidationCode() {
        return validationCode;
    }

    public void setValidationCode(String validationCode) {
        this.validationCode = validationCode;
    }

    @Override
    public String toString() {
        return "Theatre{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", validationCode='" + validationCode + '\'' +
                '}';
    }
}
