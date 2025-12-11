CREATE TABLE cliente (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(100),
    cpf VARCHAR(14)
);

CREATE TABLE veiculo (
    id SERIAL PRIMARY KEY,
    cliente_id INTEGER NOT NULL,
    placa VARCHAR(10) NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    ano INTEGER,
    categoria VARCHAR(20),
    CONSTRAINT fk_cliente FOREIGN KEY (cliente_id)
        REFERENCES cliente(id)
        ON DELETE CASCADE
);

CREATE TABLE servico (
    id SERIAL PRIMARY KEY,
	cliente_id INTEGER NOT NULL,
    veiculo_id INTEGER NOT NULL,
    tipo_servico VARCHAR(50) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    data_servico TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_veiculo FOREIGN KEY (veiculo_id)
        REFERENCES veiculo(id)
        ON DELETE CASCADE,
	CONSTRAINT fk_cliente FOREIGN KEY (cliente_id)
        REFERENCES cliente(id)
		ON DELETE CASCADE
);

CREATE TABLE servicos (
    id SERIAL PRIMARY KEY,
    nome_cliente VARCHAR(100),
    telefone_cliente VARCHAR(20),
    email_cliente VARCHAR(100),
    cpf_cliente VARCHAR(20),
    placa VARCHAR(10),
    modelo VARCHAR(50),
    ano INTEGER,
    categoria VARCHAR(50),
    tipo_servico VARCHAR(100), -- renomeado para bater com servico.tipo_servico
    preco NUMERIC(10,2)
);

CREATE OR REPLACE FUNCTION distribuir_dados_servicos()
RETURNS TRIGGER AS $$
DECLARE
    novo_cliente_id INTEGER;
    novo_veiculo_id INTEGER;
BEGIN
    -- Inserir cliente
    INSERT INTO cliente (nome, telefone, email, cpf)
    VALUES (NEW.nome_cliente, NEW.telefone_cliente, NEW.email_cliente, NEW.cpf_cliente)
    RETURNING id INTO novo_cliente_id;

    -- Inserir veículo
    INSERT INTO veiculo (cliente_id, placa, modelo, ano, categoria)
    VALUES (novo_cliente_id, NEW.placa, NEW.modelo, NEW.ano, NEW.categoria)
    RETURNING id INTO novo_veiculo_id;

    -- Inserir serviço
    INSERT INTO servico (cliente_id,veiculo_id, tipo_servico, preco)
    VALUES (novo_cliente_id,novo_veiculo_id, NEW.tipo_servico, NEW.preco);

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_distribuir_dados
AFTER INSERT ON servicos
FOR EACH ROW
EXECUTE FUNCTION distribuir_dados_servicos();
