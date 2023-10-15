package com.momukgback.Repository;

import com.momukgback.Entity.User;
import com.momukgback.Entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserStatusRepository extends JpaRepository<UserStatus, Long> {
        Optional<UserStatus> findById(Long id);

}
