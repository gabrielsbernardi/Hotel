package br.com.hotel.ws.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Predicate;

import javax.persistence.*;

import br.com.hotel.data.ConnectionDB;
import br.com.hotel.model.CheckIn;
import br.com.hotel.model.Hospede;
import br.com.hotel.utils.Utils;
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
	 * @throws Exception 
	 */
	public HospedeResponse insertUpdateHospede(HospedeRequest request) throws Exception {
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
		} catch (Exception e) {
			if (e.getMessage().contains("ConstraintViolationException")) {
				response.setIsSucess(Boolean.FALSE);
				response.setMessage("Hóspede já cadasttrado!");
			} else {
				response.setIsSucess(Boolean.FALSE);
				response.setMessage("Erro: " + e.getMessage());
			}
			this.manager.getTransaction().rollback();
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
				this.manager.getTransaction().rollback();
			}
		} catch (Exception e) {
			response.setIsSucess(Boolean.FALSE);
			response.setMessage(e.getMessage());
			this.manager.getTransaction().rollback();
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
	public List<HospedeResponse> getHospedes(HospedeRequest request) {
		List<HospedeResponse> list = new ArrayList<HospedeResponse>();
		HospedeResponse h = null;
		
		try {
			StringJoiner sql = new StringJoiner("\n");
			sql
			.add(" SELECT h.id,           ")
			.add("        h.nome,    	  ")
			.add("        h.documento     ")
			.add(" FROM \"hospede\" h 	  ");
			
			if (!Utils.stringIsNull(request.getNomDocTelFilter())) {
				sql.add(" WHERE (UPPER(h.nome) LIKE UPPER('%" + request.getNomDocTelFilter() + "%')  ");
				sql.add(" 		 OR h.documento LIKE '%" + request.getNomDocTelFilter() + "%' 	    ");
				sql.add(" 		 OR h.telefone LIKE '%" + request.getNomDocTelFilter() + "%') 	    ");
			}
			
			sql.add(" ORDER BY h.nome 	  ");
			
			Query query = this.manager.createNativeQuery(sql.toString());
			
			@SuppressWarnings("unchecked")
			List<Object[]> results = query.getResultList();
			for (Object[] o : results) {
				h = new HospedeResponse();
				h.setId(((Integer) o[0]).longValue());
				h.setNome((String) o[1]);
				h.setDocumento((String) o[2]);
				
				list.add(h);
			}
			h.setIsSucess(Boolean.TRUE);
		} catch (Exception e) {
			list = new ArrayList<HospedeResponse>();
			h = new HospedeResponse();
			h.setIsSucess(Boolean.FALSE);
			h.setMessage(e.getMessage());
			list.add(h);
			this.manager.getTransaction().rollback();
		}
		
		return list;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
