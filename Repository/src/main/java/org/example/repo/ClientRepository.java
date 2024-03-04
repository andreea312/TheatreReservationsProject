package org.example.repo;

import org.example.domain.Admin;
import org.example.domain.Client;

public interface ClientRepository extends Repository<Integer, Client>{
    Client getClient(String firstName, String lastName, String phone, String mail);
}
