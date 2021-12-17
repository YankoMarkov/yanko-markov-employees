package com.example.employees.services;

import com.example.employees.model.PairedAssignment;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface HomeService {

  void readFile(MultipartFile file) throws IOException;

  Map<PairedAssignment, Long> calculatePairedAssignments();
}
