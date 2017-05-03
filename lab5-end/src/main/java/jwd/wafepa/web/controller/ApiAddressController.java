package jwd.wafepa.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jwd.wafepa.model.Address;
import jwd.wafepa.service.AddressService;
import jwd.wafepa.support.AddressDTOToAddress;
import jwd.wafepa.support.AddressToAddressDTO;
import jwd.wafepa.web.dto.AddressDTO;

@Controller
@RequestMapping("/api/users/{userId}/addresses")
public class ApiAddressController {
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private AddressDTOToAddress toAddress;
	
	@Autowired
	private AddressToAddressDTO toDTO;

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<AddressDTO>> get(@PathVariable Long userId){
		List<Address> addresses = addressService.findByUser(userId);
		
		return new ResponseEntity<>(
				toDTO.convert(addresses), 
				HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<AddressDTO> get(@PathVariable Long userId,
			@PathVariable Long id){
		Address address = addressService.findOne(id);
		
		if(address==null || address.getUser().getId() != userId){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(
				toDTO.convert(address), 
				HttpStatus.OK);
	}
	
	@RequestMapping(
			method=RequestMethod.POST,
			consumes="application/json")
	public ResponseEntity<AddressDTO> add(
			@RequestBody AddressDTO newAddress, 
			@PathVariable Long userId){
		
		Address persisted = addressService.addAddress(
				toAddress.convert(newAddress),
				userId);
		
		return new ResponseEntity<>(
				toDTO.convert(persisted), 
				HttpStatus.CREATED);
	}
	
	@RequestMapping(
			value="/{id}",
			method=RequestMethod.PUT,
			consumes="application/json")
	public ResponseEntity<AddressDTO> edit(
		@PathVariable Long id,
		@RequestBody AddressDTO editedAddress,
		@PathVariable Long userId){
	
		if(id==null || !id.equals(editedAddress.getId())){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Address oldAddress = addressService.findOne(id);
		if(oldAddress == null || oldAddress.getUser().getId() != userId){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		
		Address persisted = addressService.save(toAddress.convert(editedAddress));
		
		return new ResponseEntity<>(
				toDTO.convert(persisted), 
				HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<AddressDTO> delete(
			@PathVariable Long id,
			@PathVariable Long userId){
		
		Address oldAddress = addressService.findOne(id);
		if(oldAddress == null || oldAddress.getUser().getId() != userId){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		addressService.delete(id);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
