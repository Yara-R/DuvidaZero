CREATE DATABASE DuvidaZero;

CREATE TABLE Prof_particular (
    cpf VARCHAR(14) UNIQUE PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(50),
    cidade VARCHAR(20),
    bairro VARCHAR(20),
    rua VARCHAR(20),
    numero int,
    apartamento VARCHAR(20)
);



CREATE TABLE Aulas (
    codigoA VARCHAR(20) UNIQUE,
    cronograma TEXT,
    horario VARCHAR(20)
);

CREATE TABLE Matematica (
    codigoA VARCHAR(20),
    apostilas TEXT,
    questoes TEXT,
    CONSTRAINT fk_Aulas_codigo_M FOREIGN KEY (codigoA) REFERENCES Aulas(codigoA),
    PRIMARY KEY(codigoA)
);

CREATE TABLE Fisica (
    codigoA VARCHAR(20),
    formulas TEXT,
    apostilas TEXT,
    questoes TEXT,
    CONSTRAINT fk_Aulas_codigo_F FOREIGN KEY (codigoA) REFERENCES Aulas(codigoA),
    PRIMARY KEY(codigoA)
);

CREATE TABLE Ingles (
    codigoA VARCHAR(20),
    apostilas TEXT,
    questoes TEXT,
    CONSTRAINT fk_Aulas_codigo_I FOREIGN KEY (codigoA) REFERENCES Aulas(codigoA),
    PRIMARY KEY(codigoA)
);

CREATE TABLE fontes_de_apoio (
    fk_Ingles_fk_Aulas_codigo VARCHAR(20),
    referencia varchar(100),
    CONSTRAINT fk_Ingles_fk_Aulas_codigo FOREIGN KEY (fk_Ingles_fk_Aulas_codigo) REFERENCES Ingles(codigoA),
    PRIMARY KEY(fk_Ingles_fk_Aulas_codigo)
);

CREATE TABLE Turmas (
    codigoT VARCHAR(20) UNIQUE
);

CREATE TABLE ensina (
    cpf_prof_particular VARCHAR(14),
    codigo_turma VARCHAR(20),
    codigo_aula VARCHAR(20),
    CONSTRAINT fk_prof_particular FOREIGN KEY (cpf_prof_particular) REFERENCES Prof_particular(cpf),
    CONSTRAINT fk_turma FOREIGN KEY (codigo_turma) REFERENCES Turmas(codigoT),
    CONSTRAINT fk_aula FOREIGN KEY (codigo_aula) REFERENCES Aulas(codigoA)
);



CREATE TABLE backup_ensina (
    cpf_prof_particular VARCHAR(14),
    codigo_turma VARCHAR(20),
    codigo_aula VARCHAR(20)
);


DELIMITER //

CREATE TRIGGER backup_ensina
BEFORE DELETE ON ensina
FOR EACH ROW
BEGIN
    INSERT INTO backup_ensina (cpf_prof_particular, codigo_turma, codigo_aula)
    VALUES (OLD.cpf_prof_particular, OLD.codigo_turma, OLD.codigo_aula);
END;
//

DELIMITER ;

CREATE TABLE alunos (
    cpf VARCHAR(14) UNIQUE,
    nome VARCHAR(50) NOT NULL,
    contato VARCHAR(50)
);

CREATE TABLE composta (
    codigo_turma VARCHAR(20),
    cpf VARCHAR(14),
    CONSTRAINT fk_turma2 FOREIGN KEY (codigo_turma) REFERENCES Turmas(codigoT),
    CONSTRAINT fk_aluno FOREIGN KEY (cpf) REFERENCES alunos(cpf)
);

CREATE TABLE recomendam (
    cpf VARCHAR(14),
    cpf_recomendado VARCHAR(14), -- Adicionando a definição da coluna
    CONSTRAINT Aluno_recomenda PRIMARY KEY(cpf), -- Movendo a definição da chave primária para cá
    CONSTRAINT FK_Aluno_recomenda FOREIGN KEY (cpf) REFERENCES alunos(cpf)
);


