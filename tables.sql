--Create table
create table usuario(correo varchar(500) not null,username varchar(500) not null,password varchar(500) not null);

create table proyecto(id int not null, nombre varchar(200) not null, publico boolean not null);


--PKS
alter table usuario  add constraint pk_usuario primary key(correo);

alter table proyecto add constraint pk_proyecto primary key(id);


--FKS

ALTER TABLE proyecto ADD CONSTRAINT fk_usuario FOREIGN KEY (proyecto.usuario) REFERENCES usuario(correo);


--UKs
alter table usuario  add constraint uk_usuario_username unique(username);

-- Checks
alter table usuario add constraint ck_usuario_correo check(correo like '%@%.%' and not(correo like('%.')) and not(correo like '%@.%'));


--drop table usuario;
-- drop table proyecto;
