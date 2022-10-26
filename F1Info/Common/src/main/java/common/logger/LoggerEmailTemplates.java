package common.logger;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LoggerEmailTemplates {
    public static final String BASE = """
        <article style="font-family: Roboto, 'Helvetica Neue', sans-serif">
            <h3 style="margin: 0; background: #c6adf6; padding: 2rem; color: white; border-bottom: rebeccapurple 2px dotted">System Alert: SEVERE</h3>

            <div style="margin: 1rem">%d exceptions in the last minute.</div>
            <br>
            <div style="margin: 1rem">View log files for stacktrace</div>

            <section>%s</section>
        </article>
    """;

    public static final String ENTRY = """
        <article style="padding: 1rem; font-size: 0.75rem">
            <h5 style="margin: 0; background: #c9c9c9; border-bottom: #6b6b6b 2px solid; padding: 1rem; font-size: 0.8rem">Error no. %d</h5>
            <br>
            <span><strong>Function: </strong>%s</span>
            <br>
            <span><strong>Location: </strong>%s</span>
            <br>
            <span><strong>Message: </strong>%s</span>
            <br>
            <span><strong>Exception: </strong>%s</span>
            <br>
            <details style="cursor: pointer">
                <summary><strong>StackTrace:</strong></summary>
                <span>%s</span>
            </details>
        </article>
        <br>
    """;
}
