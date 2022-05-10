package br.com.tarefas.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tarefas.model.Tarefa;
import br.com.tarefas.model.TarefaStatus;
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
	
	public Tarefa abrirTarefaPorId(Integer id) {
		Tarefa tarefa = getTarefaPorId(id);
		tarefa.setStatus(TarefaStatus.ABERTO);
		repositorio.save(tarefa);
		return tarefa;
	}
	
	public Tarefa iniciarTarefaPorId(Integer id) {
		Tarefa tarefa = getTarefaPorId(id);
		if(!TarefaStatus.ABERTO.equals(tarefa.getStatus())) {
			throw new IllegalStateException("Não pode iniciar uma tarefa que não esteja com status ABERTO");
		}
				
		tarefa.setStatus(TarefaStatus.EM_ANDAMENTO);
		repositorio.save(tarefa);
		return tarefa;
	}

	public Tarefa concluirTarefaPorId(Integer id) {
		Tarefa tarefa = getTarefaPorId(id);
		
		if(TarefaStatus.CANCELADA.equals(tarefa.getStatus())) {
			throw new IllegalStateException("Não pode concluir uma tarefa que está com status CANCELADA");
		}
		
		tarefa.setStatus(TarefaStatus.CONCLUIDA);
		repositorio.save(tarefa);
		return tarefa;
	}
	
	public Tarefa cancelarTarefaPorId(Integer id) {
		Tarefa tarefa = getTarefaPorId(id);
		
		if(TarefaStatus.CONCLUIDA.equals(tarefa.getStatus())) {
			throw new IllegalStateException("Não pode CANCELAR uma tarefa que está com status concluída");
		}
		
		tarefa.setStatus(TarefaStatus.CANCELADA);
		repositorio.save(tarefa);
		return tarefa;
	}
}
