package com.epam.resourceservice.mapper;

import com.epam.resourceservice.dto.ResourceResponse;
import com.epam.resourceservice.entity.Resource;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ResourceMapper {
    ResourceResponse resourceToResourceResponse(Resource resource);
}
