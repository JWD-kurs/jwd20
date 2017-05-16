package jwd.sajam.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jwd.sajam.model.User;
import jwd.sajam.service.UserService;
import jwd.sajam.support.UserDTOToUser;
import jwd.sajam.support.UserToUserDTO;
import jwd.sajam.web.dto.UserDTO;
import jwd.sajam.web.dto.UserRegistrationDTO;

@RestController
@RequestMapping(value = "/api/users")
public class ApiUserController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserDTOToUser toUser;

	@Autowired
	private UserToUserDTO toDto;

	@RequestMapping(method = RequestMethod.GET)
	ResponseEntity<List<UserDTO>> getUser(
			@RequestParam(defaultValue = "0") int page) {
		Page<User> usersPage = userService.findAll(page);

		if (page > usersPage.getTotalPages()) {
			return new ResponseEntity<List<UserDTO>>(HttpStatus.NOT_FOUND);
		}

		List<User> users = usersPage.getContent();

		return new ResponseEntity<>(toDto.convert(users), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(toDto.convert(user), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	ResponseEntity<User> delete(@PathVariable Long id) {
		userService.delete(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<UserDTO> add(@RequestBody UserRegistrationDTO newUser) {
		if (newUser.getPassword() == null || newUser.getPassword().isEmpty()
				|| !newUser.getPassword().equals(newUser.getPasswordConfirm())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		User user = new User();
		user.setEmail(newUser.getEmail());
		user.setPassword(newUser.getPassword());
		user.setFirstName(newUser.getFirstname());
		user.setLastName(newUser.getLastname());

		User savedUser = userService.save(user);

		return new ResponseEntity<>(toDto.convert(savedUser), HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}", consumes = "application/json")
	public ResponseEntity<UserDTO> edit(@RequestBody UserDTO user,
			@PathVariable Long id) {

		if (id != user.getId()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		User persisted = userService.save(toUser.convert(user));

		return new ResponseEntity<>(toDto.convert(persisted), HttpStatus.OK);
	}

}
