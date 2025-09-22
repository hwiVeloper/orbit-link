package dev.hwiveloper.orbitlink.common.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class UpperCaseNamingStrategy implements PhysicalNamingStrategy {

    @Override
    public Identifier toPhysicalTypeName(Identifier logicalName, JdbcEnvironment jdbcEnvironment) {
        return convertToUpperCase(logicalName);
    }

    @Override
    public Identifier toPhysicalCatalogName(Identifier logicalName,
        JdbcEnvironment jdbcEnvironment) {
        return convertToUpperCase(logicalName);
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier logicalName,
        JdbcEnvironment jdbcEnvironment) {
        return convertToUpperCase(logicalName);
    }

    @Override
    public Identifier toPhysicalTableName(Identifier logicalName, JdbcEnvironment jdbcEnvironment) {
        return convertTableNameToUpperCase(logicalName);
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier logicalName,
        JdbcEnvironment jdbcEnvironment) {
        return convertToUpperCase(logicalName);
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier logicalName,
        JdbcEnvironment jdbcEnvironment) {
        return convertToUpperCase(logicalName);
    }

    private Identifier convertToUpperCase(Identifier identifier) {
        if (identifier == null || identifier.getText() == null) {
            return identifier;
        }

        String newName = identifier.getText()
            .replaceAll("([a-z])([A-Z])", "$1_$2")
            .toUpperCase();
        return Identifier.toIdentifier(newName);
    }

    private Identifier convertTableNameToUpperCase(Identifier identifier) {
        if (identifier == null || identifier.getText() == null) {
            return identifier;
        }

        String newName = "TB_" + identifier.getText()
            .replaceAll("([a-z])([A-Z])", "$1_$2")
            .toUpperCase();
        return Identifier.toIdentifier(newName);
    }
}
