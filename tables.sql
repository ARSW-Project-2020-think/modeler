--Create table
create table usuario(correo varchar(500) not null,username varchar(500) not null,password varchar(500) not null);


--PKS
alter table usuario  add constraint pk_usuario primary key(correo);


--FKS


--UKs
alter table usuario  add constraint uk_usuario_username unique(username);

-- Checks
alter table usuario add constraint ck_usuario_correo check(correo like '%@%.%' and not(correo like('%.')) and not(correo like '%@.%'));


--drop table usuario;