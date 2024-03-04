package org.example.repo;

import org.example.domain.Admin;

public interface AdminRepository extends Repository<Integer, Admin> {
    Admin getByMailPassword(String mail, String password);
    Admin getByMail(String email);
}
