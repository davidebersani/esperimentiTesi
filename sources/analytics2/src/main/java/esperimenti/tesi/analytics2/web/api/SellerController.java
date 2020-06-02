package esperimenti.tesi.analytics2.web.api;

import esperimenti.tesi.analytics2.domain.application.SellerApplication;
import esperimenti.tesi.analytics2.domain.model.ProjectAnalytics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SellerController {

    @Autowired
    SellerApplication statsAplication;

    @GetMapping("/stat")
    public ResponseEntity<ProjectAnalytics> getViewStat(@RequestParam Integer projectId) {
        return ResponseEntity.ok(statsAplication.getAnalyticsOfProject(projectId));
    }
}
