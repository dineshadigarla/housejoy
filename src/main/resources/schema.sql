CREATE TABLE IF NOT EXISTS BUILDINGS(
ID    INTEGER  NOT NULL AUTO_INCREMENT PRIMARY KEY,
BUILDING_NAME VARCHAR(255) NOT NULL,
AREA VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS FLOORS(
ID    INTEGER  NOT NULL AUTO_INCREMENT PRIMARY KEY,
FLOOR_NAME VARCHAR(255) NOT NULL,
BUILDING_ID INTEGER,
FOREIGN KEY (BUILDING_ID) REFERENCES BUILDINGS(ID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS BLOCKS(
ID INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
BLOCK_NAME VARCHAR(255) NOT NULL,
FLOOR_ID INTEGER,
FOREIGN KEY (FLOOR_ID) REFERENCES FLOORS(ID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS DESKS(
ID INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
DESK_NAME VARCHAR(255) NOT NULL,
BLOCK_ID INTEGER,
FOREIGN KEY (BLOCK_ID) REFERENCES BLOCKS(ID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS EMPLOYEES(
ID INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
EMPLOYEE_NAME VARCHAR(255) NOT NULL,
DESK_ID INTEGER,
FOREIGN KEY (DESK_ID) REFERENCES DESKS(ID) ON DELETE CASCADE
);