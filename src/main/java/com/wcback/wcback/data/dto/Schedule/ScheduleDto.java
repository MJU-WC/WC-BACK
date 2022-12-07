package com.wcback.wcback.data.dto.Schedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

public class ScheduleDto {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ScheduleRegisterDto {
        int num;
        String ScheduleName;
        String Id;
        Date Appointment;
        float lon;
        float lat;
        String Weather;
        String address;
    }
}
