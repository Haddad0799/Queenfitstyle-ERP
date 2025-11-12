# 🧩 QueenFitStyle ERP

> **ERP Modular Monolith** para gestão completa de catálogo e estoque de um e-commerce de roupas de academia feminina.  
> Projeto desenvolvido em **Java + Spring Boot**, com arquitetura **Hexagonal (Ports & Adapters)** e princípios de **Clean Architecture** e **DDD**.

---

## 🧭 Visão Geral

O **QueenFitStyle ERP** é um sistema projetado sob o princípio **_Monolith First_**, com **modularização explícita**, visando evolução futura para **microsserviços independentes**.

Cada módulo encapsula suas próprias regras de negócio, entidades e casos de uso, mantendo o **baixo acoplamento** e a **alta coesão** entre as camadas.  
Essa separação permite evoluir o projeto de forma escalável, mantendo uma arquitetura limpa, sustentável e com clara separação entre **domínio, aplicação e infraestrutura**.

---

## 🏗️ Arquitetura

O sistema segue a **Arquitetura Hexagonal (Ports and Adapters)** combinada com **Clean Architecture**, mantendo o domínio completamente **independente de frameworks, banco de dados ou drivers externos**.

       +---------------------------+
       |         Interface         |
       |   (REST Controllers)      |
       +-------------+-------------+
                     |
                     v
       +---------------------------+
       |        Application        |
       | (Use Cases / Services)    |
       +-------------+-------------+
                     |
                     v
       +---------------------------+
       |          Domain           |
       | (Entities / VOs / Rules)  |
       +-------------+-------------+
                     |
                     v
       +---------------------------+
       |       Infrastructure      |
       | (JPA, S3, DB, etc.)       |
       +---------------------------+

       
### 🔹 **Principais Princípios Aplicados**
- **DDD (Domain-Driven Design):** entidades e agregados modelados segundo as regras de negócio.  
- **Clean Architecture:** domínio independente de frameworks.  
- **Hexagonal Architecture:** comunicação via _ports_ (interfaces) e _adapters_ (implementações concretas).  
- **Monolith First:** modularização interna isolando contextos antes da migração para microsserviços.  
- **Separation of Concerns:** divisão clara entre camadas de domínio, aplicação e infraestrutura.  

---

## 📦 Módulos

### 1. **App Module**
Responsável pela inicialização da aplicação e pela orquestração dos módulos internos.  
Contém a classe principal `Application.java` e a configuração base da aplicação Spring Boot.

---

### 2. **Catalog Module**
Gerencia **produtos, SKUs, categorias, cores e imagens**.  
É o núcleo principal do ERP, lidando com o domínio de catálogo e estoque.

**Principais responsabilidades:**
- Cadastro e manutenção de **produtos** e **SKUs**.  
- Validação de duplicidade, cores e tamanhos.  
- Mapeamento entre produto base e suas variações.  
- Controle de estoque por SKU.  
- Associação de múltiplas imagens a cada SKU.  

**Principais entidades:**
- `Product`
- `Sku`
- `Category`
- `Color`
- `Image`
- `SkuImage`

**Camadas internas:**
- **domain:** entidades, VOs e regras de negócio.  
- **application:** casos de uso (`usecases`), comandos (`commands`) e eventos (`events`).  
- **infra:** repositórios JPA, mappers e adapters de storage (S3).  
- **web:** controladores REST e DTOs de entrada e saída.  

---

### 3. **Import Module**
Incluído dentro do **Catalog Module**, este componente gerencia a **importação em massa de produtos e SKUs** via planilhas Excel.

**Características:**
- Geração automática de **template Excel** para preenchimento.  
- Validação de:
  - Cores existentes no sistema  
  - Produtos e SKUs duplicados  
  - Estoque zerado ou negativo  
  - Preços inválidos (negativos ou inconsistentes)
- Processamento em **lote (batch)** para otimizar o uso de memória e reduzir o número de queries no banco.  
- Separação dos registros válidos e inválidos, permitindo importação parcial sem bloqueios.

**Tecnologias utilizadas:**

- **Spring Batch** e **JPA batch inserts** para processamento otimizado.  

---

### 4. **Storage Module**
Gerencia o **upload e armazenamento de imagens** de produtos e SKUs.

**Destaques técnicos:**
- Upload via **Presigned URLs (S3 compatible)** — o cliente envia o arquivo direto para o bucket, sem passar pelo backend.  
- Redução significativa de carga no servidor de aplicação.  
- Separação entre o domínio e a infraestrutura de armazenamento (seguindo Ports & Adapters).

**Fluxo resumido:**
1. O backend gera um **presigned URL** temporário.  
2. O cliente envia a imagem diretamente para o bucket.  
3. O ERP armazena apenas a URL pública no banco.  

---

## ⚙️ Tecnologias Utilizadas

| Categoria | Ferramentas |
|------------|-------------|
| **Linguagem** | Java 21 |
| **Framework Principal** | Spring Boot 3+ |
| **Arquitetura** | Clean + Hexagonal (Ports & Adapters) |
| **Banco de Dados** | MySQL |
| **ORM** | Spring Data JPA (com batch inserts e fetch otimizado) |
| **Storage** | ou MinIO local(pronto para migrar para gcp,aws ou azure) |
| **Build Tool** | Maven |



---

## ⚡ Otimizações de Performance

### 🔸 Processamento em Lote
Durante a importação, os produtos e SKUs são salvos em **batches**, reduzindo drasticamente o overhead de transações e o número de _flushes_ no banco de dados.

- Configuração de `spring.jpa.properties.hibernate.jdbc.batch_size`  
- Uso de `EntityManager.flush()` e `clear()` controlados manualmente a cada lote.  
- Agrupamento de SKUs por produto para evitar queries redundantes.

### 🔸 Queries Otimizadas
- Consultas paginadas e com _fetch joins_ para evitar _N+1 problems_.  
- Repositórios especializados para carregamento de produtos e SKUs com dados agregados.  

### 🔸 Upload via Presigned URLs
- Evita que imagens passem pelo backend.  
- Aumenta a escalabilidade e reduz o tempo de resposta em operações de upload.

---

## 🧠 Decisões Arquiteturais

| Decisão | Justificativa |
|----------|----------------|
| **Monolith First** | Facilita a construção de um domínio sólido antes da divisão em microsserviços. |
| **Hexagonal Architecture** | Garante baixo acoplamento e fácil substituição de tecnologias. |
| **Batch Processing** | Reduz o consumo de memória e aumenta a eficiência nas importações. |
| **Presigned URLs** | Aumenta a performance e diminui a carga do backend em uploads. |



