# Certillion Samples

Exemplos de utilização do Certillion como solicitador de assinatura.

## Como rodar os exemplos apontando para o servidor de teste?

1. [Entre em contato](https://certillion.com/contato) solicitando o cadastramento da sua empresa para utilizar o servidor de teste.
1. Clone esse repositório.
1. Faça build com o Maven, através do comando `mvn package`.
1. Entre na pasta "target" e execute com o comando `java -jar certillion-ap-samples.jar`
1. Siga as instruções.

## Como rodar os exemplos apontando para o servidor de produção?

Você precisa ter um "e-CNPJ" ou "certificado de equipamento" válido da ICP-BR para autenticar o seu servidor junto ao Certillion. Existem duas formas de realizar a autenticação:

1. Altere o código dos exemplos para usar um cliente REST que suporte HTTPS Client Authentication e um cliente SOAP que suporte o padrão WS-Security. Sugerimos o [CXF](https://cxf.apache.org).
1. Ou use a ferramenta [WS-Signer](https://download.certillion.com/ws-signer), que funciona como um proxy que autentica as requisões para você.

Não esqueça de alterar a URL do servidor na classe `Constants`.

## Como reutilizar os exemplos em um projeto Java?

1. Importe as dependências desse projeto.
1. Copie o pacote `br.com.esec.mss.ap`, que contém o código para a chamar o web service.
1. Também copie o pacote `br.com.esec.icpm.samples.ap.core.utils` que contém classes utilitárias para simplificar a integração.
1. Adapte a classe `SignDocumentsSample` para utilizar um pool de threads gerenciado pelo servidor de aplicação (veja comentários na código).

## Como portar os exemplos para outra linguagem de programação?

1. Use uma ferramenta que gera código a partir do [WSDL do servidor de teste](http://labs.certillion.com/mss/SignatureService/SignatureEndpointBean.wsdl).
1. Reproduza a funcionalidade que se encontra no pacote `utils`.
