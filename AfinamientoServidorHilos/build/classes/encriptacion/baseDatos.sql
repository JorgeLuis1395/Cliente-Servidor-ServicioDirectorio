/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2008                    */
/* Created on:     29/06/2017 13:58:35                          */
/*==============================================================*/

create database ClienteCompuDistri
use ClienteCompuDistri

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('PERSONA') and o.name = 'FK_PERSONA_RELATIONS_FACULTAD')
alter table PERSONA
   drop constraint FK_PERSONA_RELATIONS_FACULTAD
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('PERSONA') and o.name = 'FK_PERSONA_RELATIONS_TIPO_PER')
alter table PERSONA
   drop constraint FK_PERSONA_RELATIONS_TIPO_PER
go

if exists (select 1
            from  sysobjects
           where  id = object_id('FACULTAD')
            and   type = 'U')
   drop table FACULTAD
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('PERSONA')
            and   name  = 'RELATIONSHIP_3_FK'
            and   indid > 0
            and   indid < 255)
   drop index PERSONA.RELATIONSHIP_3_FK
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('PERSONA')
            and   name  = 'RELATIONSHIP_2_FK'
            and   indid > 0
            and   indid < 255)
   drop index PERSONA.RELATIONSHIP_2_FK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('PERSONA')
            and   type = 'U')
   drop table PERSONA
go

if exists (select 1
            from  sysobjects
           where  id = object_id('TIPO_PERSONA')
            and   type = 'U')
   drop table TIPO_PERSONA
go

/*==============================================================*/
/* Table: FACULTAD                                              */
/*==============================================================*/
create table FACULTAD (
   ID_FACULTAD          int                  not null,
   NOMBREFACULTAD       varchar(40)             null,
   constraint PK_FACULTAD primary key (ID_FACULTAD)
)
go

/*==============================================================*/
/* Table: PERSONA                                               */
/*==============================================================*/
create table PERSONA (
   ID_PERSONA           int                  not null,
   ID_TIPO_PERSONA      int                  not null,
   ID_FACULTAD          int                  not null,
   NOMBREPERSONA        varchar(40)             null,
   USUARIO				varchar(20)             not null,
   CONTRASENA           varchar(15)             not null,
   constraint PK_PERSONA primary key (ID_PERSONA)
)
go

/*==============================================================*/
/* Index: RELATIONSHIP_2_FK                                     */
/*==============================================================*/
create index RELATIONSHIP_2_FK on PERSONA (
ID_FACULTAD ASC
)
go

/*==============================================================*/
/* Index: RELATIONSHIP_3_FK                                     */
/*==============================================================*/
create index RELATIONSHIP_3_FK on PERSONA (
ID_TIPO_PERSONA ASC
)
go

/*==============================================================*/
/* Table: TIPO_PERSONA                                          */
/*==============================================================*/
create table TIPO_PERSONA (
   ID_TIPO_PERSONA      int                  not null,
   TIPO_PERSONA         varchar(15)             null,
   constraint PK_TIPO_PERSONA primary key (ID_TIPO_PERSONA)
)
go

alter table PERSONA
   add constraint FK_PERSONA_RELATIONS_FACULTAD foreign key (ID_FACULTAD)
      references FACULTAD (ID_FACULTAD)
go

alter table PERSONA
   add constraint FK_PERSONA_RELATIONS_TIPO_PER foreign key (ID_TIPO_PERSONA)
      references TIPO_PERSONA (ID_TIPO_PERSONA)
go

insert into FACULTAD values(1,'Ingenieria en Sistemas');
insert into FACULTAD values(2,'Ingenieria Mecanica');
insert into FACULTAD values(3,'Ingenieria Empresarial');

insert into TIPO_PERSONA values(1,'Administrador');
insert into TIPO_PERSONA values(2,'Estudiante');
insert into TIPO_PERSONA values(3,'Profesor');

insert into PERSONA values (1,1,1,'Roberto Toapanta','robertToa','12345');
insert into PERSONA values (2,2,2,'Jorge Carrillo','jorge','23456');
insert into PERSONA values (3,2,3,'Bryan Ocana','bryanOc','34567');
insert into PERSONA values (4,3,1,'Bryan Jiminez','bryanJi','45678');
