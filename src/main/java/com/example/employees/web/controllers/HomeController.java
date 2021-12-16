package com.example.employees.web.controllers;

import com.example.employees.adapter.AssignmentToWorkTime;
import com.example.employees.model.SoloAssignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Controller
public class HomeController extends BaseController {

  private static final String INDEX = "index";
  private static final String HOME = "/home";
  private static final String GRID = "grid";

  private final AssignmentToWorkTime adapter;

  @Autowired
  public HomeController(AssignmentToWorkTime adapter) {
    this.adapter = adapter;
  }

  @GetMapping("/")
  ModelAndView index() {
    return this.view(INDEX);
  }

  @PostMapping("/")
  ModelAndView parseFile(@RequestParam("file") MultipartFile file) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
    String line = reader.readLine();
    while (line != null) {
      SoloAssignment nextAssignment = SoloAssignment.fromString(line);
      adapter.addAssignment(nextAssignment);
      line = reader.readLine();
    }
    return this.redirect(HOME);
  }

  @GetMapping("/home")
  ModelAndView index(ModelAndView modelAndView) {
    var grid = adapter.calculatePairedAssignments();
    if (grid.isEmpty()) {
      modelAndView.addObject(GRID, "NoData");
    } else {
      modelAndView.addObject(GRID, grid);
    }
    return this.view(HOME, modelAndView);
  }
}
