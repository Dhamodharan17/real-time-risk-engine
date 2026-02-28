package com.riskengine.model;

import lombok.NoArgsConstructor;


 public class RiskResult {
    private String userId;
    private String riskLevel;
    private String reason;

    public RiskResult(){}

     public String getUserId() {
         return userId;
     }

     public void setUserId(String userId) {
         this.userId = userId;
     }

     public String getRiskLevel() {
         return riskLevel;
     }

     public void setRiskLevel(String riskLevel) {
         this.riskLevel = riskLevel;
     }

     public String getReason() {
         return reason;
     }

     public void setReason(String reason) {
         this.reason = reason;
     }

     public RiskResult(String userId, String riskLevel, String reason) {
        this.userId = userId;
        this.riskLevel = riskLevel;
        this.reason = reason;
    }
}