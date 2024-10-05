package it.preventivo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.preventivo.entity.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByEmail(String email);
}
