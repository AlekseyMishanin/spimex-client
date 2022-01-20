package ru.spimex.services;

import ru.spimex.services.clients.SpimexClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class ApiServiceImpl implements ApiService {

    @Inject
    private SpimexClient spimexClient;

    @Override
    public long getActiveOrganizationsByRegion(int regionId) {
        return spimexClient.getOrganizations().stream()
                .filter(organization -> organization.getBlockDate() == null && organization.getRegionId() != null &&
                        organization.getRegionId() == regionId)
                .count();
    }
}
