package br.com.hotel.ws.rest.response;

import java.util.Date;

public class CheckInResponse extends DefaultResponse {
	
	private String nome;
	private String documento;
	private Date dataEntrada;
	private Date dataSaida;
	private Boolean adicionalVeiculo;
	private Double valorGasto;
	
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

	public Double getValorGasto() {
		return valorGasto;
	}
	
	public void setValorGasto(Double valorGasto) {
		this.valorGasto = valorGasto;
	}
	
}
