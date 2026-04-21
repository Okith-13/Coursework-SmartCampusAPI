package com.mycompany.smartcampusapi.config;

import com.mycompany.smartcampusapi.filter.LoggingFilter;
import com.mycompany.smartcampusapi.mapper.GlobalExceptionMapper;
import com.mycompany.smartcampusapi.mapper.LinkedResourceNotFoundExceptionMapper;
import com.mycompany.smartcampusapi.mapper.RoomNotEmptyExceptionMapper;
import com.mycompany.smartcampusapi.mapper.SensorUnavailableExceptionMapper;
import com.mycompany.smartcampusapi.resource.DiscoveryResource;
import com.mycompany.smartcampusapi.resource.RoomResource;
import com.mycompany.smartcampusapi.resource.SensorResource;
import org.glassfish.jersey.server.ResourceConfig;

public class ApplicationConfig extends ResourceConfig {

    public ApplicationConfig() {
        register(DiscoveryResource.class);
        register(RoomResource.class);
        register(SensorResource.class);

        register(RoomNotEmptyExceptionMapper.class);
        register(LinkedResourceNotFoundExceptionMapper.class);
        register(SensorUnavailableExceptionMapper.class);
        register(GlobalExceptionMapper.class);

        register(LoggingFilter.class);

        packages("org.glassfish.jersey.jackson");
    }
}