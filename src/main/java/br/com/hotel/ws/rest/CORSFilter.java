package br.com.hotel.ws.rest;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

public class CORSFilter implements ContainerResponseFilter {

	@Override
	public ContainerResponse filter(ContainerRequest request, ContainerResponse responseContext) {
		
		responseContext.getHttpHeaders().add("Access-Control-Allow-Origin", "https://hotel-bernardi-front.herokuapp.com");
	    responseContext.getHttpHeaders().add("Access-Control-Allow-Credentials", "true");
	    responseContext.getHttpHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
	    responseContext.getHttpHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
		
//		response.getHttpHeaders().putSingle("Access-Control-Allow-Origin", "*");
//		response.getHttpHeaders().putSingle("Access-Control-Allow-Credentials", "true");
//		response.getHttpHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD");
//		response.getHttpHeaders().putSingle("Access-Control-Allow-Headers", "origin, content-type, accept, Access-Control-Allow-Origin");
		
		return responseContext;
	}

}
