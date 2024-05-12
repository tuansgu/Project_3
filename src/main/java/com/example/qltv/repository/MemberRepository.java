package com.example.qltv.repository;

import com.example.qltv.entity.Member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
  @Query("SELECT m FROM Member m WHERE m.MaTV = :MaTV AND m.Password = :Password")
  Optional<Member> findOneByIdAndPassword(int MaTV, String Password);

  @Query("SELECT m FROM Member m WHERE m.Email = :Email")
  Member findByEmail(String Email);

  public Member findByResetPasswordToken(String token);
}
