package databaseAccessTests;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import dataAccess.SQLDataAccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class SQLDataAccessTest {
    private DataAccess getDataAccess() throws Exception {
        return new SQLDataAccess();
    }

    @BeforeEach
    void clear() throws Exception {
        DataAccess dataAccess = getDataAccess();
        dataAccess.clear();
    }

    @Test
    void clearDoesNotThrowException () throws Exception {
        DataAccess dataAccess = getDataAccess();
        assertDoesNotThrow(dataAccess::clear);
    }
}
