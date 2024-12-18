package ru.ifmo.se.repository.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.ifmo.se.entity.data.Coordinates;

@Repository
public interface CoordinatesRepository extends CrudRepository<Coordinates, Long>, PagingAndSortingRepository<Coordinates, Long> {

    Coordinates findById(long id);

}