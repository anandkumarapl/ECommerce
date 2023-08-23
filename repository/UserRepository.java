package com.springbootjwt.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springbootjwt.model.User;

//creating repository interface extends JpaRepository 
//inherits a set of CRUD (Create, Read, Update, Delete) operations 
@Repository
public interface UserRepository extends JpaRepository<User,Long>
{
	Optional<User> findByEmailAddress (String emailAddress);
}
