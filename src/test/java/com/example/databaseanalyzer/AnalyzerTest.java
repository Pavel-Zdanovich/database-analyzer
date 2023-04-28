package com.example.databaseanalyzer;

import com.example.databaseanalyzer.dto.Credentials;
import com.example.databaseanalyzer.model.Statistics;
import com.example.databaseanalyzer.service.Analyzer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AnalyzerTest {

    static Analyzer analyzer;

    static Credentials h2;

    static Credentials hsqldb;

    static Credentials derby;

    static Credentials mysql;

    static Credentials mariadb;

    static Credentials oracle;

    static Credentials postgresql;

    static Credentials sqlserver;

    static Credentials db2;

    @BeforeAll
    static void beforeAll() {
        analyzer = new Analyzer();

        derby = new Credentials();
        derby.setUrl("jdbc:derby:memory:test;create=true");
        derby.setUsername("sa");
        derby.setPassword("");

        h2 = new Credentials();
        h2.setUrl("jdbc:h2:mem:test");
        h2.setUsername("sa");
        h2.setPassword("");

        hsqldb = new Credentials();
        hsqldb.setUrl("jdbc:hsqldb:mem:test");
        hsqldb.setUsername("sa");
        hsqldb.setPassword("");

        mysql = new Credentials();
        mysql.setUrl("jdbc:mysql://localhost:3306");
        mysql.setUsername("root");
        mysql.setPassword("password");

        mariadb = new Credentials();
        mariadb.setUrl("jdbc:mariadb://localhost:3306");
        mariadb.setUsername("root");
        mariadb.setPassword("password");

        oracle = new Credentials();
        oracle.setUrl("jdbc:oracle:thin:@//localhost:1521");
        oracle.setUsername("SYSADMIN");
        oracle.setPassword("password");

        postgresql = new Credentials();
        postgresql.setUrl("jdbc:postgresql://localhost:5430");
        postgresql.setUsername("postgres");
        postgresql.setPassword("password");

        sqlserver = new Credentials();
        sqlserver.setUrl("jdbc:sqlserver://localhost:1434;trustServerCertificate=true;");
        sqlserver.setUsername("username");
        sqlserver.setPassword("password");

        db2 = new Credentials();
        db2.setUrl("jdbc:db2://localhost:50000");
        db2.setUsername("username");
        db2.setPassword("password");
    }

    @Test
    void derby() {
        Statistics statistics = analyzer.analyze(derby);
        Assertions.assertThat(statistics).hasNoNullFieldsOrProperties();
    }

    @Test
    void h2() {
        Statistics statistics = analyzer.analyze(h2);
        Assertions.assertThat(statistics).hasNoNullFieldsOrProperties();
    }

    @Test
    void hsqldb() {
        Statistics statistics = analyzer.analyze(hsqldb);
        Assertions.assertThat(statistics).hasNoNullFieldsOrProperties();
    }

    //    @Test
    void mysql() {
        Statistics statistics = analyzer.analyze(mysql);
        Assertions.assertThat(statistics).hasNoNullFieldsOrProperties();
    }

    //    @Test
    void mariadb() {
        Statistics statistics = analyzer.analyze(mariadb);
        Assertions.assertThat(statistics).hasNoNullFieldsOrProperties();
    }

    //    @Test
    void oracle() {
        Statistics statistics = analyzer.analyze(oracle);
        Assertions.assertThat(statistics).hasNoNullFieldsOrProperties();
    }

    //    @Test
    void postgresql() {
        Statistics statistics = analyzer.analyze(postgresql);
        Assertions.assertThat(statistics).hasNoNullFieldsOrProperties();
    }

    //    @Test
    void sqlserver() {
        Statistics statistics = analyzer.analyze(sqlserver);
        Assertions.assertThat(statistics).hasNoNullFieldsOrProperties();
    }

    //    @Test
    void db2() {
        Statistics statistics = analyzer.analyze(db2);
        Assertions.assertThat(statistics).hasNoNullFieldsOrProperties();
    }
}
