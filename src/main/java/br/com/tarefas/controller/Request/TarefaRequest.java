package br.com.tarefas.controller.Request;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TarefaRequest {
	private Integer id;
	
	@NotBlank(message = "{tarefa.descricao.not-blank}")
	@Size(min = 5, max = 150, message = "{tarefa.descricao.size}")
	@Column(name = "ds_tarefa", nullable = false, length = 150)
	private String descricao;
	@FutureOrPresent(message = "{tarefa.descricao.future-or-present}")
	private LocalDate dataEntrega;
	private Integer categoriaId;
	private Integer usuarioId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public LocalDate getDataEntrega() {
		return dataEntrega;
	}
	public void setDataEntrega(LocalDate dataEntrega) {
		this.dataEntrega = dataEntrega;
	}
	public Integer getCategoriaId() {
		return categoriaId;
	}
	public void setCategoriaId(Integer categoriaId) {
		this.categoriaId = categoriaId;
	}
	public Integer getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}
	
	
}
