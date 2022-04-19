---------------------------------------------
--System 계정에서 실행되어야하는 스크립트입니다.--
---------------------------------------------


-- 테이블 스페이스 생성
create tablespace lifebook
datafile 'D:\java\pp\db\lifebook_tablespace.dbf'
size 10M reuse
autoextend on next 5M
maxsize unlimited;

-- 계정 생성
CREATE USER lifebook --ID
IDENTIFIED BY 1234 --PASSWORD
DEFAULT TABLESPACE lifebook --기본 테이블 스페이스
TEMPORARY TABLESPACE TEMP -- 임시 테이블 스페이스
QUOTA UNLIMITED ON lifebook; -- 

--세션 생성 권한 부여
GRANT CREATE SESSION TO lifebook;

--테이블 생성 권한 부여
GRANT CREATE TABLE TO lifebook;
