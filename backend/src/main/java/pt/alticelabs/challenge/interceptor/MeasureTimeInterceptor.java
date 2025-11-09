package pt.alticelabs.challenge.interceptor;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import pt.alticelabs.challenge.service.LabseqService;

@MeasureTime
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class MeasureTimeInterceptor {

    private static final Logger LOG = Logger.getLogger(MeasureTimeInterceptor.class);

    @ConfigProperty(name = "measuretime.logging.enabled", defaultValue = "true")
    boolean loggingEnabled;

    @Inject
    LabseqService labseqService;

    @AroundInvoke
    public Object measureExecutionTime(InvocationContext ctx) throws Exception {
        if (!loggingEnabled) {
            return ctx.proceed();
        }

        if (ctx.getMethod().getName().equals("computeLabseq") && ctx.getParameters().length == 1) {
            Object param = ctx.getParameters()[0];
            if (param instanceof Long n) {
                boolean fromCache = labseqService.isCached(n);
                long start = System.nanoTime();
                try {
                    return ctx.proceed();
                } finally {
                    long end = System.nanoTime();
                    long durationMs = (end - start) / 1_000_000;
                    String source = fromCache ? "cache" : "computation";
                    LOG.infof("Labseq(%d) executed in %d ms [source: %s]", n, durationMs, source);
                }
            }
        }

        long start = System.nanoTime();
        try {
            return ctx.proceed();
        } finally {
            long end = System.nanoTime();
            LOG.infof("%s executed in %d ms", ctx.getMethod().getName(), (end - start) / 1_000_000);
        }
    }
}