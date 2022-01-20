package ru.spimex.services;

/**
 * Api service
 */
public interface ApiService {

    /**
     * Count the number of active organizations by region ID
     *
     * @param regionId Region identifier
     * @return number of active organizations
     */
    long getActiveOrganizationsByRegion(int regionId);
}
