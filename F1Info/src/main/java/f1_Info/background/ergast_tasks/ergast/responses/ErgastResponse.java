package f1_Info.background.ergast_tasks.ergast.responses;

import lombok.Value;

import java.util.List;

@Value
public class ErgastResponse<T> {
    ResponseHeader mResponseHeader;
    List<T> mData;
}
