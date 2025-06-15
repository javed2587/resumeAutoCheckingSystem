package com.resume.system.controller;
import com.resume.system.models.ResumeMatch;
import com.resume.system.services.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;
@CrossOrigin
@RestController
public class ResumeController {
    @Autowired
    private ResumeService resumeService;

    @PostMapping("/recommend")
    public List<ResumeMatch> recommend(@RequestBody Map<String, String> body) throws IOException {
        String jobDesc = body.get("jobDescription");
        System.out.println("recommand controller");
        return resumeService.recommendResumes(jobDesc);
    }
}
