ROTEIRO DE ESTUDOS JAVA COM SPRING BOOT
Do primeiro main até API, banco, filas e Docker

Este documento é o material de cada tema. A descrição é a explicação usada no estudo e no vídeo.
Cada aula tem o formato:

Tema
Descrição do conteúdo

A descrição diz para que o recurso existe, como se escreve, como se executa, em que situação ele entra no Sistema de Pedidos e em que ponto do Spring Boot ele reaparece.

================================================================================
COMO USAR ESTE ROTEIRO
================================================================================

Estude na ordem dos níveis. Um nível usa o que o anterior já ensinou.

Nível 1 — Iniciante. A linguagem: tipos, decisões, laços, métodos, texto, arrays.
Nível 2 — Iniciante. Orientação a objetos completa, inclusive enum, record, sealed e anotações.
Nível 3 — Intermediário. Coleções: List, Set, Queue, Deque, Map e cada implementação.
Nível 4 — Intermediário. Streams, e a diferença prática entre stream, for, for-each e Iterator.
Nível 5 — Intermediário. O resto da plataforma Java que uma API Spring usa todo dia.
Nível 6 — Intermediário. Concorrência em Java, porque o Spring atende várias requisições ao mesmo tempo.
Nível 7 — Spring Boot iniciante. Container, injeção, configuração e a primeira API.
Nível 8 — Spring Boot intermediário. Validação, erros, JPA, transação, teste e cliente HTTP.
Nível 9 — Spring Boot avançado. Segurança, filas, cache, Docker, observabilidade e mais de uma instância.

Projeto único da série: Sistema de Pedidos.
No nível 1 ele soma itens no console. No nível 3 o catálogo vira Map e o pedido vira List. No nível 4 os relatórios nascem com Stream. A partir do nível 7 o mesmo pedido vira endpoint Spring Boot, com PostgreSQL, chamada a API externa, fila e container.

Versões de referência: Java 21 ou superior (LTS) e Spring Boot 3 ou superior.
Ferramentas: JDK, IntelliJ IDEA ou VS Code, Maven, Git, Docker, PostgreSQL, RabbitMQ.

Há um tipo que as pessoas procuram pelo nome "SetList". Esse tipo não existe no Java.
O que existe é List (pode repetir e tem índice), Set (não repete), LinkedHashSet (Set que lembra a ordem de inserção) e SequencedSet (Java 21, Set com ordem definida). A aula de Set explica essa confusão com exemplo.

Índice de funcionalidades (tudo o que este roteiro cobre):

Linguagem: tipos primitivos, wrappers, boxing, var, literais, operadores, if, ternário, switch clássico, switch expression, switch com pattern matching, for, for aprimorado, while, do-while, break, continue, métodos, sobrecarga, varargs, escopo, arrays, Arrays, String, StringBuilder, StringBuffer, text block, Scanner, classes, objetos, construtores, blocos de inicialização, encapsulamento, static, final, this, super, pacotes, public, protected, private, herança, polimorfismo, classe abstrata, interface, método default, composição, classe aninhada, classe interna, classe anônima, enum, record, sealed, instanceof com pattern matching, record pattern, generics, curingas, anotação, exceção checked e unchecked, try-with-resources, BigDecimal, BigInteger.

Coleções: Iterable, Collection, List, ArrayList, LinkedList, Set, HashSet, LinkedHashSet, TreeSet, EnumSet, NavigableSet, Queue, Deque, ArrayDeque, PriorityQueue, Map, HashMap, LinkedHashMap, TreeMap, EnumMap, ConcurrentHashMap, Iterator, ListIterator, Comparable, Comparator, Collections, List.of, Set.of, Map.of, SequencedCollection.

Streams: Stream, IntStream, LongStream, DoubleStream, pipeline preguiçoso, filter, map, flatMap, mapMulti, distinct, sorted, peek, limit, skip, takeWhile, dropWhile, forEach, forEachOrdered, collect, reduce, count, min, max, average, sum, anyMatch, allMatch, noneMatch, findFirst, findAny, toArray, Collectors.toList, toSet, toMap, joining, groupingBy, partitioningBy, teeing, summarizing, collectingAndThen, stream paralelo, e a comparação com for, for-each e Iterator.

Plataforma: Optional, java.time, DateTimeFormatter, Duration, Period, ZoneId, Instant, Files, Path, BufferedReader, InputStream, OutputStream, Reader, Writer, regex, Pattern, Matcher, Objects, Math, Random, ThreadLocalRandom, SecureRandom, UUID, Locale, NumberFormat, MessageFormat, Properties, reflexão, Class, proxy, serialização Java, Jackson, JUL e SLF4J, classloader.

Concorrência: Thread, Runnable, Callable, synchronized, volatile, wait/notify, ReentrantLock, AtomicInteger, ConcurrentHashMap, CopyOnWriteArrayList, ExecutorService, pool, Future, CompletableFuture, virtual threads, ThreadLocal.

Spring Boot: starter, auto-configuração, @SpringBootApplication, IoC, injeção por construtor, @Component, @Service, @Repository, @Controller, @RestController, @Configuration, @Bean, @Primary, @Qualifier, @Profile, @Conditional, ciclo de vida, @PostConstruct, application.yaml, @Value, @ConfigurationProperties, profiles, REST, verbos HTTP, ResponseEntity, @PathVariable, @RequestParam, @RequestBody, @RequestHeader, validação Bean Validation, @ControllerAdvice, ProblemDetail, filtros, interceptors, CORS, JPA, relações, fetch, N+1, Spring Data, JPQL, Specification, paginação, @Transactional, propagação, isolamento, JDBC, JdbcClient, Flyway, RestClient, WebClient, RestTemplate, Resilience4j, Spring Security, autenticação, autorização, BCrypt, JWT, method security, Actuator, Micrometer, cache, @Scheduled, @Async, ApplicationEvent, RabbitMQ, Kafka, outbox, Testcontainers, MockMvc, @DataJpaTest, @SpringBootTest, Mockito, Docker, Compose, HikariCP, concorrência com @Version.


================================================================================
NÍVEL 1 — INICIANTE: A LINGUAGEM
================================================================================

Tema
Java, JDK, JVM e o ciclo de um programa

Descrição do conteúdo
Java é a linguagem. O JDK é o kit que compila e executa. A JVM é o programa que executa o bytecode. O arquivo .java é texto. O comando javac transforma esse texto em .class. O comando java entrega esse .class à JVM. A partir do meio do curso quem faz isso é o Maven e o Spring Boot, mas o ciclo continua o mesmo: código, bytecode, processo.

Como se usa no primeiro dia: instalar um JDK 21 ou superior, abrir o terminal e conferir java -version e javac -version. Os dois precisam enxergar o mesmo JDK. No IntelliJ ou no VS Code, criar um arquivo com o mesmo nome da classe pública. O método public static void main(String[] args) é a porta de entrada quando o programa ainda é um console. Spring Boot também tem um main: ele só delega para SpringApplication.run, que sobe o servidor em vez de imprimir uma linha e morrer.

No Sistema de Pedidos, o primeiro main imprime "Sistema de Pedidos iniciado". Quando o projeto virar Spring Boot, esse main permanece e passa a subir a API.


Tema
Tipos primitivos

Descrição do conteúdo
O Java tem oito tipos primitivos. Eles guardam o valor direto, sem ser objeto.

byte guarda inteiro de -128 a 127. Aparece em byte de arquivo e em alguns protocolos. Quase nunca se usa para quantidade de produto.
short guarda inteiro curto. Raro em API de negócio.
int é o inteiro padrão: quantidade, índice, status numérico, página. Faixa aproximada de -2 bilhões a 2 bilhões. Quantidade de item do pedido é int.
long é inteiro grande. Id de banco, timestamp em milissegundos e contagem que pode passar de 2 bilhões. O literal long termina com L: long id = 1L. Sem o L, o número é int e pode falhar ao compilar se for grande demais.
float é ponto flutuante de precisão simples. Evite para dinheiro e para quase todo o resto.
double é ponto flutuante de precisão dupla. Serve para medida científica e para cálculo em que um erro pequeno é aceitável. Para preço, total e desconto ele mente: 0.1 + 0.2 não é exatamente 0.3. Preço do pedido usa BigDecimal, ensinado ainda neste nível.
char guarda um único caractere Unicode, entre aspas simples: char letra = 'A'. Texto de verdade é String, não char.
boolean guarda true ou false. Flag de estoque, "pedido cancelado", resultado de comparação.

Como se declara: tipo, nome, valor. int quantidade = 2;
Como se declara sem valor local: não pode. Variável local precisa receber valor antes de ser lida. Campo de classe recebe valor padrão (0, false, null), e esse padrão é uma fonte clássica de bug. Por isso o curso inicializa campo no construtor.

No Spring, o JSON {"quantidade": 2} cai num int ou num Integer do DTO. Se o cliente omitir o campo, int vira 0 e Integer vira null. Essa diferença decide a validação lá no nível 8.


Tema
Wrappers, boxing e unboxing

Descrição do conteúdo
Cada primitivo tem uma classe correspondente: Byte, Short, Integer, Long, Float, Double, Character, Boolean. A classe existe porque coleção, genérico e muito do Spring só trabalham com objeto. List<int> não compila. List<Integer> compila.

Boxing é a conversão automática de int para Integer. Unboxing é o contrário. Integer q = 2; faz boxing. int n = q; faz unboxing.

Como se usa de propósito: Integer.valueOf não é necessário no dia a dia por causa do boxing. Integer.parseInt("10") converte texto em int e lança NumberFormatException se o texto não for número. Integer.toString(10) faz o caminho inverso. A mesma família existe em Long, Double e Boolean.

O null é o ponto que quebra. Integer quantidade = null; int n = quantidade; lança NullPointerException no unboxing. Em DTO de API, prefira o wrapper quando a ausência do campo significa "o cliente não enviou", e use validação para recusar null. Dentro do domínio, depois de validado, pode guardar o primitivo.

Comparar wrappers com == compara referência na maior parte dos valores. Dois Integer 1000 criados separados podem ser objetos diferentes. Compare com equals, ou faça unboxing e compare os primitivos.


Tema
var, nomes e literais

Descrição do conteúdo
var pede para o compilador deduzir o tipo da variável local a partir do valor da direita. var total = new BigDecimal("10.00"); o tipo é BigDecimal. var não é tipo dinâmico: depois da linha, total continua BigDecimal para sempre. var não serve em parâmetro, não serve em campo e não serve sem valor na mesma linha.

Use var quando o lado direito já mostra o tipo, como new ArrayList<ItemPedido>(). Evite var quando o leitor precisa adivinhar, como resultado de um método de nome genérico.

Literais: 10 é int, 10L é long, 10.0 é double, 10.0f é float, true e false são boolean, 'A' é char, "A" é String. Número grande pode usar _: int milhao = 1_000_000; o underline é só visual.

Constantes de negócio com nome ficam em static final, no nível 2. Número mágico solto no if de desconto é o que essa constante vem substituir.


Tema
Operadores

Descrição do conteúdo
Aritméticos: + - * / %. Com inteiros, / descarta a fração: 5 / 2 vale 2. Se a conta é dinheiro, essa divisão inteira está errada; use BigDecimal. % é o resto: útil para lote par ou ímpar, pouco usado em pedido.

Comparação: == != > >= < <=. Em primitivo, == compara valor. Em objeto, == compara se é o mesmo objeto na memória. Conteúdo de String se compara com equals.

Lógicos: && e || param de avaliar quando o resultado já está decidido. Isso se chama curto-circuito e é uso diário:

if (item != null && item.quantidade() > 0)

Se o item for null, a segunda parte não executa e não estoura NullPointerException. O & e o | sem duplicar existem, avaliam os dois lados e quase não aparecem em regra de negócio.

Unários: ! inverte boolean. ++ e -- mudam a variável. Prefira quantidade = quantidade + 1 quando a linha estiver carregada de regra, porque o incremento no meio de uma expressão confunde a ordem.

Atribuição composta: total += valor é total = total + valor.
Ternário: condicao ? valorSeVerdadeiro : valorSeFalso. Cabe em escolha curta de uma linha. Escolha com várias regras vira if ou switch.

Precedência: * e / acontecem antes de + e -. Em desconto, coloque parênteses. Quem lê a regra de negócio não deve precisar lembrar a tabela de precedência.

No pedido: subtotal = preco.multiply(new BigDecimal(quantidade)). O operador * não se usa em BigDecimal; os métodos multiply, add, subtract e divide são a conta.


Tema
BigDecimal e BigInteger: a conta que o pedido usa

Descrição do conteúdo
BigDecimal representa número decimal exato. É o tipo do preço, do frete, do desconto e do total.

Como se cria: new BigDecimal("19.90") a partir de texto. new BigDecimal(19.90) com double já nasce com o erro do double; evite esse construtor. BigDecimal.valueOf(19.90) é menos pior que o construtor com double, e ainda assim a forma estável do curso é a String ou valueOf de um long de centavos quando a origem for inteira.

Como se calcula: o objeto não muda. add, subtract, multiply e divide devolvem outro BigDecimal. Quem esquece de guardar o retorno continua com o valor velho.

BigDecimal total = subtotal.add(frete);

Divisão exige arredondamento, senão uma dízima lança exceção:

preco.divide(new BigDecimal("3"), 2, RoundingMode.HALF_EVEN);

A escala 2 significa duas casas. HALF_EVEN é o arredondamento bancário comum. Escolha um modo, documente e use o mesmo em todo o sistema.

compareTo compara valor numérico: new BigDecimal("10.0") e new BigDecimal("10.00") são iguais para compareTo e diferentes para equals, porque equals também olha a escala. Em dinheiro, compare com compareTo. Para saber se passou da faixa de desconto: total.compareTo(new BigDecimal("200.00")) >= 0.

BigInteger é o inteiro sem teto de long. Aparece em criptografia e em identificador enorme. O id do pedido do curso cabe em long. Use BigInteger quando o domínio exigir inteiro arbitrário, com add e multiply do mesmo jeito imutável.


Tema
if, else e o ternário na regra de desconto

Descrição do conteúdo
if executa um bloco quando a condição é verdadeira. else cobre o restante. else if encadeia faixas.

Como se usa no pedido:

if (total.compareTo(new BigDecimal("500")) >= 0) {
    percentual = new BigDecimal("0.10");
} else if (total.compareTo(new BigDecimal("200")) >= 0) {
    percentual = new BigDecimal("0.05");
} else {
    percentual = BigDecimal.ZERO;
}

A ordem das faixas importa: a maior faixa vem primeiro, senão um pedido de 600 também cairia na faixa de 200.

Chaves mesmo com uma linha. Sem chave, um segundo comando colado depois parece estar dentro do if e não está.

O ternário cabe quando os dois resultados são expressões curtas:

String origem = cliente == null ? "balcao" : "cadastrado";

Regra de várias faixas permanece em if. Mais tarde essa regra sai do main e vira método de domínio, testado sem Spring.


Tema
switch clássico, switch expression e switch com padrão

Descrição do conteúdo
switch escolhe um caminho a partir de um valor. No status do pedido os valores são poucos e conhecidos: CRIADO, PAGO, ENVIADO, CANCELADO.

Forma clássica, ainda encontrada em código antigo:

String acao;
switch (status) {
    case CRIADO:
        acao = "aguardar pagamento";
        break;
    case PAGO:
        acao = "separar no estoque";
        break;
    default:
        acao = "nenhuma";
}

O break encerra aquele case. Sem o break, a execução continua no case seguinte e executa os dois blocos. O Sistema de Pedidos novo usa a forma expressão, abaixo. A forma clássica fica neste tema porque código antigo ainda a traz.

Forma expressão, que é a que se usa:

String acao = switch (status) {
    case CRIADO -> "aguardar pagamento";
    case PAGO -> "separar no estoque";
    case ENVIADO -> "acompanhar entrega";
    case CANCELADO -> "nenhuma";
};

Cada seta executa só aquele case. Se o switch devolve valor, todos os caminhos precisam devolver, inclusive o que seria default. Com enum, o compilador exige todos os valores: ao nascer um status novo, o switch deixa de compilar até alguém tratar. Isso é proteção real.

switch com pattern matching escolhe pelo tipo do objeto. Útil quando uma resposta externa pode ser Aprovada, Recusada ou Indisponivel, três classes de uma mesma hierarquia:

String texto = switch (resultado) {
    case Aprovada a -> "pago " + a.codigoAutorizacao();
    case Recusada r -> "recusado: " + r.motivo();
    case Indisponivel i -> "tente de novo";
};

Esse switch depende de tipos, herança e sealed, apresentados no nível 2. No nível 1, o switch do pedido é o de enum.


Tema
for, while e do-while

Descrição do conteúdo
for clássico serve quando o índice importa:

for (int i = 0; i < itens.length; i++) {
    total = total.add(itens[i].subtotal());
}

A primeira parte inicia, a segunda testa antes de cada volta, a terceira roda no fim da volta. Índice começa em 0. O último índice válido é tamanho - 1.

for aprimorado percorre todo elemento quando o índice não importa:

for (ItemPedido item : itens) {
    total = total.add(item.subtotal());
}

Ele não deixa remover o elemento no meio da volta de uma lista; isso lança ConcurrentModificationException. Remoção no meio aparece na aula de Iterator, no nível 3.

while testa antes. Use quando não houver contador pronto, por exemplo "ler comando até a pessoa digitar sair":

while (!comando.equals("sair")) {
    comando = scanner.nextLine();
}

do-while testa depois: o bloco roda ao menos uma vez. Cabe num menu que sempre exibe a primeira vez. É o laço menos usado dos três.

Loop infinito é condição que nunca fica falsa, ou for sem avanço do índice. O debugger mostra a mesma linha repetindo. Corrija a condição; não mate o processo como solução do exercício.

Esses três laços guardam o controle com você: dá para ver o índice, parar no meio e acumular variáveis ao redor. Stream, no nível 4, descreve o resultado desejado e entrega o controle da volta para a biblioteca. Os dois convivem. Conta simples de total do pedido pode ser for. Relatório com filtro, transformação e agrupamento fica mais direto em stream, depois que o for já estiver claro.


Tema
break, continue e retorno antecipado

Descrição do conteúdo
break sai do laço mais interno. continue pula para a próxima volta e o laço segue.

for (ItemPedido item : itens) {
    if (item.quantidade() <= 0) {
        continue;
    }
    if (item.codigo().equals(codigoProcurado)) {
        encontrado = item;
        break;
    }
    total = total.add(item.subtotal());
}

continue deixa o item inválido de fora e vai ao próximo. break para ao achar o código e não soma o restante.

return devolve o valor e sai do método na hora. Em validação, o retorno antecipado deixa o caminho feliz no final do método:

if (quantidade <= 0) {
    throw new IllegalArgumentException("quantidade deve ser positiva");
}

Vários break no mesmo laço espalham as saídas. Quando a parada é a resposta do método, extraia um método e use return. O leitor vê um ponto de saída e o laço deixa de carregar a decisão.

Rótulo nomeia um laço de fora e o break daquele nome encerra os dois:

externo:
for (Pedido pedido : pedidos) {
    for (ItemPedido item : pedido.itens()) {
        if (item.codigo().equals(codigo)) {
            break externo;
        }
    }
}

O compilador aceita o rótulo. A saída fica longe do laço nomeado e a leitura piora. No Sistema de Pedidos a busca vira método com return, e o rótulo fica de fora do código.


Tema
Métodos, parâmetros, retorno, sobrecarga e varargs

Descrição do conteúdo
Método é um bloco com nome, que recebe dados e pode devolver um resultado. A assinatura é o nome mais os tipos dos parâmetros. O tipo de retorno faz parte de como se chama, e dois métodos não se diferenciam só pelo retorno.

Como se escreve:

BigDecimal subtotal(BigDecimal preco, int quantidade) {
    return preco.multiply(BigDecimal.valueOf(quantidade));
}

void significa que não devolve valor. Use void em comando que altera estado, como adicionar item, e devolva valor em consulta, como calcular total.

Parâmetro é a variável de entrada. Passar um int copia o número. Passar um objeto copia a referência: o método vê o mesmo objeto e pode alterar o estado dele. Por isso um método adicionarItem(pedido) muda o pedido que o chamador ainda segura.

Sobrecarga é o mesmo nome com parâmetros diferentes:

BigDecimal total(Pedido pedido)
BigDecimal total(Pedido pedido, BigDecimal frete)

O compilador escolhe pela chamada. Sobrecarga demais com parâmetros parecidos confunde; prefira nome específico (totalComFrete) quando a diferença não estiver óbvia nos tipos.

varargs junta vários argumentos do mesmo tipo num array:

void registrar(String... eventos) { }

A chamada pode ser registrar("criado", "pago"). O parâmetro dentro do método é um array. Só um varargs por método, e ele fica por último. No Spring isso aparece pouco no domínio; aparece em APIs de log e em alguns métodos de framework.

O main permanece pequeno: lê a entrada, chama método de regra e imprime. A faixa de desconto mora num método com nome de negócio, não num bloco de 40 linhas dentro do main.


Tema
Escopo e tempo de vida

Descrição do conteúdo
Variável declarada dentro do método existe só enquanto o método executa. Variável declarada dentro do if existe só naquele bloco. Campo da classe vive enquanto o objeto vive. Variável static vive enquanto a classe estiver carregada.

Esse fato muda o desenho do Spring. Um @Service é, por padrão, um objeto só para a aplicação inteira. Campo mutável nesse objeto é estado compartilhado entre todas as requisições. O total do pedido não pode ser campo do service. O total é variável local do método, ou campo do objeto Pedido daquela operação.

Como se pratica agora: nenhum acumulador de pedido fica em static. A lista de pedidos em memória, quando existir, será campo de um objeto repositório criado de propósito, não um global escondido.


Tema
Arrays e a classe Arrays

Descrição do conteúdo
Array guarda uma sequência de tamanho fixo. Todos os elementos são do mesmo tipo.

ItemPedido[] itens = new ItemPedido[3];
itens[0] = item;

O índice começa em 0. itens.length é 3. Acessar itens[3] lança ArrayIndexOutOfBoundsException. O tamanho não cresce. Para acrescentar um quarto item é preciso criar outro array e copiar.

Array de primitivo guarda valores. Array de objeto guarda referências; a posição não preenchida permanece null. Percorrer e chamar método sem testar null estoura.

A classe java.util.Arrays oferece operações prontas: Arrays.toString para imprimir, Arrays.sort para ordenar, Arrays.copyOf para copiar com outro tamanho, Arrays.equals para comparar conteúdo, Arrays.asList para ver o array como lista de tamanho fixo. Arrays.asList não aceita add; a lista resultante está apoiada no array.

Array ainda é o tipo certo quando o tamanho é fixo e conhecido, e é o tipo que varargs usa por baixo. No pedido, a lista de itens muda o tempo todo. O array exige tamanho fixo e uma cópia para crescer. No nível 3 essa lista vira ArrayList. Saber o array continua obrigatório porque JSON, bytecode, varargs e várias APIs devolvem array.


Tema
String, texto e StringBuilder

Descrição do conteúdo
String é uma sequência imutável de caracteres. Qualquer "mudança" cria outra String. Por isso texto.concat("!") não altera texto; é preciso guardar o retorno.

Como se usa no dia a dia do pedido:

