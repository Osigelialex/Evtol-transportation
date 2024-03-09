package com.example.transportation.transportation.repositories;

import com.example.transportation.transportation.models.Evtol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EvtolRepository extends JpaRepository<Evtol, String> {

    @Query("select e from Evtol e where e.percentage > 25")
    List<Evtol> findAvailableEvtols();
}
