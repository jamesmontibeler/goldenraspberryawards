# Teste Texto IT

Teste de avaliação Texo IT

## Especificação do Teste

Desenvolva uma API RESTful para possibilitar a leitura da lista de indicados e vencedores da categoria Pior Filme do Golden Raspberry Awards.

### Requisito do sistema:

1. Ler o arquivo CSV dos filmes e inserir os dados em uma base de dados ao iniciar a aplicação.

### Requisitos da API:

1. Obter o produtor com maior intervalo entre dois prêmios consecutivos, e o queobteve dois prêmios mais rápido, seguindo a especificação de formato definida na página 2;

### Requisitos não funcionais do sistema:
1. O web service RESTful deve ser implementado com base no nível 2 de maturidade de Richardson;
2. Devem ser implementados somente testes de integração. Eles devem garantir que os dados obtidos estão de acordo com os dados fornecidos na proposta;
3. O banco de dados deve estar em memória utilizando um SGBD embarcado (por exemplo, H2). Nenhuma instalação externa deve ser necessária;
4. A aplicação deve conter um readme com instruções para rodar o projeto e os testes de integração. O código-fonte deve ser disponibilizado em um repositório git (Github, Gitlab, Bitbucket, etc.)

### Formato da API:

Intervalo de prêmios: 
```yaml
{
"min": [
{
"producer": "Producer 1",
"interval": 1,
"previousWin": 2008,
"followingWin": 2009
},
{
"producer": "Producer 2",
"interval": 1,
"previousWin": 2018,
"followingWin": 2019
}
],
"max": [
{
"producer": "Producer 1",
"interval": 99,
"previousWin": 1900,
"followingWin": 1999
},
{
"producer": "Producer 2",
"interval": 99,
"previousWin": 2000,
"followingWin": 2099
}
]
}
```

### Formato fonte de dados .CSV

Arquivo com as colunas abaixo (colunas e dados separados com ";"), compondo dados de um filme:
year: formato numérico. Valor obrigatório.
title: formato string. Valor obrigatório.
studios: formato string. Valor obrigatório.
producers: formato string, com nomes separados por vírgula e " and ". Valor obrigatório.
winner: formato string, com valores "yes", "no" ou "". Campo opcional.

# Documentação API
 
## Específicações técnicas


* Spring Tool Suite 4.13.0.RELEASE 
* Java 17
* Banco de dados H2, com armazenamento em memória (dados são perdidos ao fechar a API).

## Inicialização da API

### Environment variables

* **DATA_FILE_CVS**: A variável de ambiente "DATA_FILE_CVS" permite informar o diretório e nome de uma fonte de dados em formato ".CSV", conforme especificado no teste. O diretório deve ser informada junto com o nome do arquivo, conforme exemplo: "C:/dadosimportacao/movielist.csv". Caso essa variável de ambiente não seja informada o sistema irá incializar sem realizar a importação de dados.

### Importação de dados

Ao importar um arquivo .CVS (conforme definido  acima na variável de ambiente e nas específicações do teste), serão validadas a quantidade de colunas do arquivo (que devem estar nomeadas na primeira linha do arquivo) e o nome das mesmas. Cada registro do arquivo será validado individualmente, considerando o tipo de dado e se o mesmo é obrigatório ou não (confome específicações do teste acima).
Erros durante a validação das colunas serão registrados na saída do console como log de erro e a importação do arquivo será interrompida.
Erros durante a validação de cada registro do arquivo serão registrados na saída do console como log de erro e a importação irá pular para o próximo registro.
O início e fim da importação será registrado na saída do console como log de info, informando o nome do arquivo sendo importado e a quantidade de registros importada com sucesso (sem erros).

### Inicalização

Rodar a classe GoldenraspberryawardsapiApplication como Spring Boot App ou Java Application.

## Endpoints

### /awardinterval

A primitiva **awardinterval** disponibiliza o verbo HTTP GET, que retorna os intervalos de premiações (confome específicações do teste acima).

### /producer

A primitiva **producer** disponibiliza a edição CRUD dos produtores através dos verbos HTTP GET, POST, DELETE, PUT, PATCH. O verbo GET irá retornar apenas um registro se informado o id do produtor (ex.: "/producer/ea0eb5ad-8e84-4aea-b7bd-626e5c5c7508").

O nome do produtor é um dado obrigatório.

Exemplo de corpo da primitiva no verbo POST:
```yaml
{
    "name": "Sandra Bullock"
}
```

### /movies

A primitiva **movies** disponibiliza a edição CRUD dos filmes através dos verbos HTTP GET, POST, DELETE, PUT, PATCH. O verbo GET irá retornar apenas um registro se informado o id do filme (ex.: "/movies/1f2d7e78-1efc-4176-bb63-d90a73419607").

O ano, título, descrição dos estúdios e produtores do filme são dados obrigatórios.


Exemplo de corpo da primitiva no verbo POST:
```yaml
{
    "year": 2009,
    "title": "All About Steve",
    "studios": "20th Century Fox",
    "producers": [
        {
            "id": "47461c77-2166-4946-b3ba-ecbd63335ca4"
        },
        {
            "id": "8cd0a752-5739-4867-bc66-e9648f37e587"
        }
    ],
    "winner": false
}
```