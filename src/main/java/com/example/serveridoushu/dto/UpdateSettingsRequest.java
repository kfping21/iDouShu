package com.example.serveridoushu.dto;

public class UpdateSettingsRequest {
    private Boolean isPrivate;
    private Boolean notificationEnabled;

    // 构造器
    public UpdateSettingsRequest() {}

    // Getters and Setters
    public Boolean getIsPrivate() { return isPrivate; }
    public void setIsPrivate(Boolean isPrivate) { this.isPrivate = isPrivate; }

    public Boolean getNotificationEnabled() { return notificationEnabled; }
    public void setNotificationEnabled(Boolean notificationEnabled) { this.notificationEnabled = notificationEnabled; }
}