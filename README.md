# 🏦 Hackathon Orbitall 2025 - Financial API

API REST desenvolvida em Java + Spring Boot para gerenciamento de clientes e registro de transações financeiras.

## 🛠️ Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.5.5**
- **Spring Data JPA**
- **H2 Database** (em memória)
- **Maven**
- **Lombok**
- **Bean Validation**

## 🚀 Como Executar a Aplicação

### Pré-requisitos
- Java 21 instalado
- Maven 3.6+ instalado
- IntelliJ IDEA (recomendado)

### Passos para execução:

1. **Clone o repositório:**
```bash
git clone <https://github.com/Derx1/desafio-final-hackathon-orbitall-2025>
cd desafio-final-hackathon-orbitall-2025-main/channels
```

2. **Compile e execute com Maven:**
```bash
mvn clean install
mvn spring-boot:run
```

3. **Ou execute pelo IntelliJ:**
- Abra o projeto na pasta `channels`
- Execute a classe `ChannelsApplication.java`

4. **Acesse a aplicação:**
- API: `http://localhost:8080`
- H2 Console: `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:channels`
  - User: `sa`
  - Password: (vazio)

## 📋 Endpoints da API

### 🧑‍💼 Clientes (/customers)

#### POST /customers - Criar Cliente
```http
POST http://localhost:8080/customers
Content-Type: application/json

{
    "fullName": "João Silva Santos",
    "email": "joao.silva@email.com",
    "phone": "(11) 99999-9999"
}
```

**Resposta (201 Created):**
```json
{
    "id": "e408e390-87af-418c-a6a4-8b8e37a08bf0",
    "fullName": "João Silva Santos",
    "email": "joao.silva@email.com",
    "phone": "(11) 99999-9999",
    "createdAt": "2025-09-20T11:48:30.220598",
    "updatedAt": "2025-09-20T11:48:30.220598",
    "active": true
}
```

#### GET /customers/{id} - Buscar Cliente por ID
```http
GET http://localhost:8080/customers/e408e390-87af-418c-a6a4-8b8e37a08bf0
```

**Resposta (200 OK):**
```json
{
    "id": "e408e390-87af-418c-a6a4-8b8e37a08bf0",
    "fullName": "João Silva Santos",
    "email": "joao.silva@email.com",
    "phone": "(11) 99999-9999",
    "createdAt": "2025-09-20T11:48:30.220598",
    "updatedAt": "2025-09-20T11:48:30.220598",
    "active": true
}
```

#### GET /customers - Listar Todos os Clientes
```http
GET http://localhost:8080/customers
```

**Resposta (200 OK):**
```json
[
    {
        "id": "e408e390-87af-418c-a6a4-8b8e37a08bf0",
        "fullName": "João Silva Santos",
        "email": "joao.silva@email.com",
        "phone": "(11) 99999-9999",
        "createdAt": "2025-09-20T11:48:30.220598",
        "updatedAt": "2025-09-20T11:48:30.220598",
        "active": true
    }
]
```

#### PUT /customers/{id} - Atualizar Cliente
```http
PUT http://localhost:8080/customers/e408e390-87af-418c-a6a4-8b8e37a08bf0a
Content-Type: application/json

{
    "fullName": "João Silva Santos Atualizado",
    "email": "joao.novo@email.com",
    "phone": "(11) 88888-8888"
}
```

**Resposta (200 OK):**
```json
{
    "id": "e408e390-87af-418c-a6a4-8b8e37a08bf0",
    "fullName": "João Silva Santos Atualizado",
    "email": "joao.silva.novo@email.com",
    "phone": "(11) 88888-8888",
    "createdAt": "2025-09-20T11:48:30.220598",
    "updatedAt": "2025-09-20T11:49:43.321509",
    "active": true
}
```
#### DELETE /customers/{id} - Excluir Cliente
```http
DELETE http://localhost:8080/customers/e408e390-87af-418c-a6a4-8b8e37a08bf0
```

**Resposta (200 OK):**
```json
{
    "id": "e408e390-87af-418c-a6a4-8b8e37a08bf0",
    "fullName": "João Silva Santos Atualizado",
    "email": "joao.silva.novo@email.com",
    "phone": "(11) 88888-8888",
    "createdAt": "2025-09-20T11:48:30.220598",
    "updatedAt": "2025-09-20T12:01:30.042439",
    "active": false
}
```

### 💳 Transações (/transactions)

#### POST /transactions - Criar Transação
```http
POST http://localhost:8080/transactions
Content-Type: application/json

{
    "customerId": "e408e390-87af-418c-a6a4-8b8e37a08bf0",
    "amount": 150.75,
    "cardType": "DÉBITO"
}
```

