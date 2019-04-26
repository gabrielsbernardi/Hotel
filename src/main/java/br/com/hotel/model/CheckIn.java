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
	
	@Column(name = "data_saida", nullable = false)
	private Boolean adicionalVeiculo;
	
	
	@JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
	private Hospede hospede;
}
