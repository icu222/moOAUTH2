package com.healthsync.user.repository.entity;

import com.healthsync.user.domain.HealthCheck.HealthNormalRange;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "health_normal_range", schema = "health_service")
public class HealthNormalRangeEntity {

    @Id
    @Column(name = "range_id")
    private Integer rangeId;

    @Column(name = "health_item_code", length = 25)
    private String healthItemCode;

    @Column(name = "health_item_name", length = 30)
    private String healthItemName;

    @Column(name = "gender_code")
    private Integer genderCode;

    @Column(name = "unit", length = 10)
    private String unit;

    @Column(name = "normal_range", length = 15)
    private String normalRange;

    @Column(name = "warning_range", length = 15)
    private String warningRange;

    @Column(name = "danger_range", length = 15)
    private String dangerRange;

    @Column(name = "note", length = 50)
    private String note;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected HealthNormalRangeEntity() {}

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

    // Entity ↔ Domain 변환 메서드
    public static HealthNormalRangeEntity fromDomain(HealthNormalRange healthNormalRange) {
        if (healthNormalRange == null) return null;

        HealthNormalRangeEntity entity = new HealthNormalRangeEntity();
        entity.rangeId = healthNormalRange.getRangeId();
        entity.healthItemCode = healthNormalRange.getHealthItemCode();
        entity.healthItemName = healthNormalRange.getHealthItemName();
        entity.genderCode = healthNormalRange.getGenderCode();
        entity.unit = healthNormalRange.getUnit();
        entity.normalRange = healthNormalRange.getNormalRange();
        entity.warningRange = healthNormalRange.getWarningRange();
        entity.dangerRange = healthNormalRange.getDangerRange();
        entity.note = healthNormalRange.getNote();
        entity.createdAt = healthNormalRange.getCreatedAt();
        return entity;
    }

    public HealthNormalRange toDomain() {
        HealthNormalRange domain = new HealthNormalRange();
        domain.setRangeId(this.rangeId);
        domain.setHealthItemCode(this.healthItemCode);
        domain.setHealthItemName(this.healthItemName);
        domain.setGenderCode(this.genderCode);
        domain.setUnit(this.unit);
        domain.setNormalRange(this.normalRange);
        domain.setWarningRange(this.warningRange);
        domain.setDangerRange(this.dangerRange);
        domain.setNote(this.note);
        domain.setCreatedAt(this.createdAt);
        return domain;
    }
}