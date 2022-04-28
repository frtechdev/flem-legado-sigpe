package br.org.flem.primeiroemprego.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author ILFernandes
 */
public class ExecutorThreads {

    private static final ExecutorThreads instancia = new ExecutorThreads();
    private final ExecutorService executor = Executors.newFixedThreadPool(1);

    private ExecutorThreads() {
    }

    public static ExecutorThreads getInstancia() {
        return instancia;
    }

    public void executar(Runnable thread) {
        executor.execute(thread);
    }

    public static void shutdownNow(){
        ExecutorThreads.getInstancia().executor.shutdownNow();
    }

}