**Resposta (201 Created):**
```json
{
    "id": "cc8f571a-c3f5-4e76-a2f3-c59d0ef3e195",
    "customerId": "e408e390-87af-418c-a6a4-8b8e37a08bf0",
    "amount": 150.75,
    "cardType": "DÉBITO",
    "createdAt": "2025-09-20T11:50:24.855887",
    "active": true
}
```

#### GET /transactions?customerId={id} - Listar Transações por Cliente
```http
http://localhost:8080/transactions?customerId=e408e390-87af-418c-a6a4-8b8e37a08bf0
```

**Resposta (200 OK):**
```json
[
    {
        "id": "956626a9-d145-411a-9e96-ca67dc0b2f35",
        "customerId": "e408e390-87af-418c-a6a4-8b8e37a08bf0",
        "amount": 150.75,
        "cardType": "DÉBITO",
        "createdAt": "2025-09-20T12:00:39.232812",
        "active": true
    },
    {
        "id": "cc8f571a-c3f5-4e76-a2f3-c59d0ef3e195",
        "customerId": "e408e390-87af-418c-a6a4-8b8e37a08bf0",
        "amount": 150.75,
        "cardType": "DÉBITO",
        "createdAt": "2025-09-20T11:50:24.855887",
        "active": true
    }
]
```


## 📊 Códigos de Status HTTP

| Código | Descrição |
|--------|-----------|
| 200 | OK - Requisição bem-sucedida |
| 201 | Created - Recurso criado com sucesso |
| 400 | Bad Request - Dados inválidos |
| 404 | Not Found - Recurso não encontrado |
| 500 | Internal Server Error - Erro interno |

## 🔍 Exemplos de Erros

### Cliente não encontrado (404):
```json
{
    "message": "Cliente não encontrado (id: 00000000-0000-0000-0000-000000000000)"
}
```

### Dados inválidos (400):
```json
{
    "fullName": "Nome completo é obrigatório",
    "email": "E-mail deve ter um formato válido"
}
```

## 📈 Regras de Negócio

### Clientes:
- ✅ Nome completo obrigatório (máx. 255 caracteres)
- ✅ E-mail obrigatório e válido (máx. 100 caracteres)
- ✅ Telefone obrigatório
- ✅ E-mail deve ser único
- ✅ Exclusão é soft delete (active = false)

### Transações:
- ✅ Cliente deve existir e estar ativo
- ✅ Valor obrigatório e maior que zero
- ✅ Tipo de cartão: apenas "DÉBITO" ou "CRÉDITO"
- ✅ Data de criação automática

## 🗄️ Estrutura do Banco H2

### Tabela CUSTOMERS
| Campo | Tipo | Descrição |
|-------|------|-----------|
| ID | VARCHAR(255) | Identificador único (UUID) |
| ACTIVE | BOOLEAN | Status ativo/inativo |
| CREATED_AT | TIMESTAMP | Data de criação |
| UPDATED_AT | TIMESTAMP | Data de atualização |
| EMAIL | VARCHAR(100) | E-mail (único) |
| FULL_NAME | VARCHAR(255) | Nome completo |
| PHONE | VARCHAR | Telefone |

### Tabela TRANSACTIONS
| Campo | Tipo | Descrição |
|-------|------|-----------|
| ID | VARCHAR(255) | Identificador único (UUID) |
| ACTIVE | BOOLEAN | Status ativo/inativo |
| AMOUNT | DECIMAL(19,2) | Valor da transação |
| CREATED_AT | TIMESTAMP | Data da transação |
| CUSTOMER_ID | VARCHAR(255) | ID do cliente (chave estrangeira) |
| CARD_TYPE | VARCHAR | Tipo do cartão (DÉBITO/CRÉDITO) |

## 🧪 Testando com Postman

1. **Importe a collection** (se disponível)
2. **Configure o environment:**
   - Variable: `baseUrl`
   - Value: `http://localhost:8080`
3. **Sequência de testes recomendada:**
   - GET / (Welcome)
   - POST /customers (Criar cliente)
   - GET /customers (Listar clientes)
   - POST /transactions (Criar transação)
   - GET /transactions?customerId={id} (Listar transações)
   - DELETE /customers/{id} (Excluir cliente - soft delete)
   - GET /customers (Verificar que cliente foi desativado)

## 👨‍💻 Desenvolvedor

**André Xavier** - [https://github.com/Derx1]

## 📄 Licença

Este projeto foi desenvolvido para o Hackathon Orbitall 2025.