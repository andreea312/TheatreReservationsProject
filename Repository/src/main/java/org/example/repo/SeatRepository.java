package org.example.repo;

import org.example.domain.Seat;
import org.example.domain.Show;

public interface SeatRepository extends Repository<Integer, Seat>{
    Seat getByLodgeRowNumber(int lodge, int row, int number);
}
