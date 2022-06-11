package com.example.covidtracker.controllers;

import com.example.covidtracker.models.Locations;
import com.example.covidtracker.services.CovidDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PrimaryController {

    @Autowired
    CovidDataService covidDataService;

    @GetMapping("/")
    public String primary(Model model) {
        List<Locations> globalTotal = covidDataService.getStatistics();
        int totalReportedCases = globalTotal.stream().mapToInt(stat -> stat.getLatestReportedTotal()).sum();
        int totalNewCases = globalTotal.stream().mapToInt(stat -> stat.getDiffFromPrev()).sum();
        model.addAttribute("locationStats", globalTotal);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);



        return "primary";
    }
}
