package online.epochsolutions.mafaro.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Host extends BaseUser{

    @Id
    private String id;

}
