package selenium.common.pagefactory;

import java.lang.reflect.Field;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

public class AjaxElementLocatorFactory implements ElementLocatorFactory {
	private final WebDriver driver;

	public AjaxElementLocatorFactory(WebDriver driver) {
		this.driver = driver;
	}

	@Override
	public ElementLocator createLocator(Field field) {
		return new AjaxElementLocator(driver, field);
	}
}
