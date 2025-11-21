import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RateLimitInfo {
    private int requestCount;
    private long windowStart;
}