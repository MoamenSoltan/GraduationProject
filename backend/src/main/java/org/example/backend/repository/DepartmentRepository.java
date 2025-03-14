package org.example.backend.repository;

import org.example.backend.entity.Department;
import org.example.backend.enums.DepartmentName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department,Integer> {
    @Query("select d from Department d where d.departmentName=:departmentName")
    Optional<Department> getGeneralDepartment(@Param("departmentName")DepartmentName departmentName);

    @Query("select d from Department d where d.departmentName=:departmentName")
    Optional<Department> findByDepartmentName(DepartmentName departmentName);

    @Modifying
    @Query("UPDATE Department d SET d.headOfDepartment = NULL WHERE d.headOfDepartment.instructorId = :instructorId")
    void unsetDepartmentHead(@Param("instructorId") int instructorId);


}
