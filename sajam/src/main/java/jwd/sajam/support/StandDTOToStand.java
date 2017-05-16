package jwd.sajam.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import jwd.sajam.model.Stand;
import jwd.sajam.service.SajamService;
import jwd.sajam.service.StandService;
import jwd.sajam.web.dto.StandDTO;

@Component
public class StandDTOToStand 
	implements Converter<StandDTO, Stand>{
	
	@Autowired
	private StandService standService;
	@Autowired
	private SajamService sajamService;
	
	
	@Override
	public Stand convert(StandDTO source) {
		Stand stand;
		if(source.getId()==null){
			stand = new Stand();
			stand.setSajam(
					sajamService.findOne(
							source.getSajamId()));
		}else{
			stand = standService.findOne(source.getId());
		}
		stand.setPovrsina(source.getPovrsina());
		stand.setZakupac(source.getZakupac());
		
		
		return stand;
	}

}
