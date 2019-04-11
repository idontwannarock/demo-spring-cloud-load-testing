package com.example.demoloadtestingzuul.configuration;

import com.google.common.collect.Lists;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.Arrays;
import java.util.List;

@Primary
@Component
public class DocumentationConfig implements SwaggerResourcesProvider {

    private final RouteLocator routeLocator;

    public DocumentationConfig(RouteLocator routeLocator) {
        this.routeLocator = routeLocator;
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = Lists.newArrayList();
        List<Route> routes = routeLocator.getRoutes();

        System.out.println(Arrays.toString(routes.toArray()));

        routes.forEach(route -> resources.add(swaggerResource(route.getId(), route.getFullPath().replace("**", "v2/api-docs"))));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