equals compara conteúdo. == compara referência. "PAGO".equals(status) é seguro mesmo quando status é null se a constante fica à esquerda: "PAGO".equals(status) devolve false quando status é null, sem estourar.
equalsIgnoreCase ignora maiúscula.
isBlank é true para null? Não. isBlank é método de instância: string vazia ou só com espaço devolve true. Em valor que pode ser null, o teste é texto == null || texto.isBlank().
trim tira espaço das pontas no sentido antigo; strip entende espaço Unicode. Prefira strip e isBlank em código novo.
length devolve a quantidade de caracteres.
charAt(0) devolve o primeiro caractere.
substring(inicio, fim) o fim é exclusivo.
toUpperCase e toLowerCase devolvem outra String. Sem Locale, a conversão de i turco e de outros alfabetos surpreende; quando a palavra for de negócio controlado, o risco é baixo.
replace e replaceAll. replace troca literal. replaceAll recebe expressão regular. Trocar ponto de CPF com replaceAll(".", "") troca todo caractere, porque ponto na regex significa "qualquer um". Para literal, use replace.
split divide. "a,b".split(",") produz um array.
join junta: String.join(", ", nomes).
formatted monta texto: "Pedido %s total %s".formatted(id, total).
valueOf e String.valueOf transformam número ou objeto em texto. Em objeto, isso chama toString.
startsWith, endsWith, contains, indexOf servem para busca simples. Busca com regra (CEP só dígito) é regex, no nível 5.

Text block é String de várias linhas, aberta com três aspas. Use para JSON de exemplo em teste e para SQL longo. A indentação da linha de fechamento define o recuo removido.

StringBuilder monta texto em laço sem criar uma String por volta:

StringBuilder relatorio = new StringBuilder();
for (ItemPedido item : itens) {
    relatorio.append(item.nome()).append(" | ").append(item.subtotal()).append('\n');
}
String texto = relatorio.toString();

StringBuffer faz o mesmo com sincronização. Em código novo de uma thread só, use StringBuilder. Log e framework quase nunca pedem StringBuffer.

No Spring, o JSON da API é String só na borda. Quem transforma objeto em JSON é o Jackson. O domínio trabalha com nome em String, dinheiro em BigDecimal e status em enum, não com um texto gigante concatenado.


Tema
Entrada no console com Scanner

Descrição do conteúdo
Scanner lê o teclado ou um texto.

Scanner scanner = new Scanner(System.in);
String nome = scanner.nextLine();
int quantidade = Integer.parseInt(scanner.nextLine());

Ler número com nextInt e depois texto com nextLine deixa um enter sobrando e a leitura seguinte vem vazia. O curso padroniza nextLine e converte com parseInt ou com new BigDecimal(linha). Se a conversão falhar, capture NumberFormatException e peça de novo.

Feche Scanner de arquivo. Scanner em System.in não precisa ser fechado no exercício curto, porque fechar também fecha a entrada padrão.

O console existe até a API nascer. Quando o Spring Boot assumir, a entrada deixa de ser Scanner e passa a ser o corpo HTTP. O método que calcula o total permanece o mesmo: ele nunca dependeu do Scanner.


Tema
Math, Random, UUID e formatação numérica

Descrição do conteúdo
java.lang.Math traz funções puras: Math.max, Math.min, Math.abs, Math.round, Math.floor, Math.ceil, Math.pow, Math.sqrt. Para dinheiro, max e min de BigDecimal existem como a.max(b) e a.min(b), e são os que o desconto usa. Math.random devolve double entre 0 e 1; não use para id nem para token.

Random gera número pseudoaleatório. new Random() serve para dado de teste e sorteio sem valor de segurança. ThreadLocalRandom.current().nextInt(1, 101) é a forma em código que pode rodar em várias threads. SecureRandom é a forma para segredo, token e qualquer coisa que um atacante não possa prever. Id de pedido exposto pode ser UUID, que não precisa ser segredo, e mesmo assim não se fabrica com Random simples se a previsão do próximo id for um problema.

UUID.randomUUID() devolve um identificador único prático para pedido, evento e chave de idempotência. Como se guarda: UUID no Java e tipo uuid no PostgreSQL, ou String se o time padronizar texto. Como se lê de volta: UUID.fromString(texto), que lança IllegalArgumentException se o formato estiver errado.

NumberFormat e DecimalFormat formatam para exibir. Eles não substituem BigDecimal na conta. Formatar total para a tela e depois fazer conta em cima do texto formatado reintroduz erro. A formatação fica na borda, no momento de imprimir ou de montar um e-mail.


================================================================================
NÍVEL 2 — INICIANTE: ORIENTAÇÃO A OBJETOS
================================================================================

Tema
Classes, objetos e new

Descrição do conteúdo
Classe descreve um conceito. Objeto é um valor daquele conceito na memória. A classe Produto descreve nome e preço. Cada new Produto(...) cria um produto independente.

Classe mínima do curso:

public class Produto {
    private final String codigo;
    private final String nome;
    private final BigDecimal preco;

    public Produto(String codigo, String nome, BigDecimal preco) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
    }
}

new Produto("SKU-1", "Caderno", new BigDecimal("19.90")) chama o construtor. this.codigo é o campo; codigo é o parâmetro. Sem this, o parâmetro atribui a ele mesmo e o campo fica null.

Objeto mora na heap. A variável guarda a referência. Produto a = b; faz as duas variáveis apontarem para o mesmo produto. Alterar pelo caminho a altera o que b enxerga.

No Spring, a classe continua sendo a unidade de código. A diferença é quem chama new. No console, o main chama new. No Spring Boot, o container chama new dos services e controllers, guarda esses objetos e entrega pronto quando outro objeto precisa. O domínio Pedido continua sendo criado com new dentro do caso de uso, porque pedido é dado da operação, não peça da infraestrutura.


Tema
Encapsulamento e invariante

Descrição do conteúdo
Campo private só é acessível dentro da classe. Quem está fora usa método. O método pode recusar estado inválido.

public void alterarPreco(BigDecimal novoPreco) {
    if (novoPreco == null || novoPreco.compareTo(BigDecimal.ZERO) <= 0) {
        throw new IllegalArgumentException("preco deve ser positivo");
    }
    this.preco = novoPreco;
}

Invariante é a verdade que o objeto protege: preço positivo, quantidade ao menos 1, pedido cancelado não recebe item. A proteção fica no método que muda o estado, não espalhada em cada tela e em cada endpoint. Quando o Spring receber um JSON, a validação da borda recusa o pedido HTTP cedo, e o construtor do domínio recusa de novo. As duas camadas existem porque a borda fala a língua do HTTP e o domínio fala a língua da regra, e o domínio pode ser chamado por fila, teste ou console, sem passar pelo controller.

Getter devolve o campo. Setter só existe se a mudança for uma operação válida. Gerar setter para tudo abre um buraco: qualquer um zera o preço depois de construído. Record e campo final são o caminho quando o valor não muda.


Tema
Construtores, sobrecarga e blocos de inicialização

Descrição do conteúdo
Construtor tem o nome da classe e não declara retorno. Ele prepara o objeto antes de qualquer uso. Se você escreve um construtor, o construtor vazio automático some. Quem precisa dos dois declara os dois.

Validação no construtor impede Produto null, nome em branco e preço negativo de nascerem. Falhar cedo aqui evita if defensivo no resto do sistema.

Bloco de inicialização de instância roda antes do construtor, a cada new. Bloco static roda uma vez, quando a classe é carregada. Os dois existem. No projeto, a inicialização explícita no campo ou no construtor é mais fácil de achar. Bloco static aparece em alguns registros de driver e em exemplos antigos; no Spring, a inicialização única de infraestrutura fica em @Bean e em @PostConstruct, não em bloco static cheio de regra.

Ordem real ao dar new: campos e blocos de instância na ordem do arquivo, depois o construtor.


Tema
static, final e membros da classe

Descrição do conteúdo
static pertence à classe. Existe um só, compartilhado por todos os objetos. Constantes são public static final, com nome em maiúsculas: public static final int PRAZO_MINUTOS = 30;

Campo static mutável é estado global. Um contador static de pedidos quebra quando existem duas instâncias da API e quando dois testes rodam juntos. Contador de verdade mora no banco.

Método static não usa this e não enxerga campo de instância. Serve para função sem estado, como um conversor. No Spring, método static de regra simples é aceitável; service com dependência não é static, porque a dependência chega pelo construtor no objeto.

final em variável: o nome não passa a apontar para outro valor. Em primitivo, o número não muda. Em objeto, a referência não muda, e o estado interno do objeto ainda pode mudar se existir método que altera. final em campo de Produto, junto com a ausência de setter, torna o produto imutável.

final em classe impede herança. final em método impede sobrescrita. Classes de valor e utilitários do curso são final quando não foram desenhadas para extensão.


Tema
Pacotes e os quatro níveis de visibilidade

Descrição do conteúdo
Pacote é a pasta lógica do código. O nome segue o domínio invertido e o módulo: com.curso.pedidos.dominio. A primeira linha do arquivo é package com.curso.pedidos.dominio;
Import traz um tipo de outro pacote. java.lang é importado sozinho: String, Integer, BigDecimal não precisam de import. BigDecimal está em java.math e precisa.

Visibilidade:

private: só a própria classe. Campos do domínio ficam private.
sem modificador (pacote): qualquer classe do mesmo pacote. Útil para teste no mesmo pacote e para detalhe interno. Não conte com isso entre módulos diferentes.
protected: o pacote e as subclasses. Aparece quando há herança real. Em domínio sem hierarquia, protected abre mais do que private sem ganho.
public: qualquer pacote. Entidade, service, controller e DTO que cruzam pacotes são public.

A organização do Sistema de Pedidos fica estável até o fim da série:

dominio — Pedido, ItemPedido, Produto, StatusPedido, dinheiro e regra
aplicacao — casos de uso, um método público por ação
porta — interfaces de repositório, de CEP, de pagamento e de fila
adaptador — console no começo; controller, JPA e RestClient quando o Spring entrar
config — classes @Configuration do Spring

Regra de dependência: dominio não importa adaptador. O caso de uso chama uma interface. O Spring injeta a implementação. Essa regra é o que permite trocar a lista em memória pelo PostgreSQL sem reescrever o desconto.


Tema
Herança, super e a classe Object

Descrição do conteúdo
Herança é uma classe reaproveitar outra com extends. A classe filha é do tipo da pai. DescontoPercentual extends Desconto herda o que for protected ou public e pode sobrescrever método.

super chama o construtor ou o método da pai. A primeira linha do construtor da filha precisa ser super(...) quando a pai não tem construtor vazio.

Toda classe herda de Object mesmo sem escrever isso. Object traz toString, equals, hashCode, getClass. toString padrão imprime o nome da classe e um código, inútil no log. Sobrescreva toString em objeto de domínio para o log do pedido mostrar id e status.

Herança cabe em relação "é um" curta: cada tipo de resultado de pagamento é um resultado. Herança não cabe para reaproveitar campo: Cliente extends Pessoa extends Entidade vira uma cadeia frágil. O curso limita herança a um nível e só quando o polimorfismo simplifica a chamada.

No Spring, herdar classe de framework acontece em casos específicos (um UserDetails, um teste). Controller não precisa estender classe base do Spring para ser controller; a anotação basta. Essa é uma diferença importante do estilo atual: preferir composição e anotação, herdar quando o contrato de tipo exige.


Tema
Polimorfismo

Descrição do conteúdo
Polimorfismo é chamar o mesmo método e executar o código da instância real.

Desconto desconto = new DescontoPercentual(new BigDecimal("0.10"));
BigDecimal valor = desconto.aplicar(total);

A variável é do tipo Desconto. O objeto é DescontoPercentual. O método que roda é o da classe de verdade. Trocar para DescontoValorFixo não muda a linha que chama aplicar.

Como se usa no pedido: o pedido guarda um Desconto. O cálculo do total pergunta desconto.aplicar(subtotal) e não faz if por tipo de cupom. Acrescentar um terceiro desconto é uma classe nova, sem abrir o método de total.

instanceof pergunta o tipo. Com pattern matching:

if (desconto instanceof DescontoPercentual percentual) {
    BigDecimal taxa = percentual.taxa();
}

Use isso em borda, log e integração. Use polimorfismo na regra que varia. Um encadeado de instanceof no meio do cálculo é o sinal de que falta um método na interface.


Tema
Classe abstrata

Descrição do conteúdo
Classe abstrata não pode ser instanciada. Ela pode ter campo, construtor, método concreto e método abstrato. Método abstrato não tem corpo; a filha concreta é obrigada a implementar.

public abstract class Desconto {
    public abstract BigDecimal aplicar(BigDecimal valor);
}

new Desconto() não compila. new DescontoPercentual(...) compila.

Use classe abstrata quando as filhas compartilham estado e uma parte da implementação. Use interface quando o que se compartilha é um contrato de comportamento. No Sistema de Pedidos, Desconto vira interface, porque não há estado comum obrigatório. Classe abstrata entra quando as filhas compartilham campo e um método concreto. O Hibernate usa classes abstratas em pontos internos: o tipo base já traz comportamento e a subclasse completa o que falta.


Tema
Interface, método default, método static e implementação

Descrição do conteúdo
Interface declara o que uma classe sabe fazer. Quem implementa escreve implements Nome e preenche os métodos.

public interface Desconto {
    BigDecimal aplicar(BigDecimal valor);
}

public final class DescontoPercentual implements Desconto {
    public BigDecimal aplicar(BigDecimal valor) {
        return valor.multiply(taxa);
    }
}

Uma classe pode implementar várias interfaces. Ela só pode estender uma classe. Por isso capacidade extra entra por interface: um pedido não "é" um notificador, ele pode receber um Notificador.

Método default tem corpo dentro da interface. Quem implementa herda esse corpo e pode sobrescrever. Serve para evoluir uma interface sem quebrar todas as implementações. O Spring e o Java usam isso em coleções e em vários contratos. No domínio do curso, default fica restrito a comportamento realmente comum e simples.

Método static na interface é função ligada ao contrato, chamada como Desconto.nenhum(). Método private na interface existe para o default não ficar duplicado. Os dois são detalhe de desenho, não a primeira ferramenta.

No Spring, interface é a fronteira de injeção. PedidoRepositorio é uma interface. JpaPedidoRepositorio é a implementação anotada. O caso de uso recebe PedidoRepositorio no construtor e não sabe que existe banco. Teste entrega uma implementação em memória. Essa é a forma de usar interface que mais aparece na série.


Tema
Composição

Descrição do conteúdo
Composição é um objeto ter outros objetos. Pedido tem uma lista de ItemPedido. ItemPedido tem um Produto e uma quantidade. Endereco é um objeto à parte referenciado pelo pedido.

Como se usa: o campo é privado, a lista nasce no construtor (new ArrayList<>()), e quem mexe na lista é método do Pedido. adicionarItem valida e inclui. removerItem valida status e remove. Ninguém de fora pega a lista interna e dá add direto, porque isso furaria a invariante. O getter de itens devolve uma cópia ou uma lista imutável: List.copyOf(itens).

Composição é o padrão do Sistema de Pedidos inteiro. O service não herda o repositório; ele recebe o repositório. O controller não herda o service; ele recebe o service. O Spring chama isso de injeção de dependência, e o mecanismo é composição: objeto tem referência para outro objeto, recebida pronta no construtor.


Tema
Classes aninhadas, internas e anônimas

Descrição do conteúdo
Classe estática aninhada é uma classe declarada dentro de outra com static. Ela não enxerga campo de instância da de fora. Use para um tipo pequeno que só faz sentido junto do principal, como Pedido.Resumo, ou para o builder se um dia existir. Em código novo, record no mesmo arquivo ou no mesmo pacote costuma ser mais claro.

Classe interna não static guarda uma referência escondida ao objeto de fora. Ela enxerga os campos privados dele. Isso prende o objeto externo na memória enquanto a interna viver. Use com intenção rara. Evite como padrão de domínio.

Classe anônima é uma implementação sem nome, escrita na hora da chamada. Antes de lambda, o Comparator e o Runnable eram assim. Hoje lambda cobre o caso de uma interface com um método só. Classe anônima ainda aparece quando a interface tem mais de um método e a implementação é local e curta.

Classe local é declarada dentro de um método e só existe ali. Ela enxerga as variáveis finais, ou que não mudam depois de capturadas, desse método.

void preparar() {
    class Trecho {
        String texto() { return "pedido"; }
    }
    Trecho trecho = new Trecho();
}

Serve para um tipo descartável que não deve aparecer no restante da classe. No Sistema de Pedidos o tipo pequeno vira record no pacote, e a classe local não entra no código.

No Spring, classes @Configuration são classes normais. Proxy do Spring gera uma classe extra em tempo de execução, parecida em espírito com uma casca ao redor do objeto. A aula de proxy, no nível 5, explica o que essa casca faz com @Transactional.


Tema
Enum

Descrição do conteúdo
Enum é um conjunto fechado de constantes. StatusPedido tem CRIADO, AGUARDANDO_PAGAMENTO, PAGO, ENVIADO, CANCELADO. Não dá para inventar um status novo em tempo de execução sem alterar o código.

public enum StatusPedido {
    CRIADO,
    AGUARDANDO_PAGAMENTO,
    PAGO,
    ENVIADO,
    CANCELADO;

    public boolean podeIrPara(StatusPedido proximo) {
        return switch (this) {
            case CRIADO -> proximo == AGUARDANDO_PAGAMENTO || proximo == CANCELADO;
            case AGUARDANDO_PAGAMENTO -> proximo == PAGO || proximo == CANCELADO;
            case PAGO -> proximo == ENVIADO || proximo == CANCELADO;
            case ENVIADO, CANCELADO -> false;
        };
    }
}

Como se usa: StatusPedido status = StatusPedido.PAGO;
StatusPedido.valueOf("PAGO") converte texto. Texto desconhecido lança IllegalArgumentException. Em JSON, o Jackson converte o nome do enum. Se a API precisar de outro texto, @JsonValue e @JsonCreator controlam a tradução.

Enum pode ter campo e construtor, por exemplo um código estável para o banco: CRIADO("CRIADO"). O construtor do enum é privado. Cada constante é uma instância única, então == funciona entre constantes do mesmo enum.

EnumSet e EnumMap, no nível 3, são as coleções desenhadas para enum: mais diretas e mais leves do que HashSet de status.

No banco, guarde o nome estável do enum, não a posição ordinal. Incluir um valor no meio muda ordinal e corrompe dado antigo.


Tema
Record

Descrição do conteúdo
Record é uma classe imutável de dados. O compilador gera construtor, acesso, equals, hashCode e toString.

public record Dinheiro(BigDecimal valor, String moeda) { }

O acesso é dinheiro.valor(), sem get. O estado não muda depois de construído.

Dá para validar no construtor compacto:

public record ItemPedido(Produto produto, int quantidade) {
    public ItemPedido {
        if (produto == null) {
            throw new IllegalArgumentException("produto obrigatorio");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("quantidade deve ser positiva");
        }
    }

    public BigDecimal subtotal() {
        return produto.preco().multiply(BigDecimal.valueOf(quantidade));
    }
}

Record pode ter método, implementar interface e ter campo static. Record não estende outra classe além de Record. Os componentes são final.

Como se usa no curso: DTO de entrada e saída da API, evento de fila, resposta de CEP e resumo de relatório são records. Pedido com ciclo de vida mutável permanece classe, porque status e lista de itens mudam.

No Spring, record funciona como @RequestBody e como resposta JSON. Funciona como chave de configuração simples. Entidade JPA do curso permanece classe, porque o ciclo de persistência altera estado e o mapeamento de record como entidade ainda é um terreno ruim para este projeto.


Tema
Sealed classes e switch completo

Descrição do conteúdo
Classe ou interface sealed lista quem pode estender ou implementar. Fora dessa lista, ninguém.

public sealed interface ResultadoPagamento
        permits Aprovado, Recusado, Indisponivel { }

public record Aprovado(String codigoAutorizacao) implements ResultadoPagamento { }
public record Recusado(String motivo) implements ResultadoPagamento { }
public record Indisponivel(String detalhe) implements ResultadoPagamento { }

O ganho prático é o switch que o compilador fiscaliza. Se nascer um quarto resultado e ele não entrar no switch, o código não compila. Sem sealed, um default engole o tipo novo em silêncio.

Como se usa: resultados de integração, tipos de desconto se a lista for fechada, eventos do pedido se cada evento for um tipo. Não sele todo o domínio por estética. Sealed vale quando o conjunto é fechado de propósito e o switch é parte da regra.


Tema
equals, hashCode e toString

Descrição do conteúdo
== pergunta se as duas variáveis apontam para o mesmo objeto. equals pergunta se o conteúdo conta como o mesmo valor, segundo a regra que você escreveu.

O contrato que importa: se a.equals(b), então a.hashCode() == b.hashCode(). O contrário não é obrigatório. Quebrar esse contrato faz HashSet e HashMap perderem o elemento: o objeto entra e a busca não acha.

Em Produto identificado por código, equals e hashCode usam só o código. Em record, isso já vem gerado com todos os componentes. Em entidade que ainda não foi ao banco, cuidado: dois pedidos novos sem id não são o mesmo pedido só porque o cliente tem o mesmo nome.

Objects.equals(a, b) compara aceitando null. Objects.hash(codigo, nome) monta hashCode. Os dois evitam esquecer o null.

toString entra em log. Não coloque senha, token, documento completo nem número de cartão no toString. O log do Spring imprime objetos com frequência; o toString vaza o que estiver nele.


Tema
Generics na linguagem

Descrição do conteúdo
Genérico é um parâmetro de tipo. List<ItemPedido> só aceita item de pedido. O compilador recusa adicionar um String. Sem genérico, a lista antiga guardava Object e cada leitura precisava de cast, que estoura em tempo de execução se o tipo estiver errado.

Método genérico:

public static <T> T primeiro(List<T> lista) {
    if (lista.isEmpty()) {
        throw new IllegalArgumentException("lista vazia");
    }
    return lista.get(0);
}

T é decidido pela chamada. Limite superior: <T extends Desconto> significa que T é Desconto ou uma implementação. Dentro do método dá para chamar aplicar.

Curinga ? significa "algum tipo". List<?> é uma lista de um tipo desconhecido: dá para ler como Object e não dá para adicionar, exceto null. List<? extends Desconto> é produtora: dá para tirar Desconto e não dá para colocar um desconto específico. List<? super DescontoPercentual> é consumidora: dá para colocar DescontoPercentual. A frase prática é: extends quando você lê, super quando você escreve. No dia a dia do Sistema de Pedidos, lista concreta (List<ItemPedido>) cobre quase tudo. O curinga aparece ao ler assinatura do Spring, do JPA e de Collectors.

Genérico some na execução por type erasure. Em tempo de execução, List<ItemPedido> e List<String> são apenas List. Por isso não dá para fazer new T() nem perguntar instanceof List<String>. Quando o Spring precisa criar o tipo certo de um campo genérico, ele usa reflexão em cima do que sobrou na assinatura. Saber que o tipo some evita achar que um if (lista instanceof List<Pedido>) é possível.


Tema
Anotações na linguagem

Descrição do conteúdo
Anotação é metadado preso a classe, método, campo ou parâmetro. Ela não executa sozinha. Alguém lê a anotação e decide o que fazer. Esse alguém, na maior parte da série, é o Spring.

Como se declara:

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CasoDeUso { }

Retention RUNTIME significa que a anotação sobrevive para ser lida com reflexão durante a execução. SOURCE some depois da compilação. CLASS fica no bytecode e a JVM não expõe por padrão.

Como se usa uma anotação pronta: coloca-se acima do alvo.

@Override no método que sobrescreve. Se o nome estiver errado, não compila. Use sempre ao sobrescrever.
@FunctionalInterface marca interface de um método só, a que lambda pode implementar. O compilador reclama se nascer um segundo método abstrato.

As anotações que dominam o resto do curso são do Spring e do Jakarta, e cada uma será explicada no momento de usar: @Service, @RestController, @GetMapping, @Transactional, @Valid, @Entity, @Id. O ponto deste nível é entender o mecanismo: a anotação é uma etiqueta; o framework é quem age ao vê-la.


