package com.ellactron.provissioning.repositories;

import com.ellactron.provissioning.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ji.wang on 2017-07-02.
 */
@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    @Query("from User u where u.username=:username and u.password=password(:password)")
    public User findByCredential(@Param("username")String username, @Param("password")String password);

    @Query("from User u where u.username=:username")
    public List<User> findAccount(@Param("username")String username);
}