CREATE TABLE PreVestibular (
    id INT PRIMARY KEY AUTO_INCREMENT,
    cpf VARCHAR(14),
    CONSTRAINT fk_alunoPV FOREIGN KEY (cpf) REFERENCES alunos(cpf)
);

CREATE TABLE Vestibular_Desejado (
    fk_prevestibular INT,
    CONSTRAINT fk_prevestibular FOREIGN KEY (fk_prevestibular) REFERENCES PreVestibular(id),
    vestibular varchar(10)
);

CREATE TABLE Ensino_Medio(
    nome_responsavel VARCHAR(50) NOT NULL,
    contato_responsavel VARCHAR(50) NOT NULL,
    ano_escolar VARCHAR(10),
    colegio VARCHAR(50),
    cpf VARCHAR(14),
    CONSTRAINT pk_Ensino_Medio PRIMARY KEY (cpf),
    CONSTRAINT fk_alunoEM FOREIGN KEY (cpf) REFERENCES alunos(cpf)
);




INSERT INTO Prof_particular (cpf, nome, telefone, email, cidade, bairro, rua, numero, apartamento)
VALUES
('059.843.524-78', 'Yara Faran', '(81) 995594885', 'yri@cesar.school', 'Recife', 'Boa Viagem', 'rua Setúbal', 1314, 'Apto 101'),
('123.456.789-01', 'Pedro Alves', '(81) 987654321', 'pedro.alves@email.com', 'Recife', 'Pina', 'Rua da Praia', 123, 'Apto 202'),
('987.654.321-09', 'Carla Lima', '(81) 999998888', 'carla.lima@email.com', 'Olinda', 'Bairro Novo', 'Rua dos Coqueiros', 456, 'Apto 303'),
('222.333.444-33', 'Marcos Santos', '(81) 987654321', 'marcos.santos@email.com', 'Recife', 'Boa Viagem', 'Rua dos Girassóis', 789, 'Apto 404'),
('333.444.555-44', 'Larissa Oliveira', '(81) 999999999', 'larissa.oliveira@email.com', 'Olinda', 'Cidade Tabajara', 'Avenida Central', 101, 'Apto 202');


INSERT INTO Aulas (codigoA, cronograma, horario)
VALUES
('MAT001', 'Semana 1 ...; Semana 2...; Semana 3...', 'Segunda 15h-16h'),
('MAT002', 'Semana 1 ...; Semana 2...; Semana 3...', 'Quarta, 16h-17h'),
('ING001', 'Semana 1 ...; Semana 2...; Semana 3...', 'Sábado, 10h-12h'),
('FIS001', 'Semana 1 ...; Semana 2...; Semana 3...', 'Quinta, 14h-15h'),
('MAT003', 'Semana 1 ...; Semana 2...; Semana 3...', 'Terça, 14h-16h'),
('ING002', 'Semana 1 ...; Semana 2...; Semana 3...', 'Sábado, 13h-15h'),
('FIS002', 'Semana 1 ...; Semana 2...; Semana 3...', 'Segunda, 18h-20h'),
('ING003', 'Semana 1 ...; Semana 2...; Semana 3...', 'Quinta, 8h-10h'),
('MAT004', 'Semana 1 ...; Semana 2...; Semana 3...', 'Quarta, 14h-16h'),
('FIS003', 'Semana 1 ...; Semana 2...; Semana 3...', 'Sexta, 9h-11h');


INSERT INTO Matematica (codigoA, apostilas, questoes)
VALUES
('MAT001', 'Apostilas de Matemática 1', 'Questões...'),
('MAT002', 'Apostilas de Matemática 2', 'Questões ...'),
('MAT003', 'Apostilas de Matemática 3', 'Questões ...'),
('MAT004', 'Apostilas de Matemática 4', 'Questões ...');


