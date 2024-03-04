package org.example.repo;

import org.example.domain.Show;

import java.time.LocalDate;

public interface ShowRepository extends Repository<Integer, Show> {
    Show getByDate(LocalDate date);
}
