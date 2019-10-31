
package jose.coronel.alexa.gadget;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "endpointId",
    "friendlyName",
    "capabilities"
})
public class Endpoint {

    @JsonProperty("endpointId")
    private String endpointId;

    @JsonProperty("friendlyName")
    private String friendlyName;

    @JsonProperty("capabilities")
    private List<Capability> capabilities = null;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("endpointId")
    public String getEndpointId() {
        return endpointId;
    }

    @JsonProperty("endpointId")
    public void setEndpointId(String endpointId) {
        this.endpointId = endpointId;
    }

    public Endpoint withEndpointId(String endpointId) {
        this.endpointId = endpointId;
        return this;
    }

    @JsonProperty("friendlyName")
    public String getFriendlyName() {
        return friendlyName;
    }

    @JsonProperty("friendlyName")
    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public Endpoint withFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
        return this;
    }

    @JsonProperty("capabilities")
    public List<Capability> getCapabilities() {
        return capabilities;
    }

    @JsonProperty("capabilities")
    public void setCapabilities(List<Capability> capabilities) {
        this.capabilities = capabilities;
    }

    public Endpoint withCapabilities(List<Capability> capabilities) {
        this.capabilities = capabilities;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Endpoint withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
