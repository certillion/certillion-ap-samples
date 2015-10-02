Conteúdo:

	/src
      
			Diretório contendo todo o código usado nos exemplos mencionados nos manuais.
			
	icpm-sample-ap.jar
	
			Artefato executável com o seguintes comandos:
				
				\> java -jar icpm-sample-ap.jar signature-sync [uniqueIdentifier] [textToBeSigned]
						uniqueIdentifier: Identificador registrado no sistema. Podendo ser um número de telefone ou um identificador de um tablet.
						textToBeSigned: Texto qualquer a ser assinado.
			
				\> java -jar icpm-sample-ap.jar signature-async [uniqueIdentifier] [textToBeSigned]
						uniqueIdentifier: Identificador registrado no sistema. Podendo ser um número de telefone ou um identificador de um tablet.
						textToBeSigned: Texto qualquer a ser assinado.				
						
				\> java -jar icpm-sample-ap.jar signature-by-template-sync [uniqueIdentifier] [text]
						uniqueIdentifier: Identificador registrado no sistema. Podendo ser um número de telefone ou um identificador de um tablet.
						text: Texto qualquer que completará a template 1.	
						
				\> java -jar icpm-sample-ap.jar signature-by-template-async [uniqueIdentifier] [text]
						uniqueIdentifier: Identificador registrado no sistema. Podendo ser um número de telefone ou um identificador de um tablet.
						text: Texto qualquer que completarÃ¡ a template 1.	
						
				\> java -jar icpm-sample-ap.jar signature-document-sync [uniqueIdentifier] [fileToBeSigned] [dataToBeDisplayed]
						uniqueIdentifier: Identificador registrado no sistema. Podendo ser um número de telefone ou um identificador de um tablet.
						fileToBeSigned: Caminho do arquivo a ser assinado.
						dataToBeDisplayed: Texto que será apresentado ao usuário.
						
				\> java -jar icpm-sample-ap.jar batch-signature-async [uniqueIdentifier] [dataToBeDisplayed] [signatureStandard] [urls]
						uniqueIdentifier: Identificador registrado no sistema. Podendo ser um número de telefone ou um identificador de um tablet.
						textToBeSigned: Texto que deverá ser apresentado para o usuário.						
						signatureStandard: Padrão de assinatura(ADOBEPDF ou CADES)
						url: Url de destino dos documentos a serem assinados.
						
				\> java -jar icpm-sample-ap.jar batch-signature-async-aputils [uniqueIdentifier] [dataToBeDisplayed] [signatureStandard] [docsPath]
						uniqueIdentifier: Identificador registrado no sistema. Podendo ser um número de telefone ou um identificador de um tablet.
						textToBeSigned: Texto que deverá ser apresentado para o usuário.						
						signatureStandard: Padrão de assinatura(ADOBEPDF ou CADES)
						docsPath: Caminho onde estão os documentos a serem assinados.
						
				\> java -jar icpm-sample-ap.jar batch-signature-async-aputils-poll [uniqueIdentifier] [dataToBeDisplayed] [signatureStandard] [docsPath]
						uniqueIdentifier: Identificador registrado no sistema. Podendo ser um número de telefone ou um identificador de um tablet.
						textToBeSigned: Texto que deverá ser apresentado para o usuário.						
						signatureStandard: Padrão de assinatura(ADOBEPDF ou CADES)
						docsPath: Caminho onde estão os documentos a serem assinados.						