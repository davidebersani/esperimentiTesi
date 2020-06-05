package esperimenti.tesi.analytics2.domain.application;

import esperimenti.tesi.analytics2.domain.model.ProjectAnalytics;
import esperimenti.tesi.analytics2.domain.repository.ProjectRepository;
import esperimenti.tesi.analytics2.domain.service.FilmSubmissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class SellerApplication {

    @Autowired
    ProjectRepository repo;

    @Autowired
    FilmSubmissionService filmSubmissionService;

    /**
     * UC: Calcolo analytics di un progetto
     *
     * @param projectId Id del progetto
     * @return Analytics del progetto: visualizzazioni per utente e conteggio totale
     */
    public ProjectAnalytics getProjectAnalytics(Integer projectId) {
        ProjectAnalytics stats = new ProjectAnalytics();
        Date creationDate = filmSubmissionService.getCreationDate(projectId);
        stats.setViews(repo.getProjectAnalytics(projectId,creationDate));
        stats.setTotal(stats.getViews().stream().mapToInt(value -> value.getViews().intValue()).sum());

        return stats;
    }
}
