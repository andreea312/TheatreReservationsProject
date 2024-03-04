package org.example.domain;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "Admin")

public class Admin extends Entity<Integer>{
    private String email;
    private String password;
    private Theatre theatre;

    public Admin(){}

    public Admin(String email, String password, Theatre theatre) {
        super();
        this.email = email;
        this.password = password;
        this.theatre = theatre;
    }

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    public Integer getId(){ return super.getId();}

    @Override
    public void setId(Integer id){ super.setId(id);}

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToOne
    @JoinColumn(name = "validationCode", referencedColumnName = "validationCode")
    public Theatre getTheatre() {
        return theatre;
    }

    public void setTheatre(Theatre theatre) {
        this.theatre = theatre;
    }

    @Override
    public String toString() {
        return "Office{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", theatre='" + theatre.toString() + '\'' +
                '}';
    }
}
