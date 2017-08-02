# Certillion Samples

Exemplos de utilização do Certillion como solicitador de assinatura.

## Onde encontro documentação?

Em [Manual de Integração](https://docs.certillion.com/manual-integracao) você encontra a versão mais atualizada.

## Como rodar os exemplos apontando para o servidor de teste (labs)?

1. [Entre em contato](https://certillion.com/contato) solicitando o cadastramento da sua empresa para utilizar o servidor de teste.
1. Clone esse repositório.
1. Adicione a pasta src no seu IDE favorito, compile e execute.

## Concluí a integração. O que preciso fazer para utilizar o servidor de produção?

Você precisa ter um "e-CNPJ" ou "certificado de equipamento" A1 válido da ICP-BR para autenticar o seu servidor junto ao Certillion.

1. Use a ferramenta [WS-Signer](https://download.certillion.com/ws-signer), que funciona como um proxy que autentica as requisões para você.

Não esqueça de alterar a URL do servidor.

## Como reutilizar os exemplos em um projeto Java?

Versão curta: não reutilize, apenas use como referência para entender e implementar rapidamente seu próprio código. Adapte para a sua arquitetura.

## Como portar os exemplos para outra linguagem de programação?

Os exemplos são disponibilizados em Java. Caso não utilize esta linguagem, siga os passos abaixo:

1. Estude o [Manual de Integração](https://docs.certillion.com/manual-integracao)
1. Use uma ferramenta que gera código a partir do [WSDL do servidor de teste](http://labs.certillion.com/mss/SignatureService/SignatureEndpointBeanV2.wsdl).
1. Reproduza a funcionalidade da versão Java.
