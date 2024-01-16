create table organization
(
    id      bigint not null,
    country varchar(255),
    crd     varchar(255),
    primary key (id)
) engine = InnoDB;

create table organization_seq
(
    next_val bigint
) engine = InnoDB;

insert into organization_seq
values (1);
