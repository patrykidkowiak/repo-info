package com.recruitment.task.repoinfo.service;

import com.recruitment.task.repoinfo.exception.ParseFromStringToJsonNotPossibleException;
import com.recruitment.task.repoinfo.exception.UserOrRepositoryNotFoundException;
import com.recruitment.task.repoinfo.model.Repository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
public class RepositoryService implements IRepositoryService {

    private final static String GIT_REPOSITORY_URL = "https://api.github.com/repos/%s/%s";
    private final static String JSON_REPOSITORY_FULL_NAME_FIELD = "full_name";
    private final static String JSON_REPOSITORY_CLONE_URL_FIELD = "clone_url";
    private final static String JSON_REPOSITORY_DESCRIPTION_FIELD = "description";
    private final static String JSON_REPOSITORY_CREATED_AT_FIELD = "created_at";
    private final static String JSON_REPOSITORY_STARS_FIELD = "stargazers_count";
    private final static Integer TIME_ZONE_MINUTES_TO_HOURS = 60;
    private final static String TIME_ZONE_PREFIX = "Etc/GMT";


    @Override
    public Repository findRepositoryByUserAndRepositoryName(String user, String repositoryName, Integer UserTimeZone) {

        RestTemplate restTemplate = new RestTemplate();

        String fullRepositoryUrl = String.format(GIT_REPOSITORY_URL, user, repositoryName);
        String receivedRepositoryString;

        try {
            receivedRepositoryString = restTemplate.getForObject(fullRepositoryUrl, String.class);
        } catch (HttpClientErrorException e) {
            throw new UserOrRepositoryNotFoundException();
        }

        try {
            JSONObject json = new JSONObject((receivedRepositoryString));
            return parseJsonToRepository(json, UserTimeZone);
        } catch (JSONException e) {
            throw new ParseFromStringToJsonNotPossibleException();
        }
    }

    private Repository parseJsonToRepository(JSONObject jsonToParse, Integer userTimeZone) throws JSONException {

        Repository repository = new Repository();

        repository.setFullName(jsonToParse.getString(JSON_REPOSITORY_FULL_NAME_FIELD));
        repository.setCloneUrl(jsonToParse.getString(JSON_REPOSITORY_CLONE_URL_FIELD));
        repository.setDescription(jsonToParse.getString(JSON_REPOSITORY_DESCRIPTION_FIELD));
        repository.setCreatedAt(calculateCreatedAtDateWithUserTimeZone(jsonToParse.getString(JSON_REPOSITORY_CREATED_AT_FIELD), userTimeZone));
        repository.setStars(jsonToParse.getInt(JSON_REPOSITORY_STARS_FIELD));

        return repository;
    }

    private LocalDateTime calculateCreatedAtDateWithUserTimeZone(String dateToCalculate, Integer userTimeZone) {
        Instant instant = Instant.parse(dateToCalculate);
        String timeZoneId = TIME_ZONE_PREFIX + (userTimeZone / TIME_ZONE_MINUTES_TO_HOURS);

        return LocalDateTime.ofInstant(instant, ZoneId.of(timeZoneId));
    }
}
