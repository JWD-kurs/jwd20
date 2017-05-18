package jwd.wafepa.repository;

import jwd.wafepa.model.Activity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository 
	extends PagingAndSortingRepository<Activity, Long> {

//	@Query("select a from Activity a where a.name = :name")
//	List<Activity> findByName(@Param("name") String name);

	Page<Activity> findByName(String name, Pageable pageable);
	
	Page<Activity> findByNameLike(String name, Pageable pageable);
	
}
