
/**
 * Radical Motos
 * @author Professor José de Assis
 */

drop database dbradicalmotos;

create database dbradicalmotos;
show databases;
use dbradicalmotos;

-- unique não permite valore duplicados
create table usuarios (
	iduser int primary key auto_increment,
	nome varchar(30) not null,
    login varchar(20) not null unique,
    senha varchar(250) not null,
    perfil varchar(5) not null
);

describe usuarios;

-- md5() gera um hash (criptografia)
insert into usuarios(nome,login,senha,perfil)
values ('Administrador','admin', md5('admin'),'admin');

select * from usuarios;

-- autenticar um usuário (logar)
select * from usuarios where login = 'admin' and senha = md5('admin');

-- update
update usuarios set nome='Administrador', login='admin',
senha=md5('admin') where iduser=1;
-- pesquisa avançada 
select * from usuarios;
select * from usuarios where nome like 'i%' order by nome;


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

drop table clientes;
select * from clientes;

describe clientes;
select * from  clientes;

drop database dbradicalmotos;

/********** relacionamento de tabelas 1 - n ***************/
-- decimal (10,2) tipos de dados (numeros não inteiros)
-- foreign key(idcli)campo da tabela os que sera vinculada 
-- references clientes(idcli) 
-- date (tipos de dados par  trabalhar com datas)
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

describe servicos;
drop table servicos;

insert into servicos ( bicicleta,defeito,statusOS,idcli,dataEntrega) values('monark aro 26','roda solta','analise',1,'/03/2024');
insert into servicos ( bicicleta,defeito,diagnostico,valor,statusOS,idcli,dataEntrega,mecanico,peçasUtil,iduser) values('?','?','?','?','?','?','?','?','?','?');
select * from servicos;

delete from clientes where idcli= 1;

insert into servicos ( bicicleta,defeito,diagnostico,valor,statusOS,idcli,dataEntrega,mecanico,peçasUtil,iduser) values('monar','roda solta','troca de garfo','1000','pendente','1','20231215','isaac','roda','1');


-- formatando a data para o padrão brasileiro
select os,bicicleta,defeito,diagnostico,statusOS,valor,idcli,iduser,date_format(dataEntrega,'%d/%m/%y') as dataEntrega,date_format(dataOS,'%d/%m/%y %H:%m')as dataOS ,mecanico,peçasUtil from servicos;

create table mecanicos (
idmecanico int primary key auto_increment,
nome varchar(30) not null,
nick varchar(30) not null unique,
fone varchar(15)not null
);
drop table mecanicos;
describe mecanicos;



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


insert into Fornecedores (razaosocial,cnpj,email,site,tel,cep,endereco,bairro,cidade,uf,num,Com) values('razaosocial','cnpj','email','site','tel','cep','endereco','bairro','cidade','uf','num','Com'); 
select * from Fornecedores;
drop table Fornecedores;
describe Fornecedores;


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
foreign key(idfor) references Fornecedores(idfor)
);




select * from servicos;

insert into mecanicos (nome,nick,fone) values('isaac','cap.uatizap','111111111');
insert into mecanicos (idmecanico,nome,nick,fone) values(0,'isaac','cap.uatizap','111111111');

select * from mecanicos;

/**relatorio**/

-- faturamento

select sum(valor) as faturamento  from servicos;

select nome,cel,email from clientes order by nome;

select os,bicicleta,defeito,date_format(dataOS,'%d/%m/%y %H:%m')as dataOS, IDcli from servicos where statusOS = "Aguardando Mecanico";

-- formato 3

select os,bicicleta,defeito,date_format(dataOS,'%d/%m/%y %H:%m')as dataOS, mecanicos.nome as nome_mecanico,mecanicos.idmecanico from servicos inner join mecanicos on servicos.idmecanico = mecanicos.idmecanico where statusOS = "Aguardando Aprovação";

select os,bicicleta,defeito,date_format(dataOS,'%d/%m/%y %H:%')as dataOS,valor, clientes.nome as nome_cliente,clientes.cel, clientes.email from servicos inner join clientes on servicos.idcli = clientes.idcli where statusOS = "Aguardando Aprovação";

select os,bicicleta,defeito,date_format(dataOS,'%d/%m/%y %H:%m')as dataOS, mecanicos.nome as nome_mecanico,peçasUtil  from servicos inner join mecanicos on servicos.idmecanico = mecanicos.idmecanico where statusOS = "Aguardando Peças";

select * from servicos;
-- rascunho 
select * from servicos inner join clientes on servicos.idcli = clientes.idcli where statusOS = "Aguardando Mecanico";


select * from servicos;

select os,bicicleta,defeito,.diagnostico,mecanicos.nome,servicos.valor,(servicos.dataOS,'%d/%m/%y %H:%m')as dataOS,date_format(servicos.dataEntrega,'%d/%m/%y') as dataEntrega ,clientes.nome as cliente,clientes.cel from servicos inner join clientes on servicos.idcli = clientes.idcli inner join mecanicos on servicos.idmecanico = mecanicos.idmecanico where statusOS = "Entregue";

select os,bicicleta,defeito,.diagnostico,mecanicos.nome,servicos.valor,(servicos.dataOS,'%d/%m/%y %H:%m')as dataOS,date_format(servicos.dataEntrega,'%d/%m/%y') as dataEntrega ,clientes.nome as cliente,clientes.cel from servicos inner join clientes on servicos.idcli = clientes.idcli inner join mecanicos on servicos.idmecanico = mecanicos.idmecanico where statusOS = "Aguardando Aprovação";


select servicos.os,
date_format(servicos.dataOS,'%d/%m/%Y - %H:%i') as data_entrada,
servicos.usuario as usuário,
servicos.bicicleta,servicos.defeito,servicos.statusOS as status_OS,servicos.diagnostico,
mecanicos.nome as mecânico,
servicos.valor,
date_format(servicos.dataEntrega,'%d/%m/%Y') as data_saida,
clientes.nome as cliente, 
clientes.cel,
clientes.cep
from servicos
inner join clientes
on servicos.idcli = clientes.idcli
inner join mecanicos
on servicos.idmecanico = mecanicos.idmecanico;

select servicos.os, date_format(servicos.dataOS,'%d/%m/%Y - %H:%i') as data_entrada, servicos.usuario as usuário, servicos.bicicleta,servicos.defeito,servicos.statusOS as status_OS,servicos.diagnostico, mecanicos.nome as mecânico, servicos.valor, date_format(servicos.dataEntrega,'%d/%m/%Y') as data_saida, clientes.nome as cliente, clientes.cel, clientes.cep, clientes.email,clientes.endereco,clientes.num from servicos inner join clientes on servicos.idcli = clientes.idcli inner join mecanicos on servicos.idmecanico = mecanicos.idmecanico where os = 1;








select os,bicicleta,defeito,.diagnostico,mecanicos.nome,servicos.valor,(servicos.dataOS,'%d/%m/%y %H:%m')as dataOS,date_format(servicos.dataEntrega,'%d/%m/%y') as dataEntrega ,clientes.nome as cliente,clientes.cel from servicos inner join clientes on servicos.idcli = clientes.idcli inner join mecanicos on servicos.idmecanico = mecanicos.idmecanico where statusOS = "Aguardando Aprovação";



-- obtendo o valor do PK(OS) da ultima os cadastrada 

select max(os) from servicos



