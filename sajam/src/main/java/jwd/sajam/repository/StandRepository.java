package jwd.sajam.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jwd.sajam.model.Stand;

@Repository
public interface StandRepository 
	extends JpaRepository<Stand, Long> {

	Page<Stand> findBySajamId(Long sajamId, Pageable pageRequest);

}
