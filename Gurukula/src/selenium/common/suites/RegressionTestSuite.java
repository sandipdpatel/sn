package selenium.common.suites;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Categories.class)
@Categories.IncludeCategory(RegressionTest.class)
@Suite.SuiteClasses({ AllTests.class })
public class RegressionTestSuite {

}
