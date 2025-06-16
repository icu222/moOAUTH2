package com.healthsync.user.domain.HealthCheck;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class HealthCheckupRaw {
    private Long rawId;
    private Integer referenceYear;
    private LocalDate birthDate;
    private String name;
    private Integer regionCode;
    private Integer genderCode;
    private Integer age;
    private Integer height;
    private Integer weight;
    private Integer waistCircumference;
    private BigDecimal visualAcuityLeft;
    private BigDecimal visualAcuityRight;
    private Integer hearingLeft;
    private Integer hearingRight;
    private Integer systolicBp;
    private Integer diastolicBp;
    private Integer fastingGlucose;
    private Integer totalCholesterol;
    private Integer triglyceride;
    private Integer hdlCholesterol;
    private Integer ldlCholesterol;
    private BigDecimal hemoglobin;
    private Integer urineProtein;
    private BigDecimal serumCreatinine;
    private Integer ast;
    private Integer alt;
    private Integer gammaGtp;
    private Integer smokingStatus;
    private Integer drinkingStatus;
    private LocalDateTime createdAt;

    public HealthCheckupRaw() {}

    // BMI 계산 메서드
    public BigDecimal calculateBMI() {
        if (height != null && weight != null && height > 0) {
            double heightInM = height / 100.0;
            double bmi = weight / (heightInM * heightInM);
            return BigDecimal.valueOf(bmi).setScale(1, BigDecimal.ROUND_HALF_UP);
        }
        return null;
    }

    // 혈압 문자열 반환 메서드
    public String getBloodPressureString() {
        if (systolicBp != null && diastolicBp != null) {
            return systolicBp + "/" + diastolicBp;
        }
        return null;
    }

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

    // 성별 관련 메서드 추가
    public Gender getGender() {
        return Gender.fromCode(this.genderCode);
    }

    public String getGenderDescription() {
        Gender gender = getGender();
        return gender != null ? gender.getDescription() : "미상";
    }
}