package br.com.tarefas.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tarefas.model.TarefaCategoria;
import br.com.tarefas.services.TarefaCategoriaService;

@RestController
@RequestMapping("/tarefaCategoria")
public class TarefaCategoriaController {
	
	@Autowired
	TarefaCategoriaService service;
	
	//buscar todos
	@GetMapping
	public List<TarefaCategoria> getCategoriasController() {
		return service.getTodasTarefaCategorias();
	}
	
	//busca uma categoria por id
	@GetMapping("/{id}")
	public TarefaCategoria getCategoria(@PathVariable Integer id) {
		return service.getCategoria(id);
	}
	
	//salvar
	@PostMapping
	public TarefaCategoria saveTarefaCategoria(@Valid @RequestBody TarefaCategoria tarefaCat) {
		return service.salvaTarefaCategoria(tarefaCat);
	}
	
	//deletar
	@DeleteMapping("/{id}")
	public void deletaTarefaCategoria(@PathVariable Integer id) {
		service.deletaTarefaCategoria(id);
	}
	
}