Tema
Imutabilidade aplicada ao pedido

Descrição do conteúdo
Objeto imutável não muda depois de criado. Record é o atalho. Classe com campo final e sem setter é a forma manual.

Objeto mutável tem ciclo de vida. Pedido muda de status e ganha item. A mutação passa por método com nome de negócio, que valida a passagem. adicionarItem recusa pedido cancelado. mudarStatus chama status.podeIrPara.

Devolver a coleção interna permite que código de fora altere o pedido sem passar pelo método. Devolva List.copyOf(itens) ou Collections.unmodifiableList(itens). A cópia imutável é mais segura porque ninguém altera nem a original por aquela referência.

DTO de API é imutável. Entidade com estado é mutável e protegida. Evento publicado na fila é imutável: o fato "pedido pago" não muda depois de acontecido. Essa separação é o desenho que o Spring Boot do nível 7 em diante só transporta, sem reinventar.


================================================================================
NÍVEL 3 — INTERMEDIÁRIO: COLEÇÕES
================================================================================

Tema
O mapa das coleções e o que cada interface promete

Descrição do conteúdo
Coleção é objeto que guarda vários elementos. O Java organiza isso em interfaces. A interface promete o comportamento. A classe concreta decide velocidade, ordem e regra de repetição.

Iterable: dá para percorrer com for-each. Quase toda coleção é iterable.
Collection: adiciona o contrato comum de add, remove, size, isEmpty, contains, clear, iterator.
List extends Collection: tem ordem de inserção (ou a ordem que você impuser), tem índice, aceita repetido.
Set extends Collection: não aceita elemento repetido segundo equals. Não promete índice.
Queue extends Collection: fila de processamento, com oferta na cauda e retirada na cabeça, na forma padrão.
Deque extends Queue: fila de duas pontas. Dá para usar como pilha e como fila.
Map não estende Collection. Guarda par chave-valor. As chaves não se repetem.

Não existe SetList. Quem quer "lista que não repete" escolhe uma combinação explícita:

List quando a posição importa e a repetição é válida. Itens do pedido: duas linhas do mesmo produto podem ser a regra, ou podem ser fundidas de propósito no adicionarItem. A estrutura continua List.
Set quando repetir é proibido e a posição não é a identidade. Conjunto de códigos de cupom já usados.
LinkedHashSet quando não pode repetir e a ordem de entrada deve aparecer na impressão.
TreeSet quando não pode repetir e a ordem é a ordem natural ou um Comparator, por exemplo nomes em ordem alfabética.

Como se declara: programe para a interface e construa a implementação.

List<ItemPedido> itens = new ArrayList<>();
Set<String> codigos = new HashSet<>();
Map<String, Produto> catalogo = new HashMap<>();

O lado esquerdo é o que o restante do código enxerga. Trocar ArrayList por LinkedList no new muda características internas sem obrigar o chamador a mudar, desde que ele só use métodos de List.


Tema
List: contrato, índice e repetição

Descrição do conteúdo
List guarda elementos com posição. O primeiro é o índice 0. add(elemento) coloca no fim. add(indice, elemento) desloca o resto para a direita. get(indice) lê. set(indice, elemento) substitui e devolve o antigo. remove(indice) tira pela posição e desloca. remove(objeto) tira a primeira ocorrência igual segundo equals. indexOf e lastIndexOf devolvem a posição ou -1. contains pergunta se existe. subList(inicio, fim) devolve uma visão do trecho; alterar a visão altera a lista original, então o uso seguro é imediato, sem guardar a subList para depois. sort recebe um Comparator. isEmpty e size respondem tamanho. clear esvazia.

Repetição é permitida. Dois itens iguais podem coexistir. A ordem do add é preservada nas implementações que o curso usa (ArrayList e LinkedList).

Lista imutável: List.of("a", "b") e List.copyOf(origem). Adicionar ou remover lança UnsupportedOperationException. List.of recusa null. Use a imutável em constante, em resposta que não deve ser alterada e ao devolver itens do pedido para fora.

Lista de tamanho fixo apoiada em array: Arrays.asList(a, b). set funciona, add e remove não.

Como o pedido usa: os itens são List<ItemPedido> dentro do agregado. A ordem é a ordem em que o cliente adicionou. O total percorre essa lista. A API devolve essa ordem no JSON.


Tema
ArrayList: como é por dentro e como se usa

Descrição do conteúdo
ArrayList é a List padrão do curso. Por dentro é um array que cresce. get por índice é direto e barato. add no fim é barato na maior parte das vezes; de vez em quando o array interno é copiado para um maior. add ou remove no meio desloca elementos e fica mais caro quanto maior e quanto mais perto do início for a mudança. contains e remove(objeto) percorrem até achar.

Como se cria:

List<ItemPedido> itens = new ArrayList<>();
List<String> nomes = new ArrayList<>(outrosNomes);

O construtor com capacidade inicial, new ArrayList<>(64), evita realocações quando o tamanho aproximado é conhecido. Em pedido comum, o construtor vazio basta.

Como se percorre:

for (ItemPedido item : itens) {
    total = total.add(item.subtotal());
}

Como se remove durante a travessia: não chame itens.remove dentro do for-each. Use removeIf:

itens.removeIf(item -> item.quantidade() == 0);

ou Iterator, explicado mais à frente.

ArrayList aceita null. Um null no meio do pedido quebra o cálculo quando alguém chama subtotal. O método adicionarItem recusa null antes do add.

No Spring, a lista que o Jackson monta a partir de um JSON de itens é uma lista mutável comum, e o caso de uso copia os dados para o domínio em vez de guardar a lista do framework como estado do pedido. A lista devolvida no JSON pode ser List.copyOf, e o Jackson a serializa do mesmo jeito.


Tema
LinkedList e quando ela perde para ArrayList e para ArrayDeque

Descrição do conteúdo
LinkedList também implementa List e Deque. Cada elemento aponta para o anterior e para o próximo. Inserir no início, se você já está com o nó na mão, é barato. Na prática da API de List, para chegar ao índice 500 ela percorre 500 nós. get do meio é lento. A memória por elemento é maior por causa dos ponteiros.

Como List de itens de pedido, ArrayList é a escolha. O acesso por índice e a leitura completa são o uso real, e ArrayList ganha.

LinkedList como fila também perde espaço para ArrayDeque, que é mais enxuta e mais rápida nas pontas. A regra do Sistema de Pedidos: ArrayList para List, ArrayDeque para fila e pilha. LinkedList aparece em código antigo e não é a escolha automática "para muita inserção". Meça o ponto da inserção. Inserção no fim de ArrayList já é o caso comum e é eficiente. Inserção no meio de LinkedList exige percorrer os nós até o ponto, e o ganho só aparece quando a posição já está na mão, como um ListIterator.


Tema
Set e a regra de não repetir

Descrição do conteúdo
Set guarda elementos únicos segundo equals. add de um elemento já contido devolve false e não duplica. Não há get por índice. A forma de usar é contains, add, remove e percorrer tudo.

Set<String> skus = new HashSet<>();
boolean entrou = skus.add("SKU-1");
boolean deNovo = skus.add("SKU-1");

entrou fica true. deNovo fica false. size continua 1.

Null: HashSet e LinkedHashSet aceitam um único null. TreeSet não aceita null se a ordem natural não souber comparar null.

O Sistema de Pedidos usa Set para códigos de cupom já aplicados numa campanha, para o conjunto de papéis de um usuário (CLIENTE, OPERADOR) e para dizer "quais status entram neste filtro" quando a ordem não é a informação principal.

Não use Set para itens do pedido. A perda do índice e a proibição de repetir atrapalham a linha do pedido. Se a regra de negócio for "um produto só pode aparecer uma vez no pedido", o certo é List mais uma verificação no adicionarItem, ou fundir a quantidade na linha existente. A estrutura continua lista ordenada; a regra de unicidade mora no método.


Tema
HashSet: igualdade, hashCode e ausência de ordem

Descrição do conteúdo
HashSet usa a mesma ideia de tabela do HashMap. A posição do elemento depende de hashCode. A confirmação de que já existe depende de equals. Se dois produtos são equals e têm hashCode diferente, o conjunto pode guardar os dois, quebrando a promessa de unicidade. Por isso equals e hashCode nascem juntos, como visto no nível 2.

HashSet não promete ordem. Percorrer hoje e amanhã pode imprimir em sequência diferente. Não use essa ordem na resposta de uma API. Quem precisa de ordem estável escolhe outra implementação.

contains, add e remove são, em média, muito rápidos e quase independentes do tamanho. No pior caso, com hash ruim, degradam. Os tipos do Java (String, Integer, record bem gerado) têm hash adequado.

Como se cria a partir de uma lista, eliminando repetidos:

Set<String> unicos = new HashSet<>(listaDeCodigos);

A lista original não muda. O conjunto resultante não tem duplicata e não tem a ordem da lista.

HashSet não é sincronizado. Duas requisições alterando o mesmo conjunto sem coordenação corrompem a estrutura. No Spring, o catálogo não é um HashSet guardado em campo de @Service e alterado por todo request. Dado compartilhado mora no banco ou numa estrutura concorrente. O HashSet local, criado dentro do método, é seguro porque cada chamada tem o seu.


Tema
LinkedHashSet: o conjunto que lembra a ordem de entrada

Descrição do conteúdo
LinkedHashSet é um HashSet com uma lista ligando os elementos na ordem em que entraram. Não repete. A travessia sai na ordem do primeiro add.

Set<String> etapas = new LinkedHashSet<>();
etapas.add("criado");
etapas.add("pago");
etapas.add("criado");

A travessia imprime criado e depois pago. O segundo "criado" foi ignorado.

É esta a classe que as pessoas tentam descrever quando dizem "setlist": parece lista porque tem ordem, parece set porque não repete. O nome certo é LinkedHashSet. Ela custa um pouco mais de memória que HashSet por guardar a ordem.

Use no pedido quando a saída precisa ser estável: as categorias presentes num pedido, na ordem em que apareceram, sem categoria repetida. Não use como substituto de List de itens, porque item tem payload e posição com repetição possível, e LinkedHashSet não tem índice.


Tema
TreeSet, SortedSet e NavigableSet

Descrição do conteúdo
TreeSet guarda elementos ordenados. A ordem é a do Comparable do elemento ou a de um Comparator passado no construtor. Não aceita duplicata segundo essa comparação. Se o Comparator disser que dois objetos são iguais (compare devolve 0), o conjunto trata como duplicata mesmo que equals discorde. Mantenha compare e equals coerentes.

Set<String> nomes = new TreeSet<>();
nomes.add("caderno");
nomes.add("apagador");

A travessia sai apagador, caderno.

SortedSet e NavigableSet são as interfaces. TreeSet implementa as duas. Métodos que a ordem habilita:

first e last devolvem o menor e o maior.
headSet, tailSet e subSet devolvem faixas.
ceiling procura o igual ou o próximo maior. floor procura o igual ou o próximo menor.
descendingSet percorre ao contrário.

Custo: cada add e cada contains são proporcionais ao logaritmo do tamanho, mais caros que HashSet e úteis quando a ordem é consulta de verdade. Exemplo no projeto: tabela de faixas de frete por valor, em que se busca a faixa aplicável ao total. Se a única necessidade for "não repetir", HashSet é o bastante e a ordenação eventual fica a cargo de uma lista ordenada na hora de exibir.


Tema
EnumSet

Descrição do conteúdo
EnumSet é o Set feito para constantes de enum. Por dentro é um conjunto de bits, muito compacto e muito rápido. Só aceita o enum da declaração.

Set<StatusPedido> ativos = EnumSet.of(
        StatusPedido.CRIADO,
        StatusPedido.AGUARDANDO_PAGAMENTO,
        StatusPedido.PAGO);

EnumSet.allOf(StatusPedido.class) começa com todos. EnumSet.noneOf(StatusPedido.class) começa vazio. complementOf inverte.

Use para "status que este endpoint lista" e para "transições permitidas" quando um Set expressa a regra melhor do que vários booleanos. Não use HashSet de enum nesse caso: funciona, e EnumSet expressa a intenção e gasta menos.

O conjunto devolvido por EnumSet.of é mutável. Se for constante de classe, embrulhe com Collections.unmodifiableSet ou copie para um Set imutável de outra forma e não deixe código de fora dar add.


Tema
Queue, Deque, ArrayDeque e PriorityQueue

Descrição do conteúdo
Queue é uma fila. A operação típica coloca de um lado e tira do outro. Os métodos vêm em dois estilos. Um estilo lança exceção se a operação não pode acontecer (add, remove, element). O outro estilo devolve um sinal (offer, poll, peek): poll em fila vazia devolve null, remove em fila vazia lança exceção. Prefira o estilo que deixa o caso vazio explícito no código que você está escrevendo. No processamento de outbox, poll encaixa no "não há evento agora".

Deque é fila de duas pontas: addFirst, addLast, pollFirst, pollLast. Como pilha, use addFirst e pollFirst (ou push e pop). Como fila, use addLast e pollFirst.

ArrayDeque é a implementação que o curso usa para as duas coisas. Não aceita null. É mais eficiente que LinkedList e que a classe antiga Stack.

Deque<String> comandos = new ArrayDeque<>();
comandos.addLast("criar");
comandos.addLast("pagar");
String proximo = comandos.pollFirst();

PriorityQueue tira primeiro o elemento "menor" segundo Comparable ou Comparator, não o que entrou primeiro. Use para "processar antes o pedido com maior valor" só se essa política for real. Sem Comparator, a ordem natural decide. PriorityQueue não é estável visualmente: percorrer com for-each não mostra a ordem de prioridade; a ordem de prioridade aparece na retirada com poll. Não aceite null.

No Sistema de Pedidos, a fila durável de "pedido pago" não é ArrayDeque. ArrayDeque vive na memória de um processo e some quando a API reinicia. A fila durável é RabbitMQ, no nível 9. ArrayDeque entra em algoritmo local, em teste e em buffer curto de uma thread. Saber a diferença evita guardar evento de pagamento numa fila em memória achando que ela sobrevive a um restart.


Tema
Map: chave, valor e as operações do dia a dia

Descrição do conteúdo
Map liga uma chave a um valor. A chave não se repete. Se a chave já existe, um put substitui o valor e devolve o valor antigo.

Map<String, Produto> catalogo = new HashMap<>();
catalogo.put("SKU-1", produto);
Produto achado = catalogo.get("SKU-1");

get de chave ausente devolve null. Por isso get seguido de chamada de método estoura se o código não existir. Formas seguras:

catalogo.getOrDefault("SKU-1", produtoPadrao) quando existe um substituto de verdade.
catalogo.containsKey("SKU-1") quando presença e valor null significam coisas diferentes. No catálogo do curso o valor não é null, então a ausência é "não achei".
Optional.ofNullable(catalogo.get(codigo)) quando a próxima etapa é a aula de Optional.

putIfAbsent coloca só se a chave não existe.
replace troca só se a chave existe.
computeIfAbsent(chave, funcao) devolve o valor atual ou calcula, guarda e devolve. É a forma de montar um mapa de listas:

Map<StatusPedido, List<Pedido>> porStatus = new HashMap<>();
porStatus.computeIfAbsent(pedido.status(), chave -> new ArrayList<>()).add(pedido);

compute recalcula sempre. merge combina o valor novo com o antigo, útil para somar:

totais.merge(categoria, subtotal, BigDecimal::add);

remove(chave) tira o par. remove(chave, valor) tira só se o valor também bater.
keySet devolve as chaves, values devolve os valores, entrySet devolve os pares. Alterar o mapa enquanto percorre o entrySet com for-each lança ConcurrentModificationException. Remover pelo iterator do entrySet é a exceção segura.

Map.of("SKU-1", produto) cria mapa imutável, recusa null e recusa chave repetida. Map.copyOf copia para um imutável. Há um teto de pares no Map.of por sobrecarga; mapa grande imutável nasce com Map.copyOf.

O catálogo em memória do começo do curso é Map<String, Produto>. Quando o PostgreSQL assumir, o mapa some do estado da aplicação e a busca vira consulta. O Map continua no código de relatório em memória, de agrupamento e de cache local de uma operação.


Tema
HashMap, LinkedHashMap, TreeMap e EnumMap

Descrição do conteúdo
HashMap é o Map padrão. Chave localizada por hashCode e equals. Não promete ordem de travessia. Aceita uma chave null e valores null. Capacidade e fator de carga existem para ajustar tamanho interno; no catálogo e nos relatórios, o construtor vazio ou o construtor que recebe outro mapa bastam.

LinkedHashMap mantém a ordem de inserção. Dá para configurar ordem de acesso, usada em cache simples que descarta o menos usado recentemente. Como cache de produção o curso usa Redis, no nível 9. LinkedHashMap cabe em relatório que deve sair na ordem em que as chaves apareceram e em cache minúsculo local de uma instância, com a consciência de que duas instâncias da API não compartilham esse mapa.

TreeMap mantém as chaves ordenadas. As chaves precisam ser Comparable ou o mapa precisa receber Comparator. Métodos de faixa: firstKey, lastKey, subMap, ceilingKey, floorKey. Use quando a chave tiver ordem com significado, como mapa de limite de frete por valor. Custo logarítmico por operação.

EnumMap é o Map cuja chave é enum. É compacto e direto.

EnumMap<StatusPedido, Long> quantidade = new EnumMap<>(StatusPedido.class);

Use para contagem por status dentro de uma operação. Não use HashMap quando a chave for enum e o mapa for pequeno e quente: EnumMap deixa a intenção visível.

Escolha rápida: HashMap para busca por código, LinkedHashMap quando a ordem de entrada aparece na resposta, TreeMap quando a ordem da chave é consulta, EnumMap quando a chave é enum.


Tema
Iterator e ListIterator

Descrição do conteúdo
Iterator é o cursor de uma coleção. next avança e devolve. hasNext diz se ainda há elemento. remove tira o último devolvido e é a remoção segura durante a travessia.

Iterator<ItemPedido> cursor = itens.iterator();
while (cursor.hasNext()) {
    ItemPedido item = cursor.next();
    if (item.quantidade() == 0) {
        cursor.remove();
    }
}

O for-each é açúcar em cima do Iterator. Por baixo, o enhanced for pede um Iterator e chama next até acabar. A diferença de uso: no for-each você não tem o Iterator na mão, então não chama remove. Se a única necessidade é apagar por condição, removeIf é mais curto e usa a mesma ideia com segurança. Iterator aparece quando a lógica da volta é mais complicada que um predicado, e quando você lê código de biblioteca.

ListIterator só existe em List. Anda para a frente e para trás, devolve o índice, altera o elemento atual com set e insere com add. É a ferramenta quando a posição importa durante a própria travessia. Não é a ferramenta do relatório; relatório que filtra e transforma vai para Stream no próximo nível.

Fail-fast: o Iterator de ArrayList, HashMap e HashSet percebe se a coleção foi alterada por fora durante a volta e lança ConcurrentModificationException. Isso não é um mecanismo de concorrência entre threads; é uma detecção de modificação. Entre threads, essa exceção pode aparecer ou a estrutura pode corromper sem aviso. Coleção compartilhada entre requisições precisa de estrutura concorrente ou de proteção externa, assunto do nível 6.


Tema
Comparable e Comparator

Descrição do conteúdo
Comparable é a ordem natural do tipo. O método é compareTo. Devolver negativo significa "eu venho antes"; zero significa "empatamos nesta ordem"; positivo significa "eu venho depois".

public int compareTo(Produto outro) {
    return this.nome.compareTo(outro.nome);
}

Comparator é uma ordem externa, passada na hora de ordenar. O mesmo produto pode ser ordenado por nome numa tela e por preço em outra, sem mudar a classe.

itens.sort(Comparator.comparing(ItemPedido::subtotal).reversed());

Comparator.comparing recebe uma função que extrai a chave. thenComparing desempatam:

Comparator.comparing(Produto::nome)
    .thenComparing(Produto::codigo);

nullsLast e nullsFirst tratam chave nula. naturalOrder usa o Comparable.

List.sort altera a própria lista. Stream.sorted devolve uma sequência ordenada e deixa a lista original quieta. Se o pedido não deve mudar de ordem só porque um relatório foi impresso, ordene uma cópia ou use stream.

TreeSet e TreeMap usam a comparação para existir. ArrayList não; ela só ordena quando alguém chama sort.

Coerência: se compareTo devolve 0, o ideal é que equals seja verdadeiro, principalmente se o objeto for para um TreeSet. Em ordenação de relatório, Comparator pode usar só o preço e aceitar empate entre produtos diferentes; nesse caso ele não está definindo identidade, está só exibindo.


Tema
A classe Collections e as coleções sincronizadas

Descrição do conteúdo
java.util.Collections é uma classe utilitária, no plural. java.util.Collection é a interface, no singular. Os nomes parecidos confundem. A classe tem métodos estáticos.

Collections.emptyList, emptySet e emptyMap devolvem vazios imutáveis. Um só objeto vazio pode ser reutilizado.
Collections.singletonList e singletonMap embrulham um elemento.
Collections.unmodifiableList e os irmãos de Set e Map devolvem uma visão que recusa alteração. A coleção original ainda pode ser alterada por quem a segura, e a visão reflete a mudança. List.copyOf é diferente: é uma cópia que não acompanha a original.
Collections.sort ordena qualquer List. Na lista, list.sort(comparator) é a forma direta.
Collections.reverse, shuffle, frequency, disjoint, min e max cobrem operações prontas. min e max precisam de ordem.
Collections.synchronizedList, synchronizedSet e synchronizedMap embrulham uma coleção com trava em cada método. A travessia com for-each ainda precisa de bloco synchronized por fora, senão outra thread pode alterar no meio. Esse detalhe faz o embrulho ser fácil de usar errado. Para mapa compartilhado entre threads, ConcurrentHashMap é a escolha do nível 6, não o synchronizedMap. Para lista publicada raramente e lida o tempo todo, CopyOnWriteArrayList entra no mesmo nível.

No domínio do pedido, a utilidade mais usada é a lista imutável devolvida para fora e a ordenação explícita. O restante existe para ler código e para não reinventar reverse e frequency.


Tema
SequencedCollection no Java 21

Descrição do conteúdo
Java 21 torna explícito o que várias coleções já faziam: elas têm uma ordem conhecida, um primeiro e um último. SequencedCollection acrescenta getFirst, getLast, addFirst, addLast, removeFirst, removeLast e reversed. SequencedSet e SequencedMap especializam isso.

List e ArrayDeque entram nesse contrato. HashSet não entra, porque não tem ordem. LinkedHashSet entra.

Como se usa:

ItemPedido primeiro = itens.getFirst();
ItemPedido ultimo = itens.getLast();

Em lista vazia, getFirst lança NoSuchElementException. Teste isEmpty antes quando o vazio for um caso normal, como pedido recém-criado sem itens.

reversed devolve uma visão ao contrário, sem copiar necessariamente todos os elementos para outra lista. Percorrer a visão invertida não reordena a lista original.

No JSON do pedido, a ordem sequenciada da List é a ordem do array. Esse contrato importa para o cliente da API: o primeiro item do array é o primeiro item adicionado, e isso permanece verdade quando o Spring serializar a lista.


Tema
Como escolher a coleção no Sistema de Pedidos

Descrição do conteúdo
Itens do pedido: List implementada por ArrayList. Ordem de inclusão, acesso por posição, repetição permitida, devolvida para fora como lista imutável.
Catálogo em memória, enquanto não há banco: Map<String, Produto> com HashMap, chave o código do produto.
Relatório que precisa sair na ordem em que as categorias apareceram: LinkedHashMap ou LinkedHashSet.
Contagem por status dentro de um método: EnumMap.
Filtro de vários status: EnumSet.
Fila ou pilha local de uma operação: ArrayDeque.
Evento que precisa sobreviver a restart e ser lido por outro processo: nenhuma coleção do java.util. Isso é fila RabbitMQ ou tabela outbox.
Dado compartilhado alterado por requisições simultâneas: não um HashMap de campo de service. Banco, ou ConcurrentHashMap se for um cache local consciente de que vale só para aquela instância.

