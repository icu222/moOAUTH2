package com.healthsync.user.dto;

public class LoginResponse {

    private String accessToken;
    private String refreshToken;
    private UserProfileResponse user;
    private boolean isNewUser;

    public LoginResponse() {}

    public LoginResponse(String accessToken, String refreshToken, UserProfileResponse user, boolean isNewUser) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.user = user;
        this.isNewUser = isNewUser;
    }

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }

    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }

    public UserProfileResponse getUser() { return user; }
    public void setUser(UserProfileResponse user) { this.user = user; }

    public boolean isNewUser() { return isNewUser; }
    public void setNewUser(boolean isNewUser) { this.isNewUser = isNewUser; }
}