SET MODE Oracle;
CREATE SEQUENCE "SUPERHEROE_SEQ"
MINVALUE 11
MAXVALUE 999999999
INCREMENT BY 1
NOCACHE
NOCYCLE;
CREATE TABLE SUPERHEROES (
   id INTEGER DEFAULT SUPERHEROE_SEQ.NEXTVAL,
--   id INT NOT NULL AUTO_INCREMENT,
   name VARCHAR(50) NOT NULL,
   entry_date DATE,
   modification_date DATE,
   PRIMARY KEY (id)
);
