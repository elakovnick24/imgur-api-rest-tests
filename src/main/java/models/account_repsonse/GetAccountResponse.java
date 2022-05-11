package models.account_repsonse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonPropertyOrder({
        "data",
        "success",
        "status"
})

@NoArgsConstructor
@Data
public class GetAccountResponse {
    @JsonProperty("data")
    private AccountData data;
    @JsonProperty("success")
    private Boolean success;
    @JsonProperty("status")
    private Integer status;
}
