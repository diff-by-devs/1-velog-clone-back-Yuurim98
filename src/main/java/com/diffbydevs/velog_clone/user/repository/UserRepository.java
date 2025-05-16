package com.diffbydevs.velog_clone.user.repository;

import com.diffbydevs.velog_clone.user.repository.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUserId(String userId);

    Optional<User> findByEmail(String email);
}
