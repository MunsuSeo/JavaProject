
-- ���� ����
CREATE USER lifebook --ID
IDENTIFIED BY 1234 --PASSWORD
DEFAULT TABLESPACE lifebook --�⺻ ���̺� �����̽�
TEMPORARY TABLESPACE TEMP -- �ӽ� ���̺� �����̽�
QUOTA UNLIMITED ON lifebook; -- 

-- ���̺� �����̽� ����
create tablespace lifebook
datafile 'D:\java\pp\db\lifebook_tablespace.dbf'
size 10M reuse
autoextend on next 5M
maxsize unlimited;

--���� ���� ���� �ο�
GRANT CREATE SESSION TO lifebook;

--���̺� ���� ���� �ο�
GRANT CREATE TABLE TO lifebook;
