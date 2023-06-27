
/**
 * Radical Bikes
 * @author Isaac Caua Mourão Da Silva
 */

use dbradicalmotos;

create table usuarios (
	iduser int primary key auto_increment,
	nome varchar(30) not null,
    login varchar(20) not null unique,
    senha varchar(250) not null,
    perfil varchar(5) not null
);

insert into usuarios(nome,login,senha,perfil) values ('Administrador','admin', md5('admin'),'admin');

create table clientes (
	idcli int primary key auto_increment,
	nome varchar(30) not null,
    cpf varchar(15) not null unique,
    email varchar(50) not null unique,
    cel varchar(15) not null,
    cep varchar(10) not null,
    endereco varchar(50) not null,
    bairro varchar(20) not null,
    cidade varchar(30) not null,
    uf varchar(4) not null,
    num varchar(5) not null,
    Com varchar(30)
);

create table mecanicos (
idmecanico int primary key auto_increment,
nome varchar(30) not null,
nick varchar(30) not null unique,
fone varchar(15)not null
);

create table servicos (
os int primary key auto_increment,
bicicleta varchar(200) not null,
defeito varchar(200) not null,
diagnostico varchar(200),
statusOS varchar(30) not null,
valor decimal(10,2),
dataOS timestamp default current_timestamp,
dataEntrega date,
mecanico varchar(30),
peçasUtil varchar(30),
idcli int not null,
idmecanico int,
usuario varchar(30) not null,
foreign key(idcli) references clientes(idcli),
foreign key(idmecanico) references mecanicos(idmecanico)
);



create table Fornecedores (
	idfor int primary key auto_increment,
	razaosocial varchar(30) not null,
    cnpj varchar(15) not null unique,
    email varchar(50) not null unique,
    site varchar(50),
    tel varchar(15) not null,
    cep varchar(10) not null,
    endereco varchar(50) not null,
    bairro varchar(20) not null,
    cidade varchar(30) not null,
    uf varchar(4) not null,
    num varchar(5) not null,
    Com varchar(30)
);

create table produtos (
idprod int primary key auto_increment,
barcode varchar(14),
descricao varchar(150),
estoque int not null,
estoquemin int not null,
valor decimal(10,2),
unidade_medida varchar(10)not null,
localarma varchar(10),
validade date,
idfor int not null,
nomeproduto varchar(50),
foreign key(idfor) references Fornecedores(idfor)
);






