package jwd.wafepa.reposiotory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jwd.wafepa.model.Activity;


@Repository
public interface ActivityRepository 
	extends JpaRepository<Activity, Long> {

	List<Activity> findByNameContaining(String name);
	

//	@Query("select a from Activity a where a.name = :name")
//	List<Activity> findByName(@Param("name") String name);

}
