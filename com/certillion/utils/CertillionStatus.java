package com.certillion.utils;

import com.certillion.api.StatusType;

/**
 * Status codes returned by the Certillion SOAP Web Service (see documentation).
 */
public enum CertillionStatus {
	
	/* Codes from 100 to 199 indicates success. */

	// 100 to 109: generic success
	REQUEST_OK(100),

	// 110 to 119: ongoing transaction
	TRANSACTION_IN_PROGRESS(110),

	// 120 to 129: registration process
	REGISTRATION_VALID(120),

	// 130 to 139: certificate life-cycle
	CERTIFICATE_VALID(130),
	CSR_VALID(131),
	REVOCATION_ACCEPTED(132),

	// 140 to 149: signature process
	SIGNATURE_VALID(140),

	// 150 to 159: querying information
	USER_ACTIVE(150),
	DEVICE_READY(151),

	/* Codes from 200 to 299 indicates an error of the sender or problems with the request, a transaction is not even created. */

	// 200 to 209: malformed or ignored requests
	REQUEST_MISSING_PARAM(200),
	REQUEST_WRONG_PARAM(201),
	REQUEST_WRONG_LENGTH(202),
	REQUEST_BAD_FORMAT(203),
	REQUEST_BAD_PROFILE(204),
	REQUEST_BAD_DATA(205),
	REQUEST_DUPLICATED(206),

	// 210 to 219: account problems
	ACCOUNT_NO_BANDWIDTH(210),
	ACCOUNT_MAX_TRIES(211),
	ACCOUNT_NO_CREDIT(212),

	// 220 to 229: authentication or access problem
	ACCESS_NOT_AUTHORIZED(220),
	ACCESS_NO_HANDSHAKE(221),
	ACCESS_NO_SPECIFIED(222),
	TRANSACTION_NOT_AUTHORIZED(223),

	// 230 to 239: user status
	USER_NO_DEVICE(230),
	DEVICE_NO_CERTIFICATE(231),
	DEVICE_REMOVED_FROM_POLLING(232),

	/* Codes from 300 to 399 are for errors that can happen in any transaction, this errors are returned only by the server. */

	// 300 to 309: network problems
	NETWORK_ERROR(300),

	// 310 to 319: not found
	TRANSACTION_NOT_FOUND(310),
	IDENTIFIER_NOT_FOUND(311),
	SERVICE_NOT_FOUND(312),


	// 320 to 329: redirecting mobile errors
	MOBILE_SIGNATURE_ERROR(320),
	MOBILE_CERTIFICATE_ERROR(321),


	/* Codes from 400 to 499 are for errors that can happen with any message, in the server or in the mobile. */

	// 400 to 409: cancellations
	USER_CANCELED(400),

	// 410 to 419: error reading message
	MESSAGE_BAD_INTEGRITY(410),
	MESSAGE_BAD_AUTHENTICATION(411),
	MESSAGE_BAD_ENCRYPTION(412),
	MESSAGE_BAD_ENCODING(413),
	MESSAGE_EXPIRED(414),

	// 420 to 429: out of sync
	MESSAGE_WRONG_VERSION(420),
	MESSAGE_MISSING_KEY(421),
	MESSAGE_UNEXPECTED_KEY(422),
	MESSAGE_UNEXPECTED(423),
	KEY_EXPIRED(424),
	KEY_REJECTED(425),

	// 430 to 439: not found
	MESSAGE_NOT_FOUND(430),
	USER_NOT_FOUND(431),

	// 440 to 449: internal errors
	INTERNAL_ERROR(440),

	// 450 to 459: problem with an additional service
	SERVICE_CANT_ACTIVATE(450),
	SERVICE_CANT_USE(451),
	SERVICE_WAS_ACTIVATED(452),


	/* Codes from 500 to 599 are for errors in registration or handshake. */

	// 500 to 509: errors returned by the server during the registration
	PLATFORM_NOT_FOUND(500),
	TOKEN_WRONG(501),
	IDENTIFIER_INVALID(502),
	IDENTIFIER_DUPLICATED(503),


	/* Codes from 600 to 699 are for errors related to certificates. */

	// 600 to 619: generic invalid certificate errors
	CERTIFICATE_INVALID(600),
	CSR_INVALID(601),
	CRL_INVALID(602),
	CERTIFICATE_MALFORMED(603),
	CERTIFICATE_REVOKED(604),
	CERTIFICATE_EXPIRED(605),
	CERTIFICATE_NOT_IN_EFFECT(606),
	CERTIFICATE_BLOCKED(607),
	CERTIFICATE_NOT_TRUSTED(608),
	KEY_SIZE_INVALID(609),
	CRL_UNAVAILABLE(610),

	// 620 to 629: not found
	CERTIFICATE_NOT_FOUND(620),
	CHAIN_NOT_FOUND(621),
	KEY_NOT_FOUND(622),

	// 630 to 639: smartcard or crypto-token problems
	CARD_ERROR(630),
	CARD_PIN_BLOCKED(631),
	CARD_BLOCKED(632),
	CARD_NOT_PRESENT(633),

	// 640 to 649: errors during certificate generation or renewal
	WRONG_PIN(640),

	// 650 to 659: errors during certificate revocation
	CERTIFICATE_CANT_REVOKE(650),

	// 660 to 669: errors during certificate importing
	CERTIFICATE_DUPLICATED(660),
	CERTIFICATE_WRONG_SUBJECT(661),
	KEY_MISMATCH(662),


	/* Codes from 700 to 799 are for errors related to signatures. */

	// 700 to 709: generic invalid signature errors
	SIGNATURE_INVALID(700),
	SIGNATURE_CANT_VALIDATE(701),

	// 710 to 719: not found
	TEMPLATE_NOT_FOUND(710),
	DOCUMENT_NOT_FOUND(711),
	WRONG_DOCUMENT_HASH(712),
	WRONG_DOCUMENT_TYPE(713),

	// 720 to 729: errors in XMLDSig signatures
	XMLDSIG_EMPTY_ELEMENT_LIST(720),
	XMLDSIG_ELEMENTS_WITHOUT_ATTRIBUTE_ID(721),
	XMLDSIG_SAME_ID_FOR_MULTIPLE_ELEMENTS(722),
	XMLDSIG_NO_ELEMENT_FOUND(723),

	/* Codes from 800 to 809 are for errors related to Certillion Manager. */
	CONTRACT_NOT_FOUND(800),
	DUPLICATED_ACCOUNT(801),
	MAX_ACCOUNTS_REACHED(802),
	ACCOUNT_NOT_REGISTERED(803);
	
	private int code;
	
	private CertillionStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	
	public boolean isSuccess() {
		return !isError();
	}
	
	public boolean isError() {
		return (code >= 200);
	}
	
	public static CertillionStatus valueOf(int code) {
		CertillionStatus[] all = CertillionStatus.values();
		for (CertillionStatus status : all) {
			if (status.getCode() == code) {
				return status;
			}
		}
		throw new IllegalArgumentException("Unknown code: " + code);
	}

	public static CertillionStatus from(StatusType statusType) {
		return valueOf(statusType.getStatusMessage());
	}

	public StatusType toStatusType() {
		StatusType result = new StatusType();
		result.setStatusCode(this.getCode());
		result.setStatusMessage(this.name());
		result.setStatusDetail("Client-side generated status");
		return result;
	}
}
