package com.meli.starwars.controllers;

import com.meli.starwars.converters.SatelliteConverter;
import com.meli.starwars.dto.InterceptedCommunicationDTO;
import com.meli.starwars.dto.SatelliteDTO;
import com.meli.starwars.dto.SatelliteWrapper;
import com.meli.starwars.entity.Satellite;
import com.meli.starwars.interfaces.MessageInterceptorInterface;
import com.meli.starwars.repositories.SatelliteRepository;
import com.meli.starwars.utils.Position;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class SatelliteControllerTests {

	@Autowired
	private SatelliteController satelliteController;

	@MockBean
	private SatelliteRepository repository;

	@Spy
	private SatelliteConverter satelliteConverter;

	@Mock
	private MessageInterceptorInterface messageInterceptorInterface;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	private final Satellite satelliteMock = new Satellite("test",1,1);

	private final SatelliteDTO satelliteDTOMock = new SatelliteDTO("test",1,1);
	
	private final SatelliteDTO updateSatelliteDTOMock = new SatelliteDTO("test",2,2);

	@Test
	void findAll() {
		when(repository.findAll()).thenReturn(Collections.singletonList(satelliteMock));
		final List<SatelliteDTO> satellites = satelliteController.all();
		assertEquals(satellites.size(),1);
	}

	@Test
	void saveNewSatellite() {
		when(repository.save(Mockito.any(Satellite.class))).thenReturn(satelliteMock);
		final SatelliteDTO resp = satelliteController.newSatellite(satelliteDTOMock);
		assertEquals(resp,satelliteDTOMock);
	}

	@Test
	void getOneSatellite() {
		when(repository.findByNameIgnoreCase(Mockito.any(String.class))).thenReturn(Optional.of(satelliteMock));
		final SatelliteDTO resp = satelliteController.one("test");
		assertEquals(resp,satelliteDTOMock);
	}

	@Test
	void getOneSatelliteError() {
		when(repository.findByNameIgnoreCase(Mockito.any(String.class))).thenReturn(Optional.empty());
		try {
			satelliteController.one("test");
		}catch (Exception ex){
			assertEquals(ex.getMessage(),"Could not find satellite test");
		}
	}

	@Test
	void replaceExistSatellite() {
		when(repository.findByNameIgnoreCase(Mockito.any(String.class))).thenReturn(Optional.of(satelliteMock));
		when(repository.save(Mockito.any(Satellite.class))).thenReturn(satelliteMock);
		final SatelliteDTO resp = satelliteController.replaceSatellite(satelliteDTOMock,"test");
		assertEquals(resp,satelliteDTOMock);
	}

	@Test
	void replaceNonExistSatellite() {
		when(repository.findByNameIgnoreCase(Mockito.any(String.class))).thenReturn(Optional.empty());
		when(repository.save(Mockito.any(Satellite.class))).thenReturn(satelliteMock);
		final SatelliteDTO resp = satelliteController.replaceSatellite(satelliteDTOMock,"test");
		assertEquals(resp,satelliteDTOMock);
	}
	
	@Test
	void updateSatelliteInformation() {
		satelliteMock.setDistance(updateSatelliteDTOMock.getDistance());
		satelliteMock.setMessage(updateSatelliteDTOMock.getMessage());
		
		when(repository.findByNameIgnoreCase(Mockito.any(String.class))).thenReturn(java.util.Optional.of(satelliteMock));
		when(repository.save(satelliteMock)).thenReturn(satelliteMock);
		
		final SatelliteDTO resp = satelliteController.updateSatelliteInformation(updateSatelliteDTOMock, "test");
		assertEquals(resp,satelliteDTOMock);	
	}
	
	@Test
	void updateSatelliteInformationError() {
		when(repository.findByNameIgnoreCase(Mockito.any(String.class))).thenReturn(Optional.empty());
		try {
			satelliteController.updateSatelliteInformation(updateSatelliteDTOMock, "test");;
		}catch (Exception ex){
			assertEquals(ex.getMessage(),"Could not find satellite test");
		}	
	}
	
	@Test
	void determineInformation() {
		Vector<String> message1 = new Vector<String>();
		final String[] s1 = {"","este","es","un","mensaje"};
		for (String word : s1) {
			message1.add(word);
		}
		Vector<String> message2 = new Vector<String>();
		final String[] s2 = {"este","","un","mensaje"};
		for (String word : s2) {
			message2.add(word);
		}
		Vector<String> message3 = new Vector<String>();
		final String[] s3 = {"", "", "", "es","","mensaje"};
		for (String word : s3) {
			message3.add(word);
		}
		
		Vector<String> interceptedMessage = new Vector<String>();
		final String[] s4 = {"este", "es", "un", "mensaje"};
		for (String word : s4) {
			interceptedMessage.add(word);
		}

		Satellite satellite1 = new Satellite("satellite1",0, 2*100*Math.sqrt(3),  100*Math.sqrt(3),message1);
		Satellite satellite2 = new Satellite("satellite2",100, 0,200,message2);
		Satellite satellite3 = new Satellite("satellite3",-100, 0,200,message2);
		List<Satellite> satellitesToUse = new ArrayList<Satellite>();
		satellitesToUse.add(satellite1);
		satellitesToUse.add(satellite2);
		satellitesToUse.add(satellite3);
		
		when(repository.findAll()).thenReturn(satellitesToUse);
		
		final InterceptedCommunicationDTO resp = satelliteController.determineInformation();
		assertEquals(new Position(0.0, 173.2051), resp.getPosition());
		assertEquals(interceptedMessage, resp.getMessage());
	}
	
	@Test
	void determineInformationErrorLessThan3Satellites() {

		Satellite satellite1 = new Satellite("satellite1",0, 2*100*Math.sqrt(3));
		Satellite satellite2 = new Satellite("satellite2",100, 0.0);
		List<Satellite> satellitesToUse = new ArrayList<Satellite>();
		satellitesToUse.add(satellite1);
		satellitesToUse.add(satellite2);		
		
		when(repository.findAll()).thenReturn(satellitesToUse);
		try {
			satelliteController.determineInformation();
		}catch (Exception ex){
			assertEquals(ex.getMessage(),"We couldn't intercept the message. You need at least 3 satellites to determine position");
		}	
	}
	
	@Test
	void interceptMessage() {
		SatelliteWrapper satelliteWrapper = new SatelliteWrapper();
		
		Vector<String> message1 = new Vector<String>();
		final String[] s1 = {"","este","es","un","mensaje"};
		for (String word : s1) {
			message1.add(word);
		}
		Vector<String> message2 = new Vector<String>();
		final String[] s2 = {"este","","un","mensaje"};
		for (String word : s2) {
			message2.add(word);
		}
		Vector<String> message3 = new Vector<String>();
		final String[] s3 = {"", "", "", "es","","mensaje"};
		for (String word : s3) {
			message3.add(word);
		}
		
		Vector<String> interceptedMessage = new Vector<String>();
		final String[] s4 = {"este", "es", "un", "mensaje"};
		for (String word : s4) {
			interceptedMessage.add(word);
		}

		SatelliteDTO satelliteDTO1 = new SatelliteDTO("satellite1",0, 2*100*Math.sqrt(3),  100*Math.sqrt(3),message1);
		SatelliteDTO satelliteDTO2 = new SatelliteDTO("satellite2",100, 0,200,message2);
		SatelliteDTO satelliteDTO3 = new SatelliteDTO("satellite3",-100, 0,200,message2);
		
		Satellite satellite1 = new Satellite("satellite1",0, 2*100*Math.sqrt(3));
		Satellite satellite2 = new Satellite("satellite2",100, 0);
		Satellite satellite3 = new Satellite("satellite3",-100, 0);
		
		List<SatelliteDTO> satellitesDTOToUse = new ArrayList<>();
		satellitesDTOToUse.add(satelliteDTO1);
		satellitesDTOToUse.add(satelliteDTO2);
		satellitesDTOToUse.add(satelliteDTO3);
		
		satelliteWrapper.setSatellites(satellitesDTOToUse);
		
		when(repository.findByNameIgnoreCase("satellite1")).thenReturn(Optional.of(satellite1));
		when(repository.findByNameIgnoreCase("satellite2")).thenReturn(Optional.of(satellite2));
		when(repository.findByNameIgnoreCase("satellite3")).thenReturn(Optional.of(satellite3));
	
		when(repository.save(satellite1)).thenReturn(satellite1);
		when(repository.save(satellite2)).thenReturn(satellite2);
		when(repository.save(satellite3)).thenReturn(satellite3);
		
		
		final InterceptedCommunicationDTO resp = satelliteController.interceptMessage(satelliteWrapper);
		assertEquals(new Position(0.0, 173.2051), resp.getPosition());
		assertEquals(interceptedMessage, resp.getMessage());
	}
	
	@Test
	void interceptMessageError() {
		SatelliteWrapper satelliteWrapper = new SatelliteWrapper();
		
		SatelliteDTO satelliteDTO1 = new SatelliteDTO("satellite1",0, 2*100*Math.sqrt(3));
		SatelliteDTO satelliteDTO2 = new SatelliteDTO("satellite2",100, 0);
		SatelliteDTO satelliteDTO3 = new SatelliteDTO("satellite3",-100, 0);
		
		List<SatelliteDTO> satellitesDTOToUse = new ArrayList<>();
		satellitesDTOToUse.add(satelliteDTO1);
		satellitesDTOToUse.add(satelliteDTO2);
		satellitesDTOToUse.add(satelliteDTO3);
		
		satelliteWrapper.setSatellites(satellitesDTOToUse);		
		
		when(repository.findByNameIgnoreCase(Mockito.any(String.class))).thenReturn(Optional.empty());
		try {
			satelliteController.interceptMessage(satelliteWrapper);
		}catch (Exception ex){
			assertEquals(ex.getMessage(),"Could not find satellite satellite1");
		}	
	}

}
