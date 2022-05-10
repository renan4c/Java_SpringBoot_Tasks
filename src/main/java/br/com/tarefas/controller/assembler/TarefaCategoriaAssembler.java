package br.com.tarefas.controller.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import br.com.tarefas.controller.TarefaCategoriaController;
import br.com.tarefas.model.TarefaCategoria;

@Component
public class TarefaCategoriaAssembler
		implements RepresentationModelAssembler<TarefaCategoria, EntityModel<TarefaCategoria>> {

	@Autowired
	private ModelMapper mapper;
	
	@Override
	public EntityModel<TarefaCategoria> toModel(TarefaCategoria tarefaCategoria) {
		EntityModel<TarefaCategoria> tarefaCategoriaModel = EntityModel.of(tarefaCategoria, 
				linkTo(methodOn(TarefaCategoriaController.class).getCategoria(tarefaCategoria.getId())).withSelfRel());
		return tarefaCategoriaModel;
	}

	
}
