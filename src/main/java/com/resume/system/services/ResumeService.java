package com.resume.system.services;

import com.resume.system.models.ResumeMatch;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ResumeService {
    public List<ResumeMatch> recommendResumes(String jobDesc) throws IOException {
        File folder = new ClassPathResource("resumes").getFile();
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));

        List<ResumeMatch> matches = new ArrayList<>();

        for (File file : files) {
            String content = new String(
                    java.nio.file.Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
            double score = cosineSimilarity(jobDesc, content);
            matches.add(new ResumeMatch(file.getName(), score));
        }

        return matches.stream()
                .sorted(Comparator.comparingDouble(ResumeMatch::getScore).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    private double cosineSimilarity(String doc1, String doc2) {
        String[] words1 = doc1.toLowerCase().split("\\W+");
        String[] words2 = doc2.toLowerCase().split("\\W+");

        Map<String, Integer> freq1 = wordFrequency(words1);
        Map<String, Integer> freq2 = wordFrequency(words2);

        Set<String> allWords = new HashSet<>(freq1.keySet());
        allWords.addAll(freq2.keySet());

        int dot = 0, norm1 = 0, norm2 = 0;
        for (String word : allWords) {
            int a = freq1.getOrDefault(word, 0);
            int b = freq2.getOrDefault(word, 0);
            dot += a * b;
            norm1 += a * a;
            norm2 += b * b;
        }

        return norm1 == 0 || norm2 == 0 ? 0.0 : dot / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }

    private Map<String, Integer> wordFrequency(String[] words) {
        Map<String, Integer> freq = new HashMap<>();
        for (String w : words) {
            freq.put(w, freq.getOrDefault(w, 0) + 1);
        }
        return freq;
    }
}
