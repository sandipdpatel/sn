package selenium.common.pagefactory;

import java.lang.reflect.Field;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

public final class DefaultElementLocatorFactory implements ElementLocatorFactory {
	private final SearchContext searchContext;

	public DefaultElementLocatorFactory(SearchContext searchContext) {
		this.searchContext = searchContext;
	}

	@Override
	public ElementLocator createLocator(Field field) {
		return new DefaultElementLocator(searchContext, field);
	}
}
