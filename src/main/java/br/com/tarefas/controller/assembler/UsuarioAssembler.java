package br.com.tarefas.controller.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import br.com.tarefas.controller.UsuarioController;
import br.com.tarefas.controller.Response.UsuarioResponse;
import br.com.tarefas.model.Usuario;

@Component	
public class UsuarioAssembler implements RepresentationModelAssembler<Usuario, EntityModel<UsuarioResponse>>{

	@Autowired
	private ModelMapper mapper;
	
	@Override
	public EntityModel<UsuarioResponse> toModel(Usuario entity) {
		UsuarioResponse userResp = mapper.map(entity, UsuarioResponse.class);
		EntityModel<UsuarioResponse> usuarioModel = EntityModel.of(userResp, 
				linkTo(methodOn(UsuarioController.class).getUmUsuario(entity.getId())).withSelfRel());
		
		return usuarioModel;
	}

}
