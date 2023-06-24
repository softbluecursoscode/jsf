# Este script insere alguns dados de exemplo nas tabelas PROPRIETARIO E VEICULO

insert into proprietario (id, cpf, nome) values (1, '03929486122', 'José Oliveira');
insert into proprietario (id, cpf, nome) values (2, '24789204758', 'Marina Rabelo');
insert into proprietario (id, cpf, nome) values (3, '95738728759', 'Priscila Mota');
insert into proprietario (id, cpf, nome) values (4, '28765873904', 'Renato Silva');

insert into veiculo (placa, marca, modelo, ano, proprietario_id) values ('ABD4872', 'Renault', 'Clio', 2010, 1);
insert into veiculo (placa, marca, modelo, ano, proprietario_id) values ('FGH4873', 'Citroen', 'C3', 2012, 1);
insert into veiculo (placa, marca, modelo, ano, proprietario_id) values ('EGA3671', 'BMW', 'X5', 2011, 2);
insert into veiculo (placa, marca, modelo, ano, proprietario_id) values ('IKQ2928', 'Volkswagen', 'Gol', 2010, 3);
insert into veiculo (placa, marca, modelo, ano, proprietario_id) values ('IKQ2928', 'Chevrolet', 'Corsa', 2012, 4);
