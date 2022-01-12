package com.example.employees.web.controllers;

import com.example.employees.services.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class HomeController extends BaseController {

  private static final String INDEX = "index";
  private static final String HOME = "/home";
  private static final String GRID = "grid";
  private static final String NO_DATA = "NoData";

  private final HomeService homeService;

  @Autowired
  public HomeController(HomeService homeService) {
    this.homeService = homeService;
  }

  @GetMapping("/")
  ModelAndView index() {
    return this.view(INDEX);
  }

  @PostMapping("/")
  ModelAndView parseFile(@RequestParam("file") MultipartFile file) throws IOException {
    homeService.readFile(file);
    return this.redirect(HOME);
  }

  @GetMapping(HOME)
  ModelAndView index(ModelAndView modelAndView) {
    var grid = homeService.calculatePairedAssignments();
    if (grid.isEmpty()) {
      modelAndView.addObject(GRID, NO_DATA);
    } else {
      modelAndView.addObject(GRID, grid);
    }
    return this.view(HOME, modelAndView);
  }
}
