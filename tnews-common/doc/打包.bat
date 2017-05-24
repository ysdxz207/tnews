@echo off
SETLOCAL ENABLEDELAYEDEXPANSION
:::::::::::::::::::::::::::::::::::::::::::::::::::::::��Ҫ�޸Ĳ��ֿ�ʼ...
::��Ŀ·��
::����Ŀ¼
set WORK_DIR=D:\workspace\idea\tnews\
::��Ŀ��ǰ׺�磺edu-service-shop��Ϊedu-
set DIR_PREFIX=tnews-
::��Ŀ����ȡ���֣��磺edu-service-shop,��Ϊshop
set DIR_LIST=(member dictionary news)
::������Ŀ¼��ע�⣡�����������������������������ǿ�Ŀ¼������ᱻɾ��
set OUTPUT_DIR=D:\workspace\bat
::�ӿ���Ŀ��
set WEB_PROJECT=tnews-web
::�����̨��Ŀ��
set ADMIN_PROJECT=tnews-manager
set VERSION=1.0.0-SNAPSHOT
:::::::::::::::::::::::::::::::::::::::::::::::::::::::��Ҫ�޸Ĳ��ֽ���...

::��Ŀ��ǰ׺
set DIR_PREFIX_SERVICE=%DIR_PREFIX%service-
set DIR_PREFIX_INTERFACE=%DIR_PREFIX%interface-
::�ӿ���Ŀ·��
set WEB_PROJECT_PATH=%WORK_DIR%%WEB_PROJECT%
::�����̨��Ŀ·��
set ADMIN_PROJECT_PATH=%WORK_DIR%%ADMIN_PROJECT%

set SHELL_FILE_NAME_LIST=(start stop)

echo �Ƿ�ִ�д�����������ɾ�����Ŀ¼[%OUTPUT_DIR%]
set /p choice=����yȷ�ϣ�nȡ��:


if /i %choice% NEQ y goto end


rd /s /q %OUTPUT_DIR%

d:
cd %WORK_DIR%%DIR_PREFIX%parent
call mvn clean package
call mvn install


cd %WORK_DIR%%DIR_PREFIX%common
call mvn clean package
call mvn install



for %%i in %DIR_LIST% do (
	set dir_interface=%WORK_DIR%%DIR_PREFIX_INTERFACE%%%i
	set output_interface=%OUTPUT_DIR%\%%i\
	cd !dir_interface!
	call mvn clean package
	call mvn install
	::XCOPY !dir_interface!\target\*.jar !output_interface! /y
	::XCOPY !dir_interface!\target\lib\* !output_interface!lib\ /y
	
	set dir_service=%WORK_DIR%%DIR_PREFIX_SERVICE%%%i
	set output_service=%OUTPUT_DIR%\%%i\
	cd !dir_service!
	call mvn clean package
	call mvn install
	XCOPY !dir_service!\target\*.jar !output_service! /y
	XCOPY !dir_service!\target\lib\* !output_service!lib\ /y
	
	
	call:create_shell !output_service! %%i
)



cd %WEB_PROJECT_PATH%
call mvn clean package
XCOPY %WEB_PROJECT_PATH%\target\*.war %OUTPUT_DIR% /y

cd %ADMIN_PROJECT_PATH%
call mvn clean package
XCOPY %ADMIN_PROJECT_PATH%\target\*.war %OUTPUT_DIR% /y

start !OUTPUT_DIR!



::����start shell�ļ�
:create_shell
for %%j in %SHELL_FILE_NAME_LIST% do (
	if "%%j" equ "start" (
		echo aaa>>aa.txt
		set output_dir_temp=%1
		set pid=%2
		set filepath=!output_dir_temp!%%j.sh
		
		if "!filepath!" neq "%%j.sh" (
			echo #^^!/bin/sh> !filepath!
			echo java -jar tnews-service-!pid!-%VERSION%.jar ^&>> !filepath!
			echo echo $^^! ^> /var/run/tnews-service-!pid!-%VERSION%.pid>> !filepath!
		)
	) else (
		if "%%j" equ "stop" (
			set output_dir_temp=%1
			set pid=%2
			set filepath=!output_dir_temp!%%j.sh
			echo #^^!/bin/sh> !filepath!
			echo PID^^=$^(cat /var/run/tnews-service-!pid!-%VERSION%.pid^)>> !filepath!
			echo kill -9 $PID>> !filepath!
		)
	)
)

goto:eof

ENDLOCAL

pause

:end