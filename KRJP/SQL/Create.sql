create user asakura identified by ryo account unlock;
grant dba to asakura;

connect asakura/ryo

CREATE TABLE THREAD_TABLE(
thread_id		 number(4)
					CONSTRAINT pk_thread_id PRIMARY KEY,
thread_title	 varchar2(100) NOT NULL
			 		CONSTRAINT uq_thread_title UNIQUE,
latest_res	 	 date NOT NULL 
);


CREATE TABLE RESPONSE_TABLE(
res_id			 number(8)
					CONSTRAINT pk_res_id PRIMARY KEY,
thread_id		 number(4) NOT NULL
					REFERENCES THREAD_TABLE(thread_id),
res_sentence	 varchar2(4000) NOT NULL,
res_name		 varchar2(40) NOT NULL,
res_number		 number(4) NOT NULL,
res_date		 date NOT NULL
);

CREATE SEQUENCE res_id
INCREMENT BY 1
MAXVALUE 9999
START WITH 1;

exit