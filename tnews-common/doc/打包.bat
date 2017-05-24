@echo off
SETLOCAL ENABLEDELAYEDEXPANSION
:::::::::::::::::::::::::::::::::::::::::::::::::::::::需要修改部分开始...
::项目路径
::工作目录
set WORK_DIR=D:\workspace\idea\tnews\
::项目名前缀如：edu-service-shop则为edu-
set DIR_PREFIX=tnews-
::项目名提取部分，如：edu-service-shop,则为shop
set DIR_LIST=(member dictionary news)
::打包输出目录，注意！！！！！！！！！！！！！必须是空目录，否则会被删除
set OUTPUT_DIR=D:\workspace\bat
::接口项目名
set WEB_PROJECT=tnews-web
::管理后台项目名
set ADMIN_PROJECT=tnews-manager
set VERSION=1.0.0-SNAPSHOT
:::::::::::::::::::::::::::::::::::::::::::::::::::::::需要修改部分结束...

::项目名前缀
set DIR_PREFIX_SERVICE=%DIR_PREFIX%service-
set DIR_PREFIX_INTERFACE=%DIR_PREFIX%interface-
::接口项目路径
set WEB_PROJECT_PATH=%WORK_DIR%%WEB_PROJECT%
::管理后台项目路径
set ADMIN_PROJECT_PATH=%WORK_DIR%%ADMIN_PROJECT%

set SHELL_FILE_NAME_LIST=(start stop)

echo 是否执行打包？打包将会删除输出目录[%OUTPUT_DIR%]
set /p choice=输入y确认，n取消:


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



::创建start shell文件
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