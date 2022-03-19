package f1_Info.ergast.responses;

import lombok.Value;

@Value
public class TopResponseData {
    ResponseHeader header;
    String dataString;
}
