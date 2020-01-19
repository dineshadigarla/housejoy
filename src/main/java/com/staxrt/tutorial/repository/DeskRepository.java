package com.staxrt.tutorial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.staxrt.tutorial.model.Desks;

public interface DeskRepository extends JpaRepository<Desks, Long> {
	
	@Query(value="select * FROM DESKS  LEFT JOIN EMPLOYEES  ON desks.id=employees.desk_id WHERE employees.employee_name is NULL",nativeQuery=true)
    List<Desks> findNonAssociatedDesks();
	
	@Query(value="SELECT * FROM DESKS LEFT JOIN EMPLOYEES on desks.id=employees.desk_id WHERE employees.employee_name is NOT NULL",nativeQuery=true)
    List<Desks> findAssociatedDesks();
	
	@Query(value="select * FROM DESKS  LEFT JOIN EMPLOYEES  ON desks.id=employees.desk_id WHERE employees.employee_name is NULL and desks.block_id=?1",nativeQuery=true)
    List<Desks> findNonAssociatedDesksForBlock(Long blockId);
	
	@Query(value="select * FROM DESKS  LEFT JOIN EMPLOYEES  ON desks.id=employees.desk_id WHERE employees.employee_name is NULL and desks.block_id in (select id from blocks where floor_id=?1)",nativeQuery=true)
    List<Desks> findNonAssociatedDesksForFloor(Long floorId);
	
	@Query(value="select * FROM DESKS  LEFT JOIN EMPLOYEES  ON desks.id=employees.desk_id WHERE employees.employee_name is NULL and desks.block_id in (select id from blocks where floor_id=(select id from floors where building_id=?1))",nativeQuery=true)
    List<Desks> findNonAssociatedDesksForBuilding(Long floorId);
}
