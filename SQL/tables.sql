--Create table

create table usuario(correo varchar(500) not null,username varchar(500) not null,password varchar(500) not null);

create table proyecto(id serial not null, nombre varchar(200) not null, publico boolean not null , usuario varchar(500) not null);

create table Version(id serial not null, numero integer not null,id_proyecto integer not null);

create table modelo (id serial not null,nombre varchar(5000) not null, id_version integer not null,tipo varchar not null);

create table Rectangulo(id serial not null, x integer not null, y integer not null,ancho integer not null,
		alto integer not null,nombre varchar(500) not null,id_modelo integer not null);
		
create table compartido(id serial not null,id_usuario varchar(5000) not null,id_proyecto integer not null);

--PKS
alter table usuario  add constraint pk_usuario primary key(correo);

alter table proyecto add constraint pk_proyecto primary key(id);

alter table version add constraint pk_version primary key(id);

alter table modelo add constraint pk_modelo primary key(id);

alter table Rectangulo add constraint pk_rectangulo primary key(id);

alter table compartido add constraint pk_compartido primary key(id);

--FKS

ALTER TABLE proyecto ADD CONSTRAINT fk_proyecto_usuario FOREIGN KEY(usuario) REFERENCES usuario(correo);

alter table version add constraint fk_version_proyecto foreign key(id_proyecto) references proyecto(id);

alter table modelo add constraint fk_modelo_version foreign key(id_version) references version(id);

alter table Rectangulo add constraint fk_rectangulo_modelo foreign key(id_modelo) references modelo(id);

alter table compartido add constraint fk_compartido_usuario foreign key(id_usuario) references usuario(correo);

alter table compartido add constraint fk_compartido_proyecto foreign key(id_proyecto) references proyecto (id);

--UKs
alter table usuario  add constraint uk_usuario_username unique(username);

-- Checks
alter table usuario add constraint ck_usuario_correo check(correo like '%@%.%' and not(correo like('%.')) and not(correo like '%@.%'));


--drop table usuario;
-- drop table proyecto;


