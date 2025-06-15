package com.resume.system.models;

public class ResumeMatch {
    private String fileName;
    private double score;

    public ResumeMatch(String fileName, double score) {
        this.fileName = fileName;
        this.score = score;
    }

    public String getFileName() {
        return fileName;
    }

    public double getScore() {
        return score;
    }
}
