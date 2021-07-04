DROP TABLE IF EXISTS AUTHOR;
CREATE TABLE AUTHOR(ID BIGINT PRIMARY KEY, NAME VARCHAR(255) NOT NULL);

DROP TABLE IF EXISTS GENRE;
CREATE TABLE GENRE(ID BIGINT PRIMARY KEY, TITLE VARCHAR(255) NOT NULL);

DROP TABLE IF EXISTS BOOK;
CREATE TABLE BOOK(
ID BIGINT PRIMARY KEY,
TITLE VARCHAR(255) NOT NULL,
AUTHOR_ID BIGINT NOT NULL,
GENRE_ID BIGINT NOT NULL,
CONSTRAINT AUTHOR_ID_FK FOREIGN KEY (AUTHOR_ID) REFERENCES AUTHOR(ID),
CONSTRAINT GENRE_ID_FL FOREIGN KEY (GENRE_ID) REFERENCES GENRE(ID));