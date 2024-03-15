package com.example.transportation.transportation.repositories;

import com.example.transportation.transportation.models.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
