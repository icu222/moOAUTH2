package com.healthsync.user.domain.HealthCheck;

import java.time.LocalDateTime;

public class HealthNormalRange {
    private Integer rangeId;
    private String healthItemCode;
    private String healthItemName;
    private Integer genderCode;
    private String unit;
    private String normalRange;
    private String warningRange;
    private String dangerRange;
    private String note;
    private LocalDateTime createdAt;

    public HealthNormalRange() {}

    // Getters and Setters
    public Integer getRangeId() { return rangeId; }
    public void setRangeId(Integer rangeId) { this.rangeId = rangeId; }

    public String getHealthItemCode() { return healthItemCode; }
    public void setHealthItemCode(String healthItemCode) { this.healthItemCode = healthItemCode; }

    public String getHealthItemName() { return healthItemName; }
    public void setHealthItemName(String healthItemName) { this.healthItemName = healthItemName; }

    public Integer getGenderCode() { return genderCode; }
    public void setGenderCode(Integer genderCode) { this.genderCode = genderCode; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public String getNormalRange() { return normalRange; }
    public void setNormalRange(String normalRange) { this.normalRange = normalRange; }

    public String getWarningRange() { return warningRange; }
    public void setWarningRange(String warningRange) { this.warningRange = warningRange; }

    public String getDangerRange() { return dangerRange; }
    public void setDangerRange(String dangerRange) { this.dangerRange = dangerRange; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}