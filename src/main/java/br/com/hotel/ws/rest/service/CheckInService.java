package br.com.hotel.ws.rest.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.hotel.ws.controller.CheckInController;
import br.com.hotel.ws.rest.request.CheckInRequest;
import br.com.hotel.ws.rest.response.CheckInResponse;

@Path("/checkinService")
public class CheckInService {
	
	@POST
	@Path("/insertUpdateCheckIn")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public CheckInResponse insertUpdateCheckIn(CheckInRequest request) throws Exception {
		CheckInController cc = new CheckInController();
		return cc.insertUpdateCheckIn(request);
	}
	
	@POST
	@Path("/getCheckIns")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public List<CheckInResponse> getCheckIns(CheckInRequest request) throws Exception {
		CheckInController cc = new CheckInController();
		return cc.getCheckIns(request);
	}
}