INSERT INTO Fisica (codigoA, formulas, apostilas, questoes)
VALUES
('FIS001', 'Fórmulas de Física...', 'Apostilas de Física...', 'Questões...'),
('FIS002', 'Fórmulas de Física...', 'Apostilas de Física...', 'Questões...'),
('FIS003', 'Fórmulas de Física...', 'Apostilas de Física...', 'Questões...');


INSERT INTO Ingles (codigoA, apostilas, questoes)
VALUES
('ING001', 'Apostilas de Inglês...', 'Questões...'),
('ING002', 'Apostilas de Inglês...', 'Questões...'),
('ING003', 'Apostilas de Inglês...', 'Questões...');


INSERT INTO fontes_de_apoio (fk_Ingles_fk_Aulas_codigo, referencia)
VALUES 
('ING001', 'pt.duolingo.com'),
('ING002', 'www.todamateria.com.br/verbos-regulares-e-irregulares-no-ingles/'),
('ING003', 'dictionary.cambridge.org/pt/dicionario/ingles-portugues/');


INSERT INTO Turmas (codigoT)
VALUES ('TURMA001'), ('TURMA002'), ('TURMA003'), ('TURMA004'), ('TURMA005'), ('TURMA006');


INSERT INTO ensina (cpf_prof_particular, codigo_turma, codigo_aula)
VALUES
('059.843.524-78', 'TURMA001', 'MAT001'),
('059.843.524-78', 'TURMA002', 'FIS001'),
('123.456.789-01', 'TURMA003', 'ING001'),
('987.654.321-09', 'TURMA004', 'MAT002'),
('222.333.444-33', 'TURMA005', 'FIS002'),
('333.444.555-44', 'TURMA006', 'ING002');


INSERT INTO alunos (cpf, nome, contato)
VALUES
('111.222.333-00', 'Rebeka Albuquerque', '(81) 99842-0957'),
('444.555.666-00', 'João Silva', '(81) 99999-9999'),
('222.333.444-11', 'Lucas Xavier', '(81) 98765-4321'),
('555.666.777-22', 'Ana Costa', '(81) 98888-7777'),
('666.777.888-99', 'Paulo Pereira', '(81) 987654321'),
('777.888.999-00', 'Amanda Souza', '(81) 999999999');


INSERT INTO composta (codigo_turma, cpf)
VALUES
('TURMA001', '111.222.333-00'),
('TURMA002', '444.555.666-00'),
('TURMA003', '222.333.444-11'),
('TURMA004', '555.666.777-22'),
('TURMA005', '666.777.888-99'),
('TURMA006', '777.888.999-00');


INSERT INTO recomendam (cpf, cpf_recomendado)
VALUES
('111.222.333-00', '444.555.666-00'),
('222.333.444-11', '555.666.777-22'),
('666.777.888-99', '777.888.999-00');


INSERT INTO PreVestibular (cpf)
VALUES
('111.222.333-00'), ('444.555.666-00'),
('222.333.444-11'), ('555.666.777-22'),
('666.777.888-99'), ('777.888.999-00');


INSERT INTO Vestibular_Desejado (fk_prevestibular, vestibular)
VALUES
(1, 'ENEM'),
(2, 'AFA'),
(3, 'USP'),
(4, 'EEAR'),
(5, 'ENEM'),
(6, 'ENEM');


INSERT INTO Ensino_Medio (nome_responsavel, contato_responsavel, ano_escolar, colegio, cpf)
VALUES
('Maria Oliveira', '(81) 5555-5555', '2º ano', 'Colégio Santa Maria', '111.222.333-00'),
('José Santos', '(81) 4444-4444', '3º ano', 'Colégio Boa Viagem', '444.555.666-00'),
('Lucas Xavier', '(81) 98765-4321', '2º ano', 'Cognitivo', '222.333.444-11'),
('Ana Costa', '(81) 98888-7777', '3º ano', 'Damas', '555.666.777-22'),
('Fernanda Silva', '(81) 5555-5555', '2º ano', 'Motivo', '666.777.888-99'),
('Ricardo Almeida', '(81) 4444-4444', '3º ano', 'Colégio Santos Dummont', '777.888.999-00');

