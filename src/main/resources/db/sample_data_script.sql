USE niths;

INSERT INTO subjects (subject_code, name, description, start_time, end_time, weekday,room_number) values('PG111', "Java 1", "Innføring i java", '10:00', '11:00', "Monday","81"); 
INSERT INTO subjects (subject_code, name, description, start_time, end_time, weekday,room_number) values('PG211', "Java 2", "Innføring i java 2", '11:00', '12:00', "Monday","43");
INSERT INTO subjects (subject_code, name, description, start_time, end_time, weekday,room_number) values('BU410', "E-Business", "Skolen sparer penger", '10:00', '11:00', "Tuesday","12");
INSERT INTO subjects (subject_code, name, description, start_time, end_time, weekday,room_number) values('PJ111', "Gruppearbeid", "Arbeid i grupper", '14:00', '17:00', "Friday","34");
INSERT INTO subjects (subject_code, name, description, start_time, end_time, weekday,room_number) values('DB110', "Database 01", "Arbeid i grupper", '14:00', '17:00', "Friday","34");
INSERT INTO subjects (subject_code, name, description, start_time, end_time, weekday,room_number) values('PB210', "Database 02", "Arbeid i grupper", '14:00', '17:00', "Friday","34");
INSERT INTO subjects (subject_code, name, description, start_time, end_time, weekday,room_number) values('PG650', "J2E del 01", "Arbeid i grupper", '14:00', '17:00', "Friday","34");
INSERT INTO subjects (subject_code, name, description, start_time, end_time, weekday,room_number) values('PG660', "J2E del 02", "Arbeid i grupper", '14:00', '17:00', "Friday","34");
INSERT INTO subjects (subject_code, name, description, start_time, end_time, weekday,room_number) values('PJ600', "Hovedprosjekt", "Arbeid i grupper", '14:00', '17:00', "Friday","34");


INSERT INTO courses (name, description) VALUES("Mobil apputvikling", "Mobil apputvikling har blitt et stort forretningsområde, der både profesjonelle og amatører kan tjene gode penger på applikasjonene sine.");
INSERT INTO courses (name, description) VALUES("Programmering", "I dag brukes IT og Internett i alt fra handel, kommunikasjon, overvåking og spill til betaling.");
INSERT INTO courses (name, description) VALUES("Spillprogrammering", "Du som søker dette studiet, ønsker å jobbe med avansert programmering og har interesse for simulerings- og spillopplevelser");
INSERT INTO courses (name, description) VALUES("Spilldesign", "En spilldesigner spesialiserer seg på å skape interaktive opplevelser, og har ansvar for spillets regler og mekanikker. ");
INSERT INTO courses (name, description) VALUES("3D-grafikk", "3D-modeller benyttes blant annet i film, spill, reklame, kunst, design og visualisering.");
INSERT INTO courses (name, description) VALUES("Interaktivt design", "Det er i dag viktig å ha brukervennlige webløsninger som tilfredsstiller brukernes behov og forventninger.");
INSERT INTO courses (name, description) VALUES("Digital markedsføring", "En ny generasjon av sosiale elektroniske nettverk gjør Internett til en av våre viktigste kommunikasjonskanaler.");
INSERT INTO courses (name, description) VALUES("E-business", "Det er i dag mangel på høgskoleutdannede personer som har kunnskap om teknologiske forretningsløsninger og hvordan disse best fungerer.");
INSERT INTO courses (name, description) VALUES("Industribachelor", "Våre studenter har nå mulighet for å kombinere en jobb i IT-industrien med en bachelor ved NITH. Vi har inngått et samarbeid med to av landets største konsulent-selskaper, Accenture og Avanade.");


insert into courses_subjects values(1,1);
insert into courses_subjects values(1,2);
insert into courses_subjects values(1,3);
insert into courses_subjects values(2,4);
insert into courses_subjects values(2,1);
insert into courses_subjects values(3,1);
insert into courses_subjects values(3,2);
insert into courses_subjects values(3,3);
insert into courses_subjects values(4,4);
insert into courses_subjects values(4,1);
insert into courses_subjects values(4,2);
insert into courses_subjects values(4,3);
insert into courses_subjects values(4,5);


