package com.wcback.wcback.controller;

import com.wcback.wcback.data.dto.Group.GroupDto;
import com.wcback.wcback.data.dto.Schedule.ScheduleDto;
import com.wcback.wcback.data.entity.Schedule;
import com.wcback.wcback.exception.user.AlreadyExistException;
import com.wcback.wcback.service.GroupService;
import com.wcback.wcback.service.ScheduleService;
import com.wcback.wcback.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/calander")
@AllArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final UserService userService;
    private final GroupService groupService;
    private final GroupController groupController;

    //ID로(Email / GroupID 무관) 해당 ID의 전체 일정 조회
    @Transactional
    @PostMapping("/getAllSchedule")
    public ResponseEntity<Object> getAllSchedule(@RequestBody String ID) {
        return ResponseEntity.ok().body(scheduleService.getAllSchedules(ID));
    }

    //일정 num으로 일정 상세 조회
    @Transactional
    @GetMapping("/getSchedule")
    public ResponseEntity<Object> getSchedule(@RequestParam int scheduleNum) {
        return ResponseEntity.ok().body(scheduleService.getSchedule(scheduleNum));
    }

    //일정 생성
    @Transactional
    @PostMapping("/createSchedule")
    public ResponseEntity<Object> createSchedule(@RequestBody ScheduleDto.ScheduleRegisterDto scheduleRegisterDto) {
        String id = scheduleRegisterDto.getId();
        String scheduleName = scheduleRegisterDto.getScheduleName();
        Date appointment = scheduleRegisterDto.getAppointment();
        float lon = scheduleRegisterDto.getLon();
        float lat = scheduleRegisterDto.getLat();
        scheduleService.create(id, scheduleName, appointment, lon, lat);
        return ResponseEntity.ok().body("일정 생성 완료");
    }

    //일정 삭제
    @Transactional
    @DeleteMapping("/deleteSchedule")
    public ResponseEntity<Object> deleteSchedule(@RequestParam int scheduleNum) {
        scheduleService.deleteSchedule(scheduleNum);
        return ResponseEntity.ok().body("일정 삭제 완료");
    }

    //일정 수정
    @Transactional
    @PatchMapping("/modifySchedule")
    public ResponseEntity<Object> modifySchedule(@RequestBody ScheduleDto.ScheduleRegisterDto scheduleDto) {
        int num = scheduleDto.getNum();
        String scheduleName = scheduleDto.getScheduleName();
        Date appointment = scheduleDto.getAppointment();
        float lon = scheduleDto.getLon();
        float lat = scheduleDto.getLat();
        scheduleService.modifySchedule(num, scheduleName, appointment, lon, lat);
        return ResponseEntity.ok().body("일정 수정 완료");
    }
    @Transactional
    @PostMapping("/getUserGroupSchedule")
    public ResponseEntity<Object> getUserGroupSchedule(@RequestBody String email) {
        List<Schedule> userSchedule = scheduleService.getAllSchedules(email);
        List<Schedule> groupSchedules = new ArrayList<>();
        List<String> gruopids = groupService.findGroupsContainUser(email);
        for (String gruopid : gruopids) {
            List<Schedule> groupSchedule = scheduleService.getAllSchedules(gruopid);
            groupSchedules.addAll(groupSchedule);
        }
        userSchedule.addAll(groupSchedules);
        return ResponseEntity.ok().body(userSchedule);
    }

}
