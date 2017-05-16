package jwd.sajam.service;

import java.util.List;

import jwd.sajam.model.Sajam;
import jwd.sajam.model.Stand;


public interface SajamService {
	List<Sajam> findAll();
	Sajam findOne(Long id);
	void save(Sajam sajam);
	void remove(Long id);

}
