package jwd.sajam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jwd.sajam.model.Address;
import jwd.sajam.model.User;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{

	List<Address> findByUser(User user);
	
	List<Address> findByUserId(Long userId);
	
	Address findByIdAndUserId(Long id, Long userId);
}
