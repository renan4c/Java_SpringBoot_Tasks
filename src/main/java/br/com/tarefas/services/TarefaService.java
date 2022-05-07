package br.com.tarefas.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tarefas.model.Tarefa;
import br.com.tarefas.repository.TarefaRepository;

@Service
public class TarefaService {
	@Autowired
	private TarefaRepository repositorio;

	public List<Tarefa> getTodasTarefas() {
		return repositorio.findAll();
	}
	
	public List<Tarefa> getTarefasPorDescricao(String descricao) {
		return repositorio.findByDescricao("%" + descricao + "%");
	}
	
	public Tarefa getTarefaPorId(Integer id) {
		return repositorio.findById(id).orElseThrow(() -> new EntityNotFoundException());
	}
	
	public Tarefa salvarTarefa(Tarefa tarefa) {
		return repositorio.save(tarefa);
	}
	
	public void deleteById(Integer id) {
		repositorio.deleteById(id);
	}
}
