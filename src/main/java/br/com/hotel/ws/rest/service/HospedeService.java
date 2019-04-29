package br.com.hotel.ws.rest.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.hotel.ws.controller.HospedeController;
import br.com.hotel.ws.rest.request.HospedeRequest;
import br.com.hotel.ws.rest.response.HospedeResponse;

@Path("/hospedeService")
public class HospedeService {
	
	@POST
	@Path("/insertUpdateHospede")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public HospedeResponse insertUpdateHospede(HospedeRequest request) throws Exception {
		HospedeController hc = new HospedeController();
		return hc.insertUpdateHospede(request);
	}
	
	@POST
	@Path("/deleteHospede")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public HospedeResponse deleteHospede(HospedeRequest request) throws Exception {
		HospedeController hc = new HospedeController();
		return hc.deleteHospede(request.getId());
	}
	
	@POST
	@Path("/getHospedes")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public List<HospedeResponse> getHospedes(HospedeRequest request) throws Exception {
		HospedeController hc = new HospedeController();
		return hc.getHospedes(request);
	}
}
