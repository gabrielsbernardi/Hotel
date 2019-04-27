package br.com.hotel.ws.rest.response;

import java.util.Date;

public class CheckInResponse extends DefaultResponse {
	
	private Long id;
	private Date dataEntrada;
	private Date dataSaida;
	private Boolean adicionalVeiculo;
	private String hospede;
	
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
	
	public String getHospede() {
		return hospede;
	}

	public void setHospede(String hospede) {
		this.hospede = hospede;
	}
	
}
