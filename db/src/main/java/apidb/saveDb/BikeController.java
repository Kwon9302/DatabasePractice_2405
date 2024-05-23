package apidb.saveDb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class BikeController {

    @Autowired
    private BikeService bikeService;

    @GetMapping("/saveBikes")
    public String saveBikes() {
        try {
            bikeService.fetchAndSaveBikes();
            return "Data saved successfully!";
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    }
}
