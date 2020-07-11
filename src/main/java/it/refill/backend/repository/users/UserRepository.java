package it.refill.backend.repository.users;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.refill.backend.models.users.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}