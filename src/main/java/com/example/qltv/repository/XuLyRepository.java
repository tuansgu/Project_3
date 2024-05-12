package com.example.qltv.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.qltv.entity.Xuly;
import com.example.qltv.entity.Member;

@Repository
public interface XuLyRepository extends JpaRepository<Xuly, Integer> {

    List<Xuly> findByThanhVien(Member tv);
}
