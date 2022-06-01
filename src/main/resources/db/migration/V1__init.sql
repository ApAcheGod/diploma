DROP SCHEMA IF EXISTS  diploma CASCADE ;
CREATE SCHEMA diploma;

CREATE TABLE diploma.ROLES
(
    ID   SERIAL PRIMARY KEY,
    NAME VARCHAR(20)
);

CREATE TABLE diploma.USERS
(
    ID         UUID         NOT NULL PRIMARY KEY,
    FIRST_NAME VARCHAR(50) NOT NULL,
    LAST_NAME  VARCHAR(50) NOT NULL,
    PATRONYMIC VARCHAR(50),
    EMAIL      VARCHAR(50),
    LOGIN      VARCHAR(50),
    PASSWORD   VARCHAR(100)
);

CREATE TABLE diploma.USER_ROLES
(
    USER_ID UUID    NOT NULL REFERENCES diploma.USERS,
    ROLE_ID INTEGER NOT NULL REFERENCES diploma.ROLES,
    PRIMARY KEY (USER_ID, ROLE_ID)
);

CREATE TABLE diploma.TEACHERS
(
    ID UUID NOT NULL PRIMARY KEY CONSTRAINT TEACHER_USER_REFERENCE REFERENCES diploma.USERS
);

CREATE TABLE diploma.ROOMS
(
    ID         UUID         NOT NULL PRIMARY KEY,
    ROOM_NAME  VARCHAR(100) NOT NULL,
    TEACHER_ID UUID CONSTRAINT TEACHER_ROOM_REFERENCE REFERENCES diploma.TEACHERS
);

CREATE TABLE diploma.GROUPS
(
    ID         UUID         NOT NULL PRIMARY KEY,
    GROUP_NAME VARCHAR(100) NOT NULL CONSTRAINT UNIQUE_GROUP_NAME UNIQUE
);

CREATE TABLE diploma.SUBJECTS
(
    ID           UUID NOT NULL PRIMARY KEY,
    SUBJECT_NAME VARCHAR(100),
    ROOM_ID      UUID CONSTRAINT SUBJECT_ROOM_REFERENCE REFERENCES diploma.ROOMS,
    TEACHER_ID   UUID CONSTRAINT SUBJECT_TEACHER_REFERENCE REFERENCES diploma.TEACHERS
);

CREATE TABLE diploma.SUBJECTS_GROUPS
(
    SUBJECTS_ID UUID NOT NULL CONSTRAINT SUBJECT_ID_REFERENCE REFERENCES diploma.SUBJECTS,
    GROUPS_ID   UUID NOT NULL CONSTRAINT GROUP_ID_REFERENCE REFERENCES diploma.GROUPS,
    PRIMARY KEY (SUBJECTS_ID, GROUPS_ID)
);

CREATE TABLE diploma.MATERIALS
(
    ID            UUID         NOT NULL PRIMARY KEY,
    MATERIAL_NAME VARCHAR(100) NOT NULL,
    TEXT          TEXT,
    SUBJECT_ID    UUID CONSTRAINT MATERIAL_SUBJECT_REFERENCE REFERENCES diploma.SUBJECTS,
    TEACHER_ID    UUID CONSTRAINT MATERIAL_TEACHER_REFERENCE REFERENCES diploma.TEACHERS
);

CREATE TABLE diploma.TASKS
(
    ID                    UUID         NOT NULL PRIMARY KEY,
    DATE_OF_CREATION      TIMESTAMP,
    TASK_NAME             VARCHAR(100) NOT NULL,
    TEXT                  TEXT,
    SUBJECT_ID            UUID CONSTRAINT TASK_SUBJECT_REFERENCE REFERENCES diploma.SUBJECTS,
    TEACHER_ID            UUID CONSTRAINT TASK_TEACHER_REFERENCE REFERENCES diploma.TEACHERS
);

CREATE TABLE diploma.STUDENTS
(
    ID       UUID NOT NULL PRIMARY KEY CONSTRAINT STUDENT_USER_REFERENCE REFERENCES diploma.USERS,
    GROUP_ID UUID CONSTRAINT STUDENT_GROUP_REFERENCE REFERENCES diploma.GROUPS
);


CREATE TABLE diploma.GROUPS_ROOMS
(
    GROUPS_ID UUID NOT NULL CONSTRAINT GROUP_REFERENCE REFERENCES diploma.GROUPS,
    ROOMS_ID  UUID NOT NULL CONSTRAINT ROOM_REFERENCE REFERENCES diploma.ROOMS,
    PRIMARY KEY (GROUPS_ID, ROOMS_ID)
);