Esta escolha é a ponte para o Spring. O controller não escolhe ArrayList versus LinkedList. O domínio escolhe. O Jackson só percorre o que o domínio devolveu. O JPA persiste a ordem da lista se o mapeamento tiver uma coluna de ordem. A coleção certa evita remendo no endpoint.


================================================================================
NÍVEL 4 — INTERMEDIÁRIO: STREAMS E OS OUTROS MODOS DE PERCORRER
================================================================================

Tema
Quatro modos de percorrer e a diferença entre eles

Descrição do conteúdo
O mesmo conjunto de itens pode ser percorrido de quatro modos. Eles não são sinônimos.

Modo 1, for com índice. Você controla a posição, pode ler o anterior e o próximo, pode parar quando quiser, pode escrever numa posição. É o modo certo quando o índice faz parte da conta ou quando você altera a estrutura no meio com regra que olha a posição.

BigDecimal total = BigDecimal.ZERO;
for (int i = 0; i < itens.size(); i++) {
    total = total.add(itens.get(i).subtotal());
}

Modo 2, for-each. Você vê cada elemento, na ordem da coleção, sem índice. Por baixo existe um Iterator. Não dá para remover com a coleção no meio da volta. É o modo mais claro para somar, validar ou executar um efeito simples em cada elemento.

Modo 3, Iterator explícito. Igual ao for-each, com o cursor na sua mão. Use quando precisar remover o elemento atual durante a volta ou andar com ListIterator. É mais verboso. Em código novo de filtro simples, removeIf ou stream substituem a maior parte dos iterators manuais.

Modo 4, Stream. Você descreve um pipeline: de onde vêm os dados, quais ficam, em o que eles se transformam e qual resultado único sai no fim. A biblioteca executa. Não há índice, a não ser que você o fabrique. A coleção original não é reordenada nem filtrada no lugar; o resultado é outro valor, em geral outra lista, um mapa, um número ou um Optional.

List<String> nomesCaros = itens.stream()
        .filter(item -> item.subtotal().compareTo(limite) >= 0)
        .map(item -> item.produto().nome())
        .toList();

Como decidir no Sistema de Pedidos:

Somar o total no método do próprio pedido pode ser for-each. É uma acumulação simples, fácil de depurar, e o método já está ao lado dos dados.
Listar nomes dos itens acima de um valor, agrupar subtotal por categoria, juntar nomes com vírgula e montar um mapa de quantidade por produto são Stream. O pipeline diz o resultado e elimina a lista temporária manual.
Remover itens inválidos de uma lista mutável é removeIf, não stream. Stream não é a ferramenta de alteração da origem.
Parar no primeiro item que casa com uma busca simples pode ser um for com break, que é óbvio, ou stream com filter e findFirst, que também é óbvio. Os dois são aceitáveis. O curso usa findFirst quando já está num pipeline e for com break quando o laço faz mais de uma coisa.
Código com efeito colateral em cada volta (enviar um e-mail por item, salvar cada um) não fica mais correto por estar em stream. Isso é for-each ou um consumidor de fila. Stream com forEach cheio de efeito esconde a ação.

Stream não substitui a coleção. A coleção guarda. O stream calcula em cima do que está guardado. Sem List, Set ou Map, o pedido não tem onde ficar. Sem stream, o relatório ainda pode ser escrito com for. O nível 4 existe para o relatório, o agrupamento e a transformação ficarem diretos, e para o for permanecer onde ele é a forma mais clara.


Tema
Como um Stream é montado e por que ele é preguiçoso

Descrição do conteúdo
Um stream começa numa fonte: lista.stream(), Set.stream(), Map.entrySet().stream(), Arrays.stream(array), Stream.of(a, b, c), ou um gerador. A fonte não é consumida no momento do stream().

No meio ficam as operações intermediárias. Elas devolvem outro stream e não disparam o trabalho: filter, map, flatMap, distinct, sorted, peek, limit, skip, takeWhile, dropWhile, mapMulti.

No fim fica uma operação terminal. Ela dispara a travessia e devolve o resultado ou executa o efeito: collect, toList, reduce, count, forEach, min, max, anyMatch, allMatch, noneMatch, findFirst, findAny, toArray, sum e average nos streams primitivos.

Enquanto a terminal não é chamada, nada percorre a lista. Por isso montar o pipeline e esquecer a terminal é um bug silencioso: o filtro "não roda".

Cada elemento anda o pipeline inteiro até onde precisa. Com limit(2), o stream não precisa transformar a lista toda se a fonte for capaz de parar. Isso é diferente de um for que você escreveu para ir até o fim. Também é diferente de criar três listas temporárias, uma para filtrar, outra para transformar e outra para ordenar. O pipeline pode fazer as etapas em sequência por elemento.

O stream é de uso único. Depois da terminal, chamar outra terminal no mesmo stream lança IllegalStateException. Se dois resultados são necessários, abra dois streams na coleção, ou colete uma vez e derive.

O stream não guarda os itens como uma List guarda. Ele não tem size confiável antes do fim, não tem índice e não aceita add. Se o resultado precisa ser guardado, a terminal materializa uma coleção nova.


Tema
filter, map e a diferença entre selecionar e transformar

Descrição do conteúdo
filter recebe um Predicate, uma função que devolve boolean. O elemento continua do mesmo tipo. Só quem passa segue no pipeline.

map recebe uma Function. Cada elemento vira outro valor, talvez de outro tipo. A quantidade de elementos não muda, só o conteúdo.

Itens com subtotal alto, exibindo só o nome:

List<String> nomes = itens.stream()
        .filter(item -> item.subtotal().compareTo(new BigDecimal("100")) >= 0)
        .map(item -> item.produto().nome())
        .toList();

O mesmo resultado com for-each exige uma lista criada antes e um if dentro da volta:

List<String> nomes = new ArrayList<>();
for (ItemPedido item : itens) {
    if (item.subtotal().compareTo(new BigDecimal("100")) >= 0) {
        nomes.add(item.produto().nome());
    }
}

Os dois resultados são iguais. O for deixa a lista e o if visíveis. O stream deixa a sequência filtro-depois-transformação visível. Quando há um filtro e um map, os dois estilos são claros. Quando entram agrupamento, ordenação e junção de texto, o for acumula várias estruturas temporárias e o stream permanece uma leitura só.

map não achata lista. Se a função devolve uma lista, o resultado é stream de listas. Para "todos os itens de todos os pedidos", a operação é flatMap, na próxima aula.

Predicate e Function são interfaces funcionais do pacote java.util.function. Lambda encaixa porque cada uma tem um método abstrato só. Dá para guardar o predicado numa variável e reutilizar:

Predicate<ItemPedido> caro = item -> item.subtotal().compareTo(limite) >= 0;

No Spring, o mesmo Predicate aparece em Specification do JPA de um jeito parecido na intenção e diferente na execução: o filter do stream decide em memória, depois que os pedidos já foram carregados. A Specification vira SQL e decide no banco. Filtrar uma página enorme em memória com stream, depois de ter buscado tudo, é o erro de uso. Stream trabalha no que já está na mão. Recorte grande é cláusula WHERE.


Tema
flatMap e mapMulti

Descrição do conteúdo
flatMap achata um stream de várias coisas num stream de coisa. Cada pedido tem vários itens. A pergunta "quais produtos aparecem nesta lista de pedidos?" começa em pedido e precisa descer ao item.

List<String> produtos = pedidos.stream()
        .flatMap(pedido -> pedido.itens().stream())
        .map(item -> item.produto().nome())
        .distinct()
        .toList();

Se fosse map em vez de flatMap, o tipo seria lista de streams ou lista de listas, e os nomes não estariam num nível só. A lambda do flatMap devolve um Stream. A biblioteca concatena esses streams.

Optional também tem flatMap. Serve quando cada passo pode não achar um valor: achar pedido, depois achar pagamento, depois achar autorização, sem if encadeado de isPresent. Esse uso fica na aula de Optional, no nível 5. A ideia é a mesma: um nível só, em vez de aninhar.

mapMulti, do Java 16, emite zero, um ou vários valores por elemento. A lambda recebe o elemento e um consumidor. Cada accept manda um valor para o stream de saída.

List<String> nomes = pedidos.stream().<String>mapMulti((pedido, saida) -> {
    if (pedido.total().signum() > 0) {
        saida.accept(pedido.codigo());
    }
    for (ItemPedido item : pedido.itens()) {
        saida.accept(item.produto().nome());
    }
}).toList();

Use quando o bloco decide, com if, quantos valores saem. flatMap nesse caso criaria um Stream.of para cada ramo. No relatório do Sistema de Pedidos, cada pedido só abre o stream dos seus itens, e flatMap cobre isso. mapMulti entra quando a emissão vira um bloco com várias decisões.


Tema
distinct, sorted, limit, skip, takeWhile e dropWhile

Descrição do conteúdo
distinct tira duplicata segundo equals e hashCode, estável em stream sequencial: a primeira ocorrência permanece. Em stream paralelo a estabilidade não é a promessa para se apoiar. distinct de produto depende do equals de Produto. Se equals usa só o código, dois objetos com o mesmo código contam como um.

sorted sem argumento exige Comparable. sorted(comparator) usa a ordem dada. sorted no meio de um pipeline grande ordena o que passou pelos filtros anteriores, não a coleção original. A List do pedido permanece na ordem de inclusão.

limit(n) deixa passar no máximo n elementos. skip(n) descarta os n primeiros. Os dois juntos paginam em memória:

List<Pedido> pagina = pedidos.stream()
        .skip(20)
        .limit(10)
        .toList();

Isso só é válido para uma coleção que já está inteira na memória e pequena. Paginação de API com milhares de pedidos é PageRequest do Spring Data, que vira LIMIT e OFFSET no SQL. skip e limit no stream depois de buscar o banco inteiro carregam o que não será exibido.

takeWhile deixa passar enquanto o predicado for verdadeiro e para no primeiro falso. dropWhile descarta enquanto for verdadeiro e entrega o resto, inclusive o que falharia depois. Os dois dependem da ordem. Em lista não ordenada pelo critério, o corte acontece cedo demais. Ordene antes se a condição for uma faixa, ou use filter se a intenção for "todos os que passam", não "até o primeiro que falhe".

Exemplo honesto de takeWhile: uma lista já ordenada por data, pegar eventos até o instante do corte. Exemplo honesto de filter: todos os pedidos pagos, em qualquer posição da lista.


Tema
peek e forEach: olhar e causar efeito

Descrição do conteúdo
peek é intermediária. Ela devolve o mesmo elemento e serve para olhar o valor no meio do pipeline durante o desenvolvimento:

stream.filter(...)
      .peek(item -> log.debug("depois do filtro: {}", item))
      .map(...)

Não use peek para alterar estado, salvar ou publicar evento. A preguiça e o curto-circuito fazem o peek não rodar para elemento que uma terminal não puxar. Efeito dentro de peek é bug intermitente. Em código que fica, tire o peek de debug.

forEach é terminal. Ela executa um bloco por elemento e não devolve valor.

nomes.forEach(nome -> log.info("produto {}", nome));

A diferença para o for-each clássico: forEach da coleção e forEach do stream não prometem ordem se a fonte não tem ordem (HashSet), e o forEach de stream paralelo não promete ordem nenhuma. forEachOrdered preserva a ordem do stream quando a ordem existe, mesmo em paralelo, com custo maior.

Efeito de negócio (mudar status, enviar mensagem, gravar) fica num for clássico ou num método de caso de uso chamado de forma explícita, não num forEach no meio de uma cadeia que parecia consulta. Consulta devolve valor. Comando altera estado. Misturar os dois na mesma cadeia dificulta o teste e o retry.


Tema
Operações terminais de busca e de teste

Descrição do conteúdo
anyMatch devolve true se algum elemento passa no predicado. allMatch devolve true se todos passam. Lista vazia faz allMatch devolver true, porque não há contraexemplo; isso surpreende. Se o pedido sem itens não pode ser "todos os itens válidos", teste isEmpty antes. noneMatch é o inverso de anyMatch.

findFirst devolve Optional com o primeiro elemento que sobrou no pipeline, respeitando a ordem se ela existir. findAny devolve algum, e em stream paralelo pode não ser o primeiro. Use findFirst quando o primeiro da ordem é a regra (o item mais antigo depois de ordenar). Use findAny quando qualquer um serve e a ordem não é contrato.

count devolve long. min e max devolvem Optional e exigem Comparator, exceto nos streams primitivos, que têm min e max numéricos.

itens.stream()
    .map(ItemPedido::subtotal)
    .max(Comparator.naturalOrder());

Essas terminais podem parar cedo. anyMatch para no primeiro verdadeiro. Isso é uma vantagem em relação a construir uma lista filtrada só para perguntar se ela está vazia.

No endpoint de pedido, "existe item deste produto?" pode ser anyMatch na lista já carregada do agregado. "Existe pedido deste cliente no banco?" é exists do repositório, não carregar todos os pedidos e abrir stream.


Tema
reduce e a soma com BigDecimal

Descrição do conteúdo
reduce combina os elementos num único valor. A forma com identidade e acumulador:

BigDecimal total = itens.stream()
        .map(ItemPedido::subtotal)
        .reduce(BigDecimal.ZERO, BigDecimal::add);

ZERO é o ponto de partida. add é a combinação. Lista vazia devolve ZERO, que é o total correto de um pedido sem itens.

A forma sem identidade devolve Optional, vazio se não houver elemento. Use quando não existir um zero natural e a ausência for uma resposta possível.

Há uma forma de três argumentos, com combinador, usada de verdade em stream paralelo para juntar resultados parciais. Em stream sequencial de dinheiro, a forma de dois argumentos é a que se lê melhor.

reduce com efeito colateral no acumulador, como passar uma lista e dar add nela, funciona por acidente e quebra em paralelo. Se o resultado é uma coleção, a terminal certa é collect ou toList, não reduce montando lista na mão.

A diferença para o for: o for também acumula total = total.add(...). O reduce torna o valor inicial e a operação explícitos e cabe no fim de um pipeline que já filtrou. Se não houve filtro nem transformação, o for-each é igualmente claro e o debugger mostra a volta com mais naturalidade. O curso usa reduce no fim de pipeline e for-each na soma simples dentro da entidade.


Tema
collect, toList e as três formas de materializar

Descrição do conteúdo
A terminal mais usada é a que transforma o stream numa estrutura.

.stream().filter(...).toList() devolve uma lista imutável. É a forma do Java 16 em diante e a padrão do curso para resposta e para valor calculado.
Collectors.toList() devolve uma lista que, na implementação atual, é mutável, mas o contrato não garante mutabilidade para sempre. Se o código vai dar add depois, crie new ArrayList<>(lista) ou use Collectors.toCollection(ArrayList::new).
Collectors.toSet() devolve um Set. A implementação não garante qual Set. Se a ordem de inserção importa no conjunto resultante, use Collectors.toCollection(LinkedHashSet::new).
Collectors.toCollection(TreeSet::new) escolhe a classe.

toArray sem argumento devolve Object[]. toArray(String[]::new) devolve array do tipo certo. Use quando uma API antiga exigir array. A API nova do curso devolve List.

No controller Spring, devolver List direto vira JSON array. Devolver a lista imutável do toList é adequado para resposta. Se um filtro posterior do framework tentar alterar essa lista, ele toma UnsupportedOperationException, e isso é um sinal de que a alteração deveria ter acontecido antes, no caso de uso.


Tema
Collectors.joining, toMap e o cuidado com chave repetida

Descrição do conteúdo
joining monta uma única String.

String resumo = itens.stream()
        .map(item -> item.produto().nome())
        .collect(Collectors.joining(", "));

Dá para passar prefixo e sufixo: joining(", ", "[", "]"). É o relatório de uma linha. A alternativa manual é StringBuilder no for. joining é a forma quando os elementos já estão num stream.

toMap constrói um Map. A primeira função é a chave, a segunda é o valor.

Map<String, BigDecimal> porCodigo = itens.stream()
        .collect(Collectors.toMap(
                item -> item.produto().codigo(),
                ItemPedido::subtotal));

Se dois itens tiverem o mesmo código, toMap sem merge lança IllegalStateException. Quando a regra é somar:

Map<String, BigDecimal> porCodigo = itens.stream()
        .collect(Collectors.toMap(
                item -> item.produto().codigo(),
                ItemPedido::subtotal,
                BigDecimal::add));

O terceiro argumento é o merge. Sem ele, chave repetida é erro. Há um quarto argumento para escolher o tipo do mapa, por exemplo LinkedHashMap::new, quando a ordem de aparição deve sobreviver.

Valor null não entra em toMap: a implementação rejeita. Limpe ou troque o null antes.

A diferença para ir pondo num HashMap dentro de um for: o for com merge manual é legítimo e às vezes mais fácil de depurar. toMap é a forma quando o pipeline já existe e a chave é simples. Se a construção do valor tem vários ifs, um for que preenche o mapa pode ser a leitura mais honesta. O curso não força stream em montagem cheia de exceção de negócio.


Tema
groupingBy, partitioningBy e teeing

Descrição do conteúdo
groupingBy agrupa por uma chave e devolve Map<Chave, List<Elemento>>.

Map<String, List<ItemPedido>> porCategoria = itens.stream()
        .collect(Collectors.groupingBy(item -> item.produto().categoria()));

O segundo argumento muda o que fica em cada grupo. Contar:

Collectors.groupingBy(item -> item.produto().categoria(), Collectors.counting())

Somar subtotal:

Collectors.groupingBy(
        item -> item.produto().categoria(),
        Collectors.reducing(BigDecimal.ZERO, ItemPedido::subtotal, BigDecimal::add));

O mapa resultante é HashMap salvo se você passar uma fábrica, como LinkedHashMap::new, no meio da chamada. A ordem das categorias no HashMap não é a ordem de aparição. Se a tela exige ordem estável, escolha o mapa ou ordene na hora de apresentar.

partitioningBy é o agrupamento em dois: true e false. A chave é Boolean. Serve para separar itens válidos e inválidos, ou pedidos atrasados e no prazo, quando as duas listas serão usadas. Se só uma interessa, filter é mais direto.

teeing, do Java 12, aplica dois collectors no mesmo stream e junta os dois resultados. Dá para calcular quantidade e soma numa travessia só. Use quando os dois resultados nascem dos mesmos elementos e uma segunda travessia seria desperdício real. Para lista pequena de pedido, duas travessias custam nada; teeing vale quando a fonte é grande ou cara. Não use teeing para parecer avançado num relatório de dez linhas.


Tema
summarizing e coletores primitivos

Descrição do conteúdo
Collectors.summarizingInt, summarizingLong e summarizingDouble devolvem um resumo com quantidade, soma, mínimo, máximo e média numa travessia. O tipo é IntSummaryStatistics e os irmãos.

IntSummaryStatistics stats = itens.stream()
        .collect(Collectors.summarizingInt(ItemPedido::quantidade));
long unidades = stats.getSum();

Isso é para int, long e double. Dinheiro em BigDecimal não passa por summarizingDouble, porque double alteraria o valor. Soma de dinheiro continua reduce com BigDecimal.

averagingInt e summingInt existem como collectors separados quando só um número interessa. count do stream já cobre a quantidade, sem collector.

Esses coletores aparecem em relatório operacional: quantas unidades saíram, qual a maior linha em unidades. O valor monetário do mesmo relatório usa o pipeline de BigDecimal ao lado, sem misturar os tipos.


Tema
Streams primitivos: IntStream, LongStream e DoubleStream

Descrição do conteúdo
Stream<Integer> guarda objeto Integer. Cada número sofre boxing. IntStream guarda o primitivo e oferece sum, average, max, min e range.

int unidades = itens.stream()
        .mapToInt(ItemPedido::quantidade)
        .sum();

mapToInt converte o stream de objeto num IntStream. mapToLong e mapToDouble fazem o análogo. boxed faz o caminho de volta para Stream<Integer> quando um collector de objeto é necessário.

IntStream.range(0, 10) gera 0 inclusive até 10 exclusive. rangeClosed inclui o fim. Serve para índice quando a operação é numérica de verdade. Não serve para percorrer o pedido só para "usar stream em tudo": itens.stream() já percorre os itens.

DoubleStream não é caminho para preço. A aula existe para métrica, estatística e código que já está em double por natureza do problema. Total do pedido permanece BigDecimal no Stream<BigDecimal> ou num for.

Stream paralelo de primitivo tem o mesmo alerta da aula de paralelo: a soma de poucas quantidades não justifica paralelizar, e o ganho só aparece em volume grande de CPU.


Tema
Stream infinito, generate e iterate

Descrição do conteúdo
Stream.iterate(0, n -> n + 1) produz 0, 1, 2, sem fim. Stream.generate(UUID::randomUUID) produz identificadores sem fim. Os dois só podem ser usados com uma trava: limit, takeWhile ou uma terminal que para, como findFirst.

List<Integer> dez = Stream.iterate(1, n -> n + 1).limit(10).toList();

Sem limit, a terminal que tenta guardar tudo não acaba. Esse é o oposto da coleção, que tem tamanho finito porque os elementos já estão guardados.

No curso, gerador infinito quase não entra no pedido. Ele aparece para dado de teste e para demonstrar que stream é uma sequência calculada, não um depósito. Id de pedido real vem de UUID.randomUUID uma vez por pedido, ou da sequence do banco, não de um stream infinito.


Tema
Stream paralelo: quando acelera e quando corrompe

Descrição do conteúdo
lista.parallelStream() ou stream.parallel() divide o trabalho entre várias threads do pool comum ForkJoinPool. O pipeline precisa ser associativo, sem efeito colateral e sem depender de ordem frágil, para o resultado permanecer correto.

Soma de uma lista enorme de números primitivos pode melhorar. Relatório do pedido com dezenas de itens piora ou empata, porque o custo de dividir é maior que o custo de somar.

O que dá errado: ArrayList compartilhada recebendo add dentro do pipeline paralelo, mapa comum recebendo put, campo de entidade sendo alterado, e qualquer chamada a código que não foi feito para rodar ao mesmo tempo. A exceção pode aparecer, ou o resultado pode sair faltando linha, o que é pior.

Ordem: forEach paralelo não respeita ordem. forEachOrdered respeita e reduz o ganho. findAny pode devolver outro elemento. distinct e sorted têm custo extra.

No Spring Boot, parallelStream dentro de uma requisição usa o ForkJoinPool comum, que não é o pool das requisições. Muitos endpoints em paralelo disputam esse mesmo pool e atrasam o restante do processo. O Sistema de Pedidos não usa parallelStream. Volume grande de processamento em lote vai para uma fila com vários consumidores, onde o paralelismo tem limite explícito. parallel() não é otimização automática: só entra depois de medir um pipeline associativo, sem efeito colateral, cujo custo de dividir seja menor que o custo de calcular.


Tema
Method reference

Descrição do conteúdo
Method reference é uma lambda que só chama um método existente. Os quatro formatos:

ItemPedido::subtotal é método de instância do elemento. Equivale a item -> item.subtotal().
BigDecimal::add é método de instância usado como função de dois argumentos no reduce. Equivale a (a, b) -> a.add(b).
Produto::new é construtor. Equivale a nome -> new Produto(nome) quando a assinatura bate. Com vários parâmetros, a assinatura do construtor precisa ser exatamente a do alvo.
String::valueOf no formato estático. Equivale a n -> String.valueOf(n).

