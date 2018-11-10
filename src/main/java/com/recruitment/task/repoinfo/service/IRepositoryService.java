package com.recruitment.task.repoinfo.service;

import com.recruitment.task.repoinfo.model.Repository;
import org.json.JSONException;

public interface IRepositoryService {
    Repository findRepositoryByUserAndRepositoryName(String user, String repositoryName, Integer userTimeZone) throws JSONException;
}
