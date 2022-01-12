package com.example.employees.adapter;

import com.example.employees.model.PairedAssignment;
import com.example.employees.model.SoloAssignment;
import com.example.employees.utils.DateUtil;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssignmentToWorkTime {

  private Map<String, Map<String, List<SoloAssignment>>> projectToEmployeeToAssignments;

  public void reset() {
    projectToEmployeeToAssignments = new HashMap<>();
  }

  public void addAssignment(SoloAssignment newAssignment) {
    Map<String, List<SoloAssignment>> employeeWorkHours;
    if (projectToEmployeeToAssignments.containsKey(newAssignment.getProjectId())) {
      employeeWorkHours = projectToEmployeeToAssignments.get(newAssignment.getProjectId());
    } else {
      employeeWorkHours = new HashMap<>();
      projectToEmployeeToAssignments.put(newAssignment.getProjectId(), employeeWorkHours);
    }

    List<SoloAssignment> workDays;
    if (employeeWorkHours.containsKey(newAssignment.getEmployeeId())) {
      workDays = employeeWorkHours.get(newAssignment.getEmployeeId());
    } else {
      workDays = new ArrayList<>();
      employeeWorkHours.put(newAssignment.getEmployeeId(), workDays);
    }
    workDays.add(newAssignment);
  }

  public Map<PairedAssignment, Long> calculatePairedAssignments() {
    Map<PairedAssignment, Long> result = new HashMap<>();
    projectToEmployeeToAssignments.forEach(
        (String projectId, Map<String, List<SoloAssignment>> employeeWorkHours) -> {
          employeeWorkHours.forEach(
              (String firstEmployee, List<SoloAssignment> firstWorkDays) -> {
                firstWorkDays.forEach(
                    (SoloAssignment firstAssignment) -> {
                      employeeWorkHours.entrySet().stream()
                          .filter(
                              (Map.Entry<String, List<SoloAssignment>> candidate) ->
                                  !candidate.getKey().equals(firstEmployee))
                          .forEach(
                              (Map.Entry<String, List<SoloAssignment>> candidate) -> {
                                String candidateEmployee = candidate.getKey();
                                Long totalOverlapDays =
                                    candidate.getValue().stream()
                                        .map(
                                            (SoloAssignment candidateAssignment) -> {
                                              LocalDate latestStart =
                                                  DateUtil.latest(
                                                      firstAssignment.getStart(),
                                                      candidateAssignment.getStart());
                                              LocalDate earliestEnd =
                                                  DateUtil.earliest(
                                                      firstAssignment.getEnd(),
                                                      candidateAssignment.getEnd());
                                              return ChronoUnit.DAYS.between(
                                                  latestStart, earliestEnd);
                                            })
                                        .filter(i -> i > 0) // only positive
                                        .reduce(Long::sum)
                                        .orElse(0L);
                                if (totalOverlapDays > 0) {
                                  PairedAssignment pairedAssignment =
                                      PairedAssignment.createWithOrderIds(
                                          firstEmployee, candidateEmployee, projectId);
                                  result.put(pairedAssignment, totalOverlapDays);
                                }
                              });
                    });
              });
        });
    return result;
  }
}