CREATE TABLE diploma.SOLUTIONS
(
    ID               UUID NOT NULL PRIMARY KEY,
    DATE_OF_DELIVERY TIMESTAMP,
    TEXT             TEXT,
    STUDENT_ID       UUID CONSTRAINT STUDENT_REFERENCE REFERENCES diploma.STUDENTS,
    TASK_ID          UUID CONSTRAINT TASK_REFERENCE REFERENCES diploma.TASKS
);

CREATE TABLE diploma.EXAMINATIONS
(
    ID                 UUID NOT NULL PRIMARY KEY,
    DATE_OF_VALUATION  TIMESTAMP,
    EXAMINATION_STATUS VARCHAR(20),
    SOLUTION_ID        UUID CONSTRAINT SOLUTION_REFERENCE REFERENCES diploma.SOLUTIONS
);

-- DROP SCHEMA IF EXISTS  DIPLOMA CASCADE ;
-- CREATE SCHEMA DIPLOMA;
-- -- DROP TABLE roles cascade ;
-- -- CREATE TABLE roles(
-- --                           uuid               serial not null primary key,
-- --                           name             varchar(50)
-- -- );
-- --
-- -- INSERT INTO roles (name) VALUES ('ROLE_TEACHER'), ('ROLE_ADMIN'), ('ROLE_STUDENT');
--
-- --=================
-- create table diploma.groups
-- (
--     id uuid not null, group_name varchar(255) not null,
--     primary key (id)
-- );
--
-- create table diploma.materials
-- (
--     id uuid not null,
--     material_name varchar(255) not null,
--     text varchar(255), subject_id uuid,
--     teacher_id uuid,
--     primary key (id)
-- );
--
-- create table diploma.roles
-- (
--     id serial not null,
--     name varchar(255),
--     primary key (id)
-- );
--
-- -- insert into diploma.roles (id, name) values (1, 'ROLE_ADMIN'), (2, 'ROLE_TEACHER'), (3, 'ROLE_STUDENT');
--
-- create table diploma.rooms
-- (
--     id uuid not null,
--     room_name varchar(255) not null,
--     teacher_id uuid,
--     primary key (id)
-- );
--
-- -- create table diploma.solutions
-- -- (
-- --     id uuid not null,
-- --     date_of_delivery timestamp,
-- --     text varchar(255),
-- --     student_id uuid,
-- --     task_id uuid,
-- --     primary key (id)
-- -- );
--
-- create table diploma.students
-- (
--     id uuid not null,
--     group_id uuid,
--     primary key (id)
-- );
--
-- create table diploma.subjects
-- (
--     id uuid not null,
--     subject_name varchar(255) not null,
--     room_id uuid,
--     student_id uuid,
--     teacher_id uuid,
--     primary key (id)
-- );
--
-- create table diploma.tasks
-- (
--     id uuid not null,
--     count_of_attempts int4 not null,
--     date_of_creation timestamp,
--     is_mandatory boolean,
--     is_temporal boolean,
--     last_date_of_delivery timestamp,
--     max_rating int4 not null,
--     min_rating int4 not null,
--     task_name varchar(255) not null,
--     task_type varchar(255),
--     text varchar(255),
--     subject_id uuid,
--     teacher_id uuid,
--     primary key (id)
-- );
--
-- create table diploma.teachers
-- (
--     id uuid not null,
--     primary key (id)
-- );
--
-- create table diploma.users
-- (
--     id uuid not null,
--     first_name varchar(255) not null,
--     last_name varchar(255) not null,
--     login varchar(255),
--     password varchar(255),
--     patronymic varchar(255),
--     primary key (id)
-- );
--
-- -- create table groups_rooms
-- -- (
-- --     groups_id uuid not null,
-- --     rooms_id uuid not null,
-- --     primary key (groups_id, rooms_id)
-- -- );
-- -- create table students_tasks
-- -- (
-- --     students_id uuid not null,
-- --     tasks_id uuid not null,
-- --     primary key (students_id, tasks_id)
-- -- );
-- -- create table subjects_groups
-- -- (
-- --     subjects_id uuid not null,
-- --     groups_id uuid not null,
-- --     primary key (subjects_id, groups_id)
-- -- );
-- -- create table tasks_groups
-- -- (
-- --     tasks_id uuid not null,
-- --     groups_id uuid not null,
-- --     primary key (tasks_id, groups_id)
-- -- );
-- -- create table teachers_groups
-- -- (
-- --     teachers_id uuid not null,
-- --     groups_id uuid not null,
-- --     primary key (teachers_id, groups_id)
-- -- );
-- create table diploma.user_roles
-- (
--     user_id uuid not null,
--     role_id int not null,
--     primary key (user_id, role_id),
--         foreign key (role_id) references diploma.roles(id),
--         foreign key (user_id) references diploma.users(id)
-- );
-- --
-- --
-- -- alter table if exists diploma.groups drop constraint if exists UK_qknyspte24la8b18kbn7e3je3;
-- -- alter table if exists diploma.groups add constraint UK_qknyspte24la8b18kbn7e3je3 unique (group_name);
-- -- alter table if exists diploma.materials add constraint FKbywsgworsme1r6mk63mgnpm7l foreign key (subject_id) references diploma.subjects;
-- -- alter table if exists diploma.materials add constraint FK1vs2rnw1fk0eolvsmrnqfnj91 foreign key (teacher_id) references diploma.teachers;
-- -- alter table if exists diploma.rooms add constraint FK5udorrqfn4b81fqa935pnw2ya foreign key (teacher_id) references diploma.teachers;
-- -- alter table if exists diploma.solutions add constraint FK1pcfy9sr41qrjfjnryxw7ld1o foreign key (student_id) references diploma.students;
-- -- alter table if exists diploma.solutions add constraint FKbqabgk1lnu9xg0rbmno60q2tw foreign key (task_id) references diploma.tasks;
-- -- alter table if exists diploma.students add constraint FKmsev1nou0j86spuk5jrv19mss foreign key (group_id) references diploma.groups;
-- -- alter table if exists diploma.students add constraint FK7xqmtv7r2eb5axni3jm0a80su foreign key (id) references diploma.users;
-- -- alter table if exists diploma.subjects add constraint FKg6ajkk7ofs1nep3xx50s278kk foreign key (room_id) references diploma.rooms;
-- -- alter table if exists diploma.subjects add constraint FKhyfrm3h9qjhvly6vcqh311gq7 foreign key (student_id) references diploma.students;
-- -- alter table if exists diploma.subjects add constraint FKsjy6ghvvelraa2w9mhv3bbnys foreign key (teacher_id) references diploma.teachers;
-- -- alter table if exists diploma.tasks add constraint FKr57noxytq33ig25g3e7utiofw foreign key (subject_id) references diploma.subjects;
-- -- alter table if exists diploma.tasks add constraint FKedr91s1ueomtf3d9vhxsxo6a1 foreign key (teacher_id) references diploma.teachers;
-- -- alter table if exists diploma.teachers add constraint FKpavufmal5lbtc60csriy8sx3 foreign key (id) references diploma.users;
-- -- alter table if exists groups_rooms add constraint FK6uc2ddegohgx7dqjh0g60cg11 foreign key (rooms_id) references diploma.rooms;
-- -- alter table if exists groups_rooms add constraint FK3et5ami1iapjc8cakxlrx00qq foreign key (groups_id) references diploma.groups;
-- -- alter table if exists students_tasks add constraint FK73awwh7q5bu1q037hel4jx030 foreign key (tasks_id) references diploma.tasks;
-- -- alter table if exists students_tasks add constraint FKc81r7mtjaqjfrlv5ndotjryxp foreign key (students_id) references diploma.students;
-- -- alter table if exists subjects_groups add constraint FK9alnrwbsypii0musvr4mx950m foreign key (groups_id) references diploma.groups;
-- -- alter table if exists subjects_groups add constraint FK5gnqax6bgms5ehtcarmj78qrh foreign key (subjects_id) references diploma.subjects;
-- -- alter table if exists tasks_groups add constraint FKl1pktrpxob9v8urrtvj1chvr9 foreign key (groups_id) references diploma.groups;
-- -- alter table if exists tasks_groups add constraint FKf6tex8n5yayrdrtw77q6tyauc foreign key (tasks_id) references diploma.tasks;
-- -- alter table if exists teachers_groups add constraint FKif7v5iboyedxqh0kql98sojbm foreign key (groups_id) references diploma.groups;
-- -- alter table if exists teachers_groups add constraint FKd0gdiwxuo3jqiuegwwmsao1dq foreign key (teachers_id) references diploma.teachers;
-- -- alter table if exists user_roles add constraint FKh8ciramu9cc9q3qcqiv4ue8a6 foreign key (role_id) references diploma.roles;
-- -- alter table if exists user_roles add constraint FKhfh9dx7w3ubf1co1vdev94g3f foreign key (user_id) references diploma.users;
-- --
--
-- -- DROP TABLE IF EXISTS user_roles cascade ;
-- -- CREATE TABLE user_roles(
-- --                            user_id int not null,
-- --                            role_id int not null,
-- --
-- --                            primary key (user_id, role_id),
-- --                            FOREIGN KEY (user_id) references users (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
-- --                            FOREIGN KEY (role_id) references roles (id) ON DELETE NO ACTION ON UPDATE NO ACTION
-- -- );
--
--
-- --     CREATE SCHEMA diploma;
-- --
-- --     DROP TABLE IF EXISTS diploma.TEACHERS;
-- --     CREATE TABLE diploma.TEACHERS(
-- --         ID SERIAL NOT NULL PRIMARY KEY ,
-- --         FIRST_NAME text NOT NULL ,
-- --         LAST_NAME text  NOT NULL ,
-- --         PATRONYMIC text NOT NULL
-- --     );
-- --
-- --     DROP TABLE IF EXISTS diploma.GROUPS;
-- --     CREATE TABLE diploma.GROUPS(
-- --         ID SERIAL NOT NULL PRIMARY KEY ,
-- --         NAME text  NOT NULL
-- --     );
-- --
-- --     DROP TABLE IF EXISTS diploma.STUDENTS;
-- --     CREATE TABLE diploma.STUDENTS(
-- --                                      ID SERIAL NOT NULL PRIMARY KEY ,
-- --                                      FIRST_NAME text NOT NULL ,
-- --                                      LAST_NAME text NOT NULL ,
-- --                                      PATRONYMIC text NOT NULL ,
-- --                                      GROUP_ID INT REFERENCES diploma.GROUPS(ID)
-- --     );
-- --
-- --     DROP TABLE IF EXISTS diploma.GRADES;
-- --     CREATE TABLE diploma.GRADES(
-- --         ID SERIAL NOT NULL PRIMARY KEY ,
-- --         STUDENT_ID INT REFERENCES diploma.STUDENTS(ID),
-- --         MARK INT ,
-- --         CREATED_AT date
-- --     );
-- --
-- --
-- --     DROP TABLE IF EXISTS diploma.SUBJECTS;
-- --     CREATE TABLE diploma.SUBJECTS(
-- --                                      ID SERIAL NOT NULL PRIMARY KEY ,
-- --                                      NAME text NOT NULL ,
-- --                                      TEACHER_ID INT REFERENCES diploma.TEACHERS(ID)
-- --     );
-- --
-- --     DROP TABLE IF EXISTS diploma.TASKS;
-- --     CREATE TABLE diploma.TASKS(
-- --         ID SERIAL NOT NULL PRIMARY KEY ,
-- --         NAME text NOT NULL ,
-- --         SUBJECT_ID INT REFERENCES diploma.SUBJECTS(ID),
-- --         TEACHER_ID INT REFERENCES diploma.TEACHERS(ID),
-- --         CREATED_AT date,
-- --         STARTED_AT date,
-- --         CLOSED_AT date,
-- --         LAST_CLOSED_AT date,
-- --         IS_CREDIT bool,
-- --     --     MIN_RATING INT,
-- --         MAX_RATING INT,
-- --         TEXT text  NOT NULL ,
-- --         IS_TEMPORAL bool,
-- --         IS_MANDATORY BOOL
-- --     );
-- --
-- --
-- --     DROP TABLE IF EXISTS diploma.SOLUTIONS;
-- --     CREATE TABLE diploma.SOLUTIONS(
-- --                                       ID SERIAL NOT NULL PRIMARY KEY ,
-- --                                       NAME text NOT NULL  ,
-- --                                       STUDENT_ID INT REFERENCES diploma.STUDENTS(ID),
-- --                                       TASK_ID INT REFERENCES diploma.TASKS(ID),
-- --                                       CREATED_AT date,
-- --                                       TEXT text  NOT NULL
-- --     );
-- --
-- --     DROP TABLE IF EXISTS diploma.ROOMS;
-- --     CREATE TABLE diploma.ROOMS(
-- --                                   ID SERIAL NOT NULL PRIMARY KEY ,
-- --                                   TEACHER_ID INT REFERENCES diploma.TEACHERS(ID)
-- --     );
--
-- create table diploma.solutions
-- (
--     id               uuid not null
--         primary key,
--     date_of_delivery timestamp,
--     text             varchar(255),
--     student_id       uuid
--         constraint fk1pcfy9sr41qrjfjnryxw7ld1o
--             references diploma.students on DELETE cascade ,
--     task_id          uuid
--         constraint fkbqabgk1lnu9xg0rbmno60q2tw
--             references diploma.tasks
-- );
--
-- create table diploma.examinations
-- (
--     id                 uuid    not null
--         primary key,
--     examination_status varchar(255),
--     mark               integer not null,
--     solution_id        uuid
--         constraint fko84r0u7bp903rkda2st5mkalw
--             references diploma.solutions on DELETE cascade ,
--     date_of_valuation  timestamp
-- );
--
--
--
insert into diploma.users(id, first_name, last_name, login, password, patronymic) values
    ('22bec816-8702-4595-960a-7b004664b3e9', 'Никита', 'Алпатов', 'admin',
     '$2a$10$EvVOWkhyVR5sdk2dQD7JFOQCd6M8a7kX4/MIfUkTXZDpr9.pHs1Li', 'Сергеевич');
insert into diploma.roles (id, name) values (1, 'ROLE_ADMIN'), (2, 'ROLE_TEACHER'), (3, 'ROLE_STUDENT');
insert into diploma.user_roles (user_id, role_id) values ('22bec816-8702-4595-960a-7b004664b3e9', 1);