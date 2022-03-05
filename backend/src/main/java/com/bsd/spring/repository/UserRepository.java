package com.bsd.spring.repository;

import com.bsd.spring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmail(String email);

    List<User> findByEmailIgnoreCaseLike(String email);

}
