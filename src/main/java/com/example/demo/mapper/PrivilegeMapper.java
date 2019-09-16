package com.example.demo.mapper;

import java.util.ArrayList;
import java.util.Collection;

import org.mapstruct.Mapper;

import com.example.demo.dto.PrivilegeDto;
import com.example.demo.model.Privilege;

@Mapper(componentModel="spring")
public interface PrivilegeMapper {
	PrivilegeDto privelegeToDto(Privilege privilege);
	Privilege dtoToPrivilege(PrivilegeDto privilegeDto);
	ArrayList<PrivilegeDto> privilegesToDtos(Collection<Privilege> privileges);
	Collection<Privilege> dtosToPrivileges(ArrayList<PrivilegeDto> privileges);
}
