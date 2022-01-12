package com.example.employees.model;

import com.example.employees.utils.DateUtil;

import java.time.LocalDate;

public class SoloAssignment {

  private String employeeId;
  private String projectId;
  private LocalDate start;
  private LocalDate end;

  private SoloAssignment() {}

  public static SoloAssignment fromString(String line) {
    String[] components = line.replaceAll("\\s+", "").split(",");

    SoloAssignment result = new SoloAssignment();

    result.employeeId = components[0];
    result.projectId = components[1];
    result.start = DateUtil.parseFrom(components[2]);

    if (components[3].equals("NULL")) {
      result.end = LocalDate.now();
    } else {
      result.end = DateUtil.parseFrom(components[3]);
    }
    return result;
  }

  public String getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(String employeeId) {
    this.employeeId = employeeId;
  }

  public String getProjectId() {
    return projectId;
  }

  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }

  public LocalDate getStart() {
    return start;
  }

  public void setStart(LocalDate start) {
    this.start = start;
  }

  public LocalDate getEnd() {
    return end;
  }

  public void setEnd(LocalDate end) {
    this.end = end;
  }
}
