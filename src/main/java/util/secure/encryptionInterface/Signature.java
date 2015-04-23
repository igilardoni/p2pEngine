package util.secure.encryptionInterface;

/**
 * Interface to sign a message and verify the sign
 * @author Julien Prudhomme
 *
 * @param <M> the type of message to sign.
 * @param <S> the type of sign.
 */
public interface Signature<M, S> {
	public S getMessageSignature(M message);
	public boolean verifySignature(M message, S signature);
}
