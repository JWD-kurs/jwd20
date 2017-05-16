package jwd.sajam;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jwd.sajam.model.Sajam;
import jwd.sajam.model.Stand;
import jwd.sajam.service.SajamService;
import jwd.sajam.service.StandService;

@Component
public class TestData {
	@Autowired
	private SajamService sajamService;
	@Autowired
	private StandService standService;

	@PostConstruct
	public void init() {
		Sajam poljoprivredni = new Sajam();
		poljoprivredni.setMesto("Novi Sad");
		poljoprivredni.setNaziv("Sajam poljoprivrede");
		poljoprivredni.setCena(600);
		poljoprivredni.setOtvaranje("pre neki dan");
		poljoprivredni.setZatvaranje("Dam pre nego što se setim da odem");
		sajamService.save(poljoprivredni);
		
		Stand stand1 = new Stand();
		stand1.setPovrsina(10);
		stand1.setZakupac("Pera Peric");
		stand1.setSajam(poljoprivredni);
		standService.save(stand1);
		

		Sajam nauke = new Sajam();
		nauke.setMesto("Novi Sad, kampus UNS");
		nauke.setNaziv("Festival nauke");
		nauke.setCena(600);
		nauke.setOtvaranje("pre neki dan");
		nauke.setZatvaranje("juče");
		sajamService.save(nauke);
		
		
	}
}