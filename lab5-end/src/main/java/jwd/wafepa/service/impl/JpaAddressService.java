package jwd.wafepa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jwd.wafepa.model.Address;
import jwd.wafepa.model.User;
import jwd.wafepa.repository.AddressRepository;
import jwd.wafepa.service.AddressService;
import jwd.wafepa.service.UserService;

@Service
public class JpaAddressService 
	implements AddressService{
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private UserService userService;

	@Override
	public Address findOne(Long id) {
		
		return addressRepository.findOne(id);
	}

	@Override
	public List<Address> findAll() {
		
		return addressRepository.findAll();
	}

	@Override
	public Address save(Address address) {
		
		return addressRepository.save(address);
	}



	@Override
	public void delete(Long id) {
		addressRepository.delete(id);
	}

	@Override
	public List<Address> findByUser(Long userId) {
		return addressRepository.findByUserId(userId);
	}

	@Override
	public Address addAddress(Address convert, Long userId) {
		User user = userService.findOne(userId);
		if(user==null){
			throw new IllegalArgumentException("Tryed to add an address"
					+ "to an unexisting user");
		}
		user.addAddress(convert);
		userService.save(user);
		
		return addressRepository.save(convert);
	}


}
