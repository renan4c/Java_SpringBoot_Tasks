package br.com.tarefas.services;

import java.security.InvalidParameterException;
import java.util.List;

import javax.persistence.EntityNotFoundException;

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
	
	public TarefaCategoria getCategoria(Integer id) {
		if(id < 1)
			throw new EntityNotFoundException();
		return repositorio.findById(id).orElse(null);
	}
	
	public TarefaCategoria salvaTarefaCategoria(TarefaCategoria tarefaCategoria) {
		if(tarefaCategoria == null)
			return null;
		return repositorio.save(tarefaCategoria);
	}

	public void deletaTarefaCategoria(Integer id) {
		if(id < 1)
			throw new EntityNotFoundException();
		repositorio.deleteById(id);
	}
	
}
