package com.healthsync.user.dto.HealthCheck;

import com.healthsync.user.domain.HealthCheck.HealthCheckupRaw;
import com.healthsync.user.domain.HealthCheck.HealthNormalRange;

import java.util.List;

public class HealthCheckupHistoryResponse {
    private HealthCheckupRaw recentCheckup;
    private List<HealthNormalRange> normalRanges;
    private String statusNote;

    public HealthCheckupHistoryResponse() {}

    public HealthCheckupHistoryResponse(HealthCheckupRaw recentCheckup,
                                        List<HealthNormalRange> normalRanges,
                                        String statusNote) {
        this.recentCheckup = recentCheckup;
        this.normalRanges = normalRanges;
        this.statusNote = statusNote;
    }

    // Getters and Setters
    public HealthCheckupRaw getRecentCheckup() { return recentCheckup; }
    public void setRecentCheckup(HealthCheckupRaw recentCheckup) { this.recentCheckup = recentCheckup; }

    public List<HealthNormalRange> getNormalRanges() { return normalRanges; }
    public void setNormalRanges(List<HealthNormalRange> normalRanges) { this.normalRanges = normalRanges; }

    public String getStatusNote() { return statusNote; }
    public void setStatusNote(String statusNote) { this.statusNote = statusNote; }
}
