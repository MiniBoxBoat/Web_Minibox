package util;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Data
@ToString
public class API {
    private String urlHeader;
    private String urlAfter;
    private String methods;
    private Map parameterMap;
    private String response;

    public API(){}
}
