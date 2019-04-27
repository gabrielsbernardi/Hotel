package br.com.hotel.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="checkin")
public class CheckIn {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "data_entrada", nullable = false)
	private Date dataEntrada;
	
	@Column(name = "data_saida", nullable = false)
	private Date dataSaida;
	
	@Column(name = "adicional_veiculo", nullable = true)
	private Boolean adicionalVeiculo;
	
	@ManyToOne
	@JoinColumn(name = "hospede_id", referencedColumnName = "id")
	private Hospede hospede;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public Date getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(Date dataSaida) {
		this.dataSaida = dataSaida;
	}

	public Boolean getAdicionalVeiculo() {
		return adicionalVeiculo;
	}

	public void setAdicionalVeiculo(Boolean adicionalVeiculo) {
		this.adicionalVeiculo = adicionalVeiculo;
	}

	public Hospede getHospede() {
		return hospede;
	}

	public void setHospede(Hospede hospede) {
		this.hospede = hospede;
	}
	
}
