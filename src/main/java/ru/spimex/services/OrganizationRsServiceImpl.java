package ru.spimex.services;

import ru.spimex.rest.OrganizationRsService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class OrganizationRsServiceImpl implements OrganizationRsService {

    @Inject
    private ApiService apiService;

    @Override
    public long countOrganizationByRegion(int regionId) {
        return apiService.getActiveOrganizationsByRegion(regionId);
    }
}
