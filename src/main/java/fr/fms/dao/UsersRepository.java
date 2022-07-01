package fr.fms.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.fms.entities.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {

}
