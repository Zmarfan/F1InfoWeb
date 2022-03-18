package f1_Info.ergast.responses;

import lombok.Value;

@Value
public class ErgastResponseData {
    ErgastResponseHeader header;
    String data;
}
