package ru.spimex.services.clients;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.LoadingCache;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import ru.spimex.models.Organization;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Log4j2
@Named
@ApplicationScoped
public class SpimexClientImpl implements SpimexClient {

    private static final String SPIMEX_URL = "https://api.spimex.com/otc/lookup-tables/1";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(30))
            .build();

    // TODO add cache

    @Override
    @SneakyThrows({IOException.class, InterruptedException.class})
    public List<Organization> getOrganizations() {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(SPIMEX_URL))
                .setHeader("User-Agent", "Java 11 HttpClient")
                .build();

        HttpResponse<InputStream> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofInputStream());

        if (response.statusCode() != 200) {
            log.warn("The server responded with code {}", response.statusCode());
            return List.of();
        }

        try (InputStream is = response.body()) {
            SpimexResponseDto responseDto = OBJECT_MAPPER.readValue(is, SpimexResponseDto.class);

            if (!"OK".equals(responseDto.getResult())) {
                log.warn("The server responded with result {}", responseDto.getResult());
                return List.of();
            }

            return responseDto.getOrganizations().stream()
                    .map(this::toOrganization)
                    .collect(toList());
        }
    }

    private Organization toOrganization(OrganizationDto organizationDto) {
        return Organization.builder()
                .id(organizationDto.getId())
                .name(organizationDto.getName())
                .inn(organizationDto.getInn())
                .residence(organizationDto.getResidence())
                .storeDate(organizationDto.getStoreDate())
                .blockDate(organizationDto.getBlockDate())
                .build();
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class SpimexResponseDto {

        @JsonProperty("result")
        private final String result;
        @JsonProperty("records")
        private final List<OrganizationDto> organizations;

        @JsonCreator
        public SpimexResponseDto(@JsonProperty("result") String result,
                                 @JsonProperty("records") List<OrganizationDto> organizations) {
            this.result = result;
            this.organizations = organizations;
        }
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class OrganizationDto {

        private static final String DATE_PATTERN = "dd.MM.yyyy hh:mm:ss";

        @JsonProperty("ID")
        private final String id;
        @JsonProperty("Name")
        private final String name;
        @JsonProperty("INN")
        private final String inn;
        @JsonProperty("Residence")
        private final String residence;
        @JsonProperty("StoreDate")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
        private final Date storeDate;
        @JsonProperty("BlockDate")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
        private final Date blockDate;

        @JsonCreator
        public OrganizationDto(@JsonProperty("ID") String id,
                               @JsonProperty("Name") String name,
                               @JsonProperty("INN") String inn,
                               @JsonProperty("Residence") String residence,
                               @JsonProperty("StoreDate") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN) Date storeDate,
                               @JsonProperty("BlockDate") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN) Date blockDate) {
            this.id = id;
            this.name = name;
            this.inn = inn;
            this.residence = residence;
            this.storeDate = storeDate;
            this.blockDate = blockDate;
        }
    }
}
