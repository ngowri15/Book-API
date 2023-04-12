package com.book.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BooksBatchController {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    //To create all the books' details
    @PostMapping("/BatchBooks")
    public ResponseEntity<String> uploadBooks() {

        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                .toJobParameters();
        try {
            System.out.println(jobLauncher.run(job, jobParameters));
            return new ResponseEntity<>("Batching of books done successfully.", HttpStatus.OK);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | DuplicateKeyException e) {
            return new ResponseEntity<>("Batching failed due to " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }}