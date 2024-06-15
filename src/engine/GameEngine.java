package engine;

import java.util.List;

public class GameEngine implements Runnable{
    private Thread gameThread;
    private List<Runtime> runtimes;
    private boolean running;
    private int fps = 60;

    private final Display display;

    public GameEngine() {
        running = false;
        display = new Display(800, 600, "Game Engine");
    }

    public void start() {
        if (running) return;
        running = true;
        gameThread = new Thread(this);
        gameThread.start();

        // find all runtime implementations & trigger start method
        if(runtimes != null) {
            for(Runtime runtime : runtimes) {
                runtime.Start();
            }
        }
    }

    public void stop() {
        if(!running) return;
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nanosecondsPerTick = 1000000000.0 / fps;

        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nanosecondsPerTick;
            lastTime = now;

            boolean shouldRender = false;

            while (delta >= 1) {
                update();
                delta -= 1;
                shouldRender = true;
            }

            if (shouldRender) {
                render();
            }

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
            }
        }
    }

    private void update() {
        if (runtimes != null) {
            for(Runtime runtime : runtimes) {
                runtime.Update();
            }
        }
    }

    private void render() {
        display.render();
    }
}
