package br.com.tarefas.repository;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.tarefas.model.Tarefa;

@SpringBootTest
public class TarefaRepositoryTest {
	@Autowired
	private TarefaRepository respositorio;
	
	@Test
	void testeFindByNomeCategoria() {
		List<Tarefa> tarefas = respositorio.findByNomeCategoria("Estudos");
		Assertions.assertEquals(2, tarefas.size());
	}
	@Test
	void tarefasPorCategoria() {
		List<Tarefa> tarefas = respositorio.tarefasPorCategoria("Estudos");
		Assertions.assertEquals(2, tarefas.size());
	}
}
