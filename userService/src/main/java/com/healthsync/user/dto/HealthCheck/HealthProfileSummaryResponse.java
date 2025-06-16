package com.healthsync.user.dto.HealthCheck;

import com.healthsync.user.domain.HealthCheck.HealthCheckupRaw;
import com.healthsync.user.domain.HealthCheck.HealthNormalRange;
import com.healthsync.user.domain.HealthCheck.Gender;

import java.util.List;

public class HealthProfileSummaryResponse {
    private HealthCheckupRaw recentCheckup;
    private List<HealthNormalRange> normalRanges;
    private Gender gender;
    private String genderDescription;

    public HealthProfileSummaryResponse() {}


    public HealthProfileSummaryResponse(HealthCheckupRaw recentCheckup) {
        this.recentCheckup = recentCheckup;
    }

    public HealthProfileSummaryResponse(HealthCheckupRaw recentCheckup, List<HealthNormalRange> normalRanges) {
        this.recentCheckup = recentCheckup;
        this.normalRanges = normalRanges;
        this.gender = recentCheckup.getGender();
        this.genderDescription = recentCheckup.getGenderDescription();
    }

    // Getters and Setters
    public HealthCheckupRaw getRecentCheckup() { return recentCheckup; }
    public void setRecentCheckup(HealthCheckupRaw recentCheckup) {
        this.recentCheckup = recentCheckup;
        if (recentCheckup != null) {
            this.gender = recentCheckup.getGender();
            this.genderDescription = recentCheckup.getGenderDescription();
        }
    }

    public List<HealthNormalRange> getNormalRanges() { return normalRanges; }
    public void setNormalRanges(List<HealthNormalRange> normalRanges) { this.normalRanges = normalRanges; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    public String getGenderDescription() { return genderDescription; }
    public void setGenderDescription(String genderDescription) { this.genderDescription = genderDescription; }
}