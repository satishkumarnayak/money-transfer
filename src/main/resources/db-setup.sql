DROP TABLE IF EXISTS TRANSFER;
DROP TABLE IF EXISTS ACCOUNT;

CREATE TABLE ACCOUNT(ID INT NOT NULL AUTO_INCREMENT, BALANCE INT, TYPE VARCHAR(50), PRIMARY KEY(ID));

CREATE TABLE TRANSFER(ID INT NOT NULL AUTO_INCREMENT, FROM_ACCOUNT_ID INT, TO_ACCOUNT_ID INT, AMOUNT INT,
 STATUS VARCHAR(50), TRANSFER_INITIATED_TIMESTAMP TIMESTAMP DEFAULT CURRENT_TIMESTAMP, PRIMARY KEY(ID),
  FOREIGN KEY (FROM_ACCOUNT_ID) REFERENCES ACCOUNT(ID), FOREIGN KEY (TO_ACCOUNT_ID) REFERENCES ACCOUNT(ID));

INSERT INTO ACCOUNT (ID, BALANCE, TYPE) VALUES (321,800, 'SAVINGS');

INSERT INTO ACCOUNT (ID, BALANCE, TYPE) VALUES (322, 900, 'SAVINGS');

COMMIT;