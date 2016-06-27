package selenium.common;

import java.util.Collection;

public interface FailureMonitor {

	void addException(Throwable e);

	Collection<Throwable> getExceptions();

	void flushExceptions();
}
