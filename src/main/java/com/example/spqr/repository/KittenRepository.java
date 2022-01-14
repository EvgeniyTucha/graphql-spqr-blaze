package com.example.spqr.repository;

import com.example.spqr.model.Kitten;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KittenRepository extends JpaRepository<Kitten, Long> {
}
