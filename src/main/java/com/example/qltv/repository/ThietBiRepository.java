package com.example.qltv.repository;

import com.example.qltv.entity.Thietbi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThietBiRepository extends JpaRepository<Thietbi,Integer>{
    
}