Use quando o nome do método comunica sozinho. Se a lambda tem if, mais de uma chamada ou um valor capturado, escreva a lambda. Referência difícil de ler é pior que lambda de duas linhas.

Variável capturada pela lambda precisa ser final ou não mudar depois de capturada. O compilador recusa a lambda que usa um total que o laço reatribui. Isso empurra a acumulação para reduce ou para o for, que é outro sinal útil de escolha de estilo.


Tema
Erros clássicos de stream no projeto

Descrição do conteúdo
Reusar o stream depois da terminal. Crie o pipeline de novo a partir da coleção.
Alterar a lista enquanto o stream dela está aberto. Termine o stream, ou opere numa cópia.
filter ou map com efeito de salvar no banco. Isso é comando, não pipeline. O caso de uso salva; o stream calcula.
Null no meio. map que chama método em produto null estoura. Recuse null na entrada da lista, ou filtre Objects::nonNull com a consciência de que esconder null pode esconder dado inválido.
Stream gigante em cima de resultado JPA carregado inteiro. A filtragem que define quais pedidos entram na página mora na consulta. O stream formata e agrega o que a página já trouxe.
Esquecer que toList é imutável e tentar adicionar o frete como item falso depois. Monte a lista completa antes, ou use uma lista mutável de propósito.
allMatch em lista vazia tratado como sucesso de negócio sem pensar. Pedido sem item não está pronto para pagamento, mesmo que "todos os itens sejam válidos" seja vacuamente verdadeiro.
Paralelizar relatório pequeno. O resultado correto e mais lento de diagnosticar não paga o custo.

A prática do nível 4 no Sistema de Pedidos fecha com três entregas escritas das duas formas, for e stream, sobre o mesmo dado: total dos itens acima de um valor, nomes distintos, e subtotal agrupado por categoria. As duas versões devolvem o mesmo resultado. A comparação mostra onde o for deixa o índice e o acumulador visíveis, e onde o stream deixa o filtro, a transformação e o agrupamento numa leitura só. A partir daí o projeto adota a regra desta aula. O Spring dos níveis seguintes chama o método que devolve o relatório e não muda essa regra.


================================================================================
NÍVEL 5 — INTERMEDIÁRIO: A PLATAFORMA JAVA QUE O SPRING USA
================================================================================

Tema
Exceções: hierarquia, checked, unchecked, try, catch, finally

Descrição do conteúdo
Erro de execução é um objeto lançado com throw. A pilha desmonta até alguém capturar com catch, ou até o processo tratar como falha não pegada.

Throwable é o topo. Error é falha grave da JVM, como falta de memória; aplicação não tenta tratar. Exception é o que o código de negócio vê. RuntimeException é exceção não checada: o compilador não obriga catch nem throws. As outras Exception são checadas: o compilador obriga a tratar ou a declarar.

IllegalArgumentException, IllegalStateException, NullPointerException e NumberFormatException são não checadas e já existem. ProdutoNaoEncontradoException e TransicaoDeStatusInvalidaException são as não checadas do projeto, estendendo RuntimeException, com mensagem e, quando ajuda, o id.

Exceção checada força todo método no caminho a declarar throws ou a capturar. Em aplicação Spring isso espalha assinatura sem ganho, porque o tratamento de verdade fica no @ControllerAdvice, num lugar só. O curso usa não checadas para regra de negócio e deixa checadas onde a plataforma já impôs, como IOException na borda de arquivo e SQLException por baixo do JPA, em geral embrulhada pelo Spring.

try executa. catch (ProdutoNaoEncontradoException e) trata aquele tipo. catch (Exception e) pega o resto e, se vier primeiro, impede o catch específico de funcionar: a ordem é da mais específica para a mais geral. finally roda nos dois caminhos, com ou sem exceção, e é o lugar antigo de fechar recurso. Recurso novo usa try-with-resources.

Não capture para logar e seguir como se nada tivesse acontecido, a menos que exista um substituto real. Não use exceção para o if do dia a dia ("produto existe?" pode ser Optional na busca). Use exceção quando a operação não pode continuar: confirmar um pedido sem item, pagar um pedido cancelado.

Como o Spring entra: a exceção sobe do domínio até o controller. Se ninguém capturar antes, o Boot devolve 500. No nível 8, um @ControllerAdvice transforma ProdutoNaoEncontradoException em 404 e TransicaoDeStatusInvalidaException em 409. A exceção não conhece HTTP. O adaptador web conhece.


Tema
try-with-resources e AutoCloseable

Descrição do conteúdo
Qualquer objeto que implementa AutoCloseable pode ser aberto no parêntese do try. Ao sair do bloco, close é chamado, com sucesso ou com exceção.

try (BufferedReader leitor = Files.newBufferedReader(caminho)) {
    return leitor.lines().toList();
}

Se o corpo lançar uma exceção e o close lançar outra, a do corpo segue como principal e a do close fica como suprimida, acessível por getSuppressed. Isso evita perder a causa original.

Dá para abrir vários recursos no mesmo parêntese, separados por ponto e vírgula. Eles fecham na ordem inversa.

No Spring, conexão de banco, sessão JPA e stream de arquivo seguem essa ideia, muitas vezes escondida. A conexão do HikariCP é emprestada no começo da transação e devolvida no fim, que é um close lógico. Quem abre InputStream num endpoint precisa garantir o fechamento, e o try-with-resources é a forma. Esquecer o close de arquivo segura descritor do sistema operacional até a API passar a falhar sob carga.


Tema
Optional

Descrição do conteúdo
Optional é uma caixa que pode ter um valor ou não. Ela torna a ausência visível no tipo de retorno. Busca de produto por código devolve Optional<Produto>, não null.

Como se cria: Optional.of(produto) se o produto não for null; of lança exceção se for. Optional.ofNullable(produto) aceita null e vira vazio. Optional.empty() é o vazio explícito.

Como se consome:

catalogo.buscar(codigo).orElseThrow(() -> new ProdutoNaoEncontradoException(codigo));

orElse(substituto) devolve o substituto se vazio. O substituto é calculado antes da chamada, então não use orElse para criar objeto caro ou para chamar banco. orElseGet(Produto::padrao) só chama a função se estiver vazio.
ifPresent executa um bloco se houver valor. ifPresentOrElse faz os dois caminhos.
map transforma o valor se ele existe, e devolve Optional do resultado. Se estiver vazio, continua vazio.
flatMap transforma num outro Optional e não cria Optional dentro de Optional. É o encadeamento de buscas.
filter deixa o valor só se o predicado passar.
or devolve outro Optional se este estiver vazio.
stream() do Optional vira stream de zero ou um elemento, útil para encaixar num flatMap de pipeline.

Onde não se usa: campo de entidade, parâmetro de método e coleção de Optional. Campo ausente da entidade é um tipo anulável tratado na borda, ou um objeto de valor com regra própria. Parâmetro Optional espalha ifPresent pelo sistema. Lista vazia já expressa "nenhum item"; List<Optional<Item>> não ajuda.

A diferença para null: null é silencioso e estoura longe da origem. Optional obriga o chamador imediato a decidir. A diferença para exceção: Optional é para busca que pode não achar no fluxo normal. Exceção é para operação inválida. buscar(codigo) devolve Optional. confirmar(pedido sem item) lança exceção.

No Spring Data, findById já devolve Optional. O caso de uso chama orElseThrow e converte ausência em erro de negócio. O controller não recebe null do repositório.


Tema
java.time: data, hora e fuso

Descrição do conteúdo
java.time substitui Date e Calendar. Os tipos são imutáveis. Uma alteração devolve outro objeto.

LocalDate é só o dia: previsão de entrega sem hora. LocalTime é só a hora. LocalDateTime é dia e hora sem fuso: cuidado, o mesmo número de relógio não é o mesmo instante em duas cidades. Instant é um ponto na linha do tempo, em UTC, e é o padrão para criadoEm persistido. ZonedDateTime junta instante e fuso. ZoneId.of("America/Sao_Paulo") é o fuso, e ele conhece horário de verão histórico. OffsetDateTime guarda o deslocamento numérico, como o que APIs devolvem em ISO-8601.

Como se obtém o agora:

Instant agora = Instant.now();
LocalDate hoje = LocalDate.now(ZoneId.of("America/Sao_Paulo"));

now() sem fuso usa o fuso da máquina. Em servidor e em container, o fuso da máquina pode ser UTC. Para "o dia do cliente", passe o ZoneId. Para "quando isso aconteceu de verdade", guarde Instant.

Como se calcula: criadoEm.plus(Duration.ofMinutes(30)) para prazo de rascunho. LocalDate.plusDays(2) para entrega. Duration é tempo corrido em horas, minutos, segundos. Period é calendário em anos, meses e dias. Os dois não se misturam à toa: um dia de Period nem sempre tem 24 horas quando o fuso muda.

Como se compara: isBefore, isAfter, equals. Instant e LocalDateTime não se comparam entre si direto; converta com fuso explícito.

Como se formata e se lê:

DateTimeFormatter iso = DateTimeFormatter.ISO_INSTANT;
DateTimeFormatter br = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withZone(ZoneId.of("America/Sao_Paulo"));
String texto = br.format(instant);
Instant lido = Instant.parse("2026-10-13T14:00:00Z");

Pattern para exibir. ISO para gravar, para JSON e para conversar com outra API. O Jackson, com o módulo de datas do Boot, serializa Instant em ISO-8601. O banco PostgreSQL recebe timestamptz.

Clock injetável deixa o teste fixar o instante. Em vez de Instant.now() espalhado, o domínio recebe um java.time.Clock. No teste, Clock.fixed(...). No Spring, um @Bean de Clock.systemUTC(). Sem isso, teste de "expira em 30 minutos" fica intermitente.


Tema
Entrada, saída e NIO.2

Descrição do conteúdo
Arquivo e rede são fluxos de bytes ou de caracteres. InputStream lê bytes. OutputStream escreve bytes. Reader e Writer trabalham com caracteres e precisam de charset. O charset do curso é UTF-8. FileReader sem charset usa o padrão da máquina e corrompe acento quando o container está em outro padrão; por isso a forma explícita é Files.newBufferedReader(caminho, StandardCharsets.UTF_8).

Path representa um caminho. Paths.get("dados", "pedido.json") ou Path.of(...) monta. Files.exists, Files.createDirectories, Files.readString, Files.writeString, Files.newBufferedReader e Files.list cobrem o uso diário. readString e writeString cabem em arquivo pequeno de configuração e em teste. Arquivo grande se lê em stream ou em linhas, sem carregar tudo:

try (Stream<String> linhas = Files.lines(caminho)) {
    return linhas.filter(linha -> !linha.isBlank()).toList();
}

O stream de linhas precisa fechar, e o try-with-resources faz isso. Deixar aberto segura o arquivo.

BufferedReader.lines devolve um stream de linhas. É aqui que o nível 4 reaparece na borda de arquivo. A regra é a mesma: a fonte pode ser cara; não leia um arquivo enorme para filtrar o que o banco filtraria melhor. Importação de catálogo em arquivo é um caso legítimo.

No Spring Boot, arquivo deixa de ser o armazenamento do pedido. O uso que permanece: ler recurso do classpath (classpath:application.yaml o próprio Boot lê), receber upload MultipartFile, gerar um PDF ou CSV para download e escrever log. MultipartFile.getInputStream também entra em try-with-resources. Caminho de arquivo fixo dentro do código quebra no container, porque o diretório da imagem não é o diretório do notebook. Caminho configurável por variável de ambiente, ou armazenamento de objeto, é a evolução quando o arquivo for de verdade.


Tema
Expressões regulares

Descrição do conteúdo
Regex descreve um formato de texto. Pattern é a expressão compilada. Matcher aplica a expressão numa String. String também tem matches, replaceAll e split, que recompilam a expressão a cada chamada. Expressão usada o tempo todo deve ser um Pattern constante.

Pattern cep = Pattern.compile("^\\d{8}$");
boolean ok = cep.matcher(texto).matches();

No Java, a barra invertida da regex é escapada de novo por causa da String: \\d significa dígito. Em text block o escape continua valendo.

Métodos do Matcher: matches exige o texto inteiro; find acha um trecho; group(1) devolve o trecho do parêntese. replaceAll no Matcher troca o que casou.

Use regex para formato fechado: CEP de oito dígitos, código de produto no padrão do catálogo. Não use regex para analisar JSON, XML ou HTML; use o parser. Não use regex para e-mail perfeito; valide presença simples na borda e deixe o provedor de e-mail fazer o resto, ou use uma validação Jakarta pronta no DTO.

No Spring, @Pattern do Bean Validation recebe a regex no campo do DTO. A expressão é a mesma. A diferença é que a anotação dispara sozinha quando o @Valid roda, e o erro vira 400 no handler do nível 8.


Tema
Objects, Objects.requireNonNull e utilitários de java.lang

Descrição do conteúdo
java.util.Objects é a classe utilitária de referência. Objects.equals(a, b) aceita null dos dois lados. Objects.hash(...) monta hashCode. Objects.toString(valor, "sem valor") evita null na impressão. Objects.isNull e nonNull viram Predicate, por exemplo filter(Objects::nonNull).

Objects.requireNonNull(produto, "produto obrigatorio") devolve o próprio produto se ele existir e lança NullPointerException com a mensagem se não existir. Use na primeira linha de construtor e de método público que não aceita ausência. Para regra mais rica que null, lance IllegalArgumentException com texto de negócio.

java.lang.System dá System.out, System.err, System.getenv e System.currentTimeMillis. getenv lê variável de ambiente, que é como o container entrega senha e URL. No Spring, o mesmo valor chega por application.yaml e por ${VARIAVEL}, e o código de negócio não chama getenv espalhado. currentTimeMillis é instante opaco em long; Instant é mais claro. System.out.println sai no console e não tem nível nem correlação; no Boot, o log ocupa esse lugar.

java.lang.Runtime representa o processo da JVM. Runtime.getRuntime().availableProcessors() devolve quantos processadores a JVM enxerga, número que um pool usa como pista de tamanho. Runtime.getRuntime().exec e ProcessBuilder sobem outro processo do sistema operacional, com comando, argumentos, diretório e a leitura da saída. A API de pedidos não sobe processo. RuntimeException é outra classe: a exceção não checada. Os nomes se parecem e não têm relação.


Tema
Reflexão, Class e o que o Spring lê em tempo de execução

Descrição do conteúdo
Reflexão é o código inspectando classe, método, campo e anotação depois de compilado.

Class<?> tipo = Pedido.class;
Method[] metodos = tipo.getDeclaredMethods();
CasoDeUso nota = metodo.getAnnotation(CasoDeUso.class);

getDeclaredFields vê campos privados. setAccessible(true) furar private é possível e perigoso: quebra encapsulamento e quebra de novo se um agente de módulo bloquear. Framework faz isso com regra conhecida. Código de negócio do pedido não altera campo privado de outra classe por reflexão.

Class é o objeto que descreve o tipo. produto.getClass() devolve a classe real da instância. Pedido.class é a classe escrita no código. O nome getClass não é o getter de um campo.

O Spring, na subida, varre os pacotes, acha classes com @Component, @Service, @RestController e @Configuration, instancia, guarda e injeta. Na chamada @Transactional, a casca ao redor do objeto lê a anotação no método. O Jackson lê o nome dos componentes do record ou dos getters para montar JSON. O JPA lê @Entity e @Id para montar SQL. Tudo isso é reflexão mais geração de proxy.

Saber usar um pouco evita tratar o Boot como magia. Saber o custo evita usar reflexão no cálculo do desconto: é mais lento, perde a checagem do compilador e não traz ganho. Regra de negócio chama método normal.


Tema
Proxy: a casca que faz @Transactional funcionar

Descrição do conteúdo
Proxy é um objeto falso na frente do objeto real. Ele implementa a mesma interface, ou é uma subclasse gerada, e intercepta a chamada. Antes e depois do método real, ele pode abrir transação, medir tempo ou verificar permissão.

Como isso se usa na prática: você chama pedidoService.confirmar(id). Se quem chama é outro bean do Spring, a chamada entra no proxy, o proxy abre a transação, chama o método real e faz commit no fim. Se o método confirmar chama this.outroMetodo() dentro da mesma classe, essa chamada interna não passa pelo proxy. A anotação @Transactional no outroMetodo não age. A forma de usar é colocar a fronteira transacional num método público de outro bean, chamado de fora, ou extrair o caso de uso para uma classe cuja entrada é o método anotado.

O mesmo mecanismo explica @Cacheable e a segurança de método. Explica também por que classe final e método private não recebem esse conselho do proxy baseado em herança: não dá para estender classe final nem sobrescrever private. Injeção por interface não sofre essa limitação do mesmo jeito, porque o proxy implementa a interface.

A aplicação escreve a classe de negócio. O Spring gera o proxy na subida. Quando a anotação parece ignorada, a chamada chegou no objeto real por this, sem passar pela casca.


Tema
Serialização Java e JSON com Jackson

Descrição do conteúdo
Serialização Java nativa transforma objeto em bytes. A classe implementa java.io.Serializable. ObjectOutputStream.writeObject grava os campos não transientes, junto com o nome da classe e um serialVersionUID. ObjectInputStream.readObject reconstitui o objeto. Se um campo nasce ou muda de tipo e o serialVersionUID não acompanha, a leitura do arquivo antigo lança InvalidClassException. A plataforma também tem histórico de falha de segurança nesse formato, porque a leitura pode instanciar classes a partir dos bytes recebidos. O Sistema de Pedidos não usa Serializable como formato de pedido, de fila ou de sessão. O aviso da IDE que sugere implements Serializable, em entidade ou DTO, fica sem a interface. O formato da borda é JSON.

O formato de borda do sistema é JSON, via Jackson, que já vem no Spring Boot. Objeto vira texto e texto vira objeto.

Record e classe com getters são lidos sem anotação quando os nomes batem. Quando o JSON externo usa outro nome, @JsonProperty("zip_code") no campo ou no componente liga os nomes. @JsonIgnoreProperties(ignoreUnknown = true) no DTO de uma API de fora ignora campo que o fornecedor acrescentar. Sem isso, um campo novo quebra a integração no dia em que o fornecedor evolui.

@JsonIgnore deixa um campo fora do JSON. Use em dado que não pode vazar na resposta. Não use toString para vazar o que o JSON escondeu.

Datas: Instant em ISO-8601. Enum: nome da constante, salvo customização. Lista: array JSON. Mapa: objeto JSON. BigDecimal: número JSON. Ausência de Optional no JSON: o campo do DTO é o tipo simples e a validação diz se é obrigatório; Optional como campo de DTO confunde o Jackson e o contrato.

ObjectMapper é a classe que faz a conversão. No Boot, existe um ObjectMapper pronto como bean. Injetar esse bean respeita a configuração global. new ObjectMapper() no meio do service cria outro, sem a mesma configuração de data e de módulo.

Na fila, o evento também é JSON. O mesmo cuidado de contrato vale: campo desconhecido, nome estável e versão do evento. O consumidor antigo precisa continuar lendo a mensagem nova, ou a aplicação precisa de um período em que os dois formatos coexistem.


Tema
Log: SLF4J, níveis e o que não se escreve

Descrição do conteúdo
SLF4J é a fachada. Logback é a implementação que o Spring Boot traz. A classe pede um logger:

private static final Logger log = LoggerFactory.getLogger(CriarPedidoService.class);

Níveis: trace e debug para diagnóstico que fica desligado em produção; info para o marco de negócio ("pedido 10 confirmado"); warn para situação estranha e recuperável; error para falha que precisa de atenção, em geral com a exceção no último argumento.

log.info("pedido {} confirmado", pedido.id());
log.error("falha ao publicar evento {}", pedido.id(), excecao);

A chave {} evita concatenar texto quando o nível está desligado e deixa o valor como dado do log. Concatenar com + sempre monta a frase.

Não registre senha, token, Authorization, número de cartão nem documento completo. O webhook e o JWT passam por essas classes. Registre o id do pedido e o id da requisição.

System.out não tem nível e não entra no formato do Boot. A partir do nível 7, print some do projeto. O console do Boot já é o log.


Tema
Classloader, módulo e por que a dependência aparece

Descrição do conteúdo
Classloader é o componente que acha o bytecode de uma classe na hora de usar. A aplicação, as dependências do Maven e o próprio JDK podem estar em classloaders diferentes. O sintoma prático de não entender isso é o ClassNotFoundException ou o NoClassDefFoundError: o código compilou contra uma classe que não está no jar que rodou. Outro sintoma é ter duas versões da mesma biblioteca e a JVM carregar a errada.

Módulo do JPMS é o arquivo module-info.java na raiz dos fontes. Ele declara requires das dependências e exports dos pacotes que outros módulos podem usar. Pacote sem export fica invisível para a reflexão de outro módulo, e a JVM lança InaccessibleObjectException. O Spring Boot tradicional roda no classpath, sem module-info na aplicação. Quando uma biblioteca nova traz module-info e a reflexão do framework bate num pacote fechado, a mensagem cita o módulo. A correção é atualizar a biblioteca para a versão que abre o pacote, ou acrescentar o --add-opens que a documentação daquela versão pede. O Sistema de Pedidos não ganha module-info. As dependências entram pelo pom e o Boot monta o classpath.

Na prática do dia: a dependência entra no pom.xml, o Maven coloca o jar no classpath, o Boot sobe com esse classpath dentro do jar executável. O que não está no pom não existe em produção, mesmo que a IDE tenha mostrado a classe de outro projeto.


================================================================================
NÍVEL 6 — INTERMEDIÁRIO: CONCORRÊNCIA EM JAVA
================================================================================

Tema
Por que concorrência aparece antes do detalhe do Spring

Descrição do conteúdo
O programa do nível 1 fazia uma coisa de cada vez. A API do nível 7 recebe duas confirmações do mesmo pedido no mesmo segundo, de duas conexões. O Java executa isso com várias linhas de execução. Sem uma noção de thread, um status gravado errado parece falha do banco.

Este nível explica a linguagem. O nível 9 aplica no Spring: pool de conexões, @Version, virtual threads e várias instâncias. A regra que já vale: campo mutável em objeto único da aplicação é compartilhamento. O pedido da requisição A não pode ficar em campo do @Service, porque a requisição B usa o mesmo service.


Tema
Thread, Runnable e Callable

Descrição do conteúdo
Thread é uma linha de execução. Runnable é uma tarefa sem retorno: void run(). Callable é uma tarefa com retorno e com exceção checada possível: call.

A forma antiga cria Thread na mão e chama start. start volta na hora e o run acontece em paralelo. Chamar run direto não cria concorrência; executa na thread atual.

O curso não espalha new Thread pelo sistema. Thread sem limite, uma por evento, derruba o processo quando o pico chega. A forma de usar é entregar a tarefa a um ExecutorService, que mantém um número controlado de threads.

Thread.sleep pausa a linha atual. Em teste, às vezes aparece. Em requisição web, dormir a thread para "esperar a outra API" segura um trabalhador. O cliente HTTP com timeout é a ferramenta de espera de rede, não o sleep. sleep não libera um lock de forma mágica e não espera condição de negócio; para esperar condição existe estrutura própria, e na aplicação de pedido essa condição é status no banco ou mensagem na fila.


Tema
synchronized e volatile

Descrição do conteúdo
synchronized impede duas threads de executarem ao mesmo tempo o bloco ou o método protegido pelo mesmo monitor. O monitor de um método de instância é o objeto. O monitor de um método static é a classe.

synchronized (this) {
    saldo = saldo.add(valor);
}

Sem sincronização, duas threads lendo o mesmo saldo e gravando de volta perdem uma das somas. Com sincronização no objeto certo, as duas somas acontecem uma depois da outra.

