package com.palmD.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.palmD.Constant.ScheduleRecurrence;
import com.palmD.DTO.SchedulesAddEdit_dto;
import com.palmD.DTO.SchedulesRespNomal_dto;
import com.palmD.DTO.SchedulesRespReccur_dto;
import com.palmD.Entity.CalGroups;
import com.palmD.Entity.Schedules;
import com.palmD.Entity.Users;
import com.palmD.Repository.CalGroups_repo;
import com.palmD.Repository.Schedules_repo;
import com.palmD.Repository.Users_repo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Schedules_serv {
	
	private final Schedules_repo schedulesRepo;
	private final CalGroups_repo calGroupsRepo;
	private final Users_repo usersRepo;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
	
	public Schedules addSchedule (SchedulesAddEdit_dto schedulesAddEditDto, String userId) {
		Users currentUser = usersRepo.findById(userId).orElseThrow(EntityNotFoundException::new);
		CalGroups selectedGroup = calGroupsRepo.findById(schedulesAddEditDto.getShceGroupId()).orElseThrow(() -> new EntityNotFoundException("그룹을 찾을 수 없음"));
		Schedules schedules = Schedules.createSchedule(schedulesAddEditDto, currentUser, selectedGroup, formatter);	
		return schedulesRepo.save(schedules);
	}
	
	public void editSchedule (SchedulesAddEdit_dto schedulesAddEditDto) {
		Schedules editSchedules = schedulesRepo.findById(schedulesAddEditDto.getShceId()).orElseThrow(() -> new EntityNotFoundException("일정을 찾을 수 없음"));
		CalGroups editGroups = calGroupsRepo.findById(schedulesAddEditDto.getShceGroupId()).orElseThrow(() -> new EntityNotFoundException("그룹을 찾을 수 없음"));
		editSchedules.updateSchedule(schedulesAddEditDto, editGroups, formatter);
	}
	
	public void deleteSchedule (Long scheduleId) {
		Schedules deleteSchedule = schedulesRepo.findById(scheduleId).orElseThrow(() -> new EntityNotFoundException("일정을 찾을 수 없음"));
		schedulesRepo.delete(deleteSchedule);
	}
	
	public List<Object> callAllSchedules (String userId) {
		Users currentUser = usersRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없음"));
		List<Schedules> allSchedules =  schedulesRepo.findByUserId(currentUser);
		List<Object> calledAllEvents = new ArrayList<>();
		for(Schedules sche : allSchedules) {
			if(sche.getRecuFreq() == ScheduleRecurrence.NONE) {
				SchedulesRespNomal_dto normalEvent = SchedulesRespNomal_dto.responseNormalSche(sche);
				calledAllEvents.add(normalEvent);
			} else {
				SchedulesRespReccur_dto reccurEvent = SchedulesRespReccur_dto.responseReccurSche(sche);
				calledAllEvents.add(reccurEvent);
			}
		}
		return calledAllEvents;
	}
}
