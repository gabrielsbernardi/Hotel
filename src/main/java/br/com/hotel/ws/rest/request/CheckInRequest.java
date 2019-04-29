package br.com.hotel.ws.rest.request;

import java.util.Date;

public class CheckInRequest {
	
	private Long id;
	private Date dataEntrada;
	private Date dataSaida;
	private Boolean adicionalVeiculo;
	private String hospedeDoc;
	private Boolean somenteAbertoFilter;

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

	public String getHospedeDoc() {
		return hospedeDoc;
	}

	public void setHospedeDoc(String hospedeDoc) {
		this.hospedeDoc = hospedeDoc;
	}

	public Boolean getSomenteAbertoFilter() {
		return somenteAbertoFilter;
	}

	public void setSomenteAbertoFilter(Boolean somenteAbertoFilter) {
		this.somenteAbertoFilter = somenteAbertoFilter;
	}
	
}
