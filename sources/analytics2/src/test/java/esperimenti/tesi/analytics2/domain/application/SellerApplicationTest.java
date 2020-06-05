package esperimenti.tesi.analytics2.domain.application;

import esperimenti.tesi.analytics2.domain.model.ProjectAnalytics;
import esperimenti.tesi.analytics2.domain.model.ViewsDetails;
import esperimenti.tesi.analytics2.domain.repository.ProjectRepository;
import esperimenti.tesi.analytics2.domain.service.FilmSubmissionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class SellerApplicationTest {

    // Test per SellerApplication
    // Test solitari

    private static Date creationDate;
    private static final Integer ID_NOT_PRESENT_PROJECT = 0;
    private static final Integer ID_PROJECT_WITH_ZERO_VIEWS = 1;
    private static final Integer ID_PROJECT_WITH_ONE_VIEW = 2;
    private static final Integer ID_PROJECT_WITH_TWO_VIEWS = 3;

    private ViewsDetails oneView;
    LinkedList views = new LinkedList<ViewsDetails>();
    private static final String USER = "user";

    @TestConfiguration
    static class TextConf {

        @Bean
        public SellerApplication sellerApplication() {
            return new SellerApplication();
        }
    }

    @Autowired
    SellerApplication sellerApplication;

    @MockBean
    ProjectRepository projectRepository;

    @MockBean
    FilmSubmissionService filmSubmissionService;

    @Before
    public void setup() {
        Calendar c = Calendar.getInstance();
        c.set(2020,06,05);
        creationDate = c.getTime();

        oneView = ViewsDetails.builder().user(USER).views(1.).build();
        views.clear();
        views.add(oneView);

        Mockito.when(filmSubmissionService.getCreationDate(Mockito.anyInt())).thenReturn(creationDate);

        Mockito.when(projectRepository.getProjectAnalytics(ID_NOT_PRESENT_PROJECT,creationDate)).thenReturn(Collections.emptyList());
        Mockito.when(projectRepository.getProjectAnalytics(ID_PROJECT_WITH_ZERO_VIEWS,creationDate)).thenReturn(Collections.emptyList());
        Mockito.when(projectRepository.getProjectAnalytics(ID_PROJECT_WITH_ONE_VIEW,creationDate)).thenReturn(views);
    }


    @Test
    public void getProjectAnalyticsTets_nonExistantProject(){
        // Il progetto non è presente nel db
        ProjectAnalytics stats = sellerApplication.getProjectAnalytics(ID_NOT_PRESENT_PROJECT);

        assertThat(stats.getViews()).isEmpty();
        assertThat(stats.getTotal()).isEqualTo(0);
    }

    @Test
    public void getProjectAnalyticsTets_ProjectWithZeroViews(){
        // Progetto esistente ma con 0 visualizzazioni
        ProjectAnalytics stats = sellerApplication.getProjectAnalytics(ID_PROJECT_WITH_ZERO_VIEWS);
        assertThat(stats.getViews()).isEmpty();
        assertThat(stats.getTotal()).isEqualTo(0);
    }

    @Test
    public void getProjectAnalyticsTets_ProjectWithOneView(){
        // Progetto esistente e con 1 visualizzazioni
        ProjectAnalytics stats = sellerApplication.getProjectAnalytics(ID_PROJECT_WITH_ONE_VIEW);
        assertThat(stats.getViews().size()).isEqualTo(1);
        assertThat(stats.getViews()).containsExactly(oneView);
        assertThat(stats.getTotal()).isEqualTo(1);
    }

    @Test
    public void getProjectAnalyticsTets_ProjectWithTwoDifferentViews(){
        // Progetto esistente e con 1 visualizzazioni
        views.add(ViewsDetails.builder().user("Davide").views(1.).build());
        Mockito.when(projectRepository.getProjectAnalytics(ID_PROJECT_WITH_TWO_VIEWS,creationDate)).thenReturn(views);

        ProjectAnalytics stats = sellerApplication.getProjectAnalytics(ID_PROJECT_WITH_TWO_VIEWS);
        assertThat(stats.getViews().size()).isEqualTo(2);
        assertThat(stats.getViews().get(0)).isNotEqualTo(stats.getViews().get(1));
        assertThat(stats.getTotal()).isEqualTo(2);
    }



}
