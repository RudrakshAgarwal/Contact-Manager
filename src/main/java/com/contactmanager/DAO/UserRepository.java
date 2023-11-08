package com.contactmanager.DAO;

import com.contactmanager.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.email = :email")
//  fetching dynamic email
//  @Param is used to bind the method parameter to Query parameter.
    public User getUserByUserName(@Param("email") String email);

}
