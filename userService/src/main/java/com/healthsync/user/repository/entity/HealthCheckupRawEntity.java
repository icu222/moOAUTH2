package com.healthsync.user.repository.entity;

import com.healthsync.user.domain.HealthCheck.HealthCheckupRaw;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "health_checkup_raw", schema = "health_service")
public class HealthCheckupRawEntity {

    @Id
    @Column(name = "raw_id")
    private Long rawId;

    @Column(name = "reference_year", nullable = false)
    private Integer referenceYear;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "region_code")
    private Integer regionCode;

    @Column(name = "gender_code")
    private Integer genderCode;

    @Column(name = "age")
    private Integer age;

    @Column(name = "height")
    private Integer height;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "waist_circumference")
    private Integer waistCircumference;

    @Column(name = "visual_acuity_left", precision = 3, scale = 1)
    private BigDecimal visualAcuityLeft;

    @Column(name = "visual_acuity_right", precision = 3, scale = 1)
    private BigDecimal visualAcuityRight;

    @Column(name = "hearing_left")
    private Integer hearingLeft;

    @Column(name = "hearing_right")
    private Integer hearingRight;

    @Column(name = "systolic_bp")
    private Integer systolicBp;

    @Column(name = "diastolic_bp")
    private Integer diastolicBp;

    @Column(name = "fasting_glucose")
    private Integer fastingGlucose;

    @Column(name = "total_cholesterol")
    private Integer totalCholesterol;

    @Column(name = "triglyceride")
    private Integer triglyceride;

    @Column(name = "hdl_cholesterol")
    private Integer hdlCholesterol;

    @Column(name = "ldl_cholesterol")
    private Integer ldlCholesterol;

    @Column(name = "hemoglobin", precision = 4, scale = 1)
    private BigDecimal hemoglobin;

    @Column(name = "urine_protein")
    private Integer urineProtein;

    @Column(name = "serum_creatinine", precision = 4, scale = 1)
    private BigDecimal serumCreatinine;

    @Column(name = "ast")
    private Integer ast;

    @Column(name = "alt")
    private Integer alt;

    @Column(name = "gamma_gtp")
    private Integer gammaGtp;

    @Column(name = "smoking_status")
    private Integer smokingStatus;

    @Column(name = "drinking_status")
    private Integer drinkingStatus;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected HealthCheckupRawEntity() {}

    // Getters and Setters
    public Long getRawId() { return rawId; }
    public void setRawId(Long rawId) { this.rawId = rawId; }

    public Integer getReferenceYear() { return referenceYear; }
    public void setReferenceYear(Integer referenceYear) { this.referenceYear = referenceYear; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getRegionCode() { return regionCode; }
    public void setRegionCode(Integer regionCode) { this.regionCode = regionCode; }

    public Integer getGenderCode() { return genderCode; }
    public void setGenderCode(Integer genderCode) { this.genderCode = genderCode; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public Integer getHeight() { return height; }
    public void setHeight(Integer height) { this.height = height; }

    public Integer getWeight() { return weight; }
    public void setWeight(Integer weight) { this.weight = weight; }

    public Integer getWaistCircumference() { return waistCircumference; }
    public void setWaistCircumference(Integer waistCircumference) { this.waistCircumference = waistCircumference; }

    public BigDecimal getVisualAcuityLeft() { return visualAcuityLeft; }
    public void setVisualAcuityLeft(BigDecimal visualAcuityLeft) { this.visualAcuityLeft = visualAcuityLeft; }

    public BigDecimal getVisualAcuityRight() { return visualAcuityRight; }
    public void setVisualAcuityRight(BigDecimal visualAcuityRight) { this.visualAcuityRight = visualAcuityRight; }

    public Integer getHearingLeft() { return hearingLeft; }
    public void setHearingLeft(Integer hearingLeft) { this.hearingLeft = hearingLeft; }

    public Integer getHearingRight() { return hearingRight; }
    public void setHearingRight(Integer hearingRight) { this.hearingRight = hearingRight; }

    public Integer getSystolicBp() { return systolicBp; }
    public void setSystolicBp(Integer systolicBp) { this.systolicBp = systolicBp; }

    public Integer getDiastolicBp() { return diastolicBp; }
    public void setDiastolicBp(Integer diastolicBp) { this.diastolicBp = diastolicBp; }

    public Integer getFastingGlucose() { return fastingGlucose; }
    public void setFastingGlucose(Integer fastingGlucose) { this.fastingGlucose = fastingGlucose; }

    public Integer getTotalCholesterol() { return totalCholesterol; }
    public void setTotalCholesterol(Integer totalCholesterol) { this.totalCholesterol = totalCholesterol; }

    public Integer getTriglyceride() { return triglyceride; }
    public void setTriglyceride(Integer triglyceride) { this.triglyceride = triglyceride; }

    public Integer getHdlCholesterol() { return hdlCholesterol; }
    public void setHdlCholesterol(Integer hdlCholesterol) { this.hdlCholesterol = hdlCholesterol; }

    public Integer getLdlCholesterol() { return ldlCholesterol; }
    public void setLdlCholesterol(Integer ldlCholesterol) { this.ldlCholesterol = ldlCholesterol; }

    public BigDecimal getHemoglobin() { return hemoglobin; }
    public void setHemoglobin(BigDecimal hemoglobin) { this.hemoglobin = hemoglobin; }

    public Integer getUrineProtein() { return urineProtein; }
    public void setUrineProtein(Integer urineProtein) { this.urineProtein = urineProtein; }

    public BigDecimal getSerumCreatinine() { return serumCreatinine; }
    public void setSerumCreatinine(BigDecimal serumCreatinine) { this.serumCreatinine = serumCreatinine; }

    public Integer getAst() { return ast; }
    public void setAst(Integer ast) { this.ast = ast; }

    public Integer getAlt() { return alt; }
    public void setAlt(Integer alt) { this.alt = alt; }

    public Integer getGammaGtp() { return gammaGtp; }
    public void setGammaGtp(Integer gammaGtp) { this.gammaGtp = gammaGtp; }

    public Integer getSmokingStatus() { return smokingStatus; }
    public void setSmokingStatus(Integer smokingStatus) { this.smokingStatus = smokingStatus; }

    public Integer getDrinkingStatus() { return drinkingStatus; }
    public void setDrinkingStatus(Integer drinkingStatus) { this.drinkingStatus = drinkingStatus; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // Entity ↔ Domain 변환 메서드
    public static HealthCheckupRawEntity fromDomain(HealthCheckupRaw healthCheckupRaw) {
        HealthCheckupRawEntity entity = new HealthCheckupRawEntity();
        entity.rawId = healthCheckupRaw.getRawId();
        entity.referenceYear = healthCheckupRaw.getReferenceYear();
        entity.birthDate = healthCheckupRaw.getBirthDate();
        entity.name = healthCheckupRaw.getName();
        entity.regionCode = healthCheckupRaw.getRegionCode();
        entity.genderCode = healthCheckupRaw.getGenderCode();
        entity.age = healthCheckupRaw.getAge();
        entity.height = healthCheckupRaw.getHeight();
        entity.weight = healthCheckupRaw.getWeight();
        entity.waistCircumference = healthCheckupRaw.getWaistCircumference();
        entity.visualAcuityLeft = healthCheckupRaw.getVisualAcuityLeft();
        entity.visualAcuityRight = healthCheckupRaw.getVisualAcuityRight();
        entity.hearingLeft = healthCheckupRaw.getHearingLeft();
        entity.hearingRight = healthCheckupRaw.getHearingRight();
        entity.systolicBp = healthCheckupRaw.getSystolicBp();
        entity.diastolicBp = healthCheckupRaw.getDiastolicBp();
        entity.fastingGlucose = healthCheckupRaw.getFastingGlucose();
        entity.totalCholesterol = healthCheckupRaw.getTotalCholesterol();
        entity.triglyceride = healthCheckupRaw.getTriglyceride();
        entity.hdlCholesterol = healthCheckupRaw.getHdlCholesterol();
        entity.ldlCholesterol = healthCheckupRaw.getLdlCholesterol();
        entity.hemoglobin = healthCheckupRaw.getHemoglobin();
        entity.urineProtein = healthCheckupRaw.getUrineProtein();
        entity.serumCreatinine = healthCheckupRaw.getSerumCreatinine();
        entity.ast = healthCheckupRaw.getAst();
        entity.alt = healthCheckupRaw.getAlt();
        entity.gammaGtp = healthCheckupRaw.getGammaGtp();
        entity.smokingStatus = healthCheckupRaw.getSmokingStatus();
        entity.drinkingStatus = healthCheckupRaw.getDrinkingStatus();
        entity.createdAt = healthCheckupRaw.getCreatedAt();
        return entity;
    }

    public HealthCheckupRaw toDomain() {
        HealthCheckupRaw healthCheckupRaw = new HealthCheckupRaw();
        healthCheckupRaw.setRawId(this.rawId);
        healthCheckupRaw.setReferenceYear(this.referenceYear);
        healthCheckupRaw.setBirthDate(this.birthDate);
        healthCheckupRaw.setName(this.name);
        healthCheckupRaw.setRegionCode(this.regionCode);
        healthCheckupRaw.setGenderCode(this.genderCode);
        healthCheckupRaw.setAge(this.age);
        healthCheckupRaw.setHeight(this.height);
        healthCheckupRaw.setWeight(this.weight);
        healthCheckupRaw.setWaistCircumference(this.waistCircumference);
        healthCheckupRaw.setVisualAcuityLeft(this.visualAcuityLeft);
        healthCheckupRaw.setVisualAcuityRight(this.visualAcuityRight);
        healthCheckupRaw.setHearingLeft(this.hearingLeft);
        healthCheckupRaw.setHearingRight(this.hearingRight);
        healthCheckupRaw.setSystolicBp(this.systolicBp);
        healthCheckupRaw.setDiastolicBp(this.diastolicBp);
        healthCheckupRaw.setFastingGlucose(this.fastingGlucose);
        healthCheckupRaw.setTotalCholesterol(this.totalCholesterol);
        healthCheckupRaw.setTriglyceride(this.triglyceride);
        healthCheckupRaw.setHdlCholesterol(this.hdlCholesterol);
        healthCheckupRaw.setLdlCholesterol(this.ldlCholesterol);
        healthCheckupRaw.setHemoglobin(this.hemoglobin);
        healthCheckupRaw.setUrineProtein(this.urineProtein);
        healthCheckupRaw.setSerumCreatinine(this.serumCreatinine);
        healthCheckupRaw.setAst(this.ast);
        healthCheckupRaw.setAlt(this.alt);
        healthCheckupRaw.setGammaGtp(this.gammaGtp);
        healthCheckupRaw.setSmokingStatus(this.smokingStatus);
        healthCheckupRaw.setDrinkingStatus(this.drinkingStatus);
        healthCheckupRaw.setCreatedAt(this.createdAt);
        return healthCheckupRaw;
    }
}