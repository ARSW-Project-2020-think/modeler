--Create table
create table usuario(correo varchar(500) not null,username varchar(500) not null,password varchar(500) not null);

create table proyecto(id serial not null, nombre varchar(200) not null, publico boolean not null , usuario varchar(500) not null);

create table Version(id serial not null, numero integer not null,id_proyecto integer not null);

--PKS
alter table usuario  add constraint pk_usuario primary key(correo);

alter table proyecto add constraint pk_proyecto primary key(id);

alter table version add constraint pk_version primary key(id);

--FKS

ALTER TABLE proyecto ADD CONSTRAINT fk_proyecto_usuario FOREIGN KEY(usuario) REFERENCES usuario(correo);
alter table version add constraint fk_version_proyecto foreign key(id_proyecto) references proyecto(id);

--UKs
alter table usuario  add constraint uk_usuario_username unique(username);

-- Checks
alter table usuario add constraint ck_usuario_correo check(correo like '%@%.%' and not(correo like('%.')) and not(correo like '%@.%'));


--drop table usuario;
-- drop table proyecto;


