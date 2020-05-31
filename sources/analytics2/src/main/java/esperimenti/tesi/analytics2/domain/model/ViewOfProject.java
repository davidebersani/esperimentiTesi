package esperimenti.tesi.analytics2.domain.model;

import com.influxdb.annotations.Column;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViewOfProject {
    @Column(name="user")
    private String user;
    @Column(name="_value")
    private Double views;
}
