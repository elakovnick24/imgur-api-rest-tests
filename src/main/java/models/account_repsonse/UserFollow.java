package models.account_repsonse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserFollow {
    @JsonProperty("status")
    private Boolean status;
}
