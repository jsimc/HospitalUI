package com.example.bolnicaui.mapper;

import com.example.bolnicaui.domain.rooms.Department;
import com.example.bolnicaui.dto.DepartmentDto;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {
    public DepartmentDto departmentToDepartmentDto(Department department) {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(department.getId());
        departmentDto.setName(department.getName());
        return departmentDto;
    }
}
