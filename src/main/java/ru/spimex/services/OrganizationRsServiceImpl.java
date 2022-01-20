package ru.spimex.services;

import ru.spimex.models.Organization;
import ru.spimex.rest.OrganizationRsService;
import ru.spimex.services.clients.SpimexClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class OrganizationRsServiceImpl implements OrganizationRsService {

    @Inject
    private SpimexClient spimexClient;

    @Override
    public long countOrganizationByRegion(int regionId) {
        // TODO regionId is ignored, because the response from spimex does not contain a region
        return spimexClient.getOrganizations().stream()
                .filter(organization -> organization.getBlockDate() != null)
                .count();
    }
}
