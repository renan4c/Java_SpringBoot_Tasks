package br.com.tarefas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tarefas.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
	
}
