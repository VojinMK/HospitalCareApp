INSERT INTO Bolnica (id, naziv, adresa, budzet) 
VALUES 
     (nextval('bolnica_seq'),'Opsta bolnica Novi Sad', 'Vuka Karadzica 1', 100000.00),
     (nextval('bolnica_seq'),'Covid bolnica', 'Vuka Petrovica 5', 150000.00),
     (nextval('bolnica_seq'),'Specijalisticka bolnica', 'Zvezdaska 3', 200000.00),
     (nextval('bolnica_seq'),'Vojna bolnica', 'Hajducka 10', 120000.00);
     
INSERT INTO Dijagnoza (id, naziv, opis, oznaka) 
VALUES 
     (nextval('dijagnoza_seq'),'Gripa', 'Respiratorna bolest uzrokovana virusom influence.', 'GR001'),
     (nextval('dijagnoza_seq'),'Hipertenzija', 'Povišeni krvni tlak.', 'HT001'),
     (nextval('dijagnoza_seq'),'Dijabetes', 'Metabolički poremećaj karakteriziran povišenom razinom glukoze u krvi.', 'DB001'),
     (nextval('dijagnoza_seq'),'Anksioznost', 'Psihološki poremećaj karakteriziran osjećajem stalne zabrinutosti i nelagode.', 'AN001');
     

INSERT INTO Odeljenje (id,naziv, lokacija, bolnica) 
VALUES 
     (nextval('odeljenje_seq'),'Interno odeljenje', 'Sprat 2', 4),
     (nextval('odeljenje_seq'),'Hirurško odeljenje', 'Sprat 3', 2),
     (nextval('odeljenje_seq'),'Pedijatrijsko odeljenje', 'Sprat 1', 3),
     (nextval('odeljenje_seq'),'Ginekološko odeljenje', 'Sprat 4', 1);
     
INSERT INTO Pacijent (id,ime, prezime, zdr_osiguranje, datum_rodjenja, odeljenje, dijagnoza) 
VALUES 
     (nextval('pacijent_seq'),'Marko', 'Markovic', true, to_date('24.04.2000','dd.mm.yyyy.'), 1, 1),
     (nextval('pacijent_seq'),'Ana', 'Anic', false, to_date('15.09.1995','dd.mm.yyyy.'), 2, 3),
     (nextval('pacijent_seq'),'Ivan', 'Ivanovic', true, to_date('03.11.1988','dd.mm.yyyy.'), 3, 2),
     (nextval('pacijent_seq'),'Jovana', 'Jovanovic', true, to_date('21.07.1976','dd.mm.yyyy.'), 4, 4);
     
     
   
