package com.morpheus.previewtyapi.util.encrypt;

public abstract class OneWayEncryptor implements Encryptor {

	@Override
	public String decrypt(EncryptParam param) throws EncryptionException {
		throw new EncryptionException("Decryption is not supported.");
	}
}
