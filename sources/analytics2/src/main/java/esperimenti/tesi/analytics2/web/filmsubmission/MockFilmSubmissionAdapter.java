package esperimenti.tesi.analytics2.web.filmsubmission;

import esperimenti.tesi.analytics2.domain.service.FilmSubmissionService;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class MockFilmSubmissionAdapter implements FilmSubmissionService {
    @Override
    public Date getCreationDate(Integer projectId) {
        Calendar cal = Calendar.getInstance();
        cal.set(2020,06,05);
        return cal.getTime();
    }
}
