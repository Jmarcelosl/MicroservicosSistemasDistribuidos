create schema if not exists products;

create table products.category (
    id bigserial primary key,
    nome varchar(100) not null
);

alter table products.category
add constraint uk_category_nome unique (nome);
