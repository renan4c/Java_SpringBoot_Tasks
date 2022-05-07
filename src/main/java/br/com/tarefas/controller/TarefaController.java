package br.com.tarefas.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.tarefas.model.Tarefa;
import br.com.tarefas.repository.TarefaRepository;
import br.com.tarefas.services.TarefaService;

@RestController
public class TarefaController {

	@Autowired
	private TarefaService service;
	
	@GetMapping("/tarefa")
	public List<Tarefa> todasTarefas(@RequestParam Map<String, String> parametros) {
		if(parametros.isEmpty())
		return service.getTodasTarefas();
		
		String desc = parametros.get("descricao");
		return service.getTarefasPorDescricao(desc);
	}
	
	@GetMapping("/tarefa/{id}")
	public Tarefa getTarefa(@PathVariable Integer id) {
		return service.getTarefaPorId(id);
	}
	
	@PostMapping("/tarefa")
	public Tarefa salvarTarefa(@Valid @RequestBody Tarefa tarefa) {
		return service.salvarTarefa(tarefa);
	}
	
	@DeleteMapping("/tarefa/{id}")
	public void excluirTarefa(@PathVariable Integer id) {
		service.deleteById(id);
	}
}
