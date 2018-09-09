package com.packa.japp.web.rest;


import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.resource.Observation;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.dstu2.valueset.NameUseEnum;
import ca.uhn.fhir.parser.IParser;
import com.packa.japp.naivechain.Block;
import net.minidev.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RunWith(SpringRunner.class)
public class RestClientTest {

    private static final String URI_API = "http://192.168.0.45:3002";


    @Test
    public void getBlockChain() throws Exception {
        final String uri = "http://192.168.0.45:3002/blocks";

        RestTemplate restTemplate = new RestTemplate();

     /*
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
        jsonMessageConverter.setObjectMapper(objectMapper);
        messageConverters.add(jsonMessageConverter);
        restTemplate.setMessageConverters(messageConverters);*/


        //String result = restTemplate.getForObject(uri, String.class);

        //objectMapper.readValue(result,Block[].class);

        Block[] result = restTemplate.getForObject(uri, Block[].class);

        FhirContext fhirCtx = FhirContext.forDstu2();

        IParser parser = fhirCtx.newJsonParser();

        for (Block item : result) {
            Map itemMap = (Map) item.getData();
            System.out.println(JSONObject.toJSONString(itemMap));
            Observation localObservation = parser.parseResource(Observation.class, JSONObject.toJSONString(itemMap));
        }


/*
        // HttpHeaders
        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        // Request to return JSON format
        headers.setContentType(MediaType.APPLICATION_JSON);
        //headers.set("my_other_key", "my_other_value");

        // HttpEntity<String>: To get result as String.
        HttpEntity<Block> entity = new HttpEntity<Block>(headers);

        // RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Send request with GET method, and Headers.
        ResponseEntity<Block[]> response = restTemplate.exchange(uri, HttpMethod.GET, entity, Block[].class);

        Block[] result = response.getBody();

*/

    }


    @Ignore
    public void encodingTest() throws Exception {

        FhirContext ctx = FhirContext.forDstu2();

        Patient patient = new Patient();
        patient.addIdentifier().setSystem("http://example.com/fictitious-mrns").setValue("MRN001");
        patient.addName().setUse(NameUseEnum.OFFICIAL).addFamily("Tester").addGiven("John").addGiven("Q");

        Observation localObservation = new Observation();
        localObservation.setComments("nueva observacion");

        String encoded = ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(localObservation);
        System.out.println(encoded);


        IParser parser = ctx.newJsonParser();

        Observation ob = parser.parseResource(Observation.class, encoded);

        System.out.println(ob.getComments());
    }




    @Test
    public void postTest() throws Exception {

        FhirContext fhirCtx = FhirContext.forDstu2();

        Observation localObservation = new Observation();
        localObservation.setComments("nueva observacion");

        String encoded = fhirCtx.newJsonParser().encodeResourceToString(localObservation);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);

        StringBuilder localSb = new StringBuilder();

        localSb.append("{\"data\" : ");
        localSb.append(encoded);
        localSb.append("}");

        System.out.println(localSb.toString());

        HttpEntity<String> requestBody = new HttpEntity<>(localSb.toString(), headers);

        String localRestResponse = restTemplate.postForObject(URI_API + "/mineBlock", requestBody, String.class);

    }

    }
