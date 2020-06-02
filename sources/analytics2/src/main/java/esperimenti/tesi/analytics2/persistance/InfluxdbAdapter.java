package esperimenti.tesi.analytics2.dbAdapter;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import esperimenti.tesi.analytics2.domain.model.ViewOfProject;
import esperimenti.tesi.analytics2.domain.service.DbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class InfluxdbAdapter implements DbService {

    @Autowired
    private InfluxDBClient influxDBClient;

    @Value("${spring.influxdb2.bucket}")
    private String dbName;

    @Value("${analytics2.default-time-interval")
    private String defaultTimeInterval;

    @Override
    public List<ViewOfProject> getViewOfProject(Integer id) {
        String query =
                "from(bucket:\"" + dbName + "\") " +
                "|> range(start: -" + defaultTimeInterval + ") " +
                "|> filter(fn:(r) => r._measurement== \"views_total\" and r.app==\"stub\" and r.projectId==\"" + id + "\") " +
                "|> last() " +
                "|> group(columns:[\"user\"]) " +
                "|> sum()";

        QueryApi queryApi = influxDBClient.getQueryApi();

        return queryApi.query(query, ViewOfProject.class);
    }
}
