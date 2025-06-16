package com.healthsync.user.dto.HealthCheck;

import com.healthsync.user.domain.HealthCheck.HealthCheckupRaw;
import com.healthsync.user.domain.HealthCheck.HealthNormalRange;

import java.util.List;

public class HealthProfileSummaryResponse {
    private HealthCheckupRaw recentCheckup;
    private List<HealthNormalRange> normalRanges;

    public HealthProfileSummaryResponse() {}

    public HealthProfileSummaryResponse(HealthCheckupRaw recentCheckup, List<HealthNormalRange> normalRanges) {
        this.recentCheckup = recentCheckup;
        this.normalRanges = normalRanges;
    }

    // Getters and Setters
    public HealthCheckupRaw getRecentCheckup() { return recentCheckup; }
    public void setRecentCheckup(HealthCheckupRaw recentCheckup) { this.recentCheckup = recentCheckup; }

    public List<HealthNormalRange> getNormalRanges() { return normalRanges; }
    public void setNormalRanges(List<HealthNormalRange> normalRanges) { this.normalRanges = normalRanges; }
}