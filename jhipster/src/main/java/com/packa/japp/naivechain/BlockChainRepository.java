package com.packa.japp.naivechain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.packa.japp.config.Constants;
import com.packa.japp.domain.HistoriaClinica;
import com.packa.japp.service.dto.HistoriaClinicaDTO;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.resource.Observation;
import ca.uhn.fhir.parser.IParser;
import net.minidev.json.JSONObject;

public class BlockChainRepository {

	private final Logger log = LoggerFactory.getLogger(BlockChainRepository.class);

	private final RestTemplate restTemplate = new RestTemplate();

	private final FhirContext fhirCtx = FhirContext.forDstu2();
	
	private HistoriaMapper mapper = new HistoriaMapper();

	public List<HistoriaClinicaDTO> findAll(List<HistoriaClinicaDTO> hcsParameter) {

		Block[] result = restTemplate.getForObject(Constants.URI_API + "/blocks", Block[].class);

		IParser parser = fhirCtx.newJsonParser();

		List<HistoriaClinicaDTO> hcsToReturn = new ArrayList<HistoriaClinicaDTO>();

		log.debug("Cantidad de elementos del bloque : {}", result.length);

		for (Block item : result) {

			Map itemMap = (Map) item.getData();

			Observation localObservation = parser.parseResource(Observation.class, JSONObject.toJSONString(itemMap));

			HistoriaClinicaDTO historiaClinicaDTO = new HistoriaClinicaDTO();

			log.debug("Id de elemento del bloque : {}", localObservation.getId().getIdPartAsLong());

			historiaClinicaDTO.setId(localObservation.getId().getIdPartAsLong());

			if (!hcsParameter.contains(historiaClinicaDTO)) {
				hcsToReturn.add(historiaClinicaDTO);
			}
		}
		return hcsToReturn;
	}

	public String save(HistoriaClinica historiaClinica) {
		Observation localObservation = new Observation();
		localObservation.setId(historiaClinica.getId().toString());
		localObservation.setComments(mapper.convertirHistoria(historiaClinica));

		String encodedDstu2 = fhirCtx.newJsonParser().setPrettyPrint(true).encodeResourceToString(localObservation);
		log.info("Historia encodeada: {}", encodedDstu2);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.setContentType(MediaType.APPLICATION_JSON);

		StringBuilder localSb = new StringBuilder();

		localSb.append("{\"data\" : ");
		localSb.append(encodedDstu2);
		localSb.append("}");

		HttpEntity<String> requestBody = new HttpEntity<>(localSb.toString(), headers);

		String localRestResponse = restTemplate.postForObject(Constants.URI_API + "/mineBlock", requestBody,
				String.class);
		return localRestResponse;
	}
}
