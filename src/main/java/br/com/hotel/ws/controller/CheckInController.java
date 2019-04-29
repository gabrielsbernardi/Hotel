package br.com.hotel.ws.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringJoiner;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.hotel.data.ConnectionDB;
import br.com.hotel.model.CheckIn;
import br.com.hotel.model.Hospede;
import br.com.hotel.utils.Utils;
import br.com.hotel.ws.rest.request.CheckInRequest;
import br.com.hotel.ws.rest.response.CheckInResponse;
import br.com.hotel.ws.rest.response.HospedeResponse;

public class CheckInController {
	
	@SuppressWarnings("unused")
	private EntityManager manager;

	public CheckInController() {
		manager = ConnectionDB.getEntityManager();
	}
	
	public List<CheckInResponse> getCheckIns(CheckInRequest request) throws Exception {
		StringJoiner sql = new StringJoiner("\n");
		sql
		.add(" SELECT h.nome,  					             	 ")
		.add("        h.documento,                           	 ")
		.add("        c.data_entrada,                        	 ")
		.add("        c.data_saida,                          	 ")
		.add("        c.adicional_veiculo                    	 ")
		.add(" FROM \"checkin\" c 					 	 	 	 ")
		.add(" INNER JOIN \"hospede\" h ON (h.id = c.hospede_id) ");
		
		if (request.getSomenteAbertoFilter()) {
			sql.add(" WHERE c.data_saida IS NULL ");
		} else {
			sql.add(" WHERE c.data_saida IS NOT NULL ");
		}
		
		sql.add(" ORDER BY c.data_entrada");
		
		Query query = this.manager.createNativeQuery(sql.toString());
		
		@SuppressWarnings("unchecked")
		List<Object[]> results = query.getResultList();
		
		CheckInResponse c = null;
		List<CheckInResponse> list = new ArrayList<CheckInResponse>();
		
		for (Object[] o : results) {
			c = new CheckInResponse();
			c.setNome((String) o[0]);
			c.setDocumento((String) o[1]);
			c.setDataEntrada((Date) o[2]);
			c.setDataSaida((Date) o[3]);
			c.setAdicionalVeiculo((Boolean) o[4]);
			c.setValorGasto(this.getValorTotalGasto(c.getDataEntrada(), c.getDataSaida(), c.getAdicionalVeiculo()));
			
			list.add(c);
		}
		
		return list;
	}

	public CheckInResponse insertUpdateCheckIn(CheckInRequest request) throws Exception {
		CheckInResponse response = new CheckInResponse();
		try {
			CheckIn checkIn = new CheckIn();
			checkIn.setId(request.getId());
			checkIn.setDataEntrada(request.getDataEntrada());
			checkIn.setDataSaida(request.getDataSaida());
			checkIn.setAdicionalVeiculo(request.getAdicionalVeiculo());
			
			Hospede h = this.manager.find(Hospede.class, this.geHospedeByDoc(request.getHospedeDoc()));
			if (h != null) {
				checkIn.setHospede(h);
				
				this.manager.getTransaction().begin();
				this.manager.merge(checkIn);
				this.manager.getTransaction().commit();
				
				response.setIsSucess(Boolean.TRUE);
				response.setMessage(request.getId() == null ? "CheckIn realizado com sucesso!" : "CheckIn atualizado com sucesso!");
			} else {
				response.setIsSucess(Boolean.FALSE);
				response.setMessage("Hópede inexistente!");
			}
		} catch (Exception e) {
			response.setIsSucess(Boolean.FALSE);
			response.setMessage("Erro: " + e.getMessage());
			this.manager.getTransaction().rollback();
		} 
		return response;
	}
	
	private Long geHospedeByDoc(String doc) {
		doc = doc.split(" - ")[1];
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT h.id FROM hospede h WHERE h.documento = '").append(doc).append("'");
		
		Query query = this.manager.createNativeQuery(sql.toString());
		
//		@SuppressWarnings("unchecked")
//		List<Integer> results = ;
//		for (Integer o : results) {
//			return (Integer) o[0];
//		}
		return ((Integer) query.getSingleResult()).longValue();
	}
	
	private Double getValorTotalGasto(Date dataEntrada, Date dataSaida, Boolean adicionalVeiculo) throws Exception {
		Date saida = new Date();
		if (dataSaida != null) {
			saida = dataSaida;
		}
		
		Integer qtdDias = qtdDias(dataEntrada, saida);
		Integer qtdFinaisDeSemana = this.calcularFinaisDeSemana(dataEntrada, qtdDias);
		Integer qtdDiasDeSemana = qtdDias - qtdFinaisDeSemana;
		
		Double qtdTotalApto = (qtdDiasDeSemana * 120.00) + (qtdFinaisDeSemana * 150.00);
		Double qtdToTalAdicionalVeiculo = 0.0;
		
		if (adicionalVeiculo) {
			qtdToTalAdicionalVeiculo = calcularTotalVeiculo(qtdDiasDeSemana, qtdFinaisDeSemana, saida);
		}
		
		return Utils.duasCasasDecimais(qtdTotalApto + qtdToTalAdicionalVeiculo);
	}
	
	private Integer qtdDias(Date dataEntrada, Date dataSaida) {
        long dt = (dataSaida.getTime() - dataEntrada.getTime()) + 3600000; // 1 hora para compensar horário de verão
		return (int) (dt/86400000L);
	}
	
	private Integer calcularFinaisDeSemana(Date dataEntrada, Integer qtdDias) {
		Integer qtdFinaisDeSemana = 0;
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(dataEntrada);
		for (int i = 0; i < qtdDias; i++) {
			if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
					|| calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
				qtdFinaisDeSemana++;
			calendar.add(Calendar.DATE, 1);
		}
		return qtdFinaisDeSemana;
	}
	
	private Double calcularTotalVeiculo(Integer qtdDiasDeSemana, Integer qtdFinaisDeSemana, Date dataSaida) throws Exception {
		Double qtdTotalVeiculo = (qtdDiasDeSemana * 15.00) + (qtdFinaisDeSemana * 20.00);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		
		Date horaMaximaSemTaxa = sdf.parse("16:30");
        GregorianCalendar gcMaximaSemTaxa = new GregorianCalendar();
        gcMaximaSemTaxa.setTime(horaMaximaSemTaxa);
        
        GregorianCalendar gcSaida = new GregorianCalendar();
        gcSaida.setTime(dataSaida);
		
        if (gcSaida.getTime().after(gcMaximaSemTaxa.getTime())){
        	Calendar calendar = Calendar.getInstance();
            calendar.setTime(dataSaida);
        	if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
					|| calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
        		qtdTotalVeiculo =+ 20.00;
        	} else {
        		qtdTotalVeiculo =+ 15.00;
        	}
        }
        
		return qtdTotalVeiculo;
	}
}
