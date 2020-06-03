package esperimenti.tesi.analytics2.domain.application;

import esperimenti.tesi.analytics2.domain.model.ProjectAnalytics;
import esperimenti.tesi.analytics2.domain.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SellerApplication {

    @Autowired
    ProjectRepository repo;

    /**
     * UC: Calcolo analytics di un progetto
     *
     * @param projectId Id del progetto
     * @return Analytics del progetto: visualizzazioni per utente e conteggio totale
     */
    public ProjectAnalytics getAnalyticsOfProject(Integer projectId) {
        ProjectAnalytics stats = new ProjectAnalytics();
        stats.setViews(repo.getProjectAnalytics(projectId));
        stats.setTotal(stats.getViews().stream().mapToInt(value -> value.getViews().intValue()).sum());

        return stats;
    }
}
