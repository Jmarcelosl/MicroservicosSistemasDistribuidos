create table products.product (
                                  id bigserial primary key,
                                  product_identifier varchar(100) not null unique,
                                  nome varchar(100) not null,
                                  descricao varchar not null,
                                  preco float not null,
                                  category_id bigint not null,
                                  CONSTRAINT fk_product_category
                                      FOREIGN KEY (category_id)
                                          REFERENCES products.category(id)
                                          ON DELETE NO ACTION
                                          ON UPDATE NO ACTION

);

ALTER TABLE products.category
    ADD CONSTRAINT uk_category_nome UNIQUE (nome);
