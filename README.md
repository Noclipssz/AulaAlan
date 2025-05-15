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