O limite, que o Spring torna visível: o monitor vive dentro de um processo. Duas instâncias da API, dois processos, dois monitores diferentes. synchronized não protege o pedido quando existem dois containers. A proteção que sobrevive a isso é no banco: transação e versão.

volatile garante que a leitura de um campo enxergue a escrita mais recente feita por outra thread. Não torna uma sequência de várias operações atômica. Serve para uma flag de "pode parar". Não serve para saldo = saldo.add(valor), que é ler e escrever em passos separados.

No projeto, synchronized quase não aparece no domínio. Se aparecer um cache local minúsculo com invariante de várias etapas, a preferência ainda é estrutura pronta do java.util.concurrent, que já documenta a concorrência que oferece.


Tema
wait, notify e por que a aplicação quase não os usa

Descrição do conteúdo
wait solta o monitor e dorme até um notify no mesmo objeto. notify acorda uma thread. notifyAll acorda todas. Os três só podem ser chamados por quem segura o monitor, isto é, dentro de synchronized no mesmo objeto. Sem um while reavaliando a condição, a thread pode acordar sem a condição estar verdadeira.

Isso é primitiva de baixo nível. Errar produz espera infinita ou acordar cedo. Fila de trabalho, CompletableFuture e as filas do java.util.concurrent cobrem o caso da aplicação. O Sistema de Pedidos espera evento durável no RabbitMQ, não num wait de objeto Java. A aula existe para ler código legado e para não reinventar essa espera num service.


Tema
Lock, AtomicInteger e coleções concorrentes

Descrição do conteúdo
java.util.concurrent.locks.ReentrantLock é uma trava explícita, com lock e unlock no finally, para não deixar a trava presa se o método lançar. Oferece tryLock, que desiste se não conseguir na hora. Para a maior parte do curso, synchronized ou, melhor, a estrutura pronta, basta. Use ReentrantLock quando tryLock ou várias condições no mesmo recurso forem a história real.

AtomicInteger, AtomicLong e AtomicReference fazem uma alteração simples de forma atômica: incrementAndGet, compareAndSet. Servem para contador e para referência que troca inteira. Não servem para regra de várias variáveis que precisam mudar juntas; isso continua sendo transação ou uma trava em volta do bloco inteiro.

ConcurrentHashMap é o mapa que várias threads podem ler e alterar sem corromper a estrutura. get e put são seguros. Ações compostas usam compute, merge e putIfAbsent, que são atômicos por chave. Não percorra o mapa e remova por fora achando que a composição inteira é uma transação de negócio. Duas chaves diferentes não viram uma operação única.

CopyOnWriteArrayList cria uma cópia interna a cada alteração. A leitura não bloqueia. A escrita é cara. Use quando a lista é lida o tempo todo e publicada raramente, por exemplo uma tabela pequena de configuração já carregada. Não use como lista de itens de cada pedido.

BlockingQueue, como ArrayBlockingQueue e LinkedBlockingQueue, é fila em memória com espera: take bloqueia se estiver vazia, put bloqueia se estiver cheia. Cabe entre um produtor e um consumidor dentro do mesmo processo. Não substitui RabbitMQ, porque o conteúdo morre com o processo e não atravessa para outra instância.

A escolha no Spring: contador de métrica de uma instância pode ser atômico ou, melhor, Micrometer. Mapa compartilhado de verdade entre instâncias é Redis ou banco. Fila de evento é broker. As classes deste tópico resolvem o problema dentro de um processo e deixam de resolver quando o Compose sobe duas APIs.


Tema
ExecutorService, pool e Future

Descrição do conteúdo
ExecutorService recebe tarefas e as executa com um conjunto limitado de threads.

ExecutorService executor = Executors.newFixedThreadPool(4);
Future<String> futuro = executor.submit(() -> publicar(evento));
String recibo = futuro.get(2, TimeUnit.SECONDS);

submit de Callable devolve Future. get espera o resultado e lança exceção checada se a tarefa falhou ou estourou o tempo. get sem timeout pode esperar para sempre se a tarefa travar.

O pool precisa ser desligado: shutdown para de aceitar tarefa e espera as atuais; shutdownNow pede interrupção. Em aplicação pequena solta, o pool é um recurso do main. Em Spring, criar Executors.newFixedThreadPool dentro de método de request vaza thread, porque cada chamada cria um pool novo e ninguém desliga. A forma Spring é um bean de TaskExecutor, ou @Async configurado, ou virtual threads com limite de outra camada. O bean nasce com a aplicação e morre no desligamento.

O tamanho do pool depende do trabalho. Conta de CPU: perto do número de núcleos. Espera de rede e de disco: maior, porque a thread passa parte do tempo parada, e mesmo assim finito, porque cada uma consome memória. O número mágico sem medição é chute. O curso configura um valor pequeno e explícito e mede antes de aumentar.


Tema
CompletableFuture

Descrição do conteúdo
CompletableFuture representa um resultado que ainda vai chegar. Dá para encadear transformação sem bloquear a thread que montou a cadeia.

CompletableFuture<Endereco> endereco = CompletableFuture.supplyAsync(() -> cep.buscar(codigo));
CompletableFuture<BigDecimal> frete = endereco.thenApply(this::cotarFrete);

supplyAsync roda a busca num executor. thenApply transforma o resultado. thenCompose encadeia outra operação assíncrona sem aninhar future dentro de future, no mesmo espírito do flatMap. exceptionally e handle tratam falha. orTimeout no Java recente limita a espera.

get e join bloqueiam a thread atual até o fim. Se o controller chamar join em tudo, a cadeia assíncrona não liberou ninguém; só mudou o estilo. Use a cadeia quando há trabalho independente de verdade, como consultar CEP e estoque ao mesmo tempo e juntar com thenCombine.

No Spring, RestClient é síncrono e simples, e é o cliente padrão do curso. WebClient devolve tipo reativo. CompletableFuture aparece quando um método da aplicação coordena algumas tarefas paralelas e o restante do fluxo continua imperativo. Não envolva cada linha do caso de uso em supplyAsync. A complexidade de erro, de timeout e de contexto de segurança cresce rápido. A aula mostra uma composição concreta no pedido: CEP e cotação de frete juntos, com timeout, e o resto do fluxo segue síncrono.


Tema
Virtual threads

Descrição do conteúdo
Thread clássica de servidor ocupa um trabalhador do sistema operacional o tempo todo, inclusive enquanto espera o banco ou a API de CEP. Virtual thread, estável desde o Java 21, é uma tarefa leve gerenciada pela JVM. Quando o código bloqueia numa espera, a JVM desmonta a virtual thread do trabalhador de sistema e usa esse trabalhador para outra virtual thread. O código continua no estilo bloqueante: chama o RestClient, espera, segue. Não é obrigatório virar programação reativa para atender muitas esperas.

Como se cria uma, quando a tarefa é sua:

Thread.ofVirtual().start(() -> processar(evento));
Executors.newVirtualThreadPerTaskExecutor();

Como o Spring Boot usa: a propriedade que liga virtual threads no servidor web embutido (spring.threads.virtual.enabled=true nas versões de Boot que expõem essa chave). Cada requisição pode rodar numa virtual thread. O código do controller não muda.

O que não muda com virtual thread: o PostgreSQL continua com um número máximo de conexões. Se mil requisições bloquearem em mil conexões, o banco recusa. O pool Hikari continua limitado. O RabbitMQ continua com um número de consumidores configurado. Código synchronized muito longo ocupa a mecânica de pinagem e desperdiça o modelo; trava curta é aceitável, trava em volta de chamada de rede não é o desenho.

O curso liga virtual threads na API do nível 9, depois de o fluxo bloqueante de CEP e pagamento estar correto, e revisa o tamanho do pool de conexão na mesma aula. Virtual thread não é motivo para abrir transação e segurar conexão durante a chamada externa.


Tema
ThreadLocal e o contexto da requisição

Descrição do conteúdo
ThreadLocal guarda um valor visível só para a thread atual. O Spring usa isso para a transação corrente, para o contexto de segurança e para o id de log da requisição. Por isso, no meio do código, SecurityContextHolder e o MDC parecem "globais" e mesmo assim não misturam o usuário da requisição A com o da B: cada thread tem o seu.

O risco é guardar coisa demais e não limpar. Em pool de thread, a thread sobrevive à requisição e o próximo request pode ver o valor velho. O Boot limpa os contextos dele nos filtros. Valor que você colocar num ThreadLocal seu precisa ser removido no finally.

Virtual thread muda a quantidade de threads e o tempo de vida delas. Dado grande preso em ThreadLocal multiplica memória. Guarde id, não o pedido inteiro. E não use ThreadLocal como atalho para não passar o id do pedido por parâmetro: parâmetro é explícito e testável. ThreadLocal fica para infraestrutura que o framework já integra, como o id de correlação no log.


================================================================================
NÍVEL 7 — SPRING BOOT INICIANTE
================================================================================

Tema
O que o Spring Boot acrescenta ao Java que você já escreveu

Descrição do conteúdo
Até aqui o Sistema de Pedidos sabe calcular, guardar em coleção e lançar exceção. O Spring Boot coloca esse código dentro de um processo que escuta HTTP, monta os objetos, lê configuração e conecta em banco sem um main gigante.

Três ideias carregam o framework.

Inversão de controle: você não dá new em cada service no main. Você descreve as classes, e o container cria e liga. Isso é composição com um montador automático.
Injeção de dependência: a classe declara no construtor o que precisa. O container passa as implementações.
Auto-configuração: se o JPA e o driver do PostgreSQL estão no classpath e a URL existe na configuração, o Boot sobe um DataSource, um pool e um EntityManager. Você cobre só o que é específico do pedido.

O main fica assim:

@SpringBootApplication
public class PedidoApplication {
    public static void main(String[] args) {
        SpringApplication.run(PedidoApplication.class, args);
    }
}

@SpringBootApplication liga três coisas: marca a classe como configuração, liga o escaneamento de componentes no pacote dela e abaixo, e liga a auto-configuração. Por isso essa classe fica no pacote raiz com.curso.pedidos. Se ela ficar num pacote irmão, o escaneamento não acha os @Service.


Tema
Starter, pom e o jar que sobe

Descrição do conteúdo
O projeto Maven do nível profissional tem um pai: spring-boot-starter-parent. Esse pai fixa versões compatíveis. Você declara o starter sem inventar a versão de cada biblioteca transitiva.

Dependências do Sistema de Pedidos, acrescentadas quando o nível chega nelas, não todas no primeiro dia:

spring-boot-starter é o núcleo.
spring-boot-starter-web traz o servidor Tomcat embutido, o Spring MVC e o Jackson.
spring-boot-starter-validation traz Bean Validation.
spring-boot-starter-data-jpa traz Hibernate e Spring Data.
spring-boot-starter-jdbc traz o JDBC e o pool, já puxado em geral pelo JPA, e explicitado se houver JdbcClient.
o driver org.postgresql.
spring-boot-starter-security quando a autenticação entrar.
spring-boot-starter-amqp quando o RabbitMQ entrar.
spring-boot-starter-actuator quando saúde e métrica entrarem.
spring-boot-starter-test no escopo de teste, com JUnit, Mockito e MockMvc.

Como se usa o Maven: mvn test roda os testes, mvn package gera o jar em target, java -jar target/pedidos.jar sobe a API. O jar do Boot é executável e contém as dependências. Não é um jar fino que espera um servidor externo instalado na máquina.

O plugin spring-boot-maven-plugin empacota esse jar. Sem ele, o jar comum não sobe com java -jar do jeito do Boot.


Tema
Container, estereótipos e injeção pelo construtor

Descrição do conteúdo
Na subida, o Boot procura classes anotadas e registra uma definição de cada. Na primeira necessidade, cria o objeto, guarda e reutiliza. O padrão de escopo é um objeto só para a aplicação, o singleton do container. Não é o singleton de static global: é o container que segura a referência.

@Component é a etiqueta genérica. @Service é o caso de uso e o serviço de aplicação. @Repository é o adaptador de persistência; além do nome, ele traduz exceção de persistência para a hierarquia do Spring. @Controller é o adaptador web que devolve visão; @RestController é o controller cuja resposta já é o corpo, em geral JSON. @Configuration é classe que declara beans em métodos.

A forma de depender de outro bean é o parâmetro do construtor:

@Service
public class CriarPedidoService {
    private final PedidoRepositorio repositorio;
    private final ConsultaCep cep;

    public CriarPedidoService(PedidoRepositorio repositorio, ConsultaCep cep) {
        this.repositorio = repositorio;
        this.cep = cep;
    }
}

Se só existe um construtor, o Boot usa esse construtor e não exige @Autowired. Campo final garante que a dependência existe depois da construção. Teste instancia CriarPedidoService com implementações falsas, sem subir o servidor.

Injeção em campo com @Autowired funciona e esconde a dependência: dá para criar o objeto sem esse campo e tomar NullPointerException depois. O curso não injeta em campo. Injeção por setter só quando a dependência for opcional de verdade.

Escopo de request (@RequestScope) cria um objeto por requisição HTTP. Quase não é necessário se o estado da operação fica em variável local e no agregado Pedido. Escopo de sessão prende dado no servidor entre requisições e atrapalha quando existem duas instâncias. O curso não guarda pedido em sessão.


Tema
@Bean, @Primary, @Qualifier e @Profile

Descrição do conteúdo
Método @Bean dentro de @Configuration devolve um objeto que o container passa a gerenciar. Use quando a classe é de uma biblioteca e você não pode anotar ela com @Component, como ObjectMapper customizado, RestClient e Clock.

@Configuration
public class RelogioConfig {
    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }
}

Quando duas implementações da mesma interface existem, o Boot não sabe qual injetar e a aplicação não sobe. Três saídas conscientes:

@Primary na implementação padrão.
@Qualifier("cepFake") no bean e no parâmetro, quando as duas convivem.
@Profile("dev") numa e @Profile("prod") na outra, quando só uma existe por ambiente. O pagamento fake fica no profile local. O cliente real fica no profile de produção. Os dois não sobem juntos.

@Conditional e as variantes (@ConditionalOnProperty, @ConditionalOnClass) ligam bean por condição. A auto-configuração do Boot é feita disso. No projeto, uma propriedade pedidos.pagamento.fake=true liga o fake, e a condição evita if espalhado no código.

Como se confere o que subiu: o log de start lista o Tomcat e os beans em caso de falha dizem qual dependência ficou ambígua. A mensagem de "required a single bean, but 2 were found" se resolve com uma das três saídas acima, não com @Autowired(required = false).


Tema
Ciclo de vida do bean

Descrição do conteúdo
Ordem prática: o container instancia, injeta o construtor, chama métodos @PostConstruct e só então considera o bean pronto para receber chamada. No desligamento, chama métodos anotados com @PreDestroy e os DisposableBean.

@PostConstruct serve para checagem que precisa do objeto inteiro montado: validar se uma propriedade obrigatória faz sentido junto com outra, aquecer um cache pequeno. Não serve para regra de pedido e não serve para chamada lenta de outra API, porque segura a subida.

@PreDestroy fecha cliente que o próprio bean abriu. Cliente criado pelo Boot, como o DataSource, o Boot fecha. Não feche no @PreDestroy o que você não abriu.

ApplicationRunner e CommandLineRunner rodam uma vez depois da subida. Úteis para um diagnóstico de desenvolvimento. Perigosos se gravarem dado de verdade toda vez que o processo sobe. O curso não usa runner para semear pedido em produção. Semear dado de desenvolvimento pode ser um runner com @Profile("dev") ou, melhor, uma migration e um script de teste.


Tema
application.yaml, @Value e @ConfigurationProperties

Descrição do conteúdo
Configuração fica fora do código. application.yaml na pasta src/main/resources é o arquivo principal. A mesma chave pode vir de variável de ambiente, e a variável ganha da chave do arquivo quando o Boot monta o ambiente. É assim que o container troca a URL do banco sem gerar outra imagem.

Exemplo:

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/pedidos
    username: pedidos
    password: ${DB_SENHA}
pedidos:
  cep:
    base-url: https://exemplo.local
    timeout-ms: 800

@ConfigurationProperties é a forma de ler um grupo. Uma classe com o prefixo, campos e getter, habilitada com @EnableConfigurationProperties ou com @ConfigurationPropertiesScan:

@ConfigurationProperties(prefix = "pedidos.cep")
public record CepProperties(String baseUrl, Duration timeout) { }

O Boot converte timeout-ms e também aceita duração escrita de forma legível quando o campo é Duration e o valor está no formato que o Binder entende, como 800ms. Validar essa classe com @Validated e @NotBlank faz a aplicação falhar na subida se a URL estiver vazia, em vez de falhar na primeira requisição.

@Value("${pedidos.cep.base-url}") injeta uma chave solta. Cabe para uma constante isolada. Grupo de configuração usa a classe de properties, porque o nome fica tipado e o teste consegue construir CepProperties sem arquivo.

Profile: application-dev.yaml e application-prod.yaml completam ou cobrem chaves. A aplicação sobe com --spring.profiles.active=dev ou com a variável SPRING_PROFILES_ACTIVE. Segredo não entra no yaml commitado. O yaml commitado aponta para ${DB_SENHA}, e o valor real chega pelo ambiente.

Lista e mapa também bindam. Uma lista de origens CORS no yaml vira List<String> na classe de properties. Esse é o uso de coleção na configuração: o Java que você já sabe, preenchido pelo Boot.


Tema
HTTP que a API precisa entender

Descrição do conteúdo
Cada chamada tem método, caminho, cabeçalhos e, às vezes, corpo. A resposta tem status, cabeçalhos e corpo.

GET lê e não altera. Pode ser repetido. GET /pedidos/10 busca um pedido.
POST cria ou dispara ação. POST /pedidos cria. POST /pedidos/10/cancelamento cancela. Repetir POST de criação sem chave de idempotência cria outro pedido; por isso criação sensível carrega uma chave.
PUT substitui a representação inteira. O curso quase não usa PUT, porque o pedido não é um formulário que se substitui; ele recebe ações.
PATCH altera trechos. O curso evita PATCH genérico que aceita qualquer campo e fura a regra de status.
DELETE remove. Cancelar pedido não é DELETE físico; é ação de negócio que guarda o histórico.

Status que o projeto usa de forma estável:

200 a leitura ou a ação devolveu um corpo.
201 a criação funcionou. O cabeçalho Location aponta para o novo recurso.
204 a ação funcionou e não há corpo.
400 o JSON ou o campo está malformado ou fere validação de formato.
401 não há credencial.
403 a credencial existe e não pode esta ação.
404 o id não existe.
409 a transição de estado conflita com o estado atual.
422 dá para usar quando o time quer separar "formato inválido" de "regra de negócio recusada". O curso escolhe um e mantém: 400 para Bean Validation, 409 para conflito de estado, 422 se uma regra de domínio não for conflito. A escolha fica registrada no @ControllerAdvice e não varia por endpoint.
500 falha inesperada. O corpo não leva stack trace.

O controller traduz. O domínio não devolve 404; ele sinaliza ausência ou exceção, e o adaptador web escolhe o status.


Tema
O primeiro controller

Descrição do conteúdo
@RestController marca a classe. @RequestMapping("/pedidos") prefixa o caminho. Cada método é uma operação.

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    private final CriarPedidoService criarPedido;

    public PedidoController(CriarPedidoService criarPedido) {
        this.criarPedido = criarPedido;
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> criar(@RequestBody CriarPedidoRequest request) {
        Pedido pedido = criarPedido.executar(request.clienteId());
        PedidoResponse corpo = PedidoResponse.de(pedido);
        URI location = URI.create("/pedidos/" + pedido.id());
        return ResponseEntity.created(location).body(corpo);
    }

    @GetMapping("/{id}")
    public PedidoResponse buscar(@PathVariable Long id) {
        return PedidoResponse.de(criarPedido.buscar(id));
    }
}

@RequestBody lê o JSON e o Jackson monta o record. @PathVariable pega o trecho da URL. @RequestParam pega a query: /pedidos?status=PAGO&page=0. @RequestHeader pega cabeçalho, como a chave de idempotência.

ResponseEntity controla status e cabeçalho. Quando o status é sempre 200 e o corpo basta, devolver o record direto também funciona, porque o @RestController serializa o retorno. Criação usa ResponseEntity para conseguir o 201 e o Location.

O método do controller é fino: traduz HTTP para chamada de caso de uso e traduz o resultado para HTTP. Faixa de desconto, soma de item e transição de status não entram no controller. Se entrarem, a mesma regra falta na fila e no teste unitário.


Tema
DTO de entrada, DTO de saída e entidade

Descrição do conteúdo
A entidade é o estado persistido e a regra. O DTO de entrada é o que o cliente pode enviar. O DTO de saída é o que o cliente pode ver. Os três podem parecer iguais no começo e divergem rápido: a entidade tem versão, status interno e relação preguiçosa; a saída tem total calculado e não tem custo interno; a entrada de criação não tem id nem status, porque quem define isso é o servidor.

public record CriarPedidoRequest(Long clienteId) { }
public record ItemResponse(String codigoProduto, int quantidade, BigDecimal subtotal) { }
public record PedidoResponse(Long id, StatusPedido status, BigDecimal total, List<ItemResponse> itens) {
    public static PedidoResponse de(Pedido pedido) { /* copia campo a campo */ }
}

A cópia é explícita num método de fábrica, campo a campo. Cada atribuição mostra o que atravessa a borda e o que fica de fora: versão, custo interno e relação preguiçosa não entram na resposta; id e status não entram na criação. Uma biblioteca de mapeamento entra depois, quando essa cópia manual cresce além do que a leitura direta ainda sustenta. No tamanho do Sistema de Pedidos, o método de fábrica permanece.

Lista dentro do response é a lista imutável vinda do domínio. O Jackson percorre com iterator e escreve o array. O cliente recebe a ordem dos itens. Nada de devolver a entidade JPA: o Jackson pode disparar carga preguiçosa fora da transação e estourar, ou serializar um grafo enorme que o contrato não prometeu.


Tema
Subida, porta e o primeiro chamado real

Descrição do conteúdo
mvn spring-boot:run ou o main da IDE sobe o Tomcat na porta 8080. A porta muda com server.port ou com a variável SERVER_PORT, o que o Compose usa para não colidir.

O primeiro chamado, ainda sem segurança:

curl -i -X POST http://localhost:8080/pedidos -H "Content-Type: application/json" -d "{\"clienteId\": 1}"

A resposta esperada tem 201, Location e um JSON com id e status CRIADO. O segundo chamado, GET no Location, devolve o mesmo pedido.

Erros deste momento: porta ocupada, pacote do controller fora do escaneamento, e JSON com nome de campo diferente do componente do record. O log da subida mostra a porta. O 404 de caminho mostra mapping diferente do que o curl chamou. O 400 de JSON mostra o corpo incompatível com o record.

A partir daqui todo recurso Java dos níveis anteriores tem um lugar: BigDecimal no total, enum no status, List no itens, exceção subindo até virar status, record na borda, construtor com validação no domínio, e o service sem static.


================================================================================
NÍVEL 8 — SPRING BOOT INTERMEDIÁRIO
================================================================================

Tema
Bean Validation: como a entrada é recusada

Descrição do conteúdo
Jakarta Validation declara regra no DTO. O starter validation traz a implementação. A regra só roda se o parâmetro estiver anotado com @Valid ou @Validated.

public record AdicionarItemRequest(
        @NotBlank String codigoProduto,
        @Positive int quantidade) { }

@NotNull recusa null. @NotBlank recusa null, vazio e espaço, e só se aplica a texto. @Positive recusa 0 e negativo em número. @PositiveOrZero aceita zero quando a regra aceita. @Size(min = 8, max = 8) limita tamanho. @Pattern(regexp = "\\d{8}") exige formato. @Email faz uma checagem prática de formato, não garante que a caixa existe. @DecimalMin("0.01") serve em BigDecimal. @Past e @Future servem em data.

