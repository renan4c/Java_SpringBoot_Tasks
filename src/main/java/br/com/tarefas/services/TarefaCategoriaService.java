package br.com.tarefas.services;

import java.security.InvalidParameterException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tarefas.model.TarefaCategoria;
import br.com.tarefas.repository.TarefaCategoriaRepository;

@Service
public class TarefaCategoriaService {
	
	@Autowired
	TarefaCategoriaRepository repositorio;
	
	public List<TarefaCategoria> getTodasTarefaCategorias() {
		return repositorio.findAll();
	}
	
	public TarefaCategoria salvaTarefaCategoria(TarefaCategoria tarefaCategoria) {
		return repositorio.save(tarefaCategoria);
	}

	public void deletaTarefaCategoria(Integer id) {
		if(id != null && id > 0)
			repositorio.deleteById(id);
		else 
			throw new InvalidParameterException("O id da tarefa categoria deve ser um número maior que zero");
	}
}
