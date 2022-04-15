--회원정보 테이블
CREATE TABLE userinfo (
    user_id    VARCHAR2(15) ,
    user_pwd   VARCHAR2(15) NOT NULL,
    user_tel  NUMBER(11),
    user_phonenumber NUMBER(11),
    user_address     VARCHAR2(100),
    user_birthdate   DATE,
    user_mileage   NUMBER(7) DEFAULT 0,
    adminYN NUMBER(1) DEFAULT 0
);
--PK추가
ALTER TABLE userinfo ADD CONSTRAINT userinfo_userid_pk PRIMARY KEY (user_id);

--공지사항 게시판 테이블
CREATE TABLE notice (
    n_title       VARCHAR2(50) NOT NULL,
    n_content          VARCHAR2(3000) NOT NULL,
    n_number          NUMBER(5) CONSTRAINT notice_number_pk PRIMARY KEY,
    user_id VARCHAR2(15) NOT NULL
);

ALTER TABLE notice
    ADD CONSTRAINT notice_userinfo_fk FOREIGN KEY (user_id)
        REFERENCES userinfo ( user_id );
        
--게시판 테이블
CREATE TABLE board (
    user_id VARCHAR2(15) NOT NULL,
    b_title      VARCHAR2(50) NOT NULL,
    b_content      VARCHAR2(3000) NOT NULL ,
    b_number   NUMBER(5) NOT NULL,
    b_hit      NUMBER(5) DEFAULT 0,
    b_date      DATE,
    b_push      NUMBER(3) DEFAULT 0
);

ALTER TABLE board ADD CONSTRAINT board_pk PRIMARY KEY (b_number);

ALTER TABLE board
    ADD CONSTRAINT board_user_fk FOREIGN KEY ( user_id )
        REFERENCES userinfo ( user_id );
--책 정보 테이블  
CREATE TABLE bookinfo (
    book_number    NUMBER(10) NOT NULL,
    book_name     VARCHAR2(50) NOT NULL,
    book_price NUMBER(6) NOT NULL,
    book_info    VARCHAR2(3000),
    book_img   VARCHAR2(3000),
    book_pub     VARCHAR2(50),
    book_author      VARCHAR2(30)
);

ALTER TABLE bookinfo ADD CONSTRAINT "bookinfo_PK" PRIMARY KEY ( book_number );

--주문 테이블
CREATE TABLE ORDER_TABLE (
    order_number          NUMBER(10) NOT NULL,
    user_id  VARCHAR2(15) NOT NULL,
    book_number NUMBER(10) NOT NULL,
    order_amount          NUMBER(3) NOT NULL
);

ALTER TABLE ORDER_TABLE ADD CONSTRAINT order_pk PRIMARY KEY ( order_number );

ALTER TABLE ORDER_TABLE
    ADD CONSTRAINT order_userinfo_fk FOREIGN KEY ( user_id )
        REFERENCES userinfo ( user_id );

ALTER TABLE ORDER_TABLE
    ADD CONSTRAINT "order_booknumber" FOREIGN KEY ( book_number )
        REFERENCES bookinfo ( book_number );