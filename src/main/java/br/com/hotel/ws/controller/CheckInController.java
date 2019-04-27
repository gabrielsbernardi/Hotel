package br.com.hotel.ws.controller;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.hotel.data.ConnectionDB;
import br.com.hotel.model.CheckIn;
import br.com.hotel.model.Hospede;
import br.com.hotel.ws.rest.request.CheckInRequest;
import br.com.hotel.ws.rest.response.CheckInResponse;

public class CheckInController {
	
	@SuppressWarnings("unused")
	private EntityManager manager;

	public CheckInController() {
		manager = ConnectionDB.getEntityManager();
	}
	
	public List<CheckInResponse> getCheckIns(CheckInRequest request) throws Exception {
		return null;
	}

	public CheckInResponse insertUpdateCheckIn(CheckInRequest request) {
		CheckInResponse response = new CheckInResponse();
		try {
			CheckIn checkIn = new CheckIn();
			checkIn.setId(request.getId());
			checkIn.setDataEntrada(request.getDataEntrada());
			checkIn.setDataSaida(request.getDataSaida());
			checkIn.setAdicionalVeiculo(request.getAdicionalVeiculo());
			
			Hospede h = this.manager.find(Hospede.class, request.getHospede().getId());
			checkIn.setHospede(h);
			
			this.manager.getTransaction().begin();
			this.manager.merge(checkIn);
			this.manager.getTransaction().commit();
			
			response.setIsSucess(Boolean.TRUE);
			response.setMessage(request.getId() == null ? "CheckIn realizado com sucesso!" : "CheckIn atualizado com sucesso!");
		} catch (Exception e) {
			response.setIsSucess(Boolean.FALSE);
			response.setMessage("Erro: " + e.getMessage());
		} 
		return response;
	}
}
