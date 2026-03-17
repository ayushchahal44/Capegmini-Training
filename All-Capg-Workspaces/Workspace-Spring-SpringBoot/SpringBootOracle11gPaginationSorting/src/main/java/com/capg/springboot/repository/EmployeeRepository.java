package com.capg.springboot.repository;

import com.capg.springboot.entity.Employee;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepository {

    private final JdbcTemplate jdbcTemplate;

    public EmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Employee> findEmployees(
            int page,
            int size,
            String sortField,
            String sortDir
    ) {

        //  Allow only valid columns (SQL Injection safe)
        if (!List.of("id", "name", "salary", "department").contains(sortField)) {
            sortField = "id";
        }

        sortDir = sortDir.equalsIgnoreCase("DESC") ? "DESC" : "ASC";

        int startRow = page * size;
        int endRow = startRow + size;

        String sql = """
            SELECT * FROM (
                SELECT e.*, ROWNUM rn FROM (
                    SELECT * FROM employee
                    ORDER BY %s %s
                ) e WHERE ROWNUM <= ?
            ) WHERE rn > ?
        """.formatted(sortField, sortDir);

        return jdbcTemplate.query(
                sql,
                new Object[]{endRow, startRow},
                (rs, rowNum) -> {
                    Employee emp = new Employee();
                    emp.setId(rs.getLong("ID"));
                    emp.setName(rs.getString("NAME"));
                    emp.setDepartment(rs.getString("DEPARTMENT"));
                    emp.setSalary(rs.getDouble("SALARY"));
                    return emp;
                }
        );
    }
}