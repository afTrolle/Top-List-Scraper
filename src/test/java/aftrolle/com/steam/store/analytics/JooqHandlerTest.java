package aftrolle.com.steam.store.analytics;

import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JooqHandlerTest {

    @Test
    void createTable() throws Exception {

        JooqHandler jooqHandler = new JooqHandler();

        DSLContext dslContext = jooqHandler.connectToDB();
        jooqHandler.createTable(dslContext);

    }
}