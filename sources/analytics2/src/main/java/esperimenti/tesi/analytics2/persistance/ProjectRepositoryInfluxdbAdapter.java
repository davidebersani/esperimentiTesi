package esperimenti.tesi.analytics2.persistance;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import esperimenti.tesi.analytics2.domain.model.ViewsDetails;
import esperimenti.tesi.analytics2.domain.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ProjectRepositoryInfluxdbAdapter implements ProjectRepository {

    @Autowired
    private InfluxDBClient influxDBClient;

    @Value("${spring.influxdb2.bucket}")
    private String dbName;

    @Value("${analytics2.default-time-interval}")
    private String defaultTimeInterval;

    @Override
    public List<ViewsDetails> getProjectAnalytics(Integer id, Date from) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(from);

        String query =
                "from(bucket:\"" + dbName + "\") " +
                "|> range(start: " + date + ", stop: now()) " +
                "|> filter(fn:(r) => r._measurement== \"views_total\" and r.app==\"stub\" and r.projectId==\"" + id + "\") " +
                "|> last() " +
                "|> group(columns:[\"user\"]) " +
                "|> sum()";

        log.debug("Query eseguita: "  + query);

        QueryApi queryApi = influxDBClient.getQueryApi();

        return queryApi.query(query, ViewsDetails.class);
    }
}
