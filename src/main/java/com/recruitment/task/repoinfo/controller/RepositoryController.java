package com.recruitment.task.repoinfo.controller;

import com.recruitment.task.repoinfo.model.Repository;
import com.recruitment.task.repoinfo.service.RepositoryService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class RepositoryController {
    private RepositoryService repositoryService;

    public RepositoryController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @GetMapping(path = "/repo/{user}/{repository}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Repository> getRepositoryInfo(@PathVariable String user, @PathVariable String repository, @RequestHeader HttpHeaders userTimeZone) {
        Integer userRecivedTimeZone;
        if (userTimeZone.getFirst("usertimezone") == null) {
            userRecivedTimeZone = 0;
        } else {
            userRecivedTimeZone = Integer.parseInt(Objects.requireNonNull(userTimeZone.getFirst("usertimezone")));
        }

        return ResponseEntity.ok(repositoryService
                .findRepositoryByUserAndRepositoryName(user, repository, userRecivedTimeZone));
    }

}
