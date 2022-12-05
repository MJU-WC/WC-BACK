package com.wcback.wcback.service;

import com.wcback.wcback.data.entity.Schedule;
import com.wcback.wcback.data.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    public void create(String ID, String scheduleName, Date date, float lon, float lat) {
        Schedule schedule = new Schedule();
        if (isEmail(ID)) schedule.setEmail(ID);
        else schedule.setGroupID(ID);
        schedule.setScheduleName(scheduleName);
        schedule.setAppointment(date);
        schedule.setLon(lon);
        schedule.setLat(lat);
        scheduleRepository.save(schedule);
        callWeather(ID, date, lon, lat);
    }

    private void callWeather(String ID, Date date, float lon, float lat) {

        //call Python
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("/home/capstone/capstone_python/test.sh");
        try {
            // Run script
            Process process = processBuilder.start();

            // Read output
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }

            System.out.println(output.toString());

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public List<Schedule> getAllSchedules(String ID) {
        if(isEmail(ID)) return scheduleRepository.findByEmail(ID);
        else return scheduleRepository.findByGroupID(ID);
    }

    public Schedule getSchedule(int num) {
        return scheduleRepository.findByNum(num);
    }

    public void deleteSchedule(int num) {
        scheduleRepository.deleteScheduleByNum(num);
    }

    public void modifySchedule(int num, String scheduleName, Date date, float lon, float lat) {
        Schedule schedule = scheduleRepository.findByNum(num);
        schedule.setScheduleName(scheduleName);
        schedule.setAppointment(date);
        schedule.setLon(lon);
        schedule.setLat(lat);
        scheduleRepository.save(schedule);
    }

    private boolean isEmail(String ID) {
        if (ID.contains("@")) return true;
        else return false;
    }
}