Validação de objeto aninhado exige @Valid no campo também, senão a lista de itens entra sem validar cada item. Em lista, @NotEmpty recusa lista vazia quando o caso de uso exige ao menos um.

O controller:

public ResponseEntity<PedidoResponse> adicionar(
        @PathVariable Long id,
        @Valid @RequestBody AdicionarItemRequest request) { }

Se a validação falha, o Boot lança MethodArgumentNotValidException antes de entrar no método. O @ControllerAdvice transforma isso em 400 com a lista de campos. Sem o advice, o corpo padrão do Boot é menos estável como contrato.

Validação de formato fica no DTO. Regra que olha o estado do pedido (não dá para adicionar item cancelado) fica no domínio, porque precisa do pedido carregado, não só do JSON. As duas rodam. Uma não substitui a outra.


Tema
@ControllerAdvice e o formato único de erro

Descrição do conteúdo
@ControllerAdvice é um bean cujo método anotado com @ExceptionHandler trata exceção vinda dos controllers. Um advice para o projeto inteiro centraliza o contrato de erro.

@ExceptionHandler(ProdutoNaoEncontradoException.class)
public ProblemDetail tratar(ProdutoNaoEncontradoException excecao) {
    ProblemDetail problema = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, excecao.getMessage());
    problema.setTitle("Produto nao encontrado");
    return problema;
}

ProblemDetail é o tipo pronto de problema HTTP (RFC 7807) que o Spring já serializa: type, title, status, detail. Dá para estender com propriedade do id do pedido. Usar um formato só evita cada endpoint inventar seu JSON de erro.

Handlers do projeto:

MethodArgumentNotValidException -> 400, com a lista de campos e mensagens.
HttpMessageNotReadableException -> 400, JSON quebrado ou tipo errado.
ProdutoNaoEncontradoException -> 404.
TransicaoDeStatusInvalidaException -> 409.
PedidoInvalidoException -> 422.
Exception genérica -> 500, com mensagem fixa para o cliente e log.error com a pilha no servidor.

A ordem dos handlers segue o tipo declarado; o mais específico é escolhido. O handler genérico não pode logar e devolver a mensagem crua de NullPointerException, porque essa mensagem vaza detalhe interno.

Três chamadas passam pelo mesmo advice. Quantidade 0 dispara a validação do DTO e volta 400, com a lista de campos. Um id inexistente dispara ProdutoNaoEncontradoException e volta 404. Cancelar um pedido já enviado dispara TransicaoDeStatusInvalidaException e volta 409. O controller não monta esses corpos. O advice monta.


Tema
Filtro, interceptor e CORS

Descrição do conteúdo
Filtro é do servlet, em volta de tudo, antes do controller. Interceptor é do Spring MVC, com ganchos antes e depois do controller, já com o handler escolhido. Os dois podem virar "lugar onde alguém colocou regra". A divisão do curso:

Filtro para coisa de borda técnica: id da requisição no MDC, autenticação que transforma o token em usuário, limite grosseiro de tamanho. A classe estende OncePerRequestFilter quando deve rodar uma vez por request.
Interceptor para coisa ligada à rota, se um dia precisar. No Sistema de Pedidos, quase tudo que seria interceptor fica melhor em método de caso de uso ou em segurança declarativa.
Controller para traduzir HTTP.
Advice para traduzir exceção.

CORS é a regra do browser sobre chamar um domínio diferente do da página. Não é autenticação. Um curl passa sem CORS. O browser bloqueia a página se a API não devolver os cabeçalhos de origem permitida. A configuração do Boot lista origens, métodos e cabeçalhos permitidos a partir da classe de properties. Origem * junto com credencial não é combinação válida. Em produção, a origem é o domínio do frontend. Em desenvolvimento, é o localhost da porta do frontend.

Como se configura no estilo do Boot 3: um bean WebMvcConfigurer ou CorsConfigurationSource usado pela segurança, quando a segurança entrar. Se os dois existirem sem ligação, a segurança pode ignorar o CORS do MVC. Quando o nível 9 ligar o Spring Security, a configuração de CORS fica na cadeia de segurança, num lugar só.


Tema
JDBC, DataSource e JdbcClient

Descrição do conteúdo
JDBC é a API padrão de SQL. O driver do PostgreSQL implementa essa API. DataSource é a fábrica de conexões. O Boot cria um DataSource HikariCP com a URL, o usuário e a senha da configuração.

JdbcClient, presente no Spring Framework 6.1 e no Boot 3.2, é a forma fluente de executar SQL sem escrever ResultSet na mão e sem chegar ainda no JPA.

List<Produto> produtos = jdbc.sql("select codigo, nome, preco from produto where ativo = :ativo")
        .param("ativo", true)
        .query(Produto.class)
        .list();

O parâmetro nomeado evita concatenar texto do usuário no SQL. Concatenar é injeção: o cliente manda um código que fecha a aspas e altera a sentença. param trata o valor como dado.

A atualização devolve quantas linhas mudaram. Uma transição de status pode ser um update condicional:

int linhas = jdbc.sql("""
        update pedido
           set status = :novo
         where id = :id
           and status = :atual
        """)
        .param("novo", "PAGO")
        .param("id", id)
        .param("atual", "AGUARDANDO_PAGAMENTO")
        .update();

Se linhas voltar 0, alguém já mudou o status. Isso é concorrência resolvida no banco, e conversa com o nível 6.

O Sistema de Pedidos usa JdbcClient na consulta em que o SQL precisa estar escrito na íntegra. O fluxo principal do pedido usa JPA, na próxima aula, porque relação, identidade e ciclo de estado ficam mais diretos. Os dois convivem. SQL explícito é a ferramenta quando o SQL gerado não é o SQL desejado.


Tema
JPA: entidade, identidade e estado

Descrição do conteúdo
Entidade é uma classe com @Entity cujo @Id identifica a linha. O EntityManager observa as entidades carregadas dentro de uma transação. Se você altera um campo e a transação commita, o Hibernate escreve o update mesmo sem uma chamada explícita de save, porque a entidade está gerenciada. Fora da transação, a entidade está destacada: alterar o campo não grava nada até alguém juntar de novo ou chamar save.

Mapeamento mínimo:

@Entity
@Table(name = "produto")
public class ProdutoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String codigo;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal preco;
}

IDENTITY usa a sequence/identity do PostgreSQL. O id existe depois do insert. Não invente o id no cliente.

Campo de dinheiro permanece BigDecimal, com precision e scale coerentes com o numeric do banco. Campo de status pode ser @Enumerated(EnumType.STRING). STRING grava o nome. ORDINAL grava a posição e quebra quando a ordem das constantes muda; o curso não usa ORDINAL.

Instant mapeado para timestamptz. Fuso na coluna errada faz "o dia do pedido" mudar conforme o servidor.

A entidade pode conter método de negócio quando ela é o agregado. O Sistema de Pedidos segue esse caminho: uma classe Pedido, com a regra e o mapeamento, no tamanho deste sistema. Se a entidade passar a misturar anotação de persistência com a regra a ponto de o método de negócio só compilar com o JPA no classpath de execução, aí se separam Pedido e PedidoEntity. Com uma classe só, o teste de domínio sobe sem banco quando a regra não toca anotação de persistência em tempo de execução. O teste de integração cobre o mapeamento: coluna, tipo, nulo e relação.


Tema
Relações, fetch e o problema N+1

Descrição do conteúdo
@ManyToOne no item aponta para o produto. @OneToMany no pedido aponta para os itens. O lado dono é o que tem a chave estrangeira, em geral o @ManyToOne no item, com @JoinColumn(name = "pedido_id"). O @OneToMany usa mappedBy = "pedido" e não cria outra coluna.

CascadeType.PERSIST e ALL fazem o save do pedido alcançar os itens novos. Cascade é poder de verdade: ALL com REMOVE apaga filhos quando o pedido é removido. O curso remove por ação explícita quando a regra é mais sutil que "apague tudo". Órfão: orphanRemoval = true tira do banco o item removido da lista. Use só se a lista da entidade for a dona completa da vida do item.

FetchType.LAZY não traz a associação no primeiro select. Ela carrega quando o código toca nela, se ainda houver sessão aberta. FetchType.EAGER traz sempre, e uma teia de EAGER puxa mais do que a tela precisa. O padrão do curso é LAZY nas coleções e cuidado no ManyToOne.

N+1 é o sintoma: um select para a página de pedidos e mais um select de itens por pedido. Vinte pedidos, vinte e um selects. A correção é uma consulta com join fetch quando a tela precisa dos itens, ou uma consulta específica que já devolve o DTO. A correção errada é EAGER global "para sumir o erro". O erro LazyInitializationException, que acontece quando se toca a coleção depois da transação, se resolve abrindo a transação no caso de uso que realmente precisa dos dados, ou copiando para DTO dentro dessa transação. Não se resolve lendo coleção no controller por acidente.

Lista indexada com @OrderColumn guarda a posição. Sem coluna de ordem, a lista pode voltar do banco numa ordem que não é a da inclusão. O pedido do curso tem coluna de ordem porque a ordem dos itens é parte do que a API promete desde o nível 3.


Tema
Spring Data JPA: repositório, método derivado e @Query

Descrição do conteúdo
A interface estende JpaRepository<Pedido, Long> e ganha save, findById, findAll, deleteById e paginação. O Spring gera a implementação. Você não escreve a classe.

PedidoRepositorio extends JpaRepository<Pedido, Long> {
    List<Pedido> findByStatus(StatusPedido status);
    Optional<Pedido> findByIdAndClienteId(Long id, Long clienteId);
    boolean existsByClienteIdAndStatus(Long clienteId, StatusPedido status);
}

O nome do método é a consulta. findByStatusAndClienteId lê: onde status igual e cliente igual. Between, LessThan, GreaterThan, Containing, StartingWith, In e OrderBy entram no nome. Quando o nome ficar uma frase, pare e escreva @Query.

@Query("select p from Pedido p where p.clienteId = :clienteId and p.criadoEm >= :desde")
List<Pedido> buscarRecentes(Long clienteId, Instant desde);

JPQL fala com entidade e campo, não com tabela e coluna. Consulta nativa, nativeQuery = true, fala SQL e se usa quando uma função do PostgreSQL não tem jeito claro em JPQL. Parâmetro continua nomeado.

Paginação:

Page<Pedido> pagina = repositorio.findByStatus(status, PageRequest.of(0, 20, Sort.by("criadoEm").descending()));

page começa em 0. size é o tamanho. O retorno Page traz o conteúdo e o total. O DTO de saída devolve os itens da página, o número e o total, sem devolver o objeto Page do Spring para fora da aplicação, para o contrato não ficar preso ao framework. Existe Slice quando não se quer o count; ele é mais barato e não diz quantas páginas existem.

exists e count evitam carregar entidade só para perguntar "tem algum?". Isso conversa com a aula de anyMatch: a pergunta grande responde no banco.


Tema
Specification e filtro combinado

Descrição do conteúdo
Tela de operador filtra por status, cliente, intervalo de data e valor mínimo, em qualquer combinação. Um método de repositório por combinação explode em dezenas de nomes.

Specification é um predicado componível. Cada filtro vira um método que devolve Specification<Pedido>, e o caso de uso junta com and só os filtros que chegaram preenchidos. O Spring Data executa uma consulta só, com os WHERE necessários.

A diferença para filtrar com stream depois do findAll: findAll traz a tabela, e o stream descarta no processo. Funciona com vinte pedidos de desenvolvimento e derruba a API com a tabela real. Specification, @Query e JdbcClient recortam no PostgreSQL. Stream entra depois, se a página já carregada precisar de uma formatação ou de um agrupamento pequeno.


Tema
Transação: @Transactional, propagação, isolamento e rollback

Descrição do conteúdo
@Transactional no método do caso de uso abre uma transação no começo e faz commit no fim se nenhuma exceção não checada sair. Se sair RuntimeException, faz rollback. Exceção checada, por padrão, não faz rollback; o curso quase não lança checada, então o padrão coincide com a expectativa. rollbackFor deixa explícito quando uma checada também precisa desfazer.

Tudo o que precisa ficar junto entra no mesmo método transacional: gravar o pedido e os itens, ou gravar o pedido e a linha da outbox. O que não deve ficar junto fica fora: a chamada HTTP ao pagamento. Segurar a transação aberta durante a rede segura conexão do pool e pode segurar lock no banco. O fluxo é: transação curta muda o status para AGUARDANDO_PAGAMENTO e commita; a chamada HTTP acontece sem transação de escrita aberta; outra transação curta grava o resultado.

Propagação REQUIRED, o padrão: se já existe transação, o método entra nela; se não existe, abre uma. REQUIRES_NEW abre outra, independente, o que é útil para gravar um log de auditoria mesmo se a transação do pedido sofrer rollback. Usar REQUIRES_NEW sem necessidade fragmenta a consistência.

Isolamento padrão do PostgreSQL é READ COMMITTED. Duas transações não leem dado não commitado. As duas ainda podem ler o mesmo status antigo e as duas tentarem gravar PAGO. Por isso a transição importante usa versão ou update condicional, não só o isolamento padrão como amuleto.

readOnly = true em consulta ajuda o provedor a evitar flush e deixa a intenção registrada. Não substitui um teste de que o método não grava.

Auto-commit solto, sem @Transactional, grava cada sentença sozinha. O pedido pode ficar gravado e o item não. O caso de uso que escreve mais de uma coisa é transacional.

Lembrete do proxy: a anotação vale quando a chamada entra pela casca do Spring. Chamada interna this.metodo() não abre transação nova.


Tema
Flyway: o banco muda por arquivo versionado

Descrição do conteúdo
ddl-auto=update do Hibernate altera tabela sozinho e não é repetível com segurança entre pessoas e ambientes. O curso usa Flyway. Cada arquivo em src/main/resources/db/migration tem versão no nome: V1__criar_produto.sql, V2__criar_pedido.sql, V3__criar_item.sql, V4__criar_outbox.sql.

Na subida, o Flyway abre a tabela flyway_schema_history, vê o que já rodou e aplica o resto em ordem, cada um numa transação. Script já aplicado não pode ser editado: o checksum não bate e a aplicação recusa subir. Correção de tabela é um arquivo novo V5__adicionar_observacao.sql.

ddl-auto fica validate: o Hibernate confere se a entidade combina com o banco e não muda nada. Se a entidade ganhar campo e a migration não existir, a subida falha, e essa falha é o desejado.

O SQL é PostgreSQL de verdade: numeric(19, 2), timestamptz, uuid, chave estrangeira, índice em pedido(status, criado_em) para a listagem do operador. Índice não é enfeite; é o que segura a consulta que o repositório faz. Índice demais deixa a escrita mais cara. O curso cria índice para a busca que a API oferece, não para toda coluna.

O mesmo script roda no notebook, no Testcontainers e no Postgres do Compose. Esse é o ponto de ligação com o nível 9.


Tema
Teste de domínio, MockMvc e teste com contexto

Descrição do conteúdo
Três anéis, três custos.

Anel 1, JUnit puro, sem Spring. Testa desconto, transição de status e total. Sobe em milissegundos. A maior parte das regras fica aqui.

@Test
void deveRecusarCancelamentoDepoisDeEnviado() {
    Pedido pedido = pedidoEnviado();
    assertThrows(TransicaoDeStatusInvalidaException.class, pedido::cancelar);
}

Anel 2, teste de web fatiado ou MockMvc com contexto mínimo. Manda JSON para o controller e verifica status e corpo, com o service substituído por Mockito. @ExtendWith(MockitoExtension.class) no teste sem Spring. Quando o teste sobe o MVC, @WebMvcTest(PedidoController.class) carrega só a fatia web.

when(criarPedido.executar(1L)).thenReturn(pedido);
mockMvc.perform(post("/pedidos").contentType(MediaType.APPLICATION_JSON).content("""
        {"clienteId": 1}
        """)).andExpect(status().isCreated());

Mockito aqui substitui o vizinho. Não se mocka Pedido para testar a regra do próprio Pedido. Verifica-se comportamento observável: status HTTP, corpo, e, quando a interação é o comportamento, verify no publicador de evento.

Anel 3, @SpringBootTest sobe o contexto inteiro. É mais lento. Usa-se poucas vezes, no fluxo que atravessa controller, service e banco. @DataJpaTest sobe só JPA e é o meio-termo para repositório. Banco em memória H2 esconde diferença de SQL do PostgreSQL. O teste que precisa do PostgreSQL de verdade usa Testcontainers, no nível 9. Até lá, o teste aponta para um Postgres local, e o README do módulo declara essa dependência: sem o banco no ar, o teste falha, e a falha é a informação correta.

Nome do teste descreve o comportamento: deveRetornar404QuandoProdutoNaoExiste. Não descreve "testa o método criar".


Tema
RestClient: chamar outra API

Descrição do conteúdo
RestClient é o cliente HTTP síncrono do Spring moderno. Um bean configura a base e o timeout.

@Bean
public RestClient cepRestClient(CepProperties props, RestClient.Builder builder) {
    SimpleClientHttpRequestFactory fabrica = new SimpleClientHttpRequestFactory();
    fabrica.setConnectTimeout(props.timeout());
    fabrica.setReadTimeout(props.timeout());
    return builder.baseUrl(props.baseUrl()).requestFactory(fabrica).build();
}

A chamada:

EnderecoExterno endereco = cepRestClient.get()
        .uri("/{cep}", cep)
        .retrieve()
        .onStatus(status -> status.value() == 404, (req, res) -> {
            throw new CepNaoEncontradoException(cep);
        })
        .body(EnderecoExterno.class);

onStatus traduz o status da outra API para exceção do nosso domínio. body usa o Jackson para montar o DTO externo. Esse DTO não é a entidade. Um método da porta ConsultaCep devolve o objeto de domínio Endereco, copiando só rua, cidade e estado.

Timeout curto impede a thread, virtual ou não, de ficar presa num fornecedor parado. 404 é regra. 5xx e timeout são indisponibilidade, tratados na aula de resiliência. Não se captura Exception genérica para devolver endereço vazio: isso grava pedido com rua em branco e parece sucesso.

RestTemplate é o cliente antigo. Código legado usa, e o Boot ainda convive com ele. Código novo do curso usa RestClient. A diferença de uso é o estilo fluente e a integração com o Builder e com o ObjectMapper do contexto.


Tema
WebClient e a diferença para o RestClient

Descrição do conteúdo
WebClient fala HTTP num tipo reativo. A chamada devolve Mono ou Flux, que descrevem o resultado antes de ele chegar. Dá para combinar várias chamadas, aplicar timeout e seguir sem bloquear a thread, se toda a cadeia for reativa.

A chamada equivalente de CEP devolve um Mono. block() espera o valor e volta ao estilo síncrono:

EnderecoExterno endereco = cepWebClient.get()
        .uri("/{cep}", cep)
        .retrieve()
        .bodyToMono(EnderecoExterno.class)
        .block();

A composição de CEP com cotação combina os dois Mono e só então espera:

Mono<EnderecoExterno> endereco = cepWebClient.get().uri("/{cep}", cep).retrieve().bodyToMono(EnderecoExterno.class);
Mono<Cotacao> cotacao = freteWebClient.get().uri("/{cep}", cep).retrieve().bodyToMono(Cotacao.class);
Frete frete = Mono.zip(endereco, cotacao, this::montarFrete).block();

block dentro de uma cadeia que já ocupa uma thread de servidor anula o motivo do WebClient. Em alguns runtimes reativos, block nem é permitido no meio do loop de eventos.

Escolha do Sistema de Pedidos: RestClient no fluxo principal, porque o caso de uso é imperativo, a transação é curta e a leitura segue de cima para baixo. WebClient fica para a composição que permanece reativa até o fim, como o zip de CEP e cotação, com timeout na cadeia e o block só na borda do caso de uso, se a borda ainda for síncrona. Ter RestClient e WebClient no mesmo método, um bloqueando o outro sem um critério, mistura os dois modelos. O critério é um só: se a resposta precisa existir antes da próxima linha, use RestClient; se várias respostas chegam e se combinam antes de qualquer espera, use WebClient até o ponto em que o caso de uso recebe o valor pronto.

Independente do cliente, o contrato é a porta ConsultaCep. O service não sabe qual implementação está injetada. O teste injeta uma implementação falsa que devolve um endereço fixo, sem rede.


Tema
Resiliência na chamada externa

Descrição do conteúdo
Timeout é o primeiro limite, já configurado no cliente. Sem ele, o restante da resiliência não tem chão.

Retry repete a chamada. Só é seguro quando repetir não cria um segundo efeito. Consulta de CEP pode repetir duas vezes, com um intervalo curto. Cobrança de pagamento não pode repetir às cegas. Se o pagamento tiver retry, a chave de idempotência vai junto e o gateway precisa honrá-la.

Circuit breaker, com Resilience4j, conta falha. Depois de um limiar, abre e falha na hora, sem chamar o fornecedor, durante uma janela. Isso protege a API de pedido quando o CEP está caído e todas as threads iriam ficar esperando o timeout. Quando o circuito está aberto, o caso de uso devolve erro claro para o cliente preencher o endereço, ou segue sem frete automático, conforme a regra definida. Não inventa rua.

Bulkhead limita quantas chamadas simultâneas vão àquele fornecedor, para um CEP lento não consumir todas as conexões e todas as threads da API.

As anotações do Resilience4j ou a montagem explícita protegem o método do adaptador, não o domínio. O domínio recebe o resultado ou a exceção traduzida. Métrica de circuito aberto entra no Actuator quando o nível 9 ligar observabilidade.

Fallback que grava sucesso falso é pior que o erro. Com o fornecedor desligado, as falhas acumulam, o circuito abre e as chamadas seguintes falham na hora, sem esperar o timeout. A API responde rápido com o status de indisponibilidade. O cliente preenche o endereço ou segue sem frete automático. A rua não é inventada.


Tema
Webhook e idempotência da borda

Descrição do conteúdo
Além de o sistema chamar o pagamento, o pagamento chama o sistema. O endpoint POST /webhooks/pagamento recebe o evento, confere uma assinatura HMAC do corpo com o segredo compartilhado e só então altera o pedido.

A conferência usa MessageDigest ou Mac do javax.crypto com comparação em tempo constante (MessageDigest.isEqual nos bytes), para não vazar informação por tempo de resposta. Segredo vem da configuração, não do código.

Idempotência: o evento tem um id. A tabela de eventos processados, ou o próprio estado do pedido, faz a segunda entrega do mesmo id não confirmar de novo e não publicar outro fato. A resposta da segunda vez ainda é 2xx, porque para o fornecedor aquilo já foi aceito. Devolver erro faz o fornecedor tentar de novo para sempre.

O processamento pesado não fica dentro do webhook. O webhook grava o fato e publica o trabalho. Se gravar e publicar não puderem falhar separados, a linha de outbox entra na mesma transação, e a aula de outbox do nível 9 completa o desenho. Responder 200 antes de gravar e só depois "lembrar" de processar perde o evento quando o processo cai.


Tema
Paginação, ordenação e contrato da listagem

Descrição do conteúdo
GET /pedidos recebe page, size e sort. O controller limita size máximo, por exemplo 100, para um cliente não pedir size=1000000. O valor entra num PageRequest. O repositório devolve a página. O response leva content, page, size e totalElements.

Ordenação permitida é uma lista branca: criadoEm e status. Ordenar por qualquer string que o cliente mandar permite pedir coluna que não deveria ser critério e, em consulta nativa mal feita, vira problema mais sério. O controller traduz o parâmetro conhecido e recusa o desconhecido com 400.

