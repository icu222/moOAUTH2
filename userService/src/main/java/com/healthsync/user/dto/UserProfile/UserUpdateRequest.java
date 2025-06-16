package com.healthsync.user.dto.UserProfile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserUpdateRequest {

    @NotBlank(message = "이름은 필수입니다")
    private String name;

    @NotBlank(message = "생년월일은 필수입니다")
    private String birthDate;

    private String occupation;

    public UserUpdateRequest() {
    }

    public UserUpdateRequest(String name, String birthDate, String occupation) {
        this.name = name;
        this.birthDate = birthDate;
        this.occupation = occupation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        if (birthDate == null || birthDate.trim().isEmpty()) {
            return null;
        }
        return LocalDate.parse(birthDate, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public String getBirthDateString() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
}