package esperimenti.tesi.analytics2.rest;

import esperimenti.tesi.analytics2.domain.application.StatsAplication;
import esperimenti.tesi.analytics2.domain.model.StatsOfProject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ViewAnalyticsController {

    @Autowired
    StatsAplication statsAplication;

    @GetMapping("/stat")
    public ResponseEntity<StatsOfProject> getViewStat(@RequestParam Integer projectId) {
        return ResponseEntity.ok(statsAplication.getStatsForProject(projectId));
    }
}
