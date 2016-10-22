alter TABLE users alter COLUMN password TYPE char(64);

alter TABLE users add COLUMN email VARCHAR(64) DEFAULT NULL;