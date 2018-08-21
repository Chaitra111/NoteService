package com.bridgelabz.eurekaServerNoteService.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

import com.bridgelabz.eurekaServerNoteService.exceptionService.ToDoException;
import com.bridgelabz.eurekaServerNoteService.model.NoteModel;

/**
 * @author Chaitra Ankolekar
 * Purpose :Utility class for validation
 */
public class Utility {
	static final String KEY = "chaitra";

	public Utility() {

	}
	/**
	 * method validate the fields when registering
	 * @param register
	 * @return
	 * @throws UserExceptionHandling
	 */

	
	/**
	 * to create a jwt token
	 * 
	 * @param id
	 * @return
	 */
	public String createToken(String id) {

		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		Date date = new Date();

		JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(date).signWith(signatureAlgorithm, KEY);
		return builder.compact();

	}

	/**
	 * to decode the jwt token
	 * 
	 * @param jwt
	 * @return
	 * @throws ToDoException 
	 */
	public Claims parseJwt(String jwt) throws ToDoException {
		System.out.println(jwt);
		 return Jwts.parser().setSigningKey(KEY).parseClaimsJws(jwt).getBody();
		/*try {
			Claims claims = Jwts.parser().setSigningKey("KEY").parseClaimsJws(jwt).getBody();
			return claims;
		} catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
			throw new ToDoException("Error in JWT Token");
		} catch (ExpiredJwtException e) {
			throw new ToDoException("Token Expired");
		}*/
	}

	/**
	 * @param user
	 * @return token
	 */
	public String createTokens(NoteModel user) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		JwtBuilder builder = Jwts.builder().setSubject(user.getEmailId()).setIssuedAt(new Date())
				.signWith(signatureAlgorithm, KEY);
		return builder.compact();
	}
}
