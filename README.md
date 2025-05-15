🔍 Endpoints Disponíveis
1. Clientes
POST /clientes - Criar novo cliente

GET /clientes/{cpf} - Buscar cliente por CPF

GET /clientes - Listar todos clientes

2. Contas
POST /contas - Criar nova conta

GET /contas/{id} - Buscar conta por ID

POST /contas/{id}/sacar - Realizar saque

POST /contas/{id}/depositar - Realizar depósito

3. Transações
GET /transacoes/conta/{contaId} - Listar transações de uma conta



-- Inserir clientes
INSERT INTO Cliente (nome, email, cpf, telefone, data_nascimento) VALUES
('João Silva', 'joao.silva@email.com', '12345678901', '11999998888', '1985-05-15'),
('Maria Oliveira', 'maria.oliveira@email.com', '98765432100', '21988887777', '1990-08-22'),
('Carlos Souza', 'carlos.souza@email.com', '45678912345', '31977776666', '1978-11-30'),
('Ana Costa', 'ana.costa@email.com', '78912345678', '41966665555', '1995-03-10'),
('Pedro Santos', 'pedro.santos@email.com', '32165498700', '51955554444', '1982-07-25');

-- Inserir contas financeiras
INSERT INTO conta_financeira (cliente_id, saldo, limite_credito, tipo_conta) VALUES
(1, 2500.00, 1000.00, 'CORRENTE'),
(2, 1800.50, 500.00, 'CORRENTE'),
(3, 3500.75, 1500.00, 'POUPANCA'),
(4, 4200.00, 2000.00, 'CORRENTE'),
(5, 1500.30, 800.00, 'POUPANCA'),
(1, 5000.00, 0.00, 'POUPANCA'),  -- Cliente João tem uma segunda conta
(3, 2000.00, 1000.00, 'CORRENTE'); -- Cliente Carlos tem uma segunda conta

-- Inserir transações
INSERT INTO Transacao (conta_id, tipo, valor, descricao) VALUES
(1, 'DEPOSITO', 1000.00, 'Depósito inicial'),
(1, 'SAQUE', 200.00, 'Saque em caixa eletrônico'),
(2, 'DEPOSITO', 500.00, 'Depósito via transferência'),
(3, 'DEPOSITO', 1500.00, 'Depósito inicial'),
(4, 'DEPOSITO', 2000.00, 'Depósito inicial'),
(5, 'DEPOSITO', 800.00, 'Depósito inicial'),
(1, 'SAQUE', 300.00, 'Saque em agência'),
(2, 'SAQUE', 100.50, 'Saque em caixa eletrônico'),
(3, 'DEPOSITO', 500.00, 'Depósito via PIX'),
(4, 'SAQUE', 700.00, 'Saque em agência'),
(5, 'DEPOSITO', 200.30, 'Depósito via TED'),
(6, 'DEPOSITO', 5000.00, 'Depósito inicial poupança'),
(7, 'DEPOSITO', 2000.00, 'Depósito inicial conta corrente'),
(1, 'DEPOSITO', 1500.00, 'Transferência recebida'),
(2, 'SAQUE', 250.00, 'Pagamento de conta');
