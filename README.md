## Gurukula

#### Approach
* __Design__
 *  Page Object design pattern
 *  Custom elements model in separate class
 *  Data model for test data
 *  JUnit custom runner for parameterization support
 
* __Build Process__
 * ANT to build and execute tests
 * ANT Ivy to manage dependacy jars
 * JUnit test framework

* __Reporting__
 * __HTML__ : HTML report will be generated at `Gurukula\reports\html\index.html`
 * __Console__ : ANT console will show suite/test level summary of execution and result
 * __Log__ : Log file will be generated at the location configured in log4j2.xml

#### How to execute tests
* __Setting Properties__
 * Copy `selenium.properties.template` file from `Gurukula` directory to `src` directory and rename to `selenium.properties` 
 * Update `browser`, `screenShotDir` and `driverPath` in `selenium.properties`. Make sure appropriate driver file in available in `driverPath` directory
 * Optinally update rest of the properties in _selenium.properties_
* __ANT targets__
 * Copy `log4j2.xml.template` to a directory and rename as and `log4j2.xml`. Replace <log4j2 directory> with absolute path of log4j2.xml directory as `file:C:\log4j\log4j.xml`
 * Run following ant command to run _regression_ cases
 ```
 ant clean resolve regression -Dlog4j.xml.path=<log4j2 directory>
 ```
 * Run following ant command to run _smoke_ cases
 ```
 ant clean resolve regression -Dlog4j.xml.path=<log4j2 directory>
 ```
 > To run ant targets behind proxy, please use proxy target with proxy server details as follows
 ```
 ant proxy clean resolve smoke -Dproxy.host=<proxy_host> -Dproxy.port=<proxy_port> -Dlog4j.xml.path=<log4j2 directory>
 ```
 ```
 ant proxy clean resolve regression -Dproxy.host=<proxy_host> -Dproxy.port=<proxy_port> -Dlog4j.xml.path=<log4j2 directory>
 ```
 
#### Features Tested
* Authentication
* Registration
* Branch
* Staff

#### Issues Found
* User registration failure
* Reset password not functional for admin
* Automatic login for user after logout
* Application access post invalidation of session
* Password strength meter reset after clearing new password field
* Pagging issue in Branch page
* Pagging issue in Staff page
