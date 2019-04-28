package br.com.hotel.ws.rest.request;

public class HospedeRequest {
	
	private Long id;
	private String nome;
	private String documento;
	private String telefone;
	private String nomDocTelFilter;

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

	public String getNomDocTelFilter() {
		return nomDocTelFilter;
	}

	public void setNomDocTelFilter(String nomDocTelFilter) {
		this.nomDocTelFilter = nomDocTelFilter;
	}
	
}