INSERT INTO students (first_name, last_name, birthday,email,gender,description,phone_number,grade,password) VALUES("Andre", "Kristensen", '2012-02-02',"mail@mail.com",'M',"super awesome","81549300",3,"super secret password *******");
INSERT INTO students (first_name, last_name, birthday,email,gender,description,phone_number,grade,password) VALUES("Bendik", "Rostad", '2012-02-02',"MMMM@mail.com",'M',"super awesome","81249300",3,"super secret password *******");
INSERT INTO students (first_name, last_name, birthday,email,gender,description,phone_number,grade,password) VALUES("Liv", "Kolås", '2012-02-02',"tea@mail.com",'F',"super awesome","15492300",3,"super secret password *******");
INSERT INTO students (first_name, last_name, birthday,email,gender,description,phone_number,grade,password) VALUES("Øyvind", "Ødegård",'2012-02-02',"free@mail.com",'M',"super awesome","41549300",3,"super secret password *******");

INSERT INTO committees (name,description) VALUES("Linx utvalget","Linux");
INSERT INTO committees (name,description) VALUES("Apple utvalget","Apple GOGO");
INSERT INTO committees (name,description) VALUES("Microsoft utvalget","Microsoft");
INSERT INTO committees (name,description) VALUES("KIT", "kvinner og it");
INSERT INTO committees (name,description) VALUES("MDF", "media design og foto");

INSERT INTO committee_leaders VALUES (1, 1);
INSERT INTO committee_leaders VALUES (2, 2);
INSERT INTO committee_leaders VALUES (3, 3);
INSERT INTO committee_leaders VALUES (4, 4);

INSERT INTO events (name,description,startTime,endTime,tags) VALUES("LUG Distro"," distro fest",'2012-03-09 11:05:32','2012-03-09 11:05:32',"Linux, utvalg");
INSERT INTO events (name,description,startTime,endTime,tags) VALUES("Fest i Kroa","Valentine party",'2012-03-09 11:05:32','2012-03-09 11:05:32',"kroa, utvalg");
INSERT INTO events (name,description,startTime,endTime,tags) VALUES("Workshop"," Android workshop",'2012-03-09 11:05:32','2012-03-09 11:05:32'"Android, workshop");
INSERT INTO events (name,description,startTime,endTime,tags) VALUES("Årsmøte"," Obligatorisk",'2012-03-09 11:05:32','2012-03-09 11:05:32',"årsmøte, møte");
INSERT INTO events (name,description,startTime,endTime,tags) VALUES("KIT-møte","Damenes møte",'2012-03-09 11:05:32','2012-03-09 11:05:32',"kit, møte");
INSERT INTO events (name,description,startTime,endTime,tags) VALUES("Fadderuke kickoff","kickoff fest",'2012-03-09 11:05:32','2012-03-09 11:05:32',"fadderuke, kroa");
INSERT INTO events (name,description,startTime,endTime,tags) VALUES("Fadderuke party","party",'2012-03-09 11:05:32','2012-03-09 11:05:32',"fadderuke");

insert into students_committees (students_id, committees_id) values(1, 1);
insert into students_committees (students_id, committees_id) values(1, 2);
insert into students_committees (students_id, committees_id) values(2, 1);
insert into students_committees (students_id, committees_id) values(3, 1);

insert into students_courses (students_id, courses_id) values(1, 1);
insert into students_courses (students_id, courses_id) values(2, 2);
insert into students_courses (students_id, courses_id) values(3, 1);
insert into students_courses (students_id, courses_id) values(4, 3);

insert into committees_committee_events (committees_id, events_id) values(1, 1);
insert into committees_committee_events (committees_id, events_id) values(1, 2);
insert into committees_committee_events (committees_id, events_id) values(1, 3);
insert into committees_committee_events (committees_id, events_id) values(1, 4);
insert into committees_committee_events (committees_id, events_id) values(2, 5);
insert into committees_committee_events (committees_id, events_id) values(2, 6);