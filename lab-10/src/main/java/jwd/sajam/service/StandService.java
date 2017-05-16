package jwd.sajam.service;

import org.springframework.data.domain.Page;

import jwd.sajam.model.Stand;

public interface StandService {
	Page<Stand> findAll(int pageNum);
	Stand findOne(Long id);
	void save(Stand stand);
	void remove(Long id);
	Page<Stand> findBySajamId(int pageNum, Long sajamId);
}
