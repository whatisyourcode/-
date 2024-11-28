### Commits on Nov 14, 2024 
#### First distriubution
1. Log-in,log-out,
2. join membership
3. personal to-do list CRUD for each user


### Commits on Nov 19, 2024
#### Second distribution
1. Correct blank error when modifying todo list
2. Corrects the error with the input tag open when moving from the completed todo list to the ongoing todo list.
3. Modify the login page so that using 'Enter' key.
4. Save user logins using cookies
5. Features screen to fit on mobile


 
### 빌드 전 mysql 설치 및 설정(필수) 
mac : brew install mysql 
        mysql -u root -p 
        create database miniproj1;
        create user 'admin'@'localhost' identified by 'qwer1234';
        grant all privileges on miniproj1.* to 'admin'@'qwer1234';
        flush privleges; 



ubuntu : sudo apt install mysql-server : mysql 설치
            sudo mysql -u root -p
            create database miniproj1;
            create user 'admin'@'localhost' identified by 'qwer1234';
            grant all privileges on miniproj1.* to 'admin'@'qwer1234';
            flush privleges; 

### 빌드 시 사전에 Gradle 설치
mac: brew install gradle
ubuntu: sudo apt-get install gradle
(설치 후 gradle -v 으로 버전 및 설치 확인)


### 빌드 
프로젝트 root 폴더에 진입시 gradle.bat 파일이 있는 위치에서
./gradlew build 명령어

빌드 성공 시 build/libs/ 폴더에서 생성된 jar 파일 확인.
  java -jar   파일.jar 
