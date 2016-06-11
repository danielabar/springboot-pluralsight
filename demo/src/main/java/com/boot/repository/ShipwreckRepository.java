package com.boot.repository;

import com.boot.model.Shipwreck;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by dbaron on 2016-06-10.
 */
public interface ShipwreckRepository extends JpaRepository<Shipwreck, Long> {

}
