package com.learning.rag.startup;

import com.learning.rag.database.model.DatabaseInfo;
import com.learning.rag.database.service.DatabaseHealthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseStartupRunner implements CommandLineRunner {


    private final DatabaseHealthService databaseHealthService;

    public DatabaseStartupRunner(DatabaseHealthService databaseHealthService) {
        this.databaseHealthService = databaseHealthService;
    }

    @Override
    public void run(String... args)  {
        DatabaseInfo databaseInfo = databaseHealthService.getDatabaseInfo();

        System.out.println("=================================");
        System.out.println("Database Connected Successfully");
        System.out.println(databaseInfo.version());
        System.out.println("=================================");
    }
}
