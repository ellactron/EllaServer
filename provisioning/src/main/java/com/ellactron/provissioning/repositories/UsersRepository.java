package com.ellactron.provissioning.repositories;

import com.ellactron.provissioning.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by ji.wang on 2017-07-02.
 */
@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    @Autowired
    @PersistenceContext
    EntityManager em = null;
    //val query = em.createQuery("UPDATE Country SET population = population * 11 / 10 " + "WHERE c.population < :p")

    @Query("select NEW User(u.id, u.username, u.phoneNumber, u.email, u.registerDate, u.lastActiviteDate) from User u where u.username=:username and u.password=:password")
    public List<User> findByCredential(@Param("username")String username, @Param("password")String password);

    @Query("from User u where u.username=:username")
    public List<User> findAccountByUsername(@Param("username")String username);
}
