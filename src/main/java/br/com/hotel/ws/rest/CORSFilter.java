package br.com.hotel.ws.rest;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

public class CORSFilter implements ContainerResponseFilter {

	@Override
	public ContainerResponse filter(ContainerRequest request, ContainerResponse response) {
		response.getHttpHeaders().putSingle("Access-Control-Allow-Origin", "*");
		response.getHttpHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST");
		response.getHttpHeaders().putSingle("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, X-Codingpedia");
		return response;
	}

}
