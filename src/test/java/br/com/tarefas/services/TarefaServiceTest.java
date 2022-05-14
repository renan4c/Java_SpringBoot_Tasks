package br.com.tarefas.services;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.tarefas.model.Tarefa;
import br.com.tarefas.model.TarefaStatus;
import br.com.tarefas.repository.TarefaRepository;

@ExtendWith(MockitoExtension.class)
public class TarefaServiceTest {
	
	@Mock
	private TarefaRepository repositorio;
	
	@InjectMocks
	private TarefaService service;
	
	@Test
	void naoDeveConcluirTarefaCancelada() {
		Integer idExemplo = 1;
		Tarefa t = new Tarefa();
		t.setId(idExemplo);
		t.setDescricao("Teste status concluida");
		t.setDataEntrega(LocalDate.now());
		t.setStatus(TarefaStatus.CANCELADA);
		
		Mockito.when(repositorio.findById(idExemplo)).thenReturn(Optional.of(t));
		
		Assertions.assertThrows(IllegalStateException.class, () -> service.concluirTarefaPorId(idExemplo));
	}
	
	@Test
	void naoDeveCancelarTarefaConcluida() {
		Integer idExemplo = 10;
		Tarefa t = new Tarefa();
		t.setId(idExemplo);
		t.setDescricao("Teste tarefa a cancelar");
		t.setStatus(TarefaStatus.CONCLUIDA);
		
		Mockito.when(repositorio.findById(idExemplo)).thenReturn(Optional.of(t));
		
		Assertions.assertThrows(IllegalStateException.class, () -> service.cancelarTarefaPorId(idExemplo));
	}
	
}
