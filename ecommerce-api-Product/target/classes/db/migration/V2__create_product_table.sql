create table products.product (
    id bigserial primary key,
    product_identifier varchar(100) not null unique,
    nome varchar(100) not null,
    descricao varchar not null,
    preco float not null,
    category_id bigint not null,
    constraint fk_product_category
        foreign key (category_id)
        references products.category(id)
        on delete no action
        on update no action
);
