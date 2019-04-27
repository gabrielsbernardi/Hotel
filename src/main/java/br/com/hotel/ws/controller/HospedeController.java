package br.com.hotel.ws.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Predicate;

import javax.persistence.*;

import org.hibernate.exception.ConstraintViolationException;

import br.com.hotel.data.ConnectionDB;
import br.com.hotel.model.CheckIn;
import br.com.hotel.model.Hospede;
import br.com.hotel.utils.Utils;
import br.com.hotel.ws.rest.request.HospedeFilterRequest;
import br.com.hotel.ws.rest.request.HospedeRequest;
import br.com.hotel.ws.rest.response.HospedeResponse;

public class HospedeController {
	
	@SuppressWarnings("unused")
	private EntityManager manager;

	public HospedeController() {
		this.manager = ConnectionDB.getEntityManager();
	}
	
	/**
	 * 
	 * Inserção e atualização de Hóspede
	 * 
	 * @param request
	 * @return
	 */
	public HospedeResponse insertUpdateHospede(HospedeRequest request) {
		HospedeResponse response = new HospedeResponse();
		try {
			Hospede hospede = new Hospede();
			hospede.setId(request.getId());
			hospede.setNome(request.getNome());
			hospede.setDocumento(request.getDocumento());
			hospede.setTelefone(request.getTelefone());
			
			this.manager.getTransaction().begin();
			this.manager.merge(hospede);
			this.manager.getTransaction().commit();
			
			response.setIsSucess(Boolean.TRUE);
			response.setMessage(request.getId() == null ? "Hóspede incluido com sucesso!" : "Hóspede atualizado com sucesso!");
		} catch (ConstraintViolationException e) {
			response.setIsSucess(Boolean.FALSE);
			response.setMessage("Hóspede já cadasttrado!");
		} catch (Exception e) {
			response.setIsSucess(Boolean.FALSE);
			response.setMessage("Erro: " + e.getMessage());
		} 
		return response;
	}
	
	/**
	 * 
	 * Exclusão de Hóspede
	 * 
	 * @param id
	 * @return
	 */
	public HospedeResponse deleteHospede(Long id) {
		HospedeResponse response = new HospedeResponse();
		try {
			Hospede hospede = this.manager.find(Hospede.class, id);
			
			if (hospede != null) {
				if (!hospede.getCheckins().isEmpty()) {
					
					Boolean possuiCheckinAberto = hospede.getCheckins().stream().filter(new Predicate<CheckIn>() {
						public boolean test(CheckIn c) {
							return c.getDataSaida() == null;
						}
					}).findAny().isPresent();
					
					if (possuiCheckinAberto) {
						response.setIsSucess(Boolean.FALSE);
						response.setMessage("Hóspede não pode ser excluído pois possui CheckIn aberto!");
						return response;
					}
					
					for (CheckIn c : hospede.getCheckins()) {
						this.manager.getTransaction().begin();
						this.manager.remove(c);
						this.manager.getTransaction().commit();
					}
				}
				
				this.manager.getTransaction().begin();
				this.manager.remove(hospede);
				this.manager.getTransaction().commit();
				
				response.setIsSucess(Boolean.TRUE);
				response.setMessage("Hóspede excluído com sucesso!");
			} else {
				response.setIsSucess(Boolean.FALSE);
				response.setMessage("Hóspede inexistente!");
			}
		} catch (Exception e) {
			response.setIsSucess(Boolean.FALSE);
			response.setMessage(e.getMessage());
		}
		return response;
	}
	
	/**
	 * 
	 * Consulta de Hóspedes
	 * 
	 * @param request
	 * @return
	 */
	public List<HospedeResponse> getHospedes(HospedeFilterRequest request) {
		
		StringJoiner sql = new StringJoiner("\n");
		sql.add(" SELECT * FROM \"hospede\" h ");
		
		if (!Utils.stringIsNull(request.getNomeDocTel())) {
			sql.add(" WHERE (UPPER(h.nome) LIKE UPPER('%:pNomeDocTel%') ");
			sql.add(" 		 OR h.documento LIKE '%:pNomeDocTel%' 		");
			sql.add(" 		 OR h.telefone LIKE '%:pNomeDocTel%') 		");
		}
		
		sql.add(" ORDER BY h.nome 	  ");
		
		Query query = this.manager.createNativeQuery(sql.toString());
		
		if (!Utils.stringIsNull(request.getNomeDocTel()))
			query.setParameter("pNomeDocTel", request.getNomeDocTel());
		
		@SuppressWarnings("unchecked")
		List<Object[]> results = query.getResultList();
		
		HospedeResponse h = null;
		List<HospedeResponse> list = new ArrayList<HospedeResponse>();
		
		for (Object[] o : results) {
			h = new HospedeResponse();
			h.setNome((String) o[0]);
			h.setDocumento((String) o[1]);
			h.setTelefone((String) o[2]);
			
			list.add(h);
		}
		
		return list;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
