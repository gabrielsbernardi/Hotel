package br.com.hotel.ws.rest.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.hotel.ws.rest.request.HospedeRequest;
import br.com.hotel.ws.rest.response.HospedeResponse;

@Path("/hospedeService")
public class HospedeService {
	
	@POST
	@Path("/getHospedes")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public List<HospedeResponse> getHospedes(HospedeRequest request) throws Exception {
		
		System.out.println("getHospedes");
		
		List<HospedeResponse> list = new ArrayList<HospedeResponse>();
		HospedeResponse h = new HospedeResponse();
		h.setNome("Gabriel");
		list.add(h);
		h = new HospedeResponse();
		h.setNome("Antonio");
		list.add(h);
		h = new HospedeResponse();
		h.setNome("Kerolyn");
		list.add(h);
		
		return list;
	}
}
