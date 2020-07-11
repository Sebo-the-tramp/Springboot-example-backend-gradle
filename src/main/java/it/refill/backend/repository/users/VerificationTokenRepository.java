package it.refill.backend.repository.users;

import org.springframework.data.jpa.repository.JpaRepository;

import it.refill.backend.models.users.UserAuth;
import it.refill.backend.models.users.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(UserAuth user);
}