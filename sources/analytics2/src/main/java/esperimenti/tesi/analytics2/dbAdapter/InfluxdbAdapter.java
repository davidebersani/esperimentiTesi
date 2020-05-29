package esperimenti.tesi.analytics2.dbAdapter;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxTable;
import esperimenti.tesi.analytics2.domain.exception.BadResultDimensionsException;
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

    @Value("${analytics.influxdb.db}")
    private String dbName;

    @Override
    public Integer getViewCount() throws BadResultDimensionsException {
        String query = "from(bucket:\"" + dbName + "\") " +
                "|> range(start:-30m) " +
                "|> filter(fn:(r) => r._measurement == \"http_server_requests_seconds_count\" and r.app == \"stub\" and r.uri == \"/view\") " +
                "|> drop(columns:[ \"app\", \"exception\", \"job\", \"kubernetes_namespace\", \"method\", \"outcome\", \"pod_template_hash\", \"status\", \"_start\", \"_stop\", \"__name__\", \"_\n" +
                "field\", \"_measurement\", \"instance\",\"uri\",\"_time\"]) " +
                "|> last() " +
                "|> drop(columns:[\"kubernetes_pod_name\"]) " +
                "|> sum()";


        QueryApi queryApi = influxDBClient.getQueryApi();

        List<FluxTable> tables = queryApi.query(query);
        // Mi aspetto un solo record
        if(tables.size() != 1 || tables.get(0).getRecords().size() != 1)
            throw new BadResultDimensionsException();

        log.debug(tables.get(0).getRecords().get(0).getValue().toString());
        Integer count = Double.valueOf(tables.get(0).getRecords().get(0).getValue().toString()).intValue();

        return count;
    }
}
