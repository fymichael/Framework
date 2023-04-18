jar -cf framework.jar -C .\Framework\build\web\WEB-INF\classes\ etu1998
jar -cvf test_framework.war -C .\Test-Framework\build\web\ .
copy test_framework.war C:\MyWebServer\apache-tomcat-10.0.20\webapps\
xcopy framework.jar D:\ITU\L2\S4\Web_Dynamique\FrameworkProject\Test-Framework\build\web\WEB-INF\lib\