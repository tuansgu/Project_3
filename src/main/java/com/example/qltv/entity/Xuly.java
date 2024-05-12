package com.example.qltv.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "xuly")
public class Xuly implements Serializable {

    @Id
    @Column(name = "MaXL")
    private Integer maXL;

    @ManyToOne
    @JoinColumn(name = "MaTV")
    private Member thanhVien;

    @Column(name = "HinhThucXL")
    private String hinhThucXL;

    @Column(name = "SoTien")
    private Integer soTien;

    @CreationTimestamp
    @Column(name = "NgayXL")
    private LocalDateTime ngayXL;

    @Column(name = "TrangThaiXL")
    private Integer trangThaiXL;

    public Xuly() {
    }

    public Xuly(Integer maXL, Member thanhVien, String hinhThucXL, Integer soTien, LocalDateTime ngayXL, Integer trangThaiXL) {
        this.maXL = maXL;
        this.thanhVien = thanhVien;
        this.hinhThucXL = hinhThucXL;
        this.soTien = soTien;
        this.ngayXL = ngayXL;
        this.trangThaiXL = trangThaiXL;
    }

    public Integer getMaXL() {
        return maXL;
    }

    public void setMaXL(Integer maXL) {
        this.maXL = maXL;
    }

    // Getter và setter cho trường thanhVien (ManyToOne)
    public Member getThanhVien() {
        return thanhVien;
    }

    public void setThanhVien(Member thanhVien) {
        this.thanhVien = thanhVien;
    }

    // Getter và setter cho trường hinhThucXL
    public String getHinhThucXL() {
        return hinhThucXL;
    }

    public void setHinhThucXL(String hinhThucXL) {
        this.hinhThucXL = hinhThucXL;
    }

    // Getter và setter cho trường soTien
    public Integer getSoTien() {
        return soTien;
    }

    public void setSoTien(Integer soTien) {
        this.soTien = soTien;
    }

    // Getter và setter cho trường ngayXL
    public LocalDateTime getNgayXL() {
        return ngayXL;
    }

    public void setNgayXL(LocalDateTime ngayXL) {
        this.ngayXL = ngayXL;
    }

    public Integer getTrangThaiXL() {
        return trangThaiXL;
    }

    public void setTrangThaiXL(Integer trangThaiXL) {
        this.trangThaiXL = trangThaiXL;
    }

}
