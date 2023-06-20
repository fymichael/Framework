jar -cf framework.jar -C .\Framework\build\web\WEB-INF\classes\ etu1998
mkdir D:\ITU\L2\S4\Web_Dynamique\FrameworkProject\temporary
mkdir D:\ITU\L2\S4\Web_Dynamique\FrameworkProject\temporary\WEB-INF
mkdir D:\ITU\L2\S4\Web_Dynamique\FrameworkProject\temporary\WEB-INF\lib
mkdir D:\ITU\L2\S4\Web_Dynamique\FrameworkProject\temporary\WEB-INF\classes
xcopy framework.jar D:\ITU\L2\S4\Web_Dynamique\FrameworkProject\temporary\WEB-INF\lib\
xcopy /E D:\ITU\L2\S4\Web_Dynamique\FrameworkProject\Test-Framework\build\web\WEB-INF\classes D:\ITU\L2\S4\Web_Dynamique\FrameworkProject\temporary\WEB-INF\classes\
xcopy D:\ITU\L2\S4\Web_Dynamique\FrameworkProject\Test-Framework\web\WEB-INF\web.xml D:\ITU\L2\S4\Web_Dynamique\FrameworkProject\temporary\WEB-INF\
cd D:\ITU\L2\S4\Web_Dynamique\FrameworkProject\Test-Framework\web\
xcopy *.jsp D:\ITU\L2\S4\Web_Dynamique\FrameworkProject\temporary\ /s /i
jar -cvf test_framework.war -C D:\ITU\L2\S4\Web_Dynamique\FrameworkProject\temporary .
xcopy test_framework.war C:\MyWebServer\apache-tomcat-10.0.20\webapps\
rmdir /s D:\ITU\L2\S4\Web_Dynamique\FrameworkProject\temporary
cd ../../