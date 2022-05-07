package br.com.tarefas.services;

import java.security.InvalidParameterException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.tarefas.model.Tarefa;
import br.com.tarefas.model.TarefaStatus;

@SpringBootTest
public class TarefaServiceIntegrationTest {
	@Autowired
	private TarefaService service;
	
	@Test
	void deveIniciarTarefa() {
		Tarefa t = service.getTarefaPorId(3);
		t.setStatus(TarefaStatus.ABERTO);
		service.salvarTarefa(t);
		t = service.iniciarTarefaPorId(3);
		Assertions.assertEquals(TarefaStatus.EM_ANDAMENTO, t.getStatus());
	}
	
	@Test
	void naoDeveIniciarTarefaDiferenteDeAberto() {
		Tarefa tarefa = service.getTarefaPorId(3);
		tarefa.setStatus(TarefaStatus.CONCLUIDA);
		service.salvarTarefa(tarefa);
		Assertions.assertThrows(InvalidParameterException.class, () -> service.iniciarTarefaPorId(3));
	}
	
	@Test
	void naoDeveCancelarTarefaConcluida() {
		Tarefa t = service.getTarefaPorId(3);
		t.setStatus(TarefaStatus.CONCLUIDA);
		service.salvarTarefa(t);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> service.cancelarTarefaPorId(3));
	}
	
	@Test
	void naoDeveConcluirTarefaCancelada() {
		Tarefa t = service.getTarefaPorId(3);
		t.setStatus(TarefaStatus.CANCELADA);
		service.salvarTarefa(t);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> service.concluirTarefaPorId(3));
	}
}
