package jwd.sajam.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import jwd.sajam.model.User;

@Repository
public interface UserRepository 
	extends PagingAndSortingRepository<User, Long> {

}
