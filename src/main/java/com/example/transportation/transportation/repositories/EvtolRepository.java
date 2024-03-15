package com.example.transportation.transportation.repositories;

import com.example.transportation.transportation.enums.EvtolState;
import com.example.transportation.transportation.models.Evtol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EvtolRepository extends JpaRepository<Evtol, String> {

    @Query("SELECT e FROM Evtol e WHERE e.percentage > 25")
    List<Evtol> findAvailableEvtols();

    @Query("SELECT e FROM Evtol e ORDER BY e.registeredAt DESC")
    List<Evtol> findAllByOrderByRegisteredAtDesc();

    Evtol findEvtolBySerialNumber(String serialNumber);

    @Query("SELECT e from Evtol e WHERE e.state = LOADED")
    List<Evtol> findLoadedEvtols();

}
