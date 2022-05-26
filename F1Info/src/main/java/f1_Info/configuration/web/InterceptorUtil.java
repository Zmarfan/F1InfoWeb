package f1_Info.configuration.web;

import lombok.experimental.UtilityClass;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@UtilityClass
public class InterceptorUtil {
    private static final List<String> VALID_IP_HEADER_CANDIDATES = List.of(
        "X-Forwarded-For",
        "Proxy-Client-IP",
        "WL-Proxy-Client-IP",
        "HTTP_X_FORWARDED_FOR",
        "HTTP_X_FORWARDED",
        "HTTP_X_CLUSTER_CLIENT_IP",
        "HTTP_CLIENT_IP",
        "HTTP_FORWARDED_FOR",
        "HTTP_FORWARDED",
        "HTTP_VIA",
        "REMOTE_ADDR"
    );

    public static String getIp(final HttpServletRequest request) {
        for (String header : VALID_IP_HEADER_CANDIDATES) {
            final String ipAddress = request.getHeader(header);
            if (ipAddress != null && ipAddress.length() != 0 && !"unknown".equalsIgnoreCase(ipAddress)) {
                return ipAddress;
            }
        }
        return request.getRemoteAddr();
    }
}