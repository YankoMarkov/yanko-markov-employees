package com.example.employees.services;

import com.example.employees.adapter.AssignmentToWorkTime;
import com.example.employees.model.PairedAssignment;
import com.example.employees.model.SoloAssignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

@Service
public class HomeServiceImpl implements HomeService {

  private final AssignmentToWorkTime adapter;

  @Autowired
  public HomeServiceImpl(AssignmentToWorkTime adapter) {
    this.adapter = adapter;
  }

  @Override
  public void readFile(MultipartFile file) {
    adapter.reset();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
      String line;
      while ((line = reader.readLine()) != null) {
        SoloAssignment nextAssignment = SoloAssignment.fromString(line);
        adapter.addAssignment(nextAssignment);
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }

  @Override
  public Map<PairedAssignment, Long> calculatePairedAssignments() {
    return adapter.calculatePairedAssignments();
  }
}
