create database profParticular;

create table prof_particular(
	cpf varchar(14) unique primary key,
	nome varchar(50) not null,
	telefone varchar(20),
	cidade varchar(20),
	bairro varchar(20),
	rua varchar(20),
	numero varchar(20),
	apartamento varchar(20)
);

create table aulas(
	cpf varchar(14) unique,
	codigo varchar(20) unique,
	cronograma text,
	horario varchar(20),
	fk_prof_particular_cpf varchar(14),
	constraint fk_aulas_prof_particular foreign key (cpf) references prof_particular(cpf),
	primary key(codigo, cpf)
);

create table lista_alunos(
	
	codigo varchar(20),
	constraint fk_Aulas_codigo foreign key (codigo) references aulas(codigo),
	primary key(codigo)
);

#---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

create table matematica(
	codigo varchar(20),
	apostilas text,
	questoes text,
	constraint fk_Aulas_codigo_M foreign key (codigo) references aulas(codigo),
	primary key(codigo)
);

create table fisica(
	codigo varchar(20),
	formulas text,
	apostilas text,
	questoes text,
	constraint fk_Aulas_codigo_F foreign key (codigo) references aulas(codigo),
	primary key(codigo)
);

create table ingles(
	codigo varchar(20),
	apostilas text,
	questoes text,
	constraint fk_Aulas_codigo_I foreign key (codigo) references aulas(codigo),
	primary key(codigo)
);

create table fontes_de_apoio(
	fk_Ingles_fk_Aulas_codigo varchar(20),
	constraint fk_Ingles_fk_Aulas_codigo foreign key (fk_Ingles_fk_Aulas_codigo) references ingles(codigo),
	primary key(fk_Ingles_fk_Aulas_codigo)
);

#---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

create table alunos(
	cpf varchar(14) unique,
	nome varchar(50) not null,
	contato varchar(50)
);

create table materias_cursadas(
	cpf varchar(14),
	constraint fk_Alunos_cpf foreign key (cpf) references alunos(cpf),
	primary key(cpf)
);

create table recomendam(
	cpf varchar(14),
	constraint Aluno_recomenda foreign key (cpf) references alunos(cpf),
	constraint Aluno_recomendado foreign key (cpf) references alunos(cpf),
	primary key(cpf)
);

create table assistem(
	codigo varchar(20),
	cpf varchar(14),
	constraint fk_Aulas_codigo_assistem foreign key (codigo) references aulas(codigo),
	constraint fk_Alunos_cpf_assistem foreign key (cpf) references alunos(cpf),
	primary key(codigo, cpf)
);

#---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

create table ensino_medio(

);

create table prova(

);

create table fazem(

);

#---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

