package f1_Info.configuration.web.interceptors;

import io.github.bucket4j.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class RateLimitInterceptor implements HandlerInterceptor {
    private static final int MAX_REQUESTS_PER_MINUTE = 60;
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        final String ipAddress = InterceptorUtil.getIp(request);
        final Bucket tokenBucket = resolveBucket(ipAddress);

        ConsumptionProbe probe = tokenBucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            return true;
        }
        response.sendError(HttpStatus.TOO_MANY_REQUESTS.value());
        return false;
    }


    public Bucket resolveBucket(String ipAddress) {
        return cache.computeIfAbsent(ipAddress, this::newBucket);
    }

    private Bucket newBucket(String ipAddress) {
        return Bucket.builder()
            .addLimit(Bandwidth.classic(MAX_REQUESTS_PER_MINUTE, Refill.intervally(MAX_REQUESTS_PER_MINUTE, Duration.ofMinutes(1))))
            .build();
    }
}
