package fr.m2i.spring.jdbc.repository;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

public class CustomSQLErrorCodeTranslator extends SQLErrorCodeSQLExceptionTranslator {

	@Override
	protected DataAccessException customTranslate(final String task, final String sql,
			final SQLException sqlException) {
		if (sqlException.getErrorCode() == 23505) {
			return new DuplicateKeyException("Custom Exception translator - Integrity contraint violation.",
					sqlException);
		}
		return null;
	}
}
