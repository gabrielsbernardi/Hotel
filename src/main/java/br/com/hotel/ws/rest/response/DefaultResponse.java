package br.com.hotel.ws.rest.response;

public class DefaultResponse {
	
	private Boolean isSucess;
	private String message;
	
	public Boolean getIsSucess() {
		return isSucess;
	}
	
	public void setIsSucess(Boolean isSucess) {
		this.isSucess = isSucess;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
}
