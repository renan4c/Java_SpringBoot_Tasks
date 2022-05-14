package br.com.tarefas.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.tarefas.controller.Response.JwtResponse;
import br.com.tarefas.model.Role;
import br.com.tarefas.model.Usuario;
import br.com.tarefas.repository.RoleRepository;
import br.com.tarefas.repository.UsuarioRepository;
import br.com.tarefas.security.JwtUtils;
import br.com.tarefas.security.UserDetailsImpl;

@Service
public class UsuarioService {
	@Autowired
	private UsuarioRepository repositorio;
	
	@Autowired
	private RoleRepository roleRepositorio;
	
	@Autowired
	private PasswordEncoder encode;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtils jwtUtils;
	
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

	public JwtResponse autenticaUsuario(String nome, String senha) {
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(nome, senha));
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		String jwt = jwtUtils.generateJwtToken(authenticate);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authenticate.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles);
	}

}
