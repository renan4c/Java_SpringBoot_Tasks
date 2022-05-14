package br.com.tarefas.services;

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
	
	private Integer id = 4;

	private Tarefa abrirTarefa() {
		Tarefa tarefa = service.abrirTarefaPorId(id);
		service.salvarTarefa(tarefa);
		return tarefa;
	}
	
	@Test
	void deveIniciarTarefa() {
		Tarefa t = service.getTarefaPorId(id);
		t.setStatus(TarefaStatus.ABERTO);
		service.salvarTarefa(t);
		t = service.iniciarTarefaPorId(id);
		Assertions.assertEquals(TarefaStatus.EM_ANDAMENTO, t.getStatus());
	}
	
	@Test
	void naoDeveIniciarTarefaDiferenteDeAberto() {
		Tarefa tarefa = abrirTarefa();
		tarefa.setStatus(TarefaStatus.CONCLUIDA);
		service.salvarTarefa(tarefa);
		Assertions.assertThrows(IllegalStateException.class, () -> service.iniciarTarefaPorId(id));
	}
	
	@Test
	void naoDeveCancelarTarefaConcluida() {
		Tarefa tarefa = abrirTarefa();
		tarefa.setStatus(TarefaStatus.CONCLUIDA);
		service.salvarTarefa(tarefa);
		
		Assertions.assertThrows(IllegalStateException.class, () -> service.cancelarTarefaPorId(id));
	}
	
	@Test
	void naoDeveConcluirTarefaCancelada() {
		Tarefa tarefa = abrirTarefa();
		tarefa.setStatus(TarefaStatus.CANCELADA);
		service.salvarTarefa(tarefa);
		
		Assertions.assertThrows(IllegalStateException.class, () -> service.concluirTarefaPorId(id));
	}
}
