package br.com.tarefas.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.tarefas.controller.Request.UsuarioRequest;
import br.com.tarefas.controller.Response.UsuarioResponse;
import br.com.tarefas.controller.assembler.UsuarioAssembler;
import br.com.tarefas.model.Usuario;
import br.com.tarefas.services.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	@Autowired
	UsuarioService service;
	
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	UsuarioAssembler assembler;
	
	@GetMapping("/{id}")
	public EntityModel<UsuarioResponse> getUmUsuario(@PathVariable Integer id) {
		Usuario usuario = service.buscaUsuarioPorId(id);
		return assembler.toModel(usuario);
	}
	
	@PostMapping
	public ResponseEntity<EntityModel<UsuarioResponse>> salvarUsuario(@Valid @RequestBody UsuarioRequest userReq) {
		Usuario user = mapper.map(userReq, Usuario.class);
		Usuario usuarioSalvo = service.salvarUsuario(user);
		EntityModel<UsuarioResponse> usuarioModel = assembler.toModel(usuarioSalvo);
		
		return ResponseEntity
				.created(usuarioModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(usuarioModel);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<UsuarioResponse>> atualizarUsuario(
			@PathVariable Integer id, @Valid @RequestBody UsuarioRequest usuarioReq) {
		Usuario usuario = mapper.map(usuarioReq, Usuario.class);
		Usuario usuarioSalvo = service.atualizar(id, usuario);
		EntityModel<UsuarioResponse> usuarioModel = assembler.toModel(usuarioSalvo);
		return ResponseEntity.ok().body(usuarioModel);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluirUsuario(@PathVariable Integer id) {
		service.deletaUsuarioPorId(id);
	}
}
