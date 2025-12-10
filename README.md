# gerenciamento_estetica_automotiva

 
---

# 🧩 Diagrama de Componentes

<img width="1920" height="1080" alt="View" src="https://github.com/user-attachments/assets/77566d99-da73-4947-b4a6-d31cd62d1db9" />

---


---

# 🗃️ Diagrama de Entidade e Relacionamento (DER)

<img width="1920" height="1080" alt="Cliente" src="https://github.com/user-attachments/assets/4b79de1f-f5dd-4138-ab03-09a1b892decc" />



# 📑 Dicionário de Dados

A seguir está o dicionário de dados baseado no SQL implementado:

---

## **Tabela: cliente**

| Campo     | Tipo           | Obrigatório | Descrição |
|-----------|----------------|-------------|-----------|
| id        | SERIAL PK      | Sim         | Identificador único do cliente |
| nome      | VARCHAR(100)   | Sim         | Nome completo do cliente |
| telefone  | VARCHAR(20)    | Não         | Telefone do cliente |
| email     | VARCHAR(100)   | Não         | Email do cliente |
| cpf       | VARCHAR(14)    | Não         | CPF do cliente |

---

## **Tabela: veiculo**

| Campo       | Tipo          | Obrigatório | Descrição |
|-------------|---------------|-------------|-----------|
| id          | SERIAL PK     | Sim         | Identificador do veículo |
| cliente_id  | INTEGER FK    | Sim         | Cliente dono do veículo |
| placa       | VARCHAR(10)   | Sim         | Placa do veículo |
| modelo      | VARCHAR(50)   | Sim         | Modelo do veículo |
| ano         | INTEGER       | Não         | Ano de fabricação |
| categoria   | VARCHAR(20)   | Não         | Categoria (sedan, SUV, etc.) |

---

## **Tabela: servico**

| Campo         | Tipo            | Obrigatório | Descrição |
|---------------|-----------------|-------------|-----------|
| id            | SERIAL PK       | Sim         | Identificador do serviço |
| cliente_id    | INTEGER FK      | Sim         | Cliente relacionado |
| veiculo_id    | INTEGER FK      | Sim         | Veículo relacionado |
| tipo_servico  | VARCHAR(50)     | Sim         | Tipo do serviço realizado |
| preco         | DECIMAL(10,2)   | Sim         | Valor do serviço |
| data_servico  | TIMESTAMP       | Não         | Data do serviço (padrão: agora) |

---

## **Tabela: servicos** (tabela temporária para trigger)

| Campo             | Tipo            | Descrição |
|------------------|------------------|-----------|
| id               | SERIAL PK        | Identificador |
| nome_cliente      | VARCHAR(100)     | Nome do cliente |
| telefone_cliente  | VARCHAR(20)      | Telefone |
| email_cliente     | VARCHAR(100)     | Email |
| cpf_cliente       | VARCHAR(20)      | CPF |
| placa             | VARCHAR(10)      | Placa do veículo |
| modelo            | VARCHAR(50)      | Modelo |
| ano               | INTEGER          | Ano |
| categoria         | VARCHAR(50)      | Categoria |
| tipo_servico      | VARCHAR(100)     | Tipo de serviço |
| preco             | NUMERIC(10,2)    | Preço |

---

# ⚙️ Trigger: distribuir_dados_servicos()

Uma trigger é usada para distribuir automaticamente os dados da tabela `servicos` para:

- cliente  
- veiculo  
- servico  

Assim, a tabela `servicos` funciona como um "depósito de entrada".

---

#Codigo SQL

```sql
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
```





