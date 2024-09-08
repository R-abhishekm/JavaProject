# JavaProject
Here there are small projects of Java in which programs of Core Java and Oracle Database.

Oracle is importent for run this code i am giving you create table queary and instruction for run this code properly.

Queary 👍
===========================================================
CREATE TABLE reservation (
RESERVATION_ID NUMBER(4) NOT NULL,
GUEST_NAME VARCHAR2(30) NOT NULL,
ROOM_NUMBER NUMBER(4) NOT NULL,
CONTACT_NUMBER VARCHAR2(10) NOT NULL,
RESERVATION_DATE TIMESTAMP(6),
CONSTRAINT pk_reservation PRIMARY KEY (RESERVATION_ID)
);
===========================================================================
NOTE:- In this queary reservation _id is contain auto generate system so after create table run the two more queary ;

First, create a sequence that will generate unique reservation IDs.
Copy code 👍
=======================================================
CREATE SEQUENCE reservation_seq
START WITH 1
INCREMENT BY 1
NOCACHE;
========================================================
Next, create a trigger that automatically assigns the next value from the sequence to the RESERVATION_ID column before a new row is inserted.
Copy code 👍
=================================================================
CREATE OR REPLACE TRIGGER reservation_bi_trigger
BEFORE INSERT ON reservation
FOR EACH ROW
BEGIN
:NEW.RESERVATION_ID := reservation_seq.NEXTVAL;
END;
=================================================
Finally code rady for user 👍
