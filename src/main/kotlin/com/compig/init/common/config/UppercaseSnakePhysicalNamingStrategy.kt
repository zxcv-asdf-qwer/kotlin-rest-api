package com.compig.init.common.config

import org.hibernate.boot.model.naming.Identifier
import org.hibernate.boot.model.naming.PhysicalNamingStrategy
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment

class UppercaseSnakePhysicalNamingStrategy : PhysicalNamingStrategy {
    override fun toPhysicalCatalogName(identifier: Identifier?, jdbcEnvironment: JdbcEnvironment): Identifier? {
        return if (identifier == null) {
            null
        } else convertToSnakeUpperCase(identifier)
    }

    override fun toPhysicalSchemaName(identifier: Identifier?, jdbcEnvironment: JdbcEnvironment): Identifier? {
        return if (identifier == null) {
            null
        } else convertToSnakeUpperCase(identifier)
    }

    override fun toPhysicalTableName(identifier: Identifier, jdbcEnvironment: JdbcEnvironment): Identifier {
        return convertToSnakeUpperCase(identifier)
    }

    override fun toPhysicalSequenceName(identifier: Identifier, jdbcEnvironment: JdbcEnvironment): Identifier {
        return convertToSnakeUpperCase(identifier)
    }

    override fun toPhysicalColumnName(identifier: Identifier, jdbcEnvironment: JdbcEnvironment): Identifier {
        return convertToSnakeUpperCase(identifier)
    }

    private fun convertToSnakeUpperCase(identifier: Identifier): Identifier {
        val regex = "([a-z])([A-Z])"
        val replacement = "$1_$2"
        val newName: String = identifier.text
            .replace(regex.toRegex(), replacement)
            .uppercase()
        return Identifier.toIdentifier(newName)
    }
}