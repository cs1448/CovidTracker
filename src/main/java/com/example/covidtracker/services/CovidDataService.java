package com.example.covidtracker.services;

import com.example.covidtracker.models.Locations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;


import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class CovidDataService {

    private static String COVID_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private List<Locations> statistics = new ArrayList<>();

    public List<Locations> getStatistics(){
        return statistics;
    }

    @PostConstruct
    @Scheduled(cron = "* * * 1 * *")
    public void fetch() throws IOException, InterruptedException {
        List<Locations> updatedStats = new ArrayList<>();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(COVID_DATA_URL)).build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);

        for (CSVRecord record : records) {
            Locations locations = new Locations();

            locations.setCountry(record.get("Country/Region"));

            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int prevDay = Integer.parseInt(record.get(record.size() - 2));

            locations.setDiffFromPrev(latestCases - prevDay);
            locations.setLatestReportedTotal(latestCases);

            if (containsCountry(updatedStats, locations.getCountry())){
                Locations toAddLocation = updateLocations(updatedStats, locations.getCountry(), locations);
                toAddLocation.setLatestReportedTotal(toAddLocation.getLatestReportedTotal() + locations.getLatestReportedTotal());
                toAddLocation.setDiffFromPrev(toAddLocation.getDiffFromPrev() + locations.getDiffFromPrev());
                updatedStats.remove(updateLocations(updatedStats, locations.getCountry(), locations));
                updatedStats.add(toAddLocation);
            }
            else {
                updatedStats.add(locations);
            }

        }
        this.statistics = updatedStats;
    }

    public boolean containsCountry(List<Locations> updatedStats, String name) {
        return updatedStats.stream().anyMatch(o -> o.getCountry().equals(name));
    }

    public Locations updateLocations(List<Locations> updatedStats, String name, Locations toAddLocations){

        return updatedStats.stream().filter(country -> country.getCountry().equals(name)).findAny().orElse(null);
    }

}
