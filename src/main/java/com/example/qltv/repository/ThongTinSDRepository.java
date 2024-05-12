package com.example.qltv.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.qltv.entity.Member;
import com.example.qltv.entity.Thongtinsd;

@Repository
public interface ThongTinSDRepository extends JpaRepository<Thongtinsd, Integer> {

    List<Thongtinsd> findByThanhVien(Member thanhVien);
}
