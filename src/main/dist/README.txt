Conte�do:

	/src
      
			Diret�rio contendo todo o c�digo usado nos exemplos mencionados nos manuais.
			
	icpm-sample-ap.jar
	
			Artefato execut�vel com o seguintes comandos:
				
				\> java -jar icpm-sample-ap.jar signature-sync [uniqueIdentifier] [textToBeSigned]
						uniqueIdentifier: Identificador registrado no sistema. Podendo ser um n�mero de telefone ou um identificador de um tablet.
						textToBeSigned: Texto qualquer a ser assinado.
			
				\> java -jar icpm-sample-ap.jar signature-async [uniqueIdentifier] [textToBeSigned]
						uniqueIdentifier: Identificador registrado no sistema. Podendo ser um n�mero de telefone ou um identificador de um tablet.
						textToBeSigned: Texto qualquer a ser assinado.				
						
				\> java -jar icpm-sample-ap.jar signature-by-template-sync [uniqueIdentifier] [text]
						uniqueIdentifier: Identificador registrado no sistema. Podendo ser um n�mero de telefone ou um identificador de um tablet.
						text: Texto qualquer que completar� a template 1.	
						
				\> java -jar icpm-sample-ap.jar signature-by-template-async [uniqueIdentifier] [text]
						uniqueIdentifier: Identificador registrado no sistema. Podendo ser um n�mero de telefone ou um identificador de um tablet.
						text: Texto qualquer que completará a template 1.	
						
				\> java -jar icpm-sample-ap.jar signature-document-sync [uniqueIdentifier] [fileToBeSigned] [dataToBeDisplayed]
						uniqueIdentifier: Identificador registrado no sistema. Podendo ser um n�mero de telefone ou um identificador de um tablet.
						fileToBeSigned: Caminho do arquivo a ser assinado.
						dataToBeDisplayed: Texto que ser� apresentado ao usu�rio.
						
				\> java -jar icpm-sample-ap.jar batch-signature-async [uniqueIdentifier] [dataToBeDisplayed] [signatureStandard] [urls]
						uniqueIdentifier: Identificador registrado no sistema. Podendo ser um n�mero de telefone ou um identificador de um tablet.
						textToBeSigned: Texto que dever� ser apresentado para o usu�rio.						
						signatureStandard: Padr�o de assinatura(ADOBEPDF ou CADES)
						url: Url de destino dos documentos a serem assinados.
						
				\> java -jar icpm-sample-ap.jar batch-signature-async-aputils [uniqueIdentifier] [dataToBeDisplayed] [signatureStandard] [docsPath]
						uniqueIdentifier: Identificador registrado no sistema. Podendo ser um n�mero de telefone ou um identificador de um tablet.
						textToBeSigned: Texto que dever� ser apresentado para o usu�rio.						
						signatureStandard: Padr�o de assinatura(ADOBEPDF ou CADES)
						docsPath: Caminho onde est�o os documentos a serem assinados.
						
				\> java -jar icpm-sample-ap.jar batch-signature-async-aputils-poll [uniqueIdentifier] [dataToBeDisplayed] [signatureStandard] [docsPath]
						uniqueIdentifier: Identificador registrado no sistema. Podendo ser um n�mero de telefone ou um identificador de um tablet.
						textToBeSigned: Texto que dever� ser apresentado para o usu�rio.						
						signatureStandard: Padr�o de assinatura(ADOBEPDF ou CADES)
						docsPath: Caminho onde est�o os documentos a serem assinados.						