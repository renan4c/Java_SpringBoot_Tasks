package br.com.tarefas.controller;

public class ErrorResponse {
	private String campo;
	private String mensagem;
	
	public ErrorResponse(String mensagem) {
		super();
		this.mensagem = mensagem;
	}
	public ErrorResponse(String campo, String mensagem) {
		super();
		this.campo = campo;
		this.mensagem = mensagem;
	}
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
}
