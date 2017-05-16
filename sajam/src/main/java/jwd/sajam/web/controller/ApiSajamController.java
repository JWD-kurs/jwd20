package jwd.sajam.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jwd.sajam.model.Sajam;
import jwd.sajam.model.Stand;
import jwd.sajam.service.SajamService;
import jwd.sajam.service.StandService;
import jwd.sajam.support.SajamToSajamDTO;
import jwd.sajam.support.StandToStandDTO;
import jwd.sajam.web.dto.SajamDTO;
import jwd.sajam.web.dto.StandDTO;

@RestController
@RequestMapping(value="/api/sajmovi")
public class ApiSajamController {
	@Autowired
	private SajamService sajamService;
	@Autowired
	private StandService standService;
	@Autowired
	private SajamToSajamDTO toDTO;
	@Autowired
	private StandToStandDTO toStandDTO;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<SajamDTO>> get(){
		return new ResponseEntity<>(
				toDTO.convert(sajamService.findAll()),
				HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<SajamDTO> get(
			@PathVariable Long id){
		
		Sajam sajam = sajamService.findOne(id);
		
		if(sajam == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(
				toDTO.convert(sajam),
				HttpStatus.OK);
	}
	
	@RequestMapping(value="/{sajamId}/standovi")
	public ResponseEntity<List<StandDTO>> standoviSajma(
			@PathVariable Long sajamId,
			@RequestParam(defaultValue="0") int pageNum){
		Page<Stand> standovi = standService.findBySajamId(pageNum, sajamId);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("totalPages", Integer.toString(standovi.getTotalPages()) );
		return  new ResponseEntity<>(
				toStandDTO.convert(standovi.getContent()),
				headers,
				HttpStatus.OK);
	}
}