Filtro de status usa enum. Texto que não é status vira 400 na conversão, e o advice trata o erro de conversão de parâmetro, não só o erro de corpo JSON. Essa é uma peça que falta em muita API: @RequestParam inválido cai numa exceção diferente de @RequestBody inválido, e as duas precisam de handler se o contrato de erro é único.


================================================================================
NÍVEL 9 — SPRING BOOT AVANÇADO
================================================================================

Tema
Spring Security: a cadeia na frente do controller

Descrição do conteúdo
Com a dependência de segurança no classpath, as rotas passam a exigir autenticação. A configuração do Boot 3 é um bean SecurityFilterChain, não a classe que estendia WebSecurityConfigurerAdapter (essa classe saiu).

@Bean
SecurityFilterChain cadeia(HttpSecurity http) throws Exception {
    return http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
            .requestMatchers(HttpMethod.POST, "/webhooks/pagamento").permitAll()
            .requestMatchers("/actuator/health").permitAll()
            .anyRequest().authenticated())
        .build();
}

STATELESS significa que o servidor não guarda sessão. Cada chamada traz o token. CSRF desligado combina com API sem cookie de sessão; se um dia a autenticação for por cookie de browser, CSRF volta a ser necessário. O webhook é permitAll na cadeia HTTP e mesmo assim exige a assinatura HMAC dentro do método. permitAll não significa "público e cego"; significa "a cadeia de login não é quem protege esta rota".

Papéis: hasRole("OPERADOR") na listagem administrativa, hasRole("CLIENTE") nas rotas do próprio pedido. O cliente só busca pedido cujo clienteId é o do token. Essa checagem é de aplicação, além do papel, porque dois clientes têm o mesmo papel e dados diferentes.

Senha de usuário de teste é gravada com BCryptPasswordEncoder. Nunca com a senha em claro, nunca com MD5. O encoder é um bean. No login, o encoder confere a senha digitada com o hash guardado.


Tema
JWT: emitir, validar e limitar

Descrição do conteúdo
No login bem-sucedido, a API assina um token com um segredo HMAC guardado fora do Git. O payload leva o sujeito (id do usuário), os papéis e a expiração curta, da ordem de minutos ou poucas horas. Não leva a senha e não leva o pedido.

Um filtro OncePerRequestFilter lê Authorization: Bearer, verifica a assinatura e a expiração com a mesma biblioteca usada para emitir, e coloca a autenticação no contexto da thread. Se a assinatura não confere ou o token venceu, o filtro não autentica e a cadeia responde 401.

Como o contexto é por thread, o service pode ler o usuário atual por um porto pequeno UsuarioAutenticado, implementado com o SecurityContextHolder. Teste de service passa um falso que devolve um id fixo, sem token.

Limite do JWT: não dá para "deslogar já" sem uma lista de revogação, porque o token vale até expirar. Por isso a expiração é curta. Dado no payload é visível para quem tem o token; a assinatura impede alteração, não impede leitura. Não coloque dado sensível achando que o token é um cofre.


Tema
Cache com Spring Cache e Redis

Descrição do conteúdo
@EnableCaching liga o suporte. @Cacheable no método de consulta de produto por código guarda o retorno. A segunda chamada com a mesma chave não vai ao banco. @CacheEvict no método que altera o preço tira a chave. @CachePut atualiza a entrada. A chave precisa incluir tudo o que muda o resultado: o código do produto, e não um cache geral sem chave que mistura produtos.

O cache local padrão vive na memória da instância. Duas APIs atrás do balanceador podem discordar do preço até a entrada vencer. Redis como CacheManager, já no Compose, é o armazenamento compartilhado. O valor em Redis precisa ser serializável pelo mecanismo escolhido; o curso guarda um DTO simples, não a entidade JPA com proxy preguiçoso.

TTL é obrigatório em dado que muda. Cache de CEP com duração de horas é razoável. Cache de status de pedido quase nunca é, porque o status muda por ação e ler status velho confirma a coisa errada. Não se cacheia resposta autenticada de um usuário sob uma chave que o outro usuário pode reutilizar.

Queda do Redis: a política do curso é a operação seguir até o banco ou até a API de CEP, com log de warn, se a biblioteca estiver configurada para não transformar falha de cache em falha da requisição. Cache é atalho, não fonte da verdade. A fonte do pedido continua sendo o PostgreSQL.


Tema
@Scheduled e @Async

Descrição do conteúdo
@EnableScheduling liga o agendador. @Scheduled(fixedDelay = 5000) no publicador da outbox roda o método, espera cinco segundos depois do fim e roda de novo. cron = "0 */5 * * * *" é o formato de expressão quando o horário importa. fixedDelay espera o término. fixedRate mede a partir do início e pode sobrepor se a execução passar do intervalo; o curso prefere fixedDelay no publicador para não empilhar duas leituras da mesma tabela.

Agendador em duas instâncias roda nos dois processos. Os dois vão ler a outbox. A leitura precisa ser segura: uma transação que marca a linha como "em publicação" com update condicional, ou um lock de banco, para os dois não publicarem sem controle. A idempotência do consumidor segura a duplicata que ainda assim acontecer.

@EnableAsync e @Async executam o método em outro executor. O retorno pode ser void ou CompletableFuture. A mesma regra do proxy vale: chamar o método async de dentro da própria classe não o torna async. Exceção em método void assíncrono não volta para o controller; ela precisa de um AsyncUncaughtExceptionHandler que logue, senão o erro some.

@Async não substitui fila. O processo que cai leva junto a tarefa que só estava na memória. Publicar evento de pedido pago é fila ou outbox. @Async cabe em trabalho local que pode se perder ou que apenas alivia a latência de uma resposta já commitada, com o risco assumido.


Tema
Evento interno da aplicação

Descrição do conteúdo
ApplicationEventPublisher publica um objeto. Um método @EventListener ou @TransactionalEventListener recebe. O listener transacional AFTER_COMMIT só roda se a transação do publicador commitou, o que evita avisar "pedido criado" quando o pedido sofreu rollback.

Isso é evento dentro do mesmo processo. É ótimo para separar "gravou" de "atualize uma leitura local" sem acoplar as classes por chamada direta. Não atravessa para outra API e não sobrevive à queda no meio do caminho, a menos que o listener publique na fila com a mesma disciplina da outbox.

O Sistema de Pedidos usa evento interno para o que acontece no mesmo processo e RabbitMQ para o que outro consumidor precisa receber com durabilidade. Os dois se chamam evento e não são o mesmo mecanismo. No pedido pago, os dois acontecem juntos: o listener interno, depois do commit, incrementa um contador local de pedidos pagos naquele processo; a mensagem no Rabbit acorda o notificador, que pode estar em outro processo e ainda estará lá se este cair depois da publicação. O contador local se perde com o processo. A mensagem no broker permanece.


Tema
RabbitMQ com Spring AMQP

Descrição do conteúdo
O broker recebe a mensagem, guarda e entrega a um consumidor. A aplicação pode cair e a mensagem continua no broker, desde que tenha sido confirmada.

Peças: exchange recebe a publicação; routing key classifica; binding liga a exchange à fila; fila segura as mensagens; consumidor tira da fila.

No projeto, uma Topic ou Direct exchange chamada pedidos, com routing key pedido.pago, e uma fila notificacao.pedido ligada a essa chave. Outra fila pode se ligar à mesma chave se outro trabalho também precisar do fato. Cada fila tem seus consumidores. Uma mensagem numa fila de trabalho é processada por um consumidor, não por todos os trabalhadores daquela mesma fila.

Configuração em @Bean: Exchange, Queue, Binding, e um RabbitTemplate com conversor JSON (Jackson2JsonMessageConverter). A porta PublicadorEventos é implementada por uma classe que chama rabbitTemplate.convertAndSend("pedidos", "pedido.pago", evento).

Consumidor:

@RabbitListener(queues = "notificacao.pedido")
public void aoPagar(PedidoPagoEvento evento) {
    notificacao.enviar(evento);
}

O conversor reconstitui o record. O método precisa ser idempotente: guardar o id do evento processado e, se ele já existe, voltar sem enviar outro e-mail.

Confirmação: acknowledgeMode manual, basicAck depois do sucesso. Se o método lança, a mensagem não é confirmada e o broker a reentrega. Retry com limite, configurado no listener container: três tentativas, com intervalo crescente. Na última falha, a dead-letter exchange manda a mensagem para a fila notificacao.pedido.dlq. Mensagem com JSON inválido não volta para a fila de trabalho. O conversor que falha publica na DLQ e confirma a original, para o ciclo parar. A DLQ guarda o corpo e o erro. Um operador relê, corrige o payload ou descarta. Reentregar JSON inválido para sempre ocupa o consumidor e não produz notificação.

O painel de management do Rabbit mostra profundidade da fila e a DLQ. Esse é o instrumento visual da aula.


Tema
Kafka: tópico, partição, offset e grupo

Descrição do conteúdo
Kafka guarda um log. O produtor acrescenta no fim do tópico. O consumidor lê e guarda o offset, que é a posição. Dá para reler. Isso é diferente do Rabbit de trabalho, em que a mensagem confirmada sai da fila.

Partição é a divisão do tópico. A chave da mensagem, no curso o id do pedido, escolhe a partição. Eventos do mesmo pedido na mesma partição permanecem em ordem. Eventos de pedidos diferentes podem ser lidos em paralelo por consumidores diferentes do mesmo grupo.

Grupo de consumidores divide as partições. Dois membros do mesmo grupo não tratam a mesma mensagem ao mesmo tempo; cada um fica com partições. Outro grupo, com outro nome, relê o mesmo tópico para outro fim, por exemplo um grupo notifica e outro atualiza um painel.

Spring for Apache Kafka: KafkaTemplate envia, @KafkaListener recebe. A configuração leva os endereços em spring.kafka.bootstrap-servers, o grupo e o conversor JSON. Auto-offset e commit: o padrão fácil commita em intervalo; o mais honesto para negócio é confirmar depois do processamento bem-sucedido, aceitando que uma falha no meio pode reentregar. Por isso o consumidor é idempotente do mesmo jeito que no Rabbit.

Quando o Sistema de Pedidos usa Kafka: trilha de fatos para mais de um time e para releitura. Quando usa Rabbit: trabalho a fazer, com retry e DLQ fáceis de mostrar. Quando usa HTTP: a resposta é necessária agora. Os três convivem no fluxo final, cada um no seu lugar.


Tema
Outbox

Descrição do conteúdo
Publicar na fila depois do commit pode falhar e perder o evento. Publicar antes do commit pode avisar um fato que sofreu rollback. A tabela outbox guarda o evento na mesma transação do pedido.

Na transação que marca o pedido como PAGO, insere-se uma linha: id do evento, tipo, payload JSON, publicado = false. Se a transação sofre rollback, a linha some junto. Um publicador @Scheduled lê um lote de não publicados, envia ao Rabbit ou ao Kafka e marca como publicado numa transação curta. Se o processo cair depois de enviar e antes de marcar, o envio acontece de novo. O consumidor idempotente absorve essa repetição.

A leitura do lote usa update condicional ou trava, por causa das duas instâncias. A linha tem tentativa e último erro, para o operador ver o que não sai. Não se apaga a linha na primeira publicação; marca-se a data de publicação, e uma limpeza posterior cuida do volume.

Essa aula junta banco, transação, agendamento e fila. O caso que prova o desenho é o processo que morre no meio. A transação do pedido já commitou, a linha da outbox está com publicado = false, o publicador enviou a mensagem e cai antes de marcar a linha. Na subida seguinte o lote lê a mesma linha e envia de novo. O consumidor idempotente encontra o id do evento já processado e volta sem notificar outra vez. O log mostra as duas publicações e uma notificação. Sem a tabela, a queda entre o commit e o envio perderia o evento. Sem a idempotência, a segunda publicação duplicaria o e-mail.


Tema
Actuator, saúde e métrica

Descrição do conteúdo
spring-boot-starter-actuator expõe /actuator/health. Com a segurança, health público mostra só UP ou DOWN. Detalhe de banco, disco e fila fica para quem tem papel de operação, porque o detalhe revela infraestrutura.

Indicadores prontos: o DataSource, o Rabbit, o Redis, se as dependências estiverem no classpath. O Compose usa esse health para saber se a API está pronta, não só se o processo existe.

Micrometer registra timer do endpoint, contador de evento publicado e contador de mensagem na DLQ. O Boot já mede HTTP. Métrica de negócio usa MeterRegistry injetado:

meterRegistry.counter("pedidos.pagos").increment();

Não se mede dado pessoal. Não se coloca id do cliente na tag da métrica, porque tag com cardinalidade alta derruba o sistema de métrica. Tag aceitável: status, tipo do evento, nome da fila.

Prometheus coleta essas métricas num intervalo e guarda a série no tempo. Grafana lê o Prometheus e desenha o painel: taxa de erro HTTP, profundidade da DLQ, circuito aberto, pedidos pagos por minuto. O endpoint do Micrometer que o Prometheus raspa é /actuator/prometheus. Trace distribuído com OpenTelemetry gera um id de rastreio na entrada do controller, propaga esse id na chamada do CEP e no cabeçalho da mensagem, e o consumidor da fila continua o mesmo rastreio. No painel de traces, uma confirmação aparece como uma linha só: HTTP, consulta externa e consumo. Log, health e contador já fazem o sistema operável. Prometheus, Grafana e o trace entram em seguida, com os mesmos nomes de métrica definidos aqui. Eles não são pré-requisito para o pedido confirmar.


Tema
Docker: imagem da API e o erro de localhost

Descrição do conteúdo
Dockerfile multi-stage. A primeira etapa usa uma imagem com JDK e Maven, copia o pom, baixa dependência, copia o código e roda mvn -DskipTests package. Os testes rodam no CI antes, não como desculpa para nunca testar. A segunda etapa usa uma imagem JRE 21, cria um usuário não root, copia só o jar e define o comando java -jar.

docker build -t pedidos-api . cria a imagem. docker run publica a porta. A API dentro do container que aponta jdbc url para localhost procura o Postgres dentro do próprio container, onde ele não está. O host certo é o nome do serviço na rede do Compose, por exemplo postgres. No notebook, sem Compose, o host é localhost. Por isso a URL é configuração, não constante.

.dockerignore tira target, .git e .env da cópia mandada ao daemon. Segredo não entra na imagem em camada. Variável de ambiente entra na execução.

A imagem de base leva versão fixa, por exemplo eclipse-temurin:21-jre-jammy, e não a tag latest. latest muda quando o publicador empurra outra build. A imagem construída hoje, com a tag fixa, sobe igual depois, dentro do que aquela versão promete.


Tema
Compose: um sistema só

Descrição do conteúdo
docker-compose.yml descreve api, postgres, rabbitmq e redis. Cada serviço tem imagem ou build, variável, porta publicada para o notebook e volume quando o dado deve sobreviver ao down. O Postgres tem volume. Sem volume, docker compose down apaga o disco do container: a fila perde a mensagem que ainda não foi confirmada, e o Postgres perde as tabelas. Volume nomeado guarda esse disco entre um down e o próximo up. A fila de estudo pode viver sem volume enquanto o exercício aceita recomeçar do zero. A fila que já entregou trabalho de verdade leva volume, do mesmo modo que o Postgres.

depends_on com condition service_healthy espera o Postgres aceitar conexão, não só o processo ter começado. A API mesmo assim deve tolerar uma falha inicial de conexão ou o healthcheck do Postgres precisa ser honesto, porque "processo no ar" não é "banco pronto".

A API recebe:

SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/pedidos
SPRING_DATASOURCE_PASSWORD
SPRING_RABBITMQ_HOST=rabbitmq
SPRING_DATA_REDIS_HOST=redis

O yaml do repositório não tem a senha real. Um .env local, fora do Git, preenche. O .env.example lista as chaves com valor fictício.

docker compose up --build sobe o conjunto. docker compose logs -f api segue o log. A conferência do conjunto é um pedido criado na porta publicada: a linha aparece no Postgres e a mensagem sai da fila depois da confirmação.


Tema
Testcontainers

Descrição do conteúdo
Testcontainers sobe um Postgres e um RabbitMQ reais para o teste e derruba no fim. A URL, o usuário e a senha são injetados na propriedade do Spring antes do contexto subir, com @DynamicPropertySource ou com o suporte de serviço do projeto de teste.

O teste sobe o fluxo: cria pedido, confirma, vê a linha no banco e vê a mensagem no Rabbit de teste. É o anel 3 com fidelidade. É mais lento que o teste puro de domínio. Poucos testes atravessam tudo; a regra de desconto não precisa de container.

O pré-requisito é o Docker rodando na máquina de quem desenvolve e no CI. Sem Docker, o teste não sobe o container e falha na hora de criar o Postgres. Essa falha fica visível. Marcar o teste como ignorado para sempre esconde a ausência do banco real. @DataJpaTest com Testcontainers substitui o H2 quando o SQL é específico de PostgreSQL, que é o caso do projeto.


Tema
Pool de conexões Hikari e o tamanho certo

Descrição do conteúdo
HikariCP é o pool que o Boot configura. Cada operação de banco pega uma conexão emprestada e devolve. O número máximo (spring.datasource.hikari.maximum-pool-size) limita quantas operações de banco acontecem ao mesmo tempo naquela instância.

Se o pool é 10 e a transação fica aberta durante o pagamento externo de 2 segundos, dez requisições travadas esgotam o pool. A décima primeira espera até connection-timeout e falha, mesmo com a API "de pé". A correção é a transação curta já desenhada, não é aumentar o pool até o PostgreSQL recusar conexão.

Conta grosseira: conexões máximas do Postgres divididas pelo número de instâncias da API, deixando folga para o Flyway, para uma sessão de operação e para o agendador. Virtual thread não entra nessa conta como "posso abrir uma conexão por virtual thread". A conexão continua cara e limitada. A virtual thread barata espera uma conexão cara.

O esgotamento se reproduz com uma transação que dorme dois segundos segurando a conexão, repetida até o tamanho do pool. A chamada seguinte espera até connection-timeout e falha com o pool esgotado, com a API ainda no ar. Tirar o sleep devolve a conexão no fim da transação curta, e a fila de espera anda. Aumentar o pool só empurra o mesmo limite para um número maior.


Tema
Concorrência do pedido com @Version

Descrição do conteúdo
Duas confirmações simultâneas leem AGUARDANDO_PAGAMENTO e as duas tentam gravar PAGO. @Version num campo long da entidade faz o Hibernate acrescentar "e version = a que eu li" no update, e incrementar a versão. A segunda gravação afeta zero linhas e lança ObjectOptimisticLockingFailureException. O advice traduz para 409.

Essa é a proteção que funciona com dois containers, porque quem arbitra é o banco, não o synchronized. O cliente que recebe 409 recarrega o pedido e vê que já está pago.

Update condicional do JdbcClient, mostrado no nível 8, resolve a mesma história em SQL explícito. O curso escolhe @Version na entidade principal e explica que as duas técnicas são o mesmo princípio: gravar só se o estado ainda é o que você viu.


Tema
Mais de uma instância

Descrição do conteúdo
O Compose ganha duas APIs e um nginx, ou o próprio teste sobe duas portas. As duas apontam para o mesmo Postgres, o mesmo Rabbit e o mesmo Redis. O login em uma e a chamada na outra funcionam porque o JWT é assinado com o mesmo segredo e não depende de memória local.

Três hábitos de uma instância quebram com duas. Um cache em HashMap de campo estático fica vazio na outra instância: a segunda API lê o banco ou o Redis, e o mapa local da primeira não existe lá. Um arquivo gravado no disco de um container não aparece no outro; o disco local não é compartilhado. Um agendador sem trava roda nos dois processos e publica duas vezes; a idempotência do consumidor absorve a duplicata, e o log mostra as duas tentativas. A correção de cada um já está nas aulas anteriores: Redis para o cache compartilhado, volume ou banco para o arquivo, e update condicional na outbox para o agendador.

Consumidores Rabbit nas duas instâncias dividem a fila de trabalho. Os dois não processam a mesma cópia ao mesmo tempo. No Kafka, os dois membros do mesmo grupo dividem partições.

Esta aula fecha o raciocínio do nível 6 com o processo real. O desenho que parecia teórico (não guardar pedido em campo de service, não confiar em lock local, usar outbox e Redis) passa a ser a diferença entre o pedido que confirma uma vez e o pedido que confirma duas.


Tema
CI: testar e empacotar

Descrição do conteúdo
Um workflow no GitHub Actions, ou na ferramenta que o time usar, faz checkout, prepara JDK 21, roda mvn test e, se passar, constrói a imagem. O job precisa de Docker quando o Testcontainers faz parte da suíte.

O segredo de teste não é o segredo de produção. O workflow não imprime a senha no log. O yaml do pipeline não contém o valor; ele referencia o cofre da ferramenta.

O jar executado no notebook e o jar copiado para a imagem são o mesmo artefato, produzido pelo mesmo mvn package. Se o teste quebra, o job para e a imagem não nasce. Uma falha introduzida de propósito, um teste que deixa de passar, interrompe o pipeline. A correção do teste libera o build da imagem. Deploy para um servidor real fica como continuação. O roteiro para no artefato reproduzível, que já é o necessário para o Compose do curso.


Tema
Episódio final: um pedido atravessa tudo

Descrição do conteúdo
Sobe o Compose. Faz login e guarda o token. Cria o pedido. Adiciona item, mostra validação recusando quantidade zero, mostra o total com BigDecimal e o desconto por faixa. Consulta o CEP pelo RestClient. Confirma. O pagamento fake aprova, o webhook assinado chega, a transação grava o status e a outbox, o publicador manda o evento, o consumidor notifica uma vez, a segunda entrega do webhook não duplica.

Em seguida o CEP é desligado. A chamada estoura o timeout, as falhas abrem o circuito e a próxima confirmação responde na hora com indisponibilidade. Um erro forçado no consumidor esgota as tentativas e a mensagem para na DLQ. A segunda instância sobe, as duas leem o mesmo pedido em AGUARDANDO_PAGAMENTO, a primeira grava PAGO e a segunda recebe 409 por causa do @Version. O health responde UP, e o log da confirmação, da chamada ao CEP e do consumo da fila carrega o mesmo id de requisição.

O que fica de fora deste roteiro, com o ponto de entrada para quem continuar: OAuth2 com provedor externo, no lugar do JWT emitido pela própria API; Kubernetes, como a tradução do Compose para um cluster; teste de carga medido, com número de requisições e limite do pool; observabilidade com o trace do OpenTelemetry já descrito na aula de métrica. O repositório do Sistema de Pedidos acompanha os níveis, com uma tag ao fim de cada um.


================================================================================
O QUE CADA NÍVEL ENTREGA
================================================================================

Nível 1. Calcula o total de um pedido no console, com BigDecimal, if, switch, laço e método.
Nível 2. O pedido é objeto com invariante, desconto polimórfico, status em enum e borda em record.
Nível 3. Escolhe List, Set, Deque e Map com critério, e explica por que não existe SetList.
Nível 4. Escreve o mesmo relatório com for e com stream e sabe dizer qual modo ficou mais claro.
Nível 5. Trata erro, ausência, data, arquivo e JSON do jeito que o Boot vai usar.
Nível 6. Reconhece estado compartilhado e não protege dado de duas instâncias com synchronized.
Nível 7. Sobe uma API Spring Boot que cria e busca pedido.
Nível 8. Valida, devolve erro estável, persiste com JPA e Flyway, testa e chama outra API.
Nível 9. Autentica, publica em fila sem perder o evento no commit, sobe o sistema no Docker e aguenta duas instâncias.

Cada tema termina no trecho do Sistema de Pedidos que passou a usar o recurso, inclusive quando a aula foi de fundamento. O Spring Boot é o lugar onde a linguagem dos níveis 1 a 6 continua visível, com a anotação explicada pelo mecanismo que está por baixo.
