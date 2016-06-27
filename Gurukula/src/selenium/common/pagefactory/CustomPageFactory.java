package selenium.common.pagefactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import selenium.common.TestProperties;

public class CustomPageFactory {

	private static final Logger LOGGER = LogManager.getLogger(CustomPageFactory.class);

	public synchronized static <T> T initElements(Class<T> pageClassToProxy) {
		WebDriver driver = TestProperties.getInstance().getDriver();
		T page = instantiatePage(driver, pageClassToProxy);
		initElements(driver, page);
		return page;
	}

	public synchronized static void initElements(Object page) {
		final WebDriver driverRef = TestProperties.getInstance().getDriver();
		initElements(new AjaxElementLocatorFactory(driverRef), page);
	}

	public synchronized static void initElements(WebDriver driver, Object page) {
		final WebDriver driverRef = driver;
		initElements(new AjaxElementLocatorFactory(driverRef), page);
	}

	public synchronized static void initElements(ElementLocatorFactory factory, Object page) {
		final ElementLocatorFactory factoryRef = factory;
		initElements(new DefaultFieldDecorator(factoryRef), page);
	}

	public synchronized static void initElements(FieldDecorator decorator, Object page) {
		Class<?> proxyIn = page.getClass();
		while (proxyIn != Object.class) {
			proxyFields(decorator, page, proxyIn);
			proxyIn = proxyIn.getSuperclass();
		}
	}

	private synchronized static void proxyFields(FieldDecorator decorator, Object page, Class<?> proxyIn) {
		Field[] fields = proxyIn.getDeclaredFields();
		for (Field field : fields) {
			if (field.getAnnotations().length == 0) {
				continue;
			}
			Object value = decorator.decorate(page.getClass().getClassLoader(), field);
			if (value != null) {
				try {
					field.setAccessible(true);
					if ((field.getType() != WebElement.class)) {
						Class<?> objectClass = Class.forName(field.getType().getName());
						Constructor<?> constructor = objectClass.getConstructor(WebElement.class);
						Object newVal = (constructor.newInstance((WebElement) value));

						field.set(page, newVal);
					} else {
						field.set(page, value);
					}
				} catch (Exception e) {
					String declaringClass = field.getDeclaringClass().getSimpleName();
					LOGGER.warn("Unable to initialize {}.{}", declaringClass, field.getName(), e);
				}

			}
		}
	}

	private synchronized static <T> T instantiatePage(WebDriver driver, Class<T> pageClassToProxy) {
		try {
			try {
				Constructor<T> constructor = pageClassToProxy.getConstructor(WebDriver.class);
				return constructor.newInstance(driver);
			} catch (NoSuchMethodException e) {
				return pageClassToProxy.newInstance();
			}
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
}
