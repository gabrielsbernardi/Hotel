package br.com.hotel.ws.rest.response;

import java.util.List;

public class HospedeResponse extends DefaultResponse {
	
	private Long id;
	private String nome;
	private String documento;
	private String telefone;
	private List<CheckInResponse> checkins;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDocumento() {
		return documento;
	}
	
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public List<CheckInResponse> getCheckins() {
		return checkins;
	}
	
	public void setCheckins(List<CheckInResponse> checkins) {
		this.checkins = checkins;
	}
	
}
