package com.apososcreditos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.apososcreditos.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	@Query
	public Role findByName(String name);

}
