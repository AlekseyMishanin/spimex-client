package ru.spimex.services.clients;

import ru.spimex.models.Organization;

import java.util.List;

/**
 * SPIMEX client
 */
public interface SpimexClient {

    /**
     * Get organizations
     *
     * @return list of organizations
     */
    List<Organization> getOrganizations();
}
