package org.example.backend.repository;

import org.example.backend.entity.Department;
import org.example.backend.enums.DepartmentName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DepartmentRepository extends JpaRepository<Department,Integer> {
    @Query("select d from Department d where d.departmentName=:departmentName")
    Department getGeneralDepartment(@Param("departmentName")DepartmentName departmentName);
}
