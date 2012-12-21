create table busines(id MEDIUMINT NOT NULL AUTO_INCREMENT,nombre varchar(50),direccion varchar(100),url varchar(100),telefono varchar(15),email varchar(40),PRIMARY KEY (id));

create table person (id VARCHAR(10),nombre VARCHAR(20),apellido1 VARCHAR(20),apellido2 VARCHAR(20),busines MEDIUMINT);

insert into person values('1','JOSE HECTOR','LOPEZ','BENAVENTE',1);
insert into person values('2','ALBERTO','CASTILLO','LOPEZ',1);
insert into person values('3','MARIO','DIAZ','',1);
