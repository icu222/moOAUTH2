package com.healthsync.user.dto.UserProfile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserUpdateRequest {
    private String name;

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
        return LocalDate.parse(birthDate, DateTimeFormatter.ISO_LOCAL_DATE);
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

