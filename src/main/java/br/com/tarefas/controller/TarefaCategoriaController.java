package br.com.tarefas.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tarefas.controller.assembler.TarefaCategoriaAssembler;
import br.com.tarefas.model.TarefaCategoria;
import br.com.tarefas.services.TarefaCategoriaService;

@RestController
@RequestMapping("/tarefaCategoria")
public class TarefaCategoriaController {
	
	@Autowired
	TarefaCategoriaService service;
	
	@Autowired
	private TarefaCategoriaAssembler assembler;
	
	//buscar todos
	@GetMapping
	public List<TarefaCategoria> getCategorias() {
		return service.getTodasTarefaCategorias();
	}
	
	//busca uma categoria por id
	@GetMapping("/{id}")
	public TarefaCategoria getCategoria(@PathVariable Integer id) {
		return service.getCategoria(id);
	}
	
	//salvar
	@PostMapping
	public ResponseEntity<EntityModel<TarefaCategoria>> saveTarefaCategoria(@Valid @RequestBody TarefaCategoria tarefaCat) {
		TarefaCategoria TarefaCategoriaSalva = service.salvaTarefaCategoria(tarefaCat);
		EntityModel<TarefaCategoria> model = assembler.toModel(TarefaCategoriaSalva);
		return ResponseEntity.created(model.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(model);
	}
	
	//deletar
	@DeleteMapping("/{id}")
	public void deletaTarefaCategoria(@PathVariable Integer id) {
		service.deletaTarefaCategoria(id);
	}
	
}
