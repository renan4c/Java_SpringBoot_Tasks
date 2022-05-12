package br.com.tarefas.services;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.tarefas.model.Role;
import br.com.tarefas.model.Usuario;
import br.com.tarefas.repository.RoleRepository;
import br.com.tarefas.repository.UsuarioRepository;

@Service
public class UsuarioService {
	@Autowired
	private UsuarioRepository repositorio;
	
	@Autowired
	private RoleRepository roleRepositorio;
	
	@Autowired
	private PasswordEncoder encode;
	
	public Usuario buscaUsuarioPorId(Integer id) {
		Usuario user = repositorio.findById(id).orElseThrow(() -> new EntityNotFoundException());
		return user;
	}

	public Usuario salvarUsuario(Usuario user) {
		Set<Role> roles = getRoles(user);
		user.setRoles(roles);
		user.setSenha(encode.encode(user.getSenha()));
		return repositorio.save(user);
	}
	
	private Set<Role> getRoles(Usuario usuario) {
		Set<Role> rolesBanco = usuario.getRoles().stream().map(role -> 
		roleRepositorio.findByNome(role.getNome())
		).collect(Collectors.toSet());
		return rolesBanco;
	}
	
	public Usuario atualizar(Integer id, Usuario user) {
		if(!repositorio.existsById(id)) 
			throw new EntityNotFoundException();
		user.setId(id);
		return salvarUsuario(user);
	}
	
	public void deletaUsuarioPorId(Integer id) {
		repositorio.deleteById(id);
	}

}
