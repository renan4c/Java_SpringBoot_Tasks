package br.com.tarefas.controller;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tarefas.controller.Response.UsuarioResponse;
import br.com.tarefas.model.Usuario;
import br.com.tarefas.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	@Autowired
	UsuarioRepository repositorio;
	
	@Autowired
	ModelMapper mapper;
	
	@GetMapping("/{id}")
	public UsuarioResponse getUmUsuario(@PathVariable Integer id) {
		Usuario user = repositorio.findById(id).orElseThrow(() -> new EntityNotFoundException());
		return mapper.map(user, UsuarioResponse.class);
	}
}
