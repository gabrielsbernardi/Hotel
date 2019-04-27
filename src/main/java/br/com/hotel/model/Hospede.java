package br.com.hotel.model;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="hospede")
public class Hospede {
	
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="nome", nullable = false, length = 64)
	private String nome;
	
	@Column(name="documento", nullable = false, length = 64)
	private String documento;
	
	@Column(name="telefone", nullable = false, length = 32)
	private String telefone;
	
	@OneToMany(mappedBy = "hospede", targetEntity = CheckIn.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<CheckIn> checkins;
	
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

	public List<CheckIn> getCheckins() {
		return checkins;
	}

	public void setCheckins(List<CheckIn> checkins) {
		this.checkins = checkins;
	}
	
